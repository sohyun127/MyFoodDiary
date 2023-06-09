package com.example.myfooddiary.Home.record;

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
import androidx.fragment.app.FragmentTransaction;

import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.FragmentRecordBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordFragment extends Fragment {

    CalendarView calendarView;
    TextView today;


    private FragmentRecordBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }

    //화면에 캘린더 띄우기
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        today = binding.today;
        calendarView = binding.calendarView;
        DateFormat formatter = new SimpleDateFormat("yyyy년MM월dd일");
        Date date = new Date(calendarView.getDate());
        today.setText(formatter.format(date));

        //날짜 클릭 시 해당 날짜로  today 텍스트 변경
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String day;
                day = year + "년" + (month + 1) + "월" + dayOfMonth + "일";
                today.setText(day);

                Bundle bundle = new Bundle();
                bundle.putString("day", day);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new RecordDetailsFragment();
                fragment.setArguments(bundle);
                transaction.replace(R.id.fcv_main,fragment);
                transaction.commit();
                Log.d("send",day);



            }

        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
