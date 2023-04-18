package com.example.myfooddiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.myfooddiary.databinding.ActivitySigninBinding;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySigninBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


    @Override
    public void onClick(View v) {





    }
}