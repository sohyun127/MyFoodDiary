package com.example.myfooddiary.Home.Ingredient;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.myfooddiary.Home.MainActivity;
import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.DialogIngredientAddDirectlyBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IngredientAddDirectlyDialog extends Dialog {

    protected Context mContext;
    protected  String name;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DialogIngredientAddDirectlyBinding binding;

    public IngredientAddDirectlyDialog(Context context,String name) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DialogIngredientAddDirectlyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.mContext = context;
        this.name = name;

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            // 화면에 가득 차도록
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = 1000;
            binding.tvDialogIngredientAddDirectlyName.setText("NAME.  "+name);

            // 열기&닫기 시 애니메이션 설정
            params.windowAnimations = R.style.AnimationPopupStyle;
            window.setAttributes(params);
            // UI 하단 정렬
            window.setGravity(Gravity.BOTTOM);

            binding.btnDialogIngredientAddDirectly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addIngredient(name);
                    mContext.startActivity(new Intent(context, MainActivity.class));
                    dismiss();
                }
            });
        }
    }


    public void addIngredient(String name) {

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("ingredient_user");
        databaseReference.child(name).setValue(new IngredientUser(name, binding.etDialogIngredientAddDirectlyCount.getText().toString()));

    }

}
