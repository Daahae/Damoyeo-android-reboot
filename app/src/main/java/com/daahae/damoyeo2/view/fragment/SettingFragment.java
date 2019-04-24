package com.daahae.damoyeo2.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.databinding.FragmentSettingBinding;
import com.daahae.damoyeo2.view.activity.MainActivity;
import com.daahae.damoyeo2.view_model.MainViewModel;

public class SettingFragment extends Fragment {

    public SettingFragment(){

    }

    public static SettingFragment getInstance(){
        return new SettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false).getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainViewModel model = new MainViewModel(MainActivity.getMainNavigator());
        FragmentSettingBinding binding = DataBindingUtil.getBinding(getView());
        binding.setModel(model);
        model.onCreate();
    }
}
