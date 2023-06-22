package com.example.myfooddiary.Home.record;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.ActivityRecordAddBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class RecordAddActivity extends AppCompatActivity implements View.OnClickListener {


    private ActivityRecordAddBinding binding;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String time = "아침";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecordAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.checkRecordAddMorning.setOnClickListener(this);
        binding.checkRecordAddLaunch.setOnClickListener(this);
        binding.checkRecordAddDinner.setOnClickListener(this);
        binding.checkRecordAddSnack.setOnClickListener(this);
        binding.btnRecordAddComplete.setOnClickListener(this);

        setClickEventOnToolBar();
        getPhoto();
    }

    private void setClickEventOnToolBar() {
        Toolbar toolbar = binding.tbRecordAddToolBar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //db에 식단 기록 저장
    private void addRecord(String date) {
        Drawable image = binding.ivRecordAdd.getDrawable();
        String sImage = "";
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] foodImage = stream.toByteArray();

        sImage = byteArrayToBinaryString(foodImage);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("record");
        databaseReference.child(date).child(binding.etRecordAddFood.getText().toString()).setValue(new Record(time,
                binding.etRecordAddFood.getText().toString(),
                binding.etRecordAddKcal.getText().toString(), sImage));
    }

    //사진 불러오기
    private void getPhoto() {
        binding.ivRecordAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.setAction(Intent.ACTION_PICK);
                activityResultLauncher.launch(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        Uri uri = intent.getData();
                        Glide.with(binding.getRoot()).load(uri).into(binding.ivRecordAdd);
                    }
                }
            }
    );


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_record_add_morning:
                binding.checkRecordAddLaunch.setChecked(false);
                binding.checkRecordAddDinner.setChecked(false);
                binding.checkRecordAddSnack.setChecked(false);
                time = "아침";
                break;
            case R.id.check_record_add_launch:
                binding.checkRecordAddMorning.setChecked(false);
                binding.checkRecordAddDinner.setChecked(false);
                binding.checkRecordAddSnack.setChecked(false);
                time = "점심";
                break;
            case R.id.check_record_add_dinner:
                binding.checkRecordAddMorning.setChecked(false);
                binding.checkRecordAddLaunch.setChecked(false);
                binding.checkRecordAddSnack.setChecked(false);
                time = "저녁";
                break;
            case R.id.check_record_add_snack:
                binding.checkRecordAddMorning.setChecked(false);
                binding.checkRecordAddLaunch.setChecked(false);
                binding.checkRecordAddDinner.setChecked(false);
                time = "간식";
                break;
            case R.id.btn_record_add_complete:
                addRecord(getIntent().getStringExtra("date"));
                finish();
                break;
        }

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static String byteArrayToBinaryString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; ++i) {
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }

    public static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) {
            if (((n >> bit) & 1) > 0) {
                sb.setCharAt(7 - bit, '1');
            }
        }
        return sb.toString();
    }
}
