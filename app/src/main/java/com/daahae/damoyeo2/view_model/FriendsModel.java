package com.daahae.damoyeo2.view_model;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.model.Friend;
import com.daahae.damoyeo2.model.FriendArr;
import com.daahae.damoyeo2.view.Constant;

import java.util.ArrayList;

public class FriendsModel {

    private String name;
    private Drawable profilePicture;

    public FriendsModel() {
    }

    public FriendsModel(Friend friend){
        this.name = friend.name;
        this.profilePicture = Constant.context.getResources().getDrawable(R.drawable.ic_friend_profile);
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

    public ArrayList<FriendsModel> getArrayListFriends(FriendArr friendArr){
        final ArrayList<FriendsModel> friendsModelArrayList = new ArrayList<>();
        for(int i=0;i<friendArr.getFriends().size();i++){
            FriendsModel friendsModel = new FriendsModel(friendArr.getFriends().get(i));
            friendsModelArrayList.add(friendsModel);
        }
        return friendsModelArrayList;

    }
}
