package com.example.myfooddiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfooddiary.databinding.FragmentRecordDetailsBinding;


public class RecordDetailsFragment extends Fragment {

    public CalendarView calendarView;
    private FragmentRecordDetailsBinding binding;


    public RecordDetailsFragment() {
        // Required empty public constructor
    }

    //public static RecordDetailsFragment newInstance(String param1, String param2) {
//        RecordDetailsFragment fragment = new RecordDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
    //   }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecordDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
        //return inflater.inflate(R.layout.fragment_record_details, container, false);
    }
}