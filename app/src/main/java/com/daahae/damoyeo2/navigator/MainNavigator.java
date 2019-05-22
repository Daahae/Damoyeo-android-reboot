package com.daahae.damoyeo2.navigator;

import android.view.View;

public interface MainNavigator {
    void convertFragment(View view);
    void enterChattingRoom(int position);
    void showAddFriendDialog();
    void closeFriendDialog();
    void showAddChattingRoomDialog();
    void closeAddChattingRoomDialog();
    void showAddFriendConfirmDialog();
    void showAddFriendCancelDialog();
}
