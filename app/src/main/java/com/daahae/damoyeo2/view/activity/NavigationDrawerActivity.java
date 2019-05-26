package com.daahae.damoyeo2.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.databinding.ActivityNavigationDrawerBinding;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.fragment.MapsFragment;
import com.daahae.damoyeo2.view.fragment.ResultFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class NavigationDrawerActivity extends AppCompatActivity {

    private static final String TAG = "NAVIGATION_ACTIVITY";

    private ActivityNavigationDrawerBinding binding;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private String roomTitle;
    private ArrayList<String> roomEmails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation_drawer);

        Intent intent = getIntent();
        roomTitle = intent.getStringExtra("roomTitle");
        roomEmails = intent.getStringArrayListExtra("roomEmails");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            setResult(Constant.LOG_OUT);
            finish();
        }

        binding.tvName.setText(roomTitle);
        binding.btnBack.setOnClickListener(onClickListener);

        setFragmentInitialization();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == binding.btnBack.getId()) {
                finish();
            }
        }
    };

    @Override
    public void onBackPressed() {

    }

    public void setFragmentInitialization(){

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(binding.clMain.getId(), MapsFragment.newInstance(roomTitle, roomEmails));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void backView(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(fragment).commit();
        fragmentManager.popBackStack();
    }

    private void setViewFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.clMain.getId(), fragment)
                .addToBackStack(null)
                .commit();
    }

    public void changeView(int nextPageNumber){

        switch (nextPageNumber){
            case Constant.MAPS_PAGE:
                Constant.existPerson = false;
                setViewFragment(MapsFragment.newInstance(roomTitle, roomEmails));
                break;

            case Constant.RESULT_PAGE:
                setViewFragment(ResultFragment.newInstance());
                break;
        }
    }
}
