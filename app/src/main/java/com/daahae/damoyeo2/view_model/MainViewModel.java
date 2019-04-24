package com.daahae.damoyeo2.view_model;

import android.content.Intent;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.databinding.FragmentChattingBinding;
import com.daahae.damoyeo2.model.ChattingRequest;
import com.daahae.damoyeo2.navigator.MainNavigator;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.activity.MainActivity;
import com.daahae.damoyeo2.view.activity.MapsActivity;
import com.daahae.damoyeo2.view.adapter.ChattingListAdapter;

import java.util.ArrayList;

public class MainViewModel implements BaseViewModel {

    MainNavigator navigator;
    public final ObservableField<Drawable> btnPerson = new ObservableField<>();
    public final ObservableField<Drawable> btnChatting = new ObservableField<>();
    public final ObservableField<Drawable> btnSetting = new ObservableField<>();
    public final ObservableField<String> txtTitle = new ObservableField<>();

    public final ObservableField<String> txtName = new ObservableField<>();
    public final ObservableField<Drawable> drawableImage = new ObservableField<>();

    private ChattingListModel chattingListModel;
    private ArrayList<ChattingListModel> chattingListModelArrayList;
    private ChattingListAdapter chattingListAdapter;


    public MainViewModel(MainNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void onCreate() {
        btnPerson.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_person_black));
        btnChatting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_chatting_white));
        btnSetting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_setting_white));
        txtTitle.set("친구");

        txtName.set(Constant.nickname);
        drawableImage.set(Constant.context.getDrawable(R.drawable.ic_my_profile));
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

    public void setChattingList(FragmentChattingBinding binding){
        chattingListModel = new ChattingListModel();
        chattingListModelArrayList = chattingListModel.getArrayListChattingList();
        chattingListAdapter = new ChattingListAdapter(MainActivity.getMainContext(),chattingListModelArrayList);
        binding.listView.setAdapter(chattingListAdapter);


        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chattingItemClick(position);
            }
        });

    }

    private void chattingItemClick(int position){
        Log.v("item","click");
        ChattingRequest chattingRequest = new ChattingRequest();
        chattingRequest.setRoomNumber(1);
        RetrofitCommunication.getInstance().setChattingRoomNumber(chattingRequest);

        Intent intent = new Intent(MainActivity.getMainContext(), MapsActivity.class);
        MainActivity.getMainContext().startActivity(intent);
        if(navigator!=null) navigator.enterChattingRoom(position);
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
