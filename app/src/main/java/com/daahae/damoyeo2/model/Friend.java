package com.daahae.damoyeo2.model;

import android.graphics.drawable.Drawable;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.view.Constant;
import com.google.gson.annotations.SerializedName;

public class Friend {

    @SerializedName("email")
    private String email;

    @SerializedName("nickname")
    public String name;

    @SerializedName("relation")
    public int relation;

    public Drawable profilePicture;

    public Friend(String email, String name, int relation) {
        this.email = email;
        this.name = name;
        this.relation = relation;
        this.profilePicture = Constant.context.getResources().getDrawable(R.drawable.ic_friend_profile);
    }

}
