package com.example.myfooddiary.Home.record;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfooddiary.databinding.ItemRecordBinding;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private ArrayList<Record> arrayList;
    private Context context;


    public RecordAdapter(ArrayList<Record> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecordAdapter.RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecordBinding binding = ItemRecordBinding.inflate(LayoutInflater.from(context), parent, false);
        return new RecordAdapter.RecordViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.RecordViewHolder holder, int position) {
        holder.bindItem(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        ItemRecordBinding binding;

        public RecordViewHolder(ItemRecordBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindItem(Record item) {
            binding.tvRecordTime.setText(item.getTime());
            binding.tvRecordFood.setText(item.getFood());
            binding.tvRecordKcal.setText(item.getKcal() + "kcal");
            byte[] b = binaryStringToByteArray(item.getUrl());
            ByteArrayInputStream is = new ByteArrayInputStream(b);
            Drawable foodImage = Drawable.createFromStream(is, "foodImage");
            binding.ivItemRecordImg.setImageDrawable(foodImage);

        }
    }

    public static byte[] binaryStringToByteArray(String s) {
        int count = s.length() / 8;
        byte[] b = new byte[count];
        for (int i = 1; i < count; ++i) {
            String t = s.substring((i - 1) * 8, i * 8);
            b[i - 1] = binaryStringToByte(t);
        }
        return b;
    }

    public static byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for (int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte) (1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }
}
