package com.example.myfooddiary.Home.Ingredient;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.DialogIngredientBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class IngredientDialog extends Dialog {

   private DialogIngredientBinding binding;

    protected Context mContext;
    protected  String name;
    protected  String count;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<IngredientUser> arrayList;

    public IngredientDialog(Context context,String name,String count) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DialogIngredientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.mContext = context;
        this.name = name;
        this.count = count;

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            // 화면에 가득 차도록
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = 1000;
            binding.tvDialogIngredientName.setText("NAME.  "+name);
            binding.tvDialogIngredientCount.setText("COUNT.  "+count);

            // 열기&닫기 시 애니메이션 설정
            params.windowAnimations = R.style.AnimationPopupStyle;
            window.setAttributes(params);
            // UI 하단 정렬
            window.setGravity(Gravity.BOTTOM);

            binding.btnDialogIngredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    private void setData(){
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("ingredient_user");


    }

}
