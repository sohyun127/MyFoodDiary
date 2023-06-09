package com.example.myfooddiary.Home.Ingredient;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfooddiary.Home.LoadingDialog;
import com.example.myfooddiary.Home.MainActivity;
import com.example.myfooddiary.databinding.ActivityIngredientAddDirectlyBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IngredientAddDirectlyActivity extends AppCompatActivity {

    private ActivityIngredientAddDirectlyBinding binding;

    private RecyclerView.Adapter adapter;

    private ArrayList<Ingredient> searchList;
    private ArrayList<Ingredient> fullList;

    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private EditText etSearch;
    private  IngredientAddDirectlyDialog dialog;
    private LoadingDialog loadingDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding = ActivityIngredientAddDirectlyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setClickEventOnToolBar();
        setEventOnSearchBar();
    }

    //상단 툴바 클릭 이벤트
    private void setClickEventOnToolBar() {
        Toolbar toolbar = binding.tbIngredientAddDirectlyToolBar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //재료 추가 시 수량 입력 다이얼로그
    private void setDialog(String name){
        dialog= new IngredientAddDirectlyDialog(this,name);
        dialog.show();
    }

    //재료 검색 기능
    private void setEventOnSearchBar() {

        setAdapter();

        adapter = new IngredientAdapter(fullList, getApplicationContext(), new IngredientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d("클릭", String.valueOf(fullList.get(position).getName()));
                setDialog(fullList.get(position).getName());
            }
        });
        recyclerView.setAdapter(adapter);

        etSearch = binding.etIngredientAddDirectly;


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = etSearch.getText().toString();
                search(text);

            }
        });

    }


    //재료 검색 시 화면 재출력
    private void search(String charText) {
        fullList.clear();

        if (charText.length() == 0) {
            fullList.addAll(searchList);
        } else {
            for (int i = 0; i < searchList.size(); i++) {
                if (searchList.get(i).getName().toLowerCase().contains(charText)) {
                    fullList.add(searchList.get(i));
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    //화면 구성
    private void setAdapter() {

        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();

        fullList = new ArrayList<>();
        searchList = new ArrayList<>();


        recyclerView = binding.rvIngredientAddDirectly;
        recyclerView.setHasFixedSize(true);


        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("ingredient");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                fullList.clear();

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    Ingredient ingredient = snapshot.getValue(Ingredient.class);

                    fullList.add(ingredient);
                    searchList.add(ingredient);
                }
                adapter.notifyDataSetChanged();
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("db error", error.toString());
                Toast.makeText(getApplicationContext(), "db 오류", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //상단 툴바 뒤로가기 누를 시 이동
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
