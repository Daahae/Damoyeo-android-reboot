package com.daahae.damoyeo2.view_model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.databinding.library.baseAdapters.BR;
import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.model.Friend;
import com.daahae.damoyeo2.model.FriendArr;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.RecyclerItemClickListener;

import java.util.ArrayList;

public class FriendsModel extends BaseObservable {

    private String name;
    private Drawable profilePicture;
    private String email;
    private boolean isCheck = false;
    private boolean isFriend = false;

    public boolean isRealFriend(){
        return isFriend;
    }

    public FriendsModel() {
    }

    public FriendsModel(Friend friend){
        this.name = friend.name;
        this.profilePicture = Constant.context.getResources().getDrawable(R.drawable.ic_friend_profile);
        this.email = friend.email;
        Log.d("isFriend", friend.isFriend() + "");
        if (friend.isFriend()) isFriend = true;
        else isFriend = false;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


    public ArrayList<FriendsModel> getRealFriendList(FriendArr friendArr){
        final ArrayList<FriendsModel> friendsModelArrayList = new ArrayList<>();

        for(int i=0;i<friendArr.getFriends().size();i++){
            FriendsModel friendsModel = new FriendsModel(friendArr.getFriends().get(i));
            Log.d("friend",friendArr.getFriends().get(i).toString());
            friendArr.getFriends().get(i).setFriend(true);
            friendsModelArrayList.add(friendsModel);
        }
        return friendsModelArrayList;

    }

    public ArrayList<FriendsModel> getArrayListFriends(FriendArr friendArr){
        final ArrayList<FriendsModel> friendsModelArrayList = new ArrayList<>();

        Constant.MAX_FRIEND =friendArr.getFriendRequesters().size();
        for(int i=0;i<friendArr.getFriendRequesters().size();i++){
            FriendsModel friendsModel = new FriendsModel(friendArr.getFriendRequesters().get(i));
            friendArr.getFriendRequesters().get(i).setFriend(false);
            friendsModelArrayList.add(friendsModel);
        }
        for(int i=0;i<friendArr.getFriends().size();i++){
            FriendsModel friendsModel = new FriendsModel(friendArr.getFriends().get(i));
            Log.d("friend",friendArr.getFriends().get(i).toString());
            friendArr.getFriends().get(i).setFriend(true);
            friendsModelArrayList.add(friendsModel);
        }
        return friendsModelArrayList;

    }

}
