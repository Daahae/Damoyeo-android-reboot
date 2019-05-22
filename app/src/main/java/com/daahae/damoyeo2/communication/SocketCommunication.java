package com.daahae.damoyeo2.communication;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.daahae.damoyeo2.model.Messages;
import com.daahae.damoyeo2.view.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketCommunication {

    private static final String TAG = "MAIN_ACTIVITY";
    private static final String URL = "http://54.180.106.100:3443";

    private Socket mSocket;

    private String user, data;

    private ChattingCallBack chattingCallBack;

    private static SocketCommunication instance = new SocketCommunication();

    public static synchronized SocketCommunication getInstance() {
        return instance;
    }

    public SocketCommunication(){

    }

    public interface ChattingCallBack{
        void broadcastingMessage(String name, String message);
    }
    public void setChattingCallBack(ChattingCallBack chattingCallBack){
        this.chattingCallBack = chattingCallBack;
    }

    private void connectSocket(){

        try {
            mSocket = IO.socket(URL);
            mSocket.connect();
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on("serverMessage", onMessageReceived); // 메시지전송
            mSocket.on("updateChat", onUpdateReceived); // 방생성
            mSocket.on("updateRooms", onChangeRoomsReceived); //방변경
        } catch(URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void sendSwitchRoom(int room){
        mSocket.emit("switchRoom",room) ;
    }

    public void sendAddUser(ArrayList<String> users, int room){
        String message="{\"emailList\":\"";
        for(int i=0;i<users.size();i++){
            if (i==users.size()-1)
                message += users.get(i);
            else
                message += users.get(i)+", ";
        }
        message += "\",\"room\":"+room+"}";

        mSocket.emit("addUser", message);
    }

    public void sendChatting(String chat){
        mSocket.emit("sendChat", chat);
    }

    /** Connect 되면 바로 실행됨
     * 서버에 connect 되었음을 확인하는 용도로 사용
     */
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            mSocket.emit("clientMessage", Constant.email);
        }
    };

    /** updateRooms 방 변경
     */
    private Emitter.Listener onChangeRoomsReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("data",args[0].toString());
        }
    };


    /** 서버로부터 전달받은 'chat-message' Event 처리.
     * BroadCasting 된 메시지 전달받는 형태
     */
    // json 형식
    private Emitter.Listener onMessageReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // 전달받은 데이터는 아래와 같이 추출할 수 있습니다.
            try {
                JSONObject receivedData = (JSONObject) args[0];

                Log.d("msg", receivedData.getString("msg"));
                Log.d("data", receivedData.getString("data"));
                //message = receivedData.getString("data");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    /*
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            // 원래 하려던 동작 (UI변경 작업 등)

            if(user.equals(me)) {
                adapter.addItem(user, message, Messages.MESSAGE_ME);
                adapter.notifyDataSetChanged();
            }
            else {
                adapter.addItem(user, message, Messages.MESSAGE_OTHER);
                adapter.notifyDataSetChanged();
            }
        }
    };
    */

    // String 형식
    private Emitter.Listener onUpdateReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            // 전달받은 데이터는 아래와 같이 추출할 수 있습니다.

            //Log.d("data",args[0].toString());

            JSONObject receivedData = (JSONObject) args[0];
            try {
                Log.d("user", receivedData.getString("user"));
                Log.d("data", receivedData.getString("data"));
                user = receivedData.getString("user");
                data = receivedData.getString("data");

                if(chattingCallBack!=null) chattingCallBack.broadcastingMessage(user,data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Message msg = handler.obtainMessage();
            //handler.sendMessage(msg);
        }
    };
}
