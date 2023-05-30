package com.example.myfooddiary.Home.Ingredient;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfooddiary.databinding.ActivityIngredientOcrTextBinding;

public class IngredientOcrTextActivity extends AppCompatActivity {

    private ActivityIngredientOcrTextBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIngredientOcrTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}
