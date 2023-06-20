package com.example.myfooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myfooddiary.Home.User;
import com.example.myfooddiary.databinding.ActivitySignupBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignupBinding binding;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button btnComplete = binding.btnSignupComplete;
        btnComplete.setOnClickListener(this);
        binding.checkSignupWomen.setOnClickListener(this);
        binding.checkSignupMen.setOnClickListener(this);
        setClickEventOnToolBar();

    }


    private void setClickEventOnToolBar() {
        Toolbar toolbar = binding.tbSignupToolBar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup_complete:
                if(TextUtils.isEmpty(binding.etSignupId.getText().toString())||TextUtils.isEmpty(binding.etSignupPw.getText().toString())||TextUtils.isEmpty(binding.etSignupNickname.getText().toString())||
                        TextUtils.isEmpty(binding.etSignupHeight.getText().toString())||TextUtils.isEmpty(binding.etSignupWeight.getText().toString())){
                    Snackbar.make(binding.getRoot(), "회원가입에 실패했습니다.", Snackbar.LENGTH_SHORT).show();
                }else{
                    Intent it = new Intent(this, SigninActivity.class);
                    Snackbar.make(binding.getRoot(), "회원가입에 성공했습니다.", Snackbar.LENGTH_SHORT).show();
                    saveData();
                    startActivity(it);
                    finish();
                }
                break;
            case R.id.check_signup_women:
                binding.checkSignupMen.setChecked(false);
                break;
            case R.id.check_signup_men:
                binding.checkSignupWomen.setChecked(false);
                break;
        }


    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, SigninActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveData(){
        String gender;
        if(binding.checkSignupMen.isChecked()){
            gender="남성";
        }else {
            gender="여성";
        }
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user_info");
        databaseReference.child(binding.etSignupId.getText().toString()).setValue(new User(binding.etSignupId.getText().toString(),
                binding.etSignupPw.getText().toString(),binding.etSignupNickname.getText().toString(),gender,binding.etSignupBirth.getText().toString(),
                binding.etSignupHeight.getText().toString(),binding.etSignupWeight.getText().toString()));


    }
}