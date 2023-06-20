package com.example.myfooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfooddiary.Home.MainActivity;
import com.example.myfooddiary.databinding.ActivitySigninBinding;
import com.google.android.material.snackbar.Snackbar;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySigninBinding binding;
    private Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        it = getIntent();

        TextView tvSignUp = binding.btnSigninSignup;
        tvSignUp.setOnClickListener(this);

        Button btnLogin = binding.btnSigninLogin;
        btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signin_signup:
                Intent signup = new Intent(this, SignupActivity.class);
                startActivity(signup); //회원가입 페이지로 이동
                finish();
                break;
            case R.id.btn_signin_login:
                if (checkLogin()) {
                    Intent main = new Intent(this, MainActivity.class);
                    Snackbar.make(binding.getRoot(), "로그인에 성공했습니다.", Snackbar.LENGTH_SHORT).show();
                    startActivity(main);
                    finish();
                    break;
                } else {
                    Snackbar.make(binding.getRoot(), "로그인에 실패했습니다.", Snackbar.LENGTH_SHORT).show();
                    break;
                }

        }


    }

    //아이디, 비밀번호 확인 함수
    public boolean checkLogin() {

        String id = it.getStringExtra("id");
        String pw = it.getStringExtra("pw");

        if (binding.etSigninId.getText().toString().equals(id) && binding.etSigninPw.getText().toString().equals(pw)) {
            return true;
        } else {
            return false;
        }

    }


}