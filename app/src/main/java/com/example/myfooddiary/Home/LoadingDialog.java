package com.example.myfooddiary.Home;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import androidx.annotation.NonNull;

import com.example.myfooddiary.databinding.DialogLoadingBinding;

public class LoadingDialog extends Dialog {

    private DialogLoadingBinding binding;
    protected Context mContext;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        binding = DialogLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.mContext = context;

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

    }
}
