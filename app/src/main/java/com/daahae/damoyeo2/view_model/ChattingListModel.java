package com.daahae.damoyeo2.view_model;

import android.graphics.drawable.Drawable;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.model.Friend;
import com.daahae.damoyeo2.view.Constant;

import java.util.ArrayList;

public class ChattingListModel {

    private String name;
    private Drawable profilePicture;

    public ChattingListModel() {
    }

    public ChattingListModel(Friend friend){
        this.name = friend.name;
        this.profilePicture = friend.profilePicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Drawable profilePicture) {
        this.profilePicture = profilePicture;
    }

    public ArrayList<ChattingListModel> getArrayListChattingList(){
        ArrayList<ChattingListModel> chattingListModelArrayList = new ArrayList<>();
        ChattingListModel friendsModel1 = new ChattingListModel(new Friend("그룹1",Constant.context.getResources().getDrawable(R.drawable.ic_guest_profile)));
        ChattingListModel friendsModel2 = new ChattingListModel(new Friend("그룹2",Constant.context.getResources().getDrawable(R.drawable.ic_guest_profile)));
        ChattingListModel friendsModel3 = new ChattingListModel(new Friend("그룹3",Constant.context.getResources().getDrawable(R.drawable.ic_guest_profile)));
        chattingListModelArrayList.add(friendsModel1);
        chattingListModelArrayList.add(friendsModel2);
        chattingListModelArrayList.add(friendsModel3);
        return chattingListModelArrayList;

    }
}
