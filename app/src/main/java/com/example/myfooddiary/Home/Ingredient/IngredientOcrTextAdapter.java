package com.example.myfooddiary.Home.Ingredient;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfooddiary.databinding.ItemOcrIngredientBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class IngredientOcrTextAdapter extends RecyclerView.Adapter<IngredientOcrTextAdapter.IngredientOcrTextViewHolder> {


    private ArrayList<IngredientUser> arrayList;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener mListener = null;

    public IngredientOcrTextAdapter(ArrayList<IngredientUser> arrayList, Context context, IngredientOcrTextAdapter.OnItemClickListener listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public IngredientOcrTextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOcrIngredientBinding binding = ItemOcrIngredientBinding.inflate(LayoutInflater.from(context), parent, false);
        return new IngredientOcrTextViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientOcrTextAdapter.IngredientOcrTextViewHolder holder, int position) {
        holder.bindItem(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class IngredientOcrTextViewHolder extends RecyclerView.ViewHolder {

        ItemOcrIngredientBinding binding;

        public IngredientOcrTextViewHolder(ItemOcrIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("클릭","클릭");
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }

                    binding.etIngredientOcrTextCountDetail.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            addIngredient(arrayList.get(pos).getName(),binding.etIngredientOcrTextCountDetail.getText().toString());
                            addOcr(arrayList.get(pos).getName(),binding.etIngredientOcrTextCountDetail.getText().toString());
                        }
                    });
                }
            });

        }

        void bindItem(IngredientUser item) {
            binding.tvIngredientOcrTextNameDetail.setText(item.getName());
            binding.etIngredientOcrTextCountDetail.setText(item.getCount());
        }
    }

    public static void addIngredient(String name, String count) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("ingredient_user");
        databaseReference.child(name).setValue(new IngredientUser(name, count));

    }

    public static void addOcr(String name, String count) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("ingredient_ocr");
        databaseReference.child(name).setValue(new IngredientUser(name, count));

    }

}
