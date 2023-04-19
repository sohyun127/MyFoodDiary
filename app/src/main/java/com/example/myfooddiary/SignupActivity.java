package com.example.myfooddiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.myfooddiary.databinding.ActivitySignupBinding;
import com.google.android.material.snackbar.Snackbar;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignupBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button btnComplete = binding.btnSignupComplete;
        btnComplete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup_complete:
                Intent it = new Intent(this, SigninActivity.class);
                Snackbar.make(binding.getRoot(), "회원가입에 성공했습니다.", Snackbar.LENGTH_SHORT).show();

                it.putExtra("id", binding.etSignupId.getText().toString());
                it.putExtra("pw", binding.etSignupPw.getText().toString());
                startActivity(it);
                finish();
        }


    }
}