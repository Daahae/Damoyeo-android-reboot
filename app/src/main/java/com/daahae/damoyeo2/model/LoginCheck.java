package com.daahae.damoyeo2.model;

import com.google.gson.annotations.SerializedName;

public class LoginCheck {

    @SerializedName("history")
    private int history;

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }
}
