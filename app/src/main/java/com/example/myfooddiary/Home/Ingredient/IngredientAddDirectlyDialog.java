package com.example.myfooddiary.Home.Ingredient;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.myfooddiary.R;
import com.example.myfooddiary.databinding.DialogIngredientAddDirectlyBinding;

public class IngredientAddDirectlyDialog extends Dialog {

    protected Context mContext;
    private DialogIngredientAddDirectlyBinding binding;

    public IngredientAddDirectlyDialog(Context context) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DialogIngredientAddDirectlyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.mContext = context;

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        Window window = getWindow();
        if (window != null) {
            // 백그라운드 투명
           // window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams params = window.getAttributes();
            // 화면에 가득 차도록
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = 1000;

            // 열기&닫기 시 애니메이션 설정
            params.windowAnimations = R.style.AnimationPopupStyle;
            window.setAttributes(params);
            // UI 하단 정렬
            window.setGravity(Gravity.BOTTOM);
        }
    }

}
