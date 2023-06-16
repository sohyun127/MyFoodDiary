package com.example.myfooddiary.Home.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfooddiary.databinding.ItemRecipeBinding;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> arrayList;
    private Context context;


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private RecipeAdapter.OnItemClickListener mListener = null;

    public RecipeAdapter(ArrayList<Recipe> arrayList, Context context, RecipeAdapter.OnItemClickListener listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecipeBinding binding = ItemRecipeBinding.inflate(LayoutInflater.from(context), parent, false);
        return new RecipeAdapter.RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
        holder.bindItem(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return (arrayList.size());
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        ItemRecipeBinding binding;

        public RecipeViewHolder(ItemRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }

        void bindItem(Recipe item) {
            binding.tvItemRecipeTitle.setText(item.getName());
            Glide.with(binding.getRoot()).load(item.getUrl()).into(binding.ivItemRecipeImg);
        }
    }


}
