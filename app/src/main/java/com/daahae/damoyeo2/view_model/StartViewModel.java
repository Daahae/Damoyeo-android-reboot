package com.daahae.damoyeo2.view_model;

import android.databinding.Bindable;
import android.databinding.BaseObservable;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.daahae.damoyeo2.navigator.StartNavigator;

public class StartViewModel extends BaseObservable implements BaseViewModel {

    private StartNavigator navigator;
    private static boolean isVisible=false;

    public StartViewModel(StartNavigator navigator) {
        this.navigator = navigator;
    }

    @Bindable
    public boolean isLoading() {
        return isVisible;
    }

    public void setVisible(boolean isVisible){
        this.isVisible = isVisible;
        notifyPropertyChanged(BR.loading);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    public View.OnClickListener googleLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            navigator.loginActivity();
        }
    };


}
