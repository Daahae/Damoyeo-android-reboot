package com.daahae.damoyeo2.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.databinding.ActivityInterestBinding;
import com.daahae.damoyeo2.handler.BackPressCloseHandler;
import com.daahae.damoyeo2.navigator.UserNavigator;
import com.daahae.damoyeo2.view_model.InterestViewModel;

public class InterestActivity extends AppCompatActivity implements UserNavigator {

    private InterestViewModel interestViewModel;

    private ActivityInterestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();

        bindingView();
    }

    private void initViewModel(){
        interestViewModel = new InterestViewModel(this);
    }

    private void bindingView(){
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_interest);
        binding.setModel(interestViewModel);

        interestViewModel.onCreate();
    }

    @Override
    public void swapActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void toastFullMessage() {
        Toast.makeText(this, "관심사가 최대입니다!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toastCategoryEmptyMessage() {
        Toast.makeText(this, "관심사를 각각 3개씩 선택해주세요", Toast.LENGTH_SHORT).show();
    }
}
