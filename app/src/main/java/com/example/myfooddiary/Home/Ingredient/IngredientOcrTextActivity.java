package com.example.myfooddiary.Home.Ingredient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfooddiary.Home.MainActivity;
import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.ActivityIngredientOcrTextBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IngredientOcrTextActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityIngredientOcrTextBinding binding;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<IngredientUser> userArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIngredientOcrTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnIngredientOcrTextComplete.setOnClickListener(this);

        setData();


    }


    public void setData() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("ingredient_ocr");
        userArrayList = new ArrayList<>();
        recyclerView = binding.rvIngredientOcrText;
        recyclerView.setHasFixedSize(true);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                userArrayList.clear();

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    IngredientUser ingredient = snapshot.getValue(IngredientUser.class);
                    userArrayList.add(ingredient);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("db error", error.toString());
                Toast.makeText(getApplicationContext(), "db 오류", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new IngredientOcrTextAdapter(userArrayList, getApplicationContext(), new IngredientOcrTextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
            }
        });
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ingredient_ocr_text_complete:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                finish();
                break;
        }

    }

}
