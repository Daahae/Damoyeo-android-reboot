package com.daahae.damoyeo2.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.databinding.ItemChattingListBinding;
import com.daahae.damoyeo2.databinding.ItemFriendBinding;
import com.daahae.damoyeo2.view_model.ChattingListModel;
import com.daahae.damoyeo2.view_model.FriendsModel;

import java.util.ArrayList;

public class ChattingListAdapter extends ArrayAdapter<ChattingListModel>{

    private ArrayList<ChattingListModel> chattingListModelArrayList;
    private Context context;

    public ChattingListAdapter(@NonNull Context context, ArrayList<ChattingListModel> chattingListModelArrayList) {
        super(context, R.layout.item_chatting_list, chattingListModelArrayList);
        this.context = context;
        this.chattingListModelArrayList = chattingListModelArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemChattingListBinding itemFriendBinding = DataBindingUtil.inflate(layoutInflater,R.layout.item_chatting_list,parent,false);
        itemFriendBinding.setModel(chattingListModelArrayList.get(position));

        return itemFriendBinding.getRoot();
    }
}
