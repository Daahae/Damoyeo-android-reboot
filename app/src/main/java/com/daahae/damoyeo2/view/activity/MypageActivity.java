package com.daahae.damoyeo2.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.databinding.ActivityMypageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MypageActivity extends AppCompatActivity {

    private final String TAG = "MYPAGE_ACTIVITY";
    private ActivityMypageBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage);

        init();
    }

    private void init() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        try {
            binding.tvEmailDescription.setText(user.getEmail());
            binding.tvNicknameDescription.setText(user.getDisplayName());
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
            binding.tvEmailDescription.setText("");
            binding.tvNicknameDescription.setText("");
        }
    }
}
