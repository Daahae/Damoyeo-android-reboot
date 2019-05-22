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
    private ArrayList<String> emails;
    private ArrayList<String> names;

    public ChattingListModel() {
    }


    public ChattingListModel(String name, String count, ArrayList<String> emails, ArrayList<String> names) {
        this.name = name;
        this.count = count;
        this.emails = emails;
        this.names = names;
    }

    public ChattingListModel(ChattingRoom chattingRoom, ArrayList<UserObject> userObjects){
        this.name = setRoomTitle(chattingRoom,userObjects);
        this.count = chattingRoom.count;
        emails = new ArrayList<>();

        if(chattingRoom.user1!=null) emails.add(chattingRoom.user1.replace(" ",""));
        if(chattingRoom.user2!=null) emails.add(chattingRoom.user2.replace(" ",""));
        if(chattingRoom.user3!=null) emails.add(chattingRoom.user3.replace(" ",""));
        if(chattingRoom.user4!=null) emails.add(chattingRoom.user4.replace(" ",""));
        if(chattingRoom.user5!=null) emails.add(chattingRoom.user5.replace(" ",""));
        if(chattingRoom.user6!=null) emails.add(chattingRoom.user6.replace(" ",""));

    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    private String findUser(String email, ArrayList<UserObject> userObjects){

        email = email.replaceAll(" ","");
        Log.v("email",email);
        for(int j=0;j<userObjects.size();j++){
            if(email.trim().equals(userObjects.get(j).email)) {
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

        if(title.length()>20) {
            title = title.substring(0, 20);
            title += "...";
        }
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
