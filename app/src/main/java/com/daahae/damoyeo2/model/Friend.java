package com.daahae.damoyeo2.model;

import android.graphics.drawable.Drawable;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.view.Constant;
import com.google.gson.annotations.SerializedName;

public class Friend {

    @SerializedName("email")
    public String email;

    @SerializedName("nickname")
    public String name;

    private boolean isFriend;

    public Drawable profilePicture;

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public Friend(String email, String name) {
        this.email = email;
        this.name = name;
        this.profilePicture = Constant.context.getResources().getDrawable(R.drawable.ic_friend_profile);
    }

}
