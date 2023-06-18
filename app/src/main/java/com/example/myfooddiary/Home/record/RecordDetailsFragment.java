package com.example.myfooddiary.Home.record;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.FragmentRecordDetailsBinding;

public class RecordDetailsFragment extends Fragment implements View.OnClickListener {

    public CalendarView calendarView;
    private FragmentRecordDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecordDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.fabRecordDetailAdd.setOnClickListener(this);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {
            String date = getArguments().getString("day");
           binding.tvRecordDetailTitle.setText("["+date+"]");
        }

    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_record_detail_add:
                startActivity(new Intent(getActivity(), RecordAddActivity.class));
        }
    }
}