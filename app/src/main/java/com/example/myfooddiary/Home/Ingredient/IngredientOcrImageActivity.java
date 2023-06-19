package com.example.myfooddiary.Home.Ingredient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfooddiary.BuildConfig;
import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.ActivityIngredientOcrImageBinding;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class IngredientOcrImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityIngredientOcrImageBinding binding;

    private static final String TAG = "OCR";

    private static final String CLOUD_VISION_API_KEY = BuildConfig.API_KEY;
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static List<String> ingredient= List.of("호박","당근","고구마","버섯","딸기","사과","설탕","고추","고추장","파","바나나");
    private static List<String> cow=List.of("살치","채끝","부채","안창","토시","치마","한우","안심","스테이크","차돌박이","양지","제비추리","소고기","등심");
    private static List<String> fork = List.of("돼지","삼겹살","대패","목살","항정살","가브리살");

    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIngredientOcrImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("ingredient_ocr");
        databaseReference.removeValue();

        binding.btnIngredientOcrImageCancel.setOnClickListener(this);
        binding.btnIngredientOcrImageComplete.setOnClickListener(this);
        setImage();



    }

    private void setImage() {

        byte[] imageData = getIntent().getByteArrayExtra("image_data");
        Log.d("after", imageData.toString());
        if (imageData != null) {
            bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            binding.ivIngredientOcrImage.setImageBitmap(bitmap);
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_ingredient_ocr_image_cancel:
                Intent intent1 = new Intent(this, IngredientOcrCameraActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_ingredient_ocr_image_complete:
                callCloudVision(bitmap);
                break;

        }

    }

    private void callCloudVision(final Bitmap bitmap) {

        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, String> labelDetectionTask = new LableDetectionTask(this, prepareAnnotationRequest(bitmap));
            labelDetectionTask.execute();
        } catch (IOException e) {
            Log.d(TAG, "failed to make API request because of other IOException " +
                    e.getMessage());
        }
    }

    private static class LableDetectionTask extends AsyncTask<Object, Void, String> {
        private final WeakReference<IngredientOcrImageActivity> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;

        LableDetectionTask(IngredientOcrImageActivity activity, Vision.Images.Annotate annotate) {
            mActivityWeakReference = new WeakReference<>(activity);
            mRequest = annotate;
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Log.d(TAG, "created Cloud Vision request object, sending request");
                BatchAnnotateImagesResponse response = mRequest.execute();
                return String.valueOf(convertResponseToString(response));

            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return "Cloud Vision API request failed. Check logs for details.";
        }

        protected void onPostExecute(String result) {
            IngredientOcrImageActivity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {

                Intent intent2 = new Intent(activity, IngredientOcrTextActivity.class);
                intent2.putExtra("text", result);
                activity.startActivity(intent2);
                activity.finish();


            }
        }
    }

    private static List<String> convertResponseToString(BatchAnnotateImagesResponse response) {
        String message = "I found these things:\n\n";

        List<String> annotations = new ArrayList<>();
        List<String> position = new ArrayList<>();
        List<String> food = new ArrayList<>();
        int countPosition = 0;

        List<EntityAnnotation> labels = response.getResponses().get(0).getTextAnnotations();


        if (labels != null) {
            for (int i = 1; i < labels.size(); i++) { // i의 초기값을 1로 변경하여 첫 번째 요소를 건너뜁니다
                EntityAnnotation annotation = labels.get(i);
                String description = annotation.getDescription();

                for (int j = 0; j < ingredient.size(); j++) {
                    if (description.contains(ingredient.get(j))) {
                        position.add(String.valueOf(annotation.getBoundingPoly().getVertices().get(0).getY()));
                        food.add(ingredient.get(j));
                    }

                    if(description.equals("수량")){
                        countPosition=annotation.getBoundingPoly().getVertices().get(0).getX();
                        Log.d("cloud", String.valueOf(annotation.getBoundingPoly().getVertices().get(0).getX()));
                    }
               }

                for(int j =0;j<position.size();j++ ){

                    if(annotation.getBoundingPoly().getVertices().get(0).getY()>Integer.valueOf(position.get(j))-10&&
                            annotation.getBoundingPoly().getVertices().get(0).getY()<Integer.valueOf(position.get(j))+10){

                        if(annotation.getBoundingPoly().getVertices().get(j).getX()>countPosition&&
                                annotation.getBoundingPoly().getVertices().get(j).getX()<countPosition+30
                        ){
                            addOcr(food.get(j),description);
                            addIngredient(food.get(j),description);
                            Log.d(String.valueOf(food.get(j)),description);
                            Log.d(String.valueOf(food.get(j)), "x:"+ annotation.getBoundingPoly().getVertices().get(0).getX());

                        }
                    }

                }

            }
        } else {
            annotations.add("nothing");
        }


        return annotations;

    }

    public static void addIngredient(String name, String count) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("ingredient_user");
        databaseReference.child(name).setValue(new IngredientUser(name, count));

    }

    public static void addOcr(String name, String count){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("ingredient_ocr");
        databaseReference.child(name).setValue(new IngredientUser(name, count));

    }



    private Vision.Images.Annotate prepareAnnotationRequest(Bitmap bitmap) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(CLOUD_VISION_API_KEY) {

                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);

                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            Image base64EncodedImage = new Image();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature textDetection = new Feature();
                textDetection.setType("TEXT_DETECTION");
                textDetection.setMaxResults(MAX_LABEL_RESULTS);
                add(textDetection);
            }});

            add(annotateImageRequest);
        }});


        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);

        annotateRequest.setDisableGZipContent(true);
        Log.d(TAG, "created Cloud Vision request object, sending request");
        return annotateRequest;
    }
}

