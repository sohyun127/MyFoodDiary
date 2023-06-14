package com.example.myfooddiary.Home.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfooddiary.databinding.FragmentRecipeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecipeFragment extends Fragment {

    private FragmentRecipeBinding binding;

    private RecyclerView.Adapter adapter;
    private ArrayList<Recipe> arrayList;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setAdapter() {


        arrayList = new ArrayList<>();

        recyclerView = binding.rvRecipe;
        recyclerView.setHasFixedSize(true);

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

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("db error", error.toString());
                Toast.makeText(getContext(), "db 오류", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new RecipeAdapter(arrayList, getContext(), new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });


        recyclerView.setAdapter(adapter);

    }
}