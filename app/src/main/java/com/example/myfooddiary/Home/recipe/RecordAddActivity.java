package com.example.myfooddiary.Home.recipe;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfooddiary.databinding.ActivityMainBinding;
import com.example.myfooddiary.databinding.ActivityRecordAddBinding;

public class RecordAddActivity extends AppCompatActivity {


    private ActivityRecordAddBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecordAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
