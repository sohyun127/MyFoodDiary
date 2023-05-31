package com.example.myfooddiary.Home.Ingredient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.ActivityIngredientOcrImageBinding;

public class IngredientOcrImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityIngredientOcrImageBinding binding;
    private String ocrText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIngredientOcrImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnIngredientOcrImageCancel.setOnClickListener(this);
        binding.btnIngredientOcrImageComplete.setOnClickListener(this);
        setImage();
        setText();


    }

    private void setImage() {

        byte[] imageData = getIntent().getByteArrayExtra("image_data");
        if (imageData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            binding.ivIngredientOcrImage.setImageBitmap(bitmap);
        }

    }

    private void setText() {
        ocrText = getIntent().getStringExtra("result_data");
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
                Intent intent2 = new Intent(this, IngredientOcrTextActivity.class);
                intent2.putExtra("text", ocrText);
                startActivity(intent2);
                finish();
                break;

        }

    }
}

