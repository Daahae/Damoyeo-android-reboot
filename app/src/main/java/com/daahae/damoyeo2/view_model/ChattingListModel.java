package com.daahae.damoyeo2.view_model;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.model.ChattingList;
import com.daahae.damoyeo2.model.ChattingRoom;
import com.daahae.damoyeo2.model.ChattingRoomArr;
import com.daahae.damoyeo2.model.Friend;
import com.daahae.damoyeo2.model.UserObject;
import com.daahae.damoyeo2.view.Constant;

import java.util.ArrayList;

public class ChattingListModel {

    private String name;
    private String count;

    public ChattingListModel() {
    }

    public ChattingListModel(ChattingRoom chattingRoom, ArrayList<UserObject> userObjects){
        this.name = setRoomTitle(chattingRoom,userObjects);
        this.count = chattingRoom.count;
    }

    private String findUser(String email, ArrayList<UserObject> userObjects){

        for(int j=0;j<userObjects.size();j++){
            if(email.equals(userObjects.get(j).email)) {
                return userObjects.get(j).nickname;
            }
        }
        return null;
    }
    private String setRoomTitle(ChattingRoom chattingRoom,ArrayList<UserObject> userObjects){
        String title="";
        if(chattingRoom.user1!=null)
            title += findUser(chattingRoom.user1,userObjects) + ", ";
        if(chattingRoom.user2!=null)
            title += findUser(chattingRoom.user2,userObjects) + ", ";
        if(chattingRoom.user3!=null) title += findUser(chattingRoom.user3,userObjects) + ", ";
        if(chattingRoom.user4!=null) title += findUser(chattingRoom.user4,userObjects) + ", ";
        if(chattingRoom.user5!=null) title += findUser(chattingRoom.user5,userObjects) + ", ";
        if(chattingRoom.user6!=null) title += findUser(chattingRoom.user6,userObjects) + ", ";

        title = title.substring(0, title.length()-2);

        return title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<ChattingListModel> getArrayListChattingList(ChattingRoomArr chattingRoomArr){
        ArrayList<ChattingListModel> chattingListModelArrayList = new ArrayList<>();
        for(int i=0;i<chattingRoomArr.getChattingRooms().size();i++){
            ChattingListModel chattingListModel = new ChattingListModel(chattingRoomArr.getChattingRooms().get(i), chattingRoomArr.getUserObjects());
            chattingListModelArrayList.add(chattingListModel);
        }
        return chattingListModelArrayList;

    }

}
