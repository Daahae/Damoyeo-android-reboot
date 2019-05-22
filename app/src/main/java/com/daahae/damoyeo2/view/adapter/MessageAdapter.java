package com.daahae.damoyeo2.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.model.Messages;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter{

    private ArrayList<Messages> messages;
    Context context;

    public MessageAdapter(Context context) {
        this.messages = Messages.messages;
        this.context = context;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Messages getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public ArrayList<Messages> getMessages() {
        return messages;
    }

    public void clearAll(){
        messages.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_chatting, parent, false);
        }
        TextView txtOtherName = convertView.findViewById(R.id.txt_other_name_chatting_item);
        TextView txtOther = convertView.findViewById(R.id.txt_other_contexts_chatting_item);
        TextView txtMe = convertView.findViewById(R.id.txt_me_contexts_chatting_item);

        RelativeLayout relativeOther = convertView.findViewById(R.id.relative_other_chatting_item);
        RelativeLayout relativeMe= convertView.findViewById(R.id.relative_me_chatting_item);

        Messages messages = getItem(position);

        if(messages.getnMessageType() == Messages.MESSAGE_OTHER){
            txtOtherName.setText(messages.getName());
            txtOther.setText(messages.getContents());
            relativeOther.setVisibility(View.VISIBLE);
            relativeMe.setVisibility(View.GONE);
        }else if(messages.getnMessageType() == Messages.MESSAGE_ME){
            txtMe.setText(messages.getContents());
            relativeMe.setVisibility(View.VISIBLE);
            relativeOther.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void addItem(String name, String str, int type){
        Messages messages = new Messages();
        messages.setName(name);
        messages.setnMessageType(type);
        messages.setContents(str);

        this.messages.add(messages);
    }

}
