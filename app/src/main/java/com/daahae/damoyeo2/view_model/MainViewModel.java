package com.daahae.damoyeo2.view_model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.EditText;

import com.android.databinding.library.baseAdapters.BR;
import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.communication.SocketCommunication;
import com.daahae.damoyeo2.databinding.FragmentChattingBinding;
import com.daahae.damoyeo2.model.ChattingRequest;
import com.daahae.damoyeo2.model.ChattingRoomArr;
import com.daahae.damoyeo2.model.FriendAddRequestForm;
import com.daahae.damoyeo2.model.FriendAcceptMessage;
import com.daahae.damoyeo2.navigator.MainNavigator;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.activity.MainActivity;
import com.daahae.damoyeo2.view.activity.MapsActivity;
import com.daahae.damoyeo2.view.adapter.ChattingListAdapter;

import java.util.ArrayList;

public class MainViewModel extends BaseObservable implements BaseViewModel {

    MainNavigator navigator;
    public final ObservableField<Drawable> btnPerson = new ObservableField<>();
    public final ObservableField<Drawable> btnChatting = new ObservableField<>();
    public final ObservableField<Drawable> btnSetting = new ObservableField<>();
    public final ObservableField<Drawable> addChattingRoomCheck = new ObservableField<>();
    public final ObservableField<String> txtTitle = new ObservableField<>();

    public final ObservableField<String> txtName = new ObservableField<>();
    public final ObservableField<Drawable> drawableImage = new ObservableField<>();

    private ChattingListModel chattingListModel;
    private ArrayList<ChattingListModel> chattingListModelArrayList;
    private ChattingListAdapter chattingListAdapter;

    private static boolean showBottomNavigator=true;

    private static boolean addButtonVisible=true;

    Dialog dialog;

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

    @Bindable
    public boolean isShowBottomNavigator() {
        return showBottomNavigator;
    }

    @Bindable
    public void setShowBottomNavigator(boolean isVisible){
        this.showBottomNavigator = isVisible;
        notifyPropertyChanged(BR.showBottomNavigator);
    }


    @Bindable
    public boolean isAddButtonVisible() {
        return addButtonVisible;
    }

    @Bindable
    public void setAddButtonVisible(boolean isVisible){
        this.addButtonVisible = isVisible;
        notifyPropertyChanged(BR.addButtonVisible);
    }

    @Bindable
    public void setAddChattingRoom(boolean isCheck){
        if(isCheck){
            addChattingRoomCheck.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_empty_radio));
        } else{
            addChattingRoomCheck.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_check_radio));
        }

    }

    public void setChattingList(FragmentChattingBinding binding, ChattingRoomArr chattingRoomArr){
        chattingListModel = new ChattingListModel();
        chattingListModelArrayList = chattingListModel.getArrayListChattingList(chattingRoomArr);
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
        chattingRequest.setRoomNumber(position+1);
        RetrofitCommunication.getInstance().setDetailChattingRoomNumber(chattingRequest);

        Intent intent = new Intent(MainActivity.getMainContext(), MapsActivity.class);

        intent.putExtra("roomTitle",chattingListModelArrayList.get(position).getName());
        intent.putExtra("roomEmails",chattingListModelArrayList.get(position).getEmails());

        Log.v("roomEmails(Main)",chattingListModelArrayList.get(position).getEmails().toString());

        MainActivity.getMainContext().startActivity(intent);
        if(navigator!=null) navigator.enterChattingRoom(position);
    }

    public View.OnClickListener addButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (Constant.FRAGMENT){
                case Constant.FRIEND_FRAGMENT:
                    navigator.showAddFriendDialog();
                    break;
                case Constant.CHATTINGROOM_FRAGMENT:
                    navigator.showAddChattingRoomDialog();
                    break;
                case Constant.SETTING_FRAGMENT:
                    break;
            }
        }
    };

    public void sendFriendAddRequest(String destEmail){

        FriendAddRequestForm requestForm = new FriendAddRequestForm();
        requestForm.setMyEmail(Constant.email);
        requestForm.setDestEmail(destEmail);
        Log.v("destEmail",destEmail);
        RetrofitCommunication.getInstance().setAddFriend(requestForm);

        RetrofitCommunication.AddFriendCallBack addFriendCallBack = new RetrofitCommunication.AddFriendCallBack() {
            @Override
            public void addFriendDataPath(FriendAcceptMessage stringMessage) {
                Log.v("stringMessage",stringMessage.message+"");
                if (stringMessage.message == 1) {
                    navigator.showAddFriendConfirmDialog();
                } else {
                    navigator.showAddFriendCancelDialog();
                }

            }
        };
        RetrofitCommunication.getInstance().setAddFriendCallBack(addFriendCallBack);

        navigator.closeFriendDialog();
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
                    Constant.FRAGMENT = Constant.FRIEND_FRAGMENT;
                    setAddButtonVisible(true);
                    break;
                case R.id.btn_chatting_main:
                    btnPerson.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_person_white));
                    btnChatting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_chatting_black));
                    btnSetting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_setting_white));
                    txtTitle.set("채팅");
                    Constant.FRAGMENT = Constant.CHATTINGROOM_FRAGMENT;
                    setAddButtonVisible(true);
                    break;
                case R.id.btn_setting_main:
                    btnPerson.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_person_white));
                    btnChatting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_chatting_white));
                    btnSetting.set(ContextCompat.getDrawable(Constant.context,R.drawable.ic_setting_black));
                    txtTitle.set("설정");
                    Constant.FRAGMENT = Constant.SETTING_FRAGMENT;
                    setAddButtonVisible(false);
                    break;
                default:
                    break;
            }
            navigator.convertFragment(v);
        }
    };

    public OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean isFocused) {
            //Hide Keyboard
            if(isFocused){
                setShowBottomNavigator(false);
            }
            else {
                setShowBottomNavigator(true);
            }
        }
    };

    public void sendSocketAddUser(){
        //SocketCommunication.getInstance().sendAddUser();
    }

    @BindingAdapter("onFocusChange")
    public static void onFocusChange(EditText text, final OnFocusChangeListener listener) {
        text.setOnFocusChangeListener(listener);
    }

    public View.OnClickListener closeAddChattingRoomDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            navigator.closeAddChattingRoomDialog();
        }
    };

    public View.OnClickListener addChattingRoomDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

}
