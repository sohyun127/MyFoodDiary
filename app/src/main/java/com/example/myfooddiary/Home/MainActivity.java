package com.example.myfooddiary.Home;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myfooddiary.Home.Ingredient.IngredientFragment;
import com.example.myfooddiary.Home.recipe.RecipeFragment;
import com.example.myfooddiary.Home.record.RecordDetailsFragment;
import com.example.myfooddiary.Home.record.RecordFragment;
import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FirebaseApp.initializeApp(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Fragment ingredientFragment = new IngredientFragment();
        Fragment recipeFragment = new RecipeFragment();
        Fragment recordFragment = new RecordFragment();
        Fragment mypageFragment = new MyPageFragment();

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
                    case R.id.menu_my_page:
                        changeFragment(mypageFragment);
                        return true;
                }
                return false;
            }
        });

        if(getIntent().hasExtra("fragmentToLoad")){
            String fragmentToLoad = getIntent().getStringExtra("fragmentToLoad");
            if (fragmentToLoad.equals("recordFragment")) {
                // RecordFragment 초기화 코드
                long now = System.currentTimeMillis();
                DateFormat formatter = new SimpleDateFormat("yyyy년M월d일");
                Date date = new Date(now);
                String getDay = formatter.format(date);
                Bundle bundle = new Bundle();
                bundle.putString("day", getDay);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = new RecordDetailsFragment();
                fragment.setArguments(bundle);
                transaction.replace(R.id.fcv_main, fragment);
                transaction.commit();
            }
        }


    }



    @Override
    public void onClick(View v) {

    }

    private void changeFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.fcv_main, fragment).commit();

    }

}