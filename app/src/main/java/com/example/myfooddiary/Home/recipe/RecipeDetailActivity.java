package com.example.myfooddiary.Home.recipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myfooddiary.Home.Ingredient.IngredientUser;
import com.example.myfooddiary.Home.LoadingDialog;
import com.example.myfooddiary.Home.MainActivity;
import com.example.myfooddiary.Home.record.Record;
import com.example.myfooddiary.databinding.ActivityRecipeDetailBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecipeDetailActivity extends AppCompatActivity {

    private ActivityRecipeDetailBinding binding;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Recipe> arrayList;
    private DatabaseReference databaseReferenceUser;
    private DatabaseReference databaseReferenceRecord;
    private LoadingDialog loadingDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding = ActivityRecipeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setData();
        setClickEventOnToolBar();
        setOnClickEventOnRecordButton();

    }

    private void setOnClickEventOnRecordButton(){
        binding.btnRecipeDetailComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecord();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("fragmentToLoad","recordFragment");
                removeData();
                intent.putExtra("user_id",getIntent().getStringExtra("user_id"));
                startActivity(intent);
                finish();
            }
        });
    }

    private void removeData() {
        int position = getIntent().getIntExtra("id", 0);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("ingredient_user");
        databaseReference.child(arrayList.get(position).getMainIngredient()).removeValue();
    }

    //식단 자동으로 기록하기
    private void addRecord() {
        Drawable image = binding.ivRecipeDetail.getDrawable();
        String sImage = "";
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] foodImage = stream.toByteArray();

        sImage = byteArrayToBinaryString(foodImage);

        long now = System.currentTimeMillis();
        DateFormat formatter = new SimpleDateFormat("yyyy년M월d일");
        DateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        Date date = new Date(now);
        String getDay = formatter.format(date);
        String getTime = timeFormatter.format(date);

        databaseReferenceRecord = database.getReference("record");
        databaseReferenceRecord.child(getDay).push().setValue(new Record(getTime,
                binding.tvRecipeTitle.getText().toString(), binding.tvRecipeKcal.getText().toString().
                substring(0,binding.tvRecipeKcal.getText().toString().length()-4), sImage));
    }


    //db에서 데이터 불러오기
    private void setData() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();


        databaseReference = database.getReference("recipe");
        databaseReferenceUser = database.getReference("ingredient_user");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshotUser) {
                        arrayList.clear();

                        for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                            Recipe recipe = snapshot.getValue(Recipe.class);

                            for (DataSnapshot snapshot2 : snapshotUser.getChildren()) {
                                IngredientUser ingredientUser = snapshot2.getValue(IngredientUser.class);

                                if (recipe.getMainIngredient().contains(ingredientUser.getName())) {

                                    arrayList.add(recipe);
                                    break;
                                }

                            }

                        }

                        setView();
                        loadingDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("db error", error.toString());
                        Toast.makeText(getApplicationContext(), "db 오류", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    //화면 구성하기
    private void setView() {

        int position = getIntent().getIntExtra("id", 0);

        Glide.with(binding.getRoot()).load(arrayList.get(position).getUrl()).into(binding.ivRecipeDetail);
        binding.tvRecipeTitle.setText(arrayList.get(position).getName());
        binding.tvRecipeKcal.setText(arrayList.get(position).getKcal()+"kcal");
        binding.tvRecipeIngredient.setText(arrayList.get(position).getIngredient());

        if (!arrayList.get(position).getRecipe1().isBlank()) {
            binding.tvRecipeOrder.append("1. " + arrayList.get(position).getRecipe1());
        }
        if (!arrayList.get(position).getRecipe2().isBlank()) {
            binding.tvRecipeOrder.append("\n\n2. " + arrayList.get(position).getRecipe2());
        }
        if (!arrayList.get(position).getRecipe3().isBlank()) {
            binding.tvRecipeOrder.append("\n\n3. " + arrayList.get(position).getRecipe3());
        }
        if (!arrayList.get(position).getRecipe4().isBlank()) {
            binding.tvRecipeOrder.append("\n\n4. " + arrayList.get(position).getRecipe4());
        }
        if (!arrayList.get(position).getRecipe5().isBlank()) {
            binding.tvRecipeOrder.append("\n\n5. " + arrayList.get(position).getRecipe5());
        }
        if (!arrayList.get(position).getRecipe6().isBlank()) {
            binding.tvRecipeOrder.append("\n\n6. " + arrayList.get(position).getRecipe6());
        }
        if (!arrayList.get(position).getRecipe7().isBlank()) {
            binding.tvRecipeOrder.append("\n\n7. " + arrayList.get(position).getRecipe7());
        }
        if (!arrayList.get(position).getRecipe8().isBlank()) {
            binding.tvRecipeOrder.append("\n\n8. " + arrayList.get(position).getRecipe8());
        }
        if (!arrayList.get(position).getRecipe9().isBlank()) {
            binding.tvRecipeOrder.append("\n\n9. " + arrayList.get(position).getRecipe9());
        }
        if (!arrayList.get(position).getRecipe10().isBlank()) {
            binding.tvRecipeOrder.append("\n\n10. " + arrayList.get(position).getRecipe10());
        }
    }


    private void setClickEventOnToolBar() {
        Toolbar toolbar = binding.tbRecipeToolBar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static String byteArrayToBinaryString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; ++i) {
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }

    public static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((n >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }



}
