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

        setView();
        setClickEventOnToolBar();

    }

    private void setView(){

        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        int position = getIntent().getIntExtra("id",0);

        databaseReference = database.getReference("recipe");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                arrayList.clear();

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);

                    arrayList.add(recipe);

                }

                Glide.with(binding.getRoot()).load(arrayList.get(position).getUrl()).into(binding.ivRecipeDetail);
                binding.tvRecipeTitle.setText(arrayList.get(position).getName());
                binding.tvRecipeKcal.setText(arrayList.get(position).getKcal());
                binding.tvRecipeIngredient.setText(arrayList.get(position).getIngredient());
                binding.tvRecipeOrder.append("1. " + arrayList.get(position).getRecipe1());
                binding.tvRecipeOrder.append("\n\n2. " + arrayList.get(position).getRecipe2());
                binding.tvRecipeOrder.append("\n\n3. " + arrayList.get(position).getRecipe3());
                binding.tvRecipeOrder.append("\n\n4. " + arrayList.get(position).getRecipe4());
                binding.tvRecipeOrder.append("\n\n5. " + arrayList.get(position).getRecipe5());
                binding.tvRecipeOrder.append("\n\n6. " + arrayList.get(position).getRecipe6());
                binding.tvRecipeOrder.append("\n\n7. " + arrayList.get(position).getRecipe7());
                binding.tvRecipeOrder.append("\n\n8. " + arrayList.get(position).getRecipe8());
                binding.tvRecipeOrder.append("\n\n9. " + arrayList.get(position).getRecipe9());
                binding.tvRecipeOrder.append("\n\n10. " + arrayList.get(position).getRecipe10());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("db error", error.toString());
                Toast.makeText(getApplicationContext(), "db 오류", Toast.LENGTH_SHORT).show();
            }

        });


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
