package com.example.myfooddiary.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import com.example.myfooddiary.Home.Ingredient.IngredientFragment;
import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Fragment ingredientFragment = new IngredientFragment();
        Fragment recipeFragment = new RecipeFragment();
        Fragment recordFragment = new RecordFragment();

        startFragment(ingredientFragment);

        BottomNavigationView bnv = binding.bnvMain;

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_ingredient:
                        changeFragment(ingredientFragment);
                        return true;
                    case R.id.menu_recipe:
                        changeFragment(recipeFragment);
                        return true;
                    case R.id.menu_record:
                        changeFragment(recordFragment);
                        return true;
                }
                return false;
            }
        });


    }


    @Override
    public void onClick(View v) {

    }

    private void changeFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.fcv_main, fragment).commit();

    }

    private void startFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.fcv_main, fragment).commit();

    }

}