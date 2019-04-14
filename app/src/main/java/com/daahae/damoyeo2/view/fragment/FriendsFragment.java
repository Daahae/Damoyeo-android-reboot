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
import com.daahae.damoyeo2.databinding.FragmentFriendsBinding;
import com.daahae.damoyeo2.view_model.MainViewModel;

public class FriendsFragment extends Fragment {

    public FriendsFragment(){

    }

    public static FriendsFragment getInstance(){
        return new FriendsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return DataBindingUtil.inflate(inflater, R.layout.fragment_friends, container, false).getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainViewModel model = new MainViewModel();
        FragmentFriendsBinding binding = DataBindingUtil.getBinding(getView());
        binding.setModel(model);
        model.onCreate();
    }
}
