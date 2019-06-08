package com.daahae.damoyeo2.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.SocketCommunication;
import com.daahae.damoyeo2.model.Messages;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.adapter.MessageAdapter;
import com.daahae.damoyeo2.view_model.ChattingViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChattingActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listMessage;
    static public MessageAdapter adapter;

    private TextView txtTitle;
    private ImageButton btnClose;
    private Button btnSend;
    private EditText editText;

    String title;

    ChattingViewModel chattingViewModel;
    private JSONObject msg;
    private JSONArray json;

    SocketCommunication socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        Intent intent = getIntent();
        title = intent.getStringExtra("roomTitle");

        socket = new SocketCommunication();

        initView();

        listMessage.setAdapter(adapter);
        adapter.clearAll();

        adapter.notifyDataSetChanged();
        listMessage.setAdapter(adapter);

        getMessage();
    }

    private void getMessage(){
        //SocketCommunication.getInstance().sendSwitchRoom(Constant.CURRENT_ROOM_NUMBER);
        socket.sendSwitchRoom(Constant.CURRENT_ROOM_NUMBER);

        socket.setChattingCallBack(new SocketCommunication.ChattingCallBack() {
            @Override
            public void broadcastingMessage(String name, String message) {

                Log.v("json","msg");
                if(name.equals("msg")) {

                    adapter.clearAll();

                    try {
                        json = new JSONArray(message);

                        for (int i = 0; i < json.length(); i++) {
                            msg = json.getJSONObject(i);

                            if (msg.getString("userNickName").trim().equals(Constant.nickname)) {
                                addMessage(msg.getString("userNickName"), msg.getString("msg"), Messages.MESSAGE_ME);
                                Log.v("json nickname", msg.getString("userNickName"));
                                Log.v("json msg", msg.getString("msg"));
                            } else {
                                addMessage(msg.getString("userNickName"), msg.getString("msg"), Messages.MESSAGE_OTHER);

                            }
                        }


                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        /*
        SocketCommunication.ChattingCallBack chattingCallBack = new SocketCommunication.ChattingCallBack() {
            @Override
            public void broadcastingMessage(String name, String message) {
                Log.v("json","msg");
                if(name.equals("msg")){

                    adapter.clearAll();

                    try {
                        json = new JSONArray(message);

                        for(int i=0;i<json.length();i++){
                            msg = json.getJSONObject(i);

                            if(msg.getString("userNickName").trim().equals(Constant.nickname)) {
                                addMessage(msg.getString("userNickName"), msg.getString("msg"), Messages.MESSAGE_ME);
                                Log.v("json nickname", msg.getString("userNickName"));
                                Log.v("json msg", msg.getString("msg"));
                            } else {
                                addMessage(msg.getString("userNickName"), msg.getString("msg"), Messages.MESSAGE_OTHER);

                            }
                        }


                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        SocketCommunication.getInstance().setChattingCallBack(chattingCallBack);
        */
    }



    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            // 원래 하려던 동작 (UI변경 작업 등)

            adapter.notifyDataSetChanged();
            listMessage.setAdapter(adapter);

            listMessage.setSelection(adapter.getCount()-1);
        }
    };

    private void addMessage(String name, String contents, int type){
        adapter.addItem(name,contents,type);
    }

    private void initView() {
        listMessage = findViewById(R.id.list_chatting);
        adapter = new MessageAdapter(this);

        txtTitle = findViewById(R.id.txt_title_chatting);
        txtTitle.setText(title);

        btnClose = findViewById(R.id.btn_close_chatting);
        btnClose.setOnClickListener(this);

        chattingViewModel = new ChattingViewModel();

        btnSend = findViewById(R.id.btn_send_chatting);
        btnSend.setOnClickListener(this);

        editText = findViewById(R.id.edit_chatting);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close_chatting:
                onBackPressed();
                /*
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("roomTitle",title);
                startActivity(intent);
                */
                break;
            case R.id.btn_send_chatting:
                if(!editText.getText().toString().equals("")){
                    socket.sendChatting(editText.getText().toString());
                    //SocketCommunication.getInstance().sendChatting(editText.getText().toString());
                    addMessage(Constant.nickname,editText.getText().toString(),Messages.MESSAGE_ME);
                    editText.setText("");
                    getMessage();
                }
                break;
        }
    }
}
