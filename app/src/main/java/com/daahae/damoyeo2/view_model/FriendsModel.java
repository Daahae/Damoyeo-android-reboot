package com.daahae.damoyeo2.view_model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.model.Friend;
import com.daahae.damoyeo2.view.Constant;

import java.util.ArrayList;

public class FriendsModel {

    private String name;
    private Drawable profilePicture;

    public FriendsModel() {
    }

    public FriendsModel(Friend friend){
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

    public ArrayList<FriendsModel> getArrayListFriends(){
        ArrayList<FriendsModel> friendsModelArrayList = new ArrayList<>();
        FriendsModel friendsModel1 = new FriendsModel(new Friend("안종희",Constant.context.getResources().getDrawable(R.drawable.ic_friend_profile)));
        FriendsModel friendsModel2 = new FriendsModel(new Friend("허진규",Constant.context.getResources().getDrawable(R.drawable.ic_friend_profile)));
        FriendsModel friendsModel3 = new FriendsModel(new Friend("김태우",Constant.context.getResources().getDrawable(R.drawable.ic_friend_profile)));
        FriendsModel friendsModel4 = new FriendsModel(new Friend("강인혁",Constant.context.getResources().getDrawable(R.drawable.ic_friend_profile)));
        friendsModelArrayList.add(friendsModel1);
        friendsModelArrayList.add(friendsModel2);
        friendsModelArrayList.add(friendsModel3);
        friendsModelArrayList.add(friendsModel4);
        return friendsModelArrayList;

    }
}
