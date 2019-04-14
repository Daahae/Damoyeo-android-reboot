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
import com.daahae.damoyeo2.databinding.SettingFragmentBinding;
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

        return DataBindingUtil.inflate(inflater, R.layout.setting_fragment, container, false).getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainViewModel model = new MainViewModel();
        SettingFragmentBinding binding = DataBindingUtil.getBinding(getView());
        binding.setModel(model);
        model.onCreate();
    }
}
