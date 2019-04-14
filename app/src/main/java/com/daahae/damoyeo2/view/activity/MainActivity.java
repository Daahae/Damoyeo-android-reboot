package com.daahae.damoyeo2.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.databinding.ActivityMainBinding;
import com.daahae.damoyeo2.navigator.MainNavigator;
import com.daahae.damoyeo2.view.fragment.ChattingFragment;
import com.daahae.damoyeo2.view.fragment.FriendsFragment;
import com.daahae.damoyeo2.view.fragment.SettingFragment;
import com.daahae.damoyeo2.view_model.MainViewModel;
import com.daahae.damoyeo2.view.Constant;

public class MainActivity extends AppCompatActivity{

    private MainViewModel mainViewModel;

    private ActivityMainBinding binding;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private MainNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();

        bindingView();

        initFragment();

        setNavigator();
    }

    private void setNavigator(){
        navigator = new MainNavigator() {
            @Override
            public void convertFragment(View view) {
                switch (view.getId()){
                    case R.id.btn_person_main:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame, FriendsFragment.getInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.btn_chatting_main:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame, ChattingFragment.getInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.btn_setting_main:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame, SettingFragment.getInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    default:
                        break;

                }
            }
        };
        mainViewModel.setMainNavigator(navigator);
    }

    private void initViewModel(){
        mainViewModel = MainViewModel.getInstance();
        Constant.context = this;

    }

    private void bindingView(){
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setModel(mainViewModel);

        mainViewModel.onCreate();
    }

    private void initFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace( R.id.main_frame, FriendsFragment.getInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
