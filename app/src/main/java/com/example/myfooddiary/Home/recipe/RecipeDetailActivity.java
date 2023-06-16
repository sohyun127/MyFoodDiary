package com.example.myfooddiary.Home.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myfooddiary.databinding.ActivityRecipeDetailBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private ActivityRecipeDetailBinding binding;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<Recipe> arrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding = ActivityRecipeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setData();
        setClickEventOnToolBar();

    }

    private void setData() {

        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();


        databaseReference = database.getReference("recipe");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                arrayList.clear();

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);

                    arrayList.add(recipe);

                }

                setView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("db error", error.toString());
                Toast.makeText(getApplicationContext(), "db 오류", Toast.LENGTH_SHORT).show();
            }

        });


    }

    private void setView() {

        int position = getIntent().getIntExtra("id", 0);

        Glide.with(binding.getRoot()).load(arrayList.get(position).getUrl()).into(binding.ivRecipeDetail);
        binding.tvRecipeTitle.setText(arrayList.get(position).getName());
        binding.tvRecipeKcal.setText(arrayList.get(position).getKcal());
        binding.tvRecipeIngredient.setText(arrayList.get(position).getIngredient());

        if (!arrayList.get(position).getRecipe1().isBlank()) {
            binding.tvRecipeOrder.append("1. " + arrayList.get(position).getRecipe1());
        }
        if (!arrayList.get(position).getRecipe2().isBlank()) {
            binding.tvRecipeOrder.append("\n\n2. " + arrayList.get(position).getRecipe2());
        }
        if (!arrayList.get(position).getRecipe3().isBlank()) {
            binding.tvRecipeOrder.append("\n\n3. " + arrayList.get(position).getRecipe3());
        }
        if (!arrayList.get(position).getRecipe4().isBlank()) {
            binding.tvRecipeOrder.append("\n\n4. " + arrayList.get(position).getRecipe4());
        }
        if (!arrayList.get(position).getRecipe5().isBlank()) {
            binding.tvRecipeOrder.append("\n\n5. " + arrayList.get(position).getRecipe5());
        }
        if (!arrayList.get(position).getRecipe6().isBlank()) {
            binding.tvRecipeOrder.append("\n\n6. " + arrayList.get(position).getRecipe6());
        }
        if (!arrayList.get(position).getRecipe7().isBlank()) {
            binding.tvRecipeOrder.append("\n\n7. " + arrayList.get(position).getRecipe7());
        }
        if (!arrayList.get(position).getRecipe8().isBlank()) {
            binding.tvRecipeOrder.append("\n\n8. " + arrayList.get(position).getRecipe8());
        }
        if (!arrayList.get(position).getRecipe9().isBlank()) {
            binding.tvRecipeOrder.append("\n\n9. " + arrayList.get(position).getRecipe9());
        }
        if (!arrayList.get(position).getRecipe10().isBlank()) {
            binding.tvRecipeOrder.append("\n\n10. " + arrayList.get(position).getRecipe10());
        }
    }


    private void setClickEventOnToolBar() {
        Toolbar toolbar = binding.tbRecipeToolBar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
