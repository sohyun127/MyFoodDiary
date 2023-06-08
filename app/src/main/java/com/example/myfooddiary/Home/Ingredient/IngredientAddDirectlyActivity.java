package com.example.myfooddiary.Home.Ingredient;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfooddiary.databinding.ActivityIngredientAddDirectlyBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IngredientAddDirectlyActivity  extends AppCompatActivity {

    private ActivityIngredientAddDirectlyBinding binding;

    private RecyclerView.Adapter adapterMeat;
    private RecyclerView.Adapter adapterFish;
    private RecyclerView.Adapter adapterVegetable;
    private RecyclerView.Adapter adapterFruit;
    private RecyclerView.Adapter adapterEtc;
    private ArrayList<Ingredient> meatList;
    private ArrayList<Ingredient> fishList;
    private ArrayList<Ingredient> vegetableList;
    private ArrayList<Ingredient> fruitList;
    private ArrayList<Ingredient> etcList;
    private RecyclerView recyclerMeatView;
    private RecyclerView recyclerFishView;
    private RecyclerView recyclerVegetableView;
    private RecyclerView recyclerFruitView;
    private RecyclerView recyclerEtcView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FirebaseApp.initializeApp(this);

        binding = ActivityIngredientAddDirectlyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setAdapter();
    }

    private void setAdapter(){

        meatList = new ArrayList<>();
        fishList = new ArrayList<>();
        vegetableList = new ArrayList<>();
        fruitList = new ArrayList<>();
        etcList = new ArrayList<>();

        recyclerMeatView = binding.rvIngredientAddDirectlyMeat;
        recyclerMeatView.setHasFixedSize(true);
        recyclerFishView = binding.rvIngredientAddDirectlyFish;
        recyclerFishView.setHasFixedSize(true);
        recyclerVegetableView=  binding.rvIngredientAddDirectlyVegetable;
        recyclerVegetableView.setHasFixedSize(true);
        recyclerFruitView=binding.rvIngredientAddDirectlyFruit;
        recyclerFruitView.setHasFixedSize(true);
        recyclerEtcView = binding.rvIngredientAddDirectlyEtc;
        recyclerEtcView.setHasFixedSize(true);

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("ingredient");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                meatList.clear();
                fishList.clear();
                vegetableList.clear();
                fruitList.clear();
                etcList.clear();

                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    Ingredient ingredient = snapshot.getValue(Ingredient.class);

                    switch (ingredient.getTypeId()){
                        case 1 :
                            meatList.add(ingredient);
                            break;
                        case 2:
                            fishList.add(ingredient);
                            break;
                        case 3:
                            vegetableList.add(ingredient);
                            break;
                        case 4:
                            fruitList.add(ingredient);
                            break;
                        case 5:
                            etcList.add(ingredient);
                            break;

                    }

                }
                adapterMeat.notifyDataSetChanged();
                adapterFish.notifyDataSetChanged();
                adapterVegetable.notifyDataSetChanged();
                adapterFruit.notifyDataSetChanged();
                adapterEtc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("db error",error.toString());
                Toast.makeText(getApplicationContext(),"db 오류",Toast.LENGTH_SHORT).show();
            }
        });


        adapterMeat = new IngredientAdapter(meatList,getApplicationContext());
        recyclerMeatView.setAdapter(adapterMeat);

        adapterFish = new IngredientAdapter(fishList,getApplicationContext());
        recyclerFishView.setAdapter(adapterFish);

        adapterVegetable = new IngredientAdapter(vegetableList,getApplicationContext());
        recyclerVegetableView.setAdapter(adapterVegetable);

        adapterFruit = new IngredientAdapter(fruitList,getApplicationContext());
        recyclerFruitView.setAdapter(adapterFruit);

        adapterEtc = new IngredientAdapter(etcList,getApplicationContext());
        recyclerEtcView.setAdapter(adapterEtc);

    }

}
