package com.example.myfooddiary.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfooddiary.Home.MainActivity;
import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.ActivitySigninBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySigninBinding binding;
    private Intent it;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


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
                checkLogin();

        }


    }

    //아이디, 비밀번호 확인 함수
    public void checkLogin() {

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user_info");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    if(binding.etSigninId.getText().toString().equals(user.getId())&&binding.etSigninPw.getText().toString().equals(user.getPw())){
                        Intent main = new Intent(getApplicationContext(), MainActivity.class);
                        Snackbar.make(binding.getRoot(), "로그인에 성공했습니다.", Snackbar.LENGTH_SHORT).show();
                        main.putExtra("user_id",user.getId());
                        startActivity(main);
                        finish();
                        break;
                    }
                }
                Snackbar.make(binding.getRoot(), "로그인에 실패했습니다.", Snackbar.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("db error", error.toString());
                Toast.makeText(getApplicationContext(), "db 오류", Toast.LENGTH_SHORT).show();
            }
        });

    }


}