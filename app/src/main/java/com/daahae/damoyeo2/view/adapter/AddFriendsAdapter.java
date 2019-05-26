package com.daahae.damoyeo2.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.databinding.ItemAddFriendBinding;
import com.daahae.damoyeo2.databinding.ItemFriendBinding;
import com.daahae.damoyeo2.model.FriendAcceptMessage;
import com.daahae.damoyeo2.model.FriendAcceptRequestForm;
import com.daahae.damoyeo2.model.FriendArr;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view_model.FriendsModel;

import java.util.ArrayList;

public class AddFriendsAdapter extends ArrayAdapter<FriendsModel>{

    private ArrayList<FriendsModel> friendsModelArrayList;
    private Context context;

    public AddFriendsAdapter(@NonNull Context context, ArrayList<FriendsModel> friendsModelArrayList) {
        super(context, R.layout.item_friend, friendsModelArrayList);
        this.context = context;
        this.friendsModelArrayList = friendsModelArrayList;
    }

    public void setFriendsModelArrayList(ArrayList<FriendsModel> friendsModelArrayList){
        this.friendsModelArrayList = friendsModelArrayList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAddFriendBinding itemFriendBinding = DataBindingUtil.inflate(layoutInflater,R.layout.item_add_friend,parent,false);

        if(friendsModelArrayList!=null) {
            itemFriendBinding.setModel(friendsModelArrayList.get(position));
        }

        return itemFriendBinding.getRoot();
    }

}
