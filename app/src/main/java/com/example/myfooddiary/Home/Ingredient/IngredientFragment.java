package com.example.myfooddiary.Home.Ingredient;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.FragmentIngredientBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IngredientFragment extends Fragment implements View.OnClickListener {

    private FragmentIngredientBinding binding;

    // 플로팅버튼 상태
    private boolean fabMain_status = false;
    private RecyclerView.Adapter adapter;
    private ArrayList<Ingredient> arrayList;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    private static final int PERMISSIONS_REQUEST_CODE = 1000;
    String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentIngredientBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseApp.initializeApp(requireActivity().getApplicationContext());

        FloatingActionButton fabMain = binding.fabIngredientMain;
        FloatingActionButton fabAddDirectly = binding.fabIngredientAddDirectly;
        FloatingActionButton fabOcr = binding.fabIngredientOcr;

        fabMain.setOnClickListener(this);
        fabAddDirectly.setOnClickListener(this);
        fabOcr.setOnClickListener(this);

        setTabLayout();
        setAdapter(0);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setAdapter(int typeId){

        recyclerView = binding.rvIngredient;
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();

        databaseReference = database.getReference("ingredient");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    Ingredient ingredient = snapshot.getValue(Ingredient.class);
                    if(ingredient.getTypeId()==typeId){
                        arrayList.add(ingredient);
                    }
                    else if (typeId==0) {
                        arrayList.add(ingredient);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("db error",error.toString());
                Toast.makeText(getContext(),"db 오류",Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new IngredientAdapter(arrayList,getContext());
        recyclerView.setAdapter(adapter);

    }


    private void setTabLayout(){
        binding.tabsIngredient.addTab(binding.tabsIngredient.newTab().setText("종합"));
        binding.tabsIngredient.addTab(binding.tabsIngredient.newTab().setText("육류"));
        binding.tabsIngredient.addTab(binding.tabsIngredient.newTab().setText("해물"));
        binding.tabsIngredient.addTab(binding.tabsIngredient.newTab().setText("채소"));
        binding.tabsIngredient.addTab(binding.tabsIngredient.newTab().setText("과일"));
        binding.tabsIngredient.addTab(binding.tabsIngredient.newTab().setText("기타"));



        binding.tabsIngredient.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if(position==0){
                    setAdapter(0);
                }else if(position==1){
                    setAdapter(1);
                } else if (position==2) {
                    setAdapter(2);
                }else if (position==3) {
                    setAdapter(3);
                }else if (position==4) {
                    setAdapter(4);
                }else if (position==5) {
                    setAdapter(5);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab_ingredient_main:
                toggleFab();
                break;
            case R.id.fab_ingredient_add_directly:
                Toast.makeText(requireContext(), "수동 추가", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),IngredientAddDirectlyActivity.class));
                // getActivity().finish();
                break;
            case R.id.fab_ingredient_ocr:
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(!hasPermissions(PERMISSIONS)){
                        requestPermissions(PERMISSIONS,PERMISSIONS_REQUEST_CODE);
                    }else{
                        Toast.makeText(requireContext(), "ocr", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(),IngredientOcrCameraActivity.class));
                        getActivity().finish();
                    }
                }
                break;


        }

    }


    private void toggleFab() {
        if (fabMain_status) {
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(binding.fabIngredientAddDirectly, "translationY", 0f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(binding.fabIngredientOcr, "translationY", 0f);
            fe_animation.start();
            // 메인 플로팅 이미지 변경
            binding.fabIngredientMain.setImageResource(R.drawable.ic_add);

        } else {
            // 플로팅 액션 버튼 열기
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(binding.fabIngredientAddDirectly, "translationY", -200f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(binding.fabIngredientOcr, "translationY", -400f);
            fe_animation.start();
            // 메인 플로팅 이미지 변경
            binding.fabIngredientMain.setImageResource(R.drawable.ic_close);
        }
        // 플로팅 버튼 상태 변경
        fabMain_status = !fabMain_status;
    }

    private boolean hasPermissions(String[] permissions) {
        int result;

        for (String perms : permissions) {
            result = ContextCompat.checkSelfPermission(requireContext(), perms);

            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraPermissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean diskPermissionsAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (!cameraPermissionAccepted || diskPermissionsAccepted)
                        showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다");
                    else {
                        startActivity(new Intent(getActivity(), IngredientOcrCameraActivity.class));
                        getActivity().finish();
                    }
                }
                break;

        }

    }

    private void showDialogForPermission(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });
        builder.create().show();
    }


}
