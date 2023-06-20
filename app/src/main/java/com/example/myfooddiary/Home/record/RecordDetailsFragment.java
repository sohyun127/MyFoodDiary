package com.example.myfooddiary.Home.record;

import android.content.Intent;
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

import com.example.myfooddiary.Home.User;
import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.FragmentRecordDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecordDetailsFragment extends Fragment implements View.OnClickListener {

    private FragmentRecordDetailsBinding binding;

    private ArrayList<Record> arrayList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceUser;
    String date="0";
    int kcal=0;

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
            date = getArguments().getString("day");
            binding.tvRecordDetailTitle.setText("[" + date + "]");
        }

        setAdapter();
        setInfo();

    }

    @Override
    public void onResume() {
        super.onResume();

        if (getArguments() != null) {
            date = getArguments().getString("day");
            binding.tvRecordDetailTitle.setText("[" + date + "]");
        }

        setAdapter();
        setInfo();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    private void setAdapter(){
        arrayList = new ArrayList<>();

        recyclerView = binding.rvRecordDetail;
        recyclerView.setHasFixedSize(true);

        database=FirebaseDatabase.getInstance();
        databaseReference = database.getReference("record").child(date);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                arrayList.clear();
                kcal=0;

                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    Record record = snapshot.getValue(Record.class);
                    kcal= Integer.valueOf(record.getKcal()) + kcal;
                    arrayList.add(record);
                }
                Log.d("recordtest", String.valueOf(arrayList));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("db error", error.toString());
                Toast.makeText(getContext(),"db 오류",Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new RecordAdapter(arrayList,getContext());
        recyclerView.setAdapter(adapter);

    }

    public void setInfo() {
        String userId = getActivity().getIntent().getStringExtra("user_id");

        databaseReferenceUser = database.getReference("user_info").child(userId);
        databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    User user = datasnapshot.getValue(User.class);
                    int result = Integer.parseInt(String.valueOf(kcal))-user.getKcal();
                    binding.tvRecordDetailKcal.setText(kcal+"(섭취 칼로리)" + " / " + user.getKcal()+"(권장 칼로리)" + " kcal");
                    binding.tvRecordDetailTotalDetail.setText(result + "kcal");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("db error", error.toString());
                Toast.makeText(getContext(), "db 오류", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_record_detail_add:
                Intent intent = new Intent(getActivity(),RecordAddActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
        }
    }
}