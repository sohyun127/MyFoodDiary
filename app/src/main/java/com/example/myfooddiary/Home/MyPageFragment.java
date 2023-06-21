package com.example.myfooddiary.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfooddiary.databinding.FragmentMyPageBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyPageFragment extends Fragment {

    private FragmentMyPageBinding binding;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            binding = FragmentMyPageBinding.inflate(inflater,container,false);
            View view = binding.getRoot();
            return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.show();
        setInfo();

    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    public void setInfo(){
        String userId = getActivity().getIntent().getStringExtra("user_id");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user_info").child(userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if(datasnapshot.exists()){
                    User user = datasnapshot.getValue(User.class);

                    binding.tvMyPageNickname.setText("Nickname.   "+user.getNickName());
                    binding.tvMyPageGender.setText("Gender.   "+user.getGender());
                    binding.tvMyPageBirth.setText("Birth.   "+user.getBirth());
                    binding.tvMyPageWeight.setText("Weight.   "+user.getWeight()+"kg");
                    binding.tvMyPageHeight.setText("Height.   "+user.getHeight()+"cm");
                    binding.tvMyPageKcal.setText("Recommended calories per day.   "+user.getKcal()+"kcal");
                    loadingDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("db error", error.toString());
                Toast.makeText(getContext(), "db 오류", Toast.LENGTH_SHORT).show();
            }
        });


    }
}