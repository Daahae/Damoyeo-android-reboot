package com.daahae.damoyeo2.view.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.model.Messages;
import com.daahae.damoyeo2.view.adapter.MessageAdapter;
import com.daahae.damoyeo2.view_model.ChattingViewModel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        initView();

        Intent intent = getIntent();
        title = intent.getStringExtra("chattingTitle");

        listMessage.setAdapter(adapter);
        adapter.clearAll();

        addMessage("people","hi",Messages.MESSAGE_OTHER);
        addMessage("people","hello",Messages.MESSAGE_ME);
        addMessage("people","안녕",Messages.MESSAGE_OTHER);

        adapter.notifyDataSetChanged();
        listMessage.setAdapter(adapter);
    }

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
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra("roomTitle",title);
                break;
            case R.id.btn_send_chatting:
                if(!editText.getText().toString().equals("")){
                    chattingViewModel.sendChatting(editText.getText().toString());
                }
                break;
        }
    }
}
