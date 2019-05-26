package com.daahae.damoyeo2.model;

import android.support.design.widget.FloatingActionButton;
import android.view.animation.Animation;

public class FloatingActionBtn {
    private Animation fabOpen, fabClose;
    private boolean isFabOpen = false;
    private FloatingActionButton fabMenu, fabGPS, fabUser, fabFull;
    private FloatingActionButton fabLogout, fabChat;

    public FloatingActionButton getFabMenu() {
        return fabMenu;
    }

    public void setFabOpen(Animation fabOpen) {
        this.fabOpen = fabOpen;
    }

    public void setFabClose(Animation fabClose) {
        this.fabClose = fabClose;
    }

    public void setFabMenu(FloatingActionButton fab) {
        this.fabMenu = fab;
    }

    public FloatingActionButton getFabGPS() {
        return fabGPS;
    }

    public void setFabGPS(FloatingActionButton fabGPS) {
        this.fabGPS = fabGPS;
    }

    public FloatingActionButton getFabUser() {
        return fabUser;
    }

    public void setFabUser(FloatingActionButton fabUser) {
        this.fabUser = fabUser;
    }

    public FloatingActionButton getFabFull() {
        return fabFull;
    }

    public void setFabFull(FloatingActionButton fabFull) {
        this.fabFull = fabFull;
    }

    public FloatingActionButton getFabLogout() {
        return fabLogout;
    }

    public void setFabLogout(FloatingActionButton fabLogout) {
        this.fabLogout = fabLogout;
    }

    public FloatingActionButton getFabChat() {
        return fabChat;
    }

    public void setFabChat(FloatingActionButton fabChat) {
        this.fabChat = fabChat;
    }

    public void anim() {
        if (isFabOpen) {
            fabGPS.startAnimation(fabClose);
            fabUser.startAnimation(fabClose);
            fabFull.startAnimation(fabClose);
            fabGPS.setClickable(false);
            fabUser.setClickable(false);
            fabFull.setClickable(false);
            isFabOpen = false;
        } else {
            fabGPS.startAnimation(fabOpen);
            fabUser.startAnimation(fabOpen);
            fabFull.startAnimation(fabOpen);
            fabGPS.setClickable(true);
            fabUser.setClickable(true);
            fabFull.setClickable(true);
            isFabOpen = true;
        }
    }
}
