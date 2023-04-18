package com.example.myfooddiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myfooddiary.databinding.ActivitySigninBinding;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySigninBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView signUp = binding.tvSigninSignup;
        signUp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_signin_signup:
                Intent it = new Intent(this,SignupActivity.class);
                startActivity(it); //회원가입 페이지로 이동
                finish();
        }




    }
}