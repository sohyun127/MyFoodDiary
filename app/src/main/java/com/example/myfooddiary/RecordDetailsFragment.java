package com.example.myfooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfooddiary.Home.recipe.RecordAddActivity;
import com.example.myfooddiary.databinding.FragmentRecordDetailsBinding;

public class RecordDetailsFragment extends Fragment implements View.OnClickListener {

    public CalendarView calendarView;
    private FragmentRecordDetailsBinding binding;
    TextView record_date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecordDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Bundle args=getArguments();

        binding.newFood.setOnClickListener(this);

        if (getArguments() != null) {
            Log.d("getttttt", getArguments().getString("today"));
            String date = getArguments().getString("today");
            record_date.setText(date);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_food:
                startActivity(new Intent(getActivity(), RecordAddActivity.class));
                getActivity().finish();
        }
    }
}