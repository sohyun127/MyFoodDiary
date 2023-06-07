package com.example.myfooddiary.Home.Ingredient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.ItemIngredientBinding;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{


    private ArrayList<Ingredient> arrayList;
    private Context context;

    public IngredientAdapter(ArrayList<Ingredient> arrayList,Context context){
        this.arrayList = arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ingredient,parent,false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bindItem(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return (arrayList.size());
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder{

        ItemIngredientBinding binding;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ItemIngredientBinding.bind(itemView);
        }

        void bindItem(Ingredient item){
            binding.tvItemIngredient.setText(item.getName());
            Glide.with(binding.getRoot()).load(item.getUrl()).into(binding.ivItemIngredient);

        }
    }



}
