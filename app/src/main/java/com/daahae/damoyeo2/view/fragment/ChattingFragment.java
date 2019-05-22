package com.daahae.damoyeo2.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.databinding.ActivityMainBinding;
import com.daahae.damoyeo2.databinding.FragmentChattingBinding;
import com.daahae.damoyeo2.databinding.FragmentFriendsBinding;
import com.daahae.damoyeo2.model.ChattingRoomArr;
import com.daahae.damoyeo2.model.FriendArr;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.activity.MainActivity;
import com.daahae.damoyeo2.view.adapter.ChattingListAdapter;
import com.daahae.damoyeo2.view.adapter.FriendsAdapter;
import com.daahae.damoyeo2.view_model.ChattingListModel;
import com.daahae.damoyeo2.view_model.FriendsModel;
import com.daahae.damoyeo2.view_model.MainViewModel;

import java.util.ArrayList;

public class ChattingFragment extends Fragment {

    private ChattingListModel chattingListModel;
    private ArrayList<ChattingListModel> chattingListModelArrayList;

    private ChattingListAdapter chattingListAdapter;

    public ChattingFragment(){

    }

    public static ChattingFragment getInstance(){
        return new ChattingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_chatting, container, false).getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RetrofitCommunication.getInstance().sendRoomListRequest();

        final MainViewModel model = new MainViewModel(MainActivity.getMainNavigator());
        FragmentChattingBinding binding = DataBindingUtil.getBinding(getView());
        binding.setModel(model);
        model.onCreate();

        chattingListModel = new ChattingListModel();

        RetrofitCommunication.ChattingRoomListCallBack chattingRoomListCallBack = new RetrofitCommunication.ChattingRoomListCallBack() {
            @Override
            public void chattingRoomListDataPath(ChattingRoomArr chattingRoomArr) {
                FragmentChattingBinding binding = DataBindingUtil.getBinding(getView());
                chattingListModelArrayList = new ArrayList<>();

                if(chattingRoomArr != null||chattingRoomArr.getChattingRooms() != null) {

                    chattingListModelArrayList = chattingListModel.getArrayListChattingList(chattingRoomArr);

                    chattingListAdapter = new ChattingListAdapter(getContext(), chattingListModelArrayList);
                    binding.listView.setAdapter(chattingListAdapter);

                    model.setChattingList(binding, chattingRoomArr);
                }
            }
        };

        RetrofitCommunication.getInstance().setChattingRoomListCallBack(chattingRoomListCallBack);

    }


}
