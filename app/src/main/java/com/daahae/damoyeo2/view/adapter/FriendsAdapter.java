package com.daahae.damoyeo2.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.databinding.ItemFriendBinding;
import com.daahae.damoyeo2.model.Friend;
import com.daahae.damoyeo2.model.FriendAcceptMessage;
import com.daahae.damoyeo2.model.FriendAcceptRequestForm;
import com.daahae.damoyeo2.model.FriendAddRequestForm;
import com.daahae.damoyeo2.model.FriendArr;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.fragment.FriendsFragment;
import com.daahae.damoyeo2.view_model.FriendsModel;

import java.util.ArrayList;

public class FriendsAdapter extends ArrayAdapter<FriendsModel>{

    private ArrayList<FriendsModel> friendsModelArrayList;
    private Context context;

    public FriendsAdapter(@NonNull Context context, ArrayList<FriendsModel> friendsModelArrayList) {
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
        ItemFriendBinding itemFriendBinding = DataBindingUtil.inflate(layoutInflater,R.layout.item_friend,parent,false);

        if(friendsModelArrayList!=null) {
            itemFriendBinding.setModel(friendsModelArrayList.get(position));

            LinearLayout linearLayout = itemFriendBinding.linearAddFriend;
            Button btnCancel = itemFriendBinding.btnCancelAddFriend;
            Button btnConsent = itemFriendBinding.btnConsentAddFriend;

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("cancel", "enter");
                    FriendAcceptRequestForm friendAcceptRequestForm = new FriendAcceptRequestForm();
                    friendAcceptRequestForm.setDestEmail(friendsModelArrayList.get(position).getEmail());
                    friendAcceptRequestForm.setMyEmail(Constant.email);
                    friendAcceptRequestForm.setAccept(0);

                    acceptFreind(friendAcceptRequestForm);
                    notifyDataSetChanged();
                }
            });

            btnConsent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("consent", "enter");
                    FriendAcceptRequestForm friendAcceptRequestForm = new FriendAcceptRequestForm();
                    friendAcceptRequestForm.setDestEmail(friendsModelArrayList.get(position).getEmail());
                    friendAcceptRequestForm.setMyEmail(Constant.email);
                    friendAcceptRequestForm.setAccept(1);

                    acceptFreind(friendAcceptRequestForm);
                    notifyDataSetChanged();
                }
            });

            if (position >= Constant.MAX_FRIEND)
                linearLayout.setVisibility(View.GONE);
        }
        return itemFriendBinding.getRoot();
    }

    public void acceptFreind(FriendAcceptRequestForm friendAcceptRequestForm){


        RetrofitCommunication.getInstance().setAcceptFriend(friendAcceptRequestForm);

        RetrofitCommunication.AddFriendCallBack addFriendCallBack = new RetrofitCommunication.AddFriendCallBack() {
            @Override
            public void addFriendDataPath(FriendAcceptMessage stringMessage) {
                Log.v("stringMessage",stringMessage.message+"");
                if (stringMessage.message == 1) {
                    requestFriendList();
                } else {

                }

            }
        };
        RetrofitCommunication.getInstance().setAddFriendCallBack(addFriendCallBack);
    }
    public void requestFriendList(){

        RetrofitCommunication.getInstance().sendFriendsListRequest();

        RetrofitCommunication.FriendsListCallBack friendsListCallBack = new RetrofitCommunication.FriendsListCallBack() {
            @Override
            public void friendsListDataPath(FriendArr friendArr) {
                FriendsModel friendsModel = new FriendsModel();

                friendsModelArrayList = friendsModel.getArrayListFriends(friendArr);
            }
        };
        RetrofitCommunication.getInstance().setFriendsListCallBack(friendsListCallBack);

    }
}
