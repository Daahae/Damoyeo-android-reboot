package com.daahae.damoyeo2.view_model;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.navigator.MainNavigator;
import com.daahae.damoyeo2.view.Constant;

public class MainViewModel implements BaseViewModel {

    MainNavigator navigator;
    private static MainViewModel mainViewModel = new MainViewModel();
    public final ObservableField<Drawable> btnPerson = new ObservableField<>();
    public final ObservableField<Drawable> btnChatting = new ObservableField<>();
    public final ObservableField<Drawable> btnSetting = new ObservableField<>();
    public final ObservableField<String> txtTitle = new ObservableField<>();

    public void setMainNavigator(MainNavigator navigator){
        this.navigator = navigator;
    }

    public static MainViewModel getInstance(){
        return mainViewModel;
    }

    public MainViewModel() {
    }

    @Override
    public void onCreate() {
        btnPerson.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_person_black));
        btnChatting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_chatting_white));
        btnSetting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_setting_white));
        txtTitle.set("친구");
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


    public View.OnClickListener replaceFragmentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_person_main:
                    btnPerson.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_person_black));
                    btnChatting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_chatting_white));
                    btnSetting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_setting_white));

                    txtTitle.set("친구");
                    RetrofitCommunication.getInstance().sendFriendsListRequest();
                    break;
                case R.id.btn_chatting_main:
                    btnPerson.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_person_white));
                    btnChatting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_chatting_black));
                    btnSetting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_setting_white));
                    txtTitle.set("채팅");
                    break;
                case R.id.btn_setting_main:
                    btnPerson.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_person_white));
                    btnChatting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_chatting_white));
                    btnSetting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_setting_black));
                    txtTitle.set("설정");
                    break;
                default:
                    break;
            }
            navigator.convertFragment(v);
        }
    };
}
