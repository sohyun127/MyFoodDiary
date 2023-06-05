package com.example.myfooddiary.Home.Ingredient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.ActivityIngredientOcrTextBinding;

public class IngredientOcrTextActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityIngredientOcrTextBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIngredientOcrTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String result = getIntent().getStringExtra("text");

        binding.tvIngredientOcrText.setText(result);

        binding.btnIngredientOcrTextEdit.setOnClickListener(this);
        binding.btnIngredientOcrTextComplete.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ingredient_ocr_text_complete:
                Intent intent1 = new Intent(this, IngredientOcrCameraActivity.class);
                startActivity(intent1);
                finish();
                break;
        }

    }
}
