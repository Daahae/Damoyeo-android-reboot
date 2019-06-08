package com.daahae.damoyeo2.model;

import com.daahae.damoyeo2.view.Constant;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("mostLike")
    private int mostLike;
    @SerializedName("moreLike")
    private int moreLike;
    @SerializedName("like")
    private int like;

    public int getMostLike() {
        return mostLike;
    }

    public void setMostLike(int mostLike) {
        this.mostLike = mostLike;
    }

    public int getMoreLike() {
        return moreLike;
    }

    public void setMoreLike(int moreLike) {
        this.moreLike = moreLike;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "{" +
                "\"email\":\"" + Constant.email +"\","
                +"\"mostLike\":" + mostLike +","
                +"\"moreLike\":" + moreLike +","
                +"\"like\":" + like +"}";
    }
}
