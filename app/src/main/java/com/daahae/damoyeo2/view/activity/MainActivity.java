package com.daahae.damoyeo2.view.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.databinding.ActivityMainBinding;
import com.daahae.damoyeo2.databinding.FragmentFriendsBinding;
import com.daahae.damoyeo2.model.FriendArr;
import com.daahae.damoyeo2.navigator.MainNavigator;
import com.daahae.damoyeo2.view.adapter.AddFriendsAdapter;
import com.daahae.damoyeo2.view.adapter.FriendsAdapter;
import com.daahae.damoyeo2.view.fragment.ChattingFragment;
import com.daahae.damoyeo2.view.fragment.FriendsFragment;
import com.daahae.damoyeo2.view.fragment.SettingFragment;
import com.daahae.damoyeo2.view_model.FriendsModel;
import com.daahae.damoyeo2.view_model.MainViewModel;
import com.daahae.damoyeo2.view.Constant;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainNavigator{

    private MainViewModel mainViewModel;

    private ActivityMainBinding binding;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private static Context context;
    private static MainNavigator mainNavigator;


    private FriendsModel friendsModel = new FriendsModel();
    private ArrayList<FriendsModel> friendsModelArrayList;
    private AddFriendsAdapter addFriendsAdapter;

    private ArrayList<Boolean> isChecks = new ArrayList<>();

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        initViewModel();

        bindingView();

        initFragment();
    }

    public static MainNavigator getMainNavigator() {
        return mainNavigator;
    }

    private void initViewModel(){
        mainViewModel = new MainViewModel(this);
        Constant.context = this;
    }

    public static Context getMainContext(){
        return context;
    }

    private void bindingView(){
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setModel(mainViewModel);

        mainViewModel.onCreate();
    }

    private void initFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace( R.id.main_frame, FriendsFragment.getInstance());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void convertFragment(View view) {
        switch (view.getId()){
            case R.id.btn_person_main:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame, FriendsFragment.getInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btn_chatting_main:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame, ChattingFragment.getInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btn_setting_main:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame, SettingFragment.getInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;

        }

    }

    @Override
    public void enterChattingRoom(int position) {
        Log.v("item","chattingItemClick"+position);
    }

    @Override
    public void showAddFriendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout=View.inflate(this,R.layout.dialog_add_friend,null);
        builder.setView(customLayout);

        final EditText destEmail = customLayout.findViewById(R.id.editDestEmail);
        customLayout.findViewById(R.id.btn_add_friend_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.sendFriendAddRequest(destEmail.getText().toString());
            }
        });
        dialog=builder.create();
        dialog.show();
    }

    @Override
    public void closeFriendDialog() {
        dialog.dismiss();
    }

    @Override
    public void showAddFriendConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout=View.inflate(this,R.layout.dialog_confirm_add_friend,null);
        builder.setView(customLayout);

        dialog=builder.create();
        dialog.show();
    }

    @Override
    public void showAddFriendCancelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customLayout=View.inflate(this,R.layout.dialog_cancel_add_friend,null);
        builder.setView(customLayout);

        dialog=builder.create();
        dialog.show();

    }

    @Override
    public void showAddChattingRoomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout=View.inflate(this,R.layout.dialog_add_chatting_room,null);
        builder.setView(customLayout);

        final ListView listView = customLayout.findViewById(R.id.friendListView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(friendsModelArrayList.get(position).isCheck()) friendsModelArrayList.get(position).setCheck(false);
                else friendsModelArrayList.get(position).setCheck(true);
                mainViewModel.setAddChattingRoom(friendsModelArrayList.get(position).isCheck());
            }
        });

        RetrofitCommunication.getInstance().sendFriendsListRequest();

        RetrofitCommunication.FriendsListCallBack friendsListCallBack = new RetrofitCommunication.FriendsListCallBack() {
            @Override
            public void friendsListDataPath(FriendArr friendArr) {
                friendsModelArrayList = friendsModel.getRealFriendList(friendArr);

                addFriendsAdapter = new AddFriendsAdapter(context,friendsModelArrayList);
                listView.setAdapter(addFriendsAdapter);

                for(int i=0;i<friendArr.getFriends().size();i++){
                    isChecks.add(false);
                }
            }
        };
        RetrofitCommunication.getInstance().setFriendsListCallBack(friendsListCallBack);

        dialog=builder.create();
        dialog.show();


        customLayout.findViewById(R.id.btn_close_add_chatting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        customLayout.findViewById(R.id.btn_cancel_add_chatting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        customLayout.findViewById(R.id.btn_send_add_chatting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void closeAddChattingRoomDialog() {
        dialog.dismiss();
    }
}
