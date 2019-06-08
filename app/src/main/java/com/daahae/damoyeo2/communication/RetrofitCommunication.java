package com.daahae.damoyeo2.communication;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;


import com.daahae.damoyeo2.exception.ExceptionHandle;
import com.daahae.damoyeo2.exception.ExceptionService;
import com.daahae.damoyeo2.model.Building;
import com.daahae.damoyeo2.model.BuildingArr;
import com.daahae.damoyeo2.model.BuildingDetail;
import com.daahae.damoyeo2.model.BuildingRequest;
import com.daahae.damoyeo2.model.Category;
import com.daahae.damoyeo2.model.CategoryInfo;
import com.daahae.damoyeo2.model.ChattingRequest;
import com.daahae.damoyeo2.model.ChattingRoomArr;
import com.daahae.damoyeo2.model.FriendAcceptRequestForm;
import com.daahae.damoyeo2.model.FriendArr;
import com.daahae.damoyeo2.model.FriendAddRequestForm;
import com.daahae.damoyeo2.model.LoginCheck;
import com.daahae.damoyeo2.model.FriendAcceptMessage;
import com.daahae.damoyeo2.model.MidInfo;
import com.daahae.damoyeo2.model.Person;
import com.daahae.damoyeo2.model.RequestForm;
import com.daahae.damoyeo2.model.ScheduleArr;
import com.daahae.damoyeo2.model.ScheduleRequest;
import com.daahae.damoyeo2.model.UserArr;
import com.daahae.damoyeo2.model.UserLoginInfo;
import com.daahae.damoyeo2.model.UserPos;
import com.daahae.damoyeo2.model.SearchPubTransPath;
import com.daahae.damoyeo2.model.TransPathList;
import com.daahae.damoyeo2.model.UserRequest;
import com.daahae.damoyeo2.view.Constant;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCommunication {

    private final String TAG = "RETROFIT_COMMUNICATION";

    private RetrofitService retrofitService;
    private Retrofit retrofit;

    private ArrayList<String> totalTimes;
    private TransPathList transportList;
    private BuildingArr buildingList;
    private BuildingDetail buildingDetail;
    private FriendArr friendList;
    private ChattingRoomArr chattingRoomArr;
    private LoginCheck loginCheck;
    private FriendAcceptMessage friendAcceptMessage;
    private UserArr userArr;
    private ScheduleArr scheduleArr;

    private UserCallBack userCallBack;
    private BuildingCallBack buildingCallBack;
    private BuildingDetailCallBack buildingDetailCallBack;
    private UserLandmarkBack userLandmarkBack;
    private LoginCallBack loginCallBack;
    private FriendsListCallBack friendsListCallBack;
    private ChattingRoomListCallBack chattingRoomListCallBack;
    private AddFriendCallBack addFriendCallBack;
    private DetailChattingRoomCallBack detailChattingRoomCallBack;
    private ScheduleCallBack scheduleCallBack;

    private static RetrofitCommunication instance = new RetrofitCommunication();

    public static synchronized RetrofitCommunication getInstance() {
        return instance;
    }

    public interface UserCallBack {
        void userDataPath(ArrayList<String> totalTimes);
        void disconnectServer();
    }

    public interface BuildingCallBack {
        void buildingDataPath(BuildingArr buildingArr);
    }

    public interface BuildingDetailCallBack{
        void buildingDetailDataPath(BuildingDetail buildingDetail);
    }

    public interface LoginCallBack {
        void loginDataPath(LoginCheck loginCheck);
    }

    public interface FriendsListCallBack {
        void friendsListDataPath(FriendArr friendArr);
    }

    public interface AddFriendCallBack {
        void addFriendDataPath(FriendAcceptMessage friendAcceptMessage);
    }

    public interface ChattingRoomListCallBack {
        void chattingRoomListDataPath(ChattingRoomArr chattingRoomArr);
    }

    public interface DetailChattingRoomCallBack {
        void detailChattingRoomDataPath(UserArr userArr);
    }

    public interface ScheduleCallBack {
        void scheduleDataPath(ScheduleArr scheduleArr);
    }

    public interface UserLandmarkBack {
        void userLandmarkDataPath(ArrayList<String> totalTimes);
        void disconnectServer();
    }

    private RetrofitCommunication(){
        connectServer();
        init();
    }

    public void setUserData(UserCallBack userCallBack){
        this.userCallBack = userCallBack;
    }
    public void setBuildingData(BuildingCallBack buildingCallBack){
        this.buildingCallBack = buildingCallBack;
    }
    public void setBuildingDetailData(BuildingDetailCallBack buildingDetailCallBack){
        this.buildingDetailCallBack = buildingDetailCallBack;
    }
    public void setUserLandmarkData(UserLandmarkBack userLandmarkBack){
        this.userLandmarkBack = userLandmarkBack;
    }
    public void setLoginCheck(LoginCallBack loginCallBack){
        this.loginCallBack = loginCallBack;
    }
    public void setFriendsListCallBack(FriendsListCallBack friendsListCallBack){
        this.friendsListCallBack = friendsListCallBack;
    }
    public void setAddFriendCallBack(AddFriendCallBack addFriendCallBack){
        this.addFriendCallBack = addFriendCallBack;
    }
    public void setChattingRoomListCallBack(ChattingRoomListCallBack chattingRoomListCallBack){
        this.chattingRoomListCallBack = chattingRoomListCallBack;
    }
    public void setDetailChattingRoomCallBack(DetailChattingRoomCallBack detailChattingRoomCallBack){
        this.detailChattingRoomCallBack = detailChattingRoomCallBack;
    }
    public void setScheduleArrCallBack(ScheduleCallBack scheduleCallBack){
        this.scheduleCallBack = scheduleCallBack;
    }
    private void init() {
        retrofitService = retrofit.create(RetrofitService.class);
        totalTimes = new ArrayList<>();
    }

    private void connectServer(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(Constant.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void sendUserPosForSync(UserPos userPos, final Handler handler){
        String strMessage = userPos.toString();

        final Call<JsonObject> comment = retrofitService.getUserLatLng(strMessage);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject json = response.body();
                    try {
                        UserPos userPosList = new Gson().fromJson(json, UserPos.class);
                        if (userPosList != null) {
                            UserPos.getInstance().clear();
                            for (UserPos user: userPosList.getUserPosList()) {
                                if (user.getStartLat() != -1) {
                                    UserPos.getInstance().add(user);
                                    Log.d(TAG, "userPos list" + user.toString());
                                }
                            }
                            if (userPosList.getUserPosList().size() > 0)
                                handler.sendEmptyMessage(Constant.REQUEST_LOCATION_SYNC_SUCCESS);
                            else
                                handler.sendEmptyMessage(Constant.REQUEST_LOCATION_SYNC_NONE);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "callback error" + e.getMessage());
                        handler.sendEmptyMessage(Constant.REQUEST_LOCATION_SYNC_FAIL);
                    }
                } else {
                    Log.e(TAG, "response fail" + response.message());
                    handler.sendEmptyMessage(Constant.REQUEST_LOCATION_SYNC_FAIL);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e(TAG, "enqueue fail " + t.getMessage());
                handler.sendEmptyMessage(Constant.REQUEST_LOCATION_SYNC_FAIL);
            }
        });
    }

    private void sendPersonLocation(ArrayList<Person> persons){
        try{
            ExceptionService.getInstance().isExistPerson(persons.size());
        }catch (ExceptionHandle e){
            e.printStackTrace();
            return;
        }
        String strMessage = makeForm(persons);
        Log.v("메시지",strMessage);
        totalTimes = new ArrayList<>();

        final Call<JsonObject> comment = retrofitService.getMidTransportData(strMessage);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("알림", response.toString());
                    Log.v("전체", response.body().toString());
                    JsonObject json = response.body();
                    if(response.body().toString().equals(Constant.ALGORITHM_ERROR)){
                        if (userCallBack != null) userCallBack.disconnectServer();
                        Log.e("algorithm","알고리즘 오류");
                    }else {
                        transportList = new Gson().fromJson(json, TransPathList.class);
                        try {
                            ExceptionService.getInstance().isExistTransportInformation(transportList);
                        } catch (ExceptionHandle e) {
                            e.printStackTrace();
                            if (userCallBack != null) userCallBack.userDataPath(null);
                        }
                        if (transportList != null) {
                            Log.v("총 시간 개수", String.valueOf(transportList.getUserArr().size()));

                            for (SearchPubTransPath transPath: transportList.getUserArr()) {
                                transPath.setTotalHour(transPath.getTotalTime() / 60);
                                transPath.setTotalMinute(transPath.getTotalTime() % 60);
                            }
                            TransPathList.getInstance().setUserArr(transportList.getUserArr());
                            // set MidInfo
                            LatLng latLng = new LatLng(transportList.getMidInfo().getMidLat(), transportList.getMidInfo().getMidLng());
                            MidInfo midInfo = new MidInfo(latLng, transportList.getMidInfo().getAddress());
                            MidInfo.setMidInfo(midInfo);
                            //* set TransportInfo
                            for (SearchPubTransPath searchPubTransPath : transportList.getUserArr())
                                totalTimes.add(String.valueOf(searchPubTransPath.getTotalTime()));

                            if (userCallBack != null) userCallBack.userDataPath(totalTimes);

                            Log.d("end1", new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS").format(System.currentTimeMillis()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                if (userCallBack != null) userCallBack.disconnectServer();
                Log.e("retrofit","통신 실패");
            }
        });
    }

    private void sendBuildingInfo(UserRequest request){
        try{
            ExceptionService.getInstance().isCorrectUserRequest(request);
        }catch (ExceptionHandle e){
            e.printStackTrace();
            return;
        }
        String message = request.toString();
        Log.v("메시지",message+"");

        final Call<JsonObject> comment = retrofitService.getBuildingData(message);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("알림", response.toString());
                    Log.v("전체", response.body().toString());
                    JsonObject json = response.body();
                    buildingList = new Gson().fromJson(json, BuildingArr.class);

                    try {
                        ExceptionService.getInstance().isExistBuilding(buildingList);
                    } catch (ExceptionHandle exceptionHandle) {
                        exceptionHandle.printStackTrace();
                        if(buildingCallBack!=null)buildingCallBack.buildingDataPath(null);
                    }
                    if(buildingList!=null){
                        Log.v("총 빌딩 개수", String.valueOf(buildingList.getBuildingArr().size()));
                        if(buildingCallBack!=null)buildingCallBack.buildingDataPath(buildingList);
                    }

                    Log.d("end2", new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS").format(System.currentTimeMillis()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("retrofit","통신 실패");
            }
        });
    }


    private void sendBuildingDetail(BuildingRequest request){
        try {
            ExceptionService.getInstance().isCorrectBuildingRequest(request);
        } catch (ExceptionHandle exceptionHandle) {
            exceptionHandle.printStackTrace();
            return;
        }
        String message = request.toString();
        Log.v("메시지",message+"");

        final Call<JsonObject> comment = retrofitService.getBuildingDetail(message);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("알림", response.toString());
                    Log.v("전체", response.body().toString());
                    JsonObject json = response.body();
                    buildingDetail = new Gson().fromJson(json, BuildingDetail.class);
                    try {
                        ExceptionService.getInstance().isExistBuildingDetail(buildingDetail);
                    } catch (ExceptionHandle exceptionHandle) {
                        exceptionHandle.printStackTrace();
                    }
                    buildingDetailCallBack.buildingDetailDataPath(buildingDetail);
                }
            }


            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("retrofit","통신 실패");
            }
        });
    }

    private void sendUserLoginInformation(UserLoginInfo userLoginInfo){
        String message = userLoginInfo.toString();
        Log.v("메시지",message+"");

        final Call<JsonObject> comment = retrofitService.getLoginInfo(message);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("알림", response.toString());
                    Log.v("전체", response.body().toString());
                    //JsonObject json = response.body();
                    String json = response.body().toString();
                    loginCheck = new Gson().fromJson(json, LoginCheck.class);
                    Log.v("로그인", loginCheck.getHistory()+"");
                    loginCallBack.loginDataPath(loginCheck);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("retrofit","통신 실패");
            }
        });
    }


    private void sendDetailChattingRoomRequest(ChattingRequest request){
        String message = request.toString();
        Log.v("메시지 detailChatRoom",message+"");

        final Call<JsonObject> comment = retrofitService.getChattingRoomInformation(message);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("알림", response.toString());
                    Log.v("전체", response.body().toString());
                    //JsonObject json = response.body();
                    JsonObject json = response.body();
                    userArr = new Gson().fromJson(json, UserArr.class);
                    if(detailChattingRoomCallBack != null) detailChattingRoomCallBack.detailChattingRoomDataPath(userArr);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("retrofit","통신 실패");
            }
        });
    }

    private void sendFriendsRequest(RequestForm request){
        String message = request.toString();
        Log.v("메시지",message+"");

        final Call<JsonObject> comment = retrofitService.getFriendsList(message);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("알림", response.toString());
                    Log.v("전체", response.body().toString());
                    JsonObject json = response.body();
                    friendList = new Gson().fromJson(json, FriendArr.class);
                    if(friendsListCallBack!=null) friendsListCallBack.friendsListDataPath(friendList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("retrofit","통신 실패");
            }
        });
    }

    private void sendAddFriend(FriendAddRequestForm request){
        String message = request.toString();
        Log.v("메시지",message+"");

        final Call<JsonObject> comment = retrofitService.getAddFriend(message);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("알림", response.toString());
                    Log.v("전체", response.body().toString());
                    String json = response.body().toString();
                    friendAcceptMessage = new Gson().fromJson(json, FriendAcceptMessage.class);
                    if(addFriendCallBack!=null) addFriendCallBack.addFriendDataPath(friendAcceptMessage);

                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("retrofit","통신 실패");
            }
        });
    }

    private void sendAcceptFriend(FriendAcceptRequestForm request){
        String message = request.toString();
        Log.v("메시지",message+"");

        final Call<JsonObject> comment = retrofitService.getAcceptFriend(message);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("알림", response.toString());
                    Log.v("전체", response.body().toString());
                    String json = response.body().toString();
                    friendAcceptMessage = new Gson().fromJson(json, FriendAcceptMessage.class);
                    if(addFriendCallBack!=null) addFriendCallBack.addFriendDataPath(friendAcceptMessage);

                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("retrofit","통신 실패");
            }
        });
    }

    private void sendRoomListRequest(RequestForm request){
        String message = request.toString();
        Log.v("메시지 chatRoom",message+"");

        final Call<JsonObject> comment = retrofitService.getRoomList(message);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("알림", response.toString());
                    Log.v("전체", response.body().toString());
                    //JsonObject json = response.body();
                    String json = response.body().toString();
                    chattingRoomArr = new Gson().fromJson(json, ChattingRoomArr.class);
                    if(chattingRoomListCallBack!=null) chattingRoomListCallBack.chattingRoomListDataPath(chattingRoomArr);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("retrofit","통신 실패");
            }
        });
    }

    private void sendCategoryInformation(CategoryInfo categoryInfo){
        String message = makeCategoryForm(categoryInfo);
        Log.v("메시지",message+"");

        final Call<JsonObject> comment = retrofitService.getCategoryInformation(message);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("알림", response.toString());
                    Log.v("전체", response.body().toString());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("retrofit","통신 실패");
            }
        });
    }
    private void sendScheduleRequest(ScheduleRequest request){
        String message = request.toString();
        Log.v("메시지 ScheduleRequest",message+"");

        final Call<JsonObject> comment = retrofitService.getScheduleResult(message);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("알림", response.toString());
                    Log.v("전체", response.body().toString());
                    //JsonObject json = response.body();
                    String json = response.body().toString();
                    json = reformStringToJson(json);
                    scheduleArr = new Gson().fromJson(json, ScheduleArr.class);
                    if(scheduleCallBack!=null) scheduleCallBack.scheduleDataPath(scheduleArr);
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e("retrofit","스케줄 통신 실패");
            }
        });
    }

    public void sendLoginInformation(){
        sendUserLoginInformation(UserLoginInfo.getInstance());
    }

    private String reformStringToJson(String str){
        String strSpilt = str.substring(61,str.length()-3);
        strSpilt = strSpilt.replace("\\","");
        Log.v("strSpilt",strSpilt);
        return strSpilt;
    }

    private String makeForm(ArrayList<Person> persons){
        String strMessage="{\"userArr\":[";
        for(int i=0;i<persons.size();i++){
            strMessage += persons.get(i).getAddressPosition().toString();
            if(i!=persons.size()-1)
                strMessage += ",";
        }
        strMessage+="]}";
        //,\"meetingTime\":"+Constant.MEETING_TIME+"}";
        return strMessage;
    }

    private String makeCategoryForm(CategoryInfo categoryInfo){
        ArrayList<Category> categories  = categoryInfo.getCategories();
        String strMessage="[";
        for(int i=0;i<categories.size();i++){
            strMessage += categories.get(i).toString();
            if(i!=categories.size()-1)
                strMessage += ",";
        }
        strMessage +=
                "]";
        return strMessage;
    }

    public void setAddFriend(FriendAddRequestForm friendAddRequestForm){
        sendAddFriend(friendAddRequestForm);
    }

    public void setAcceptFriend(FriendAcceptRequestForm friendAcceptRequestForm){
        sendAcceptFriend(friendAcceptRequestForm);
    }

    public void setDetailChattingRoomNumber(ChattingRequest chattingRequest){
        sendDetailChattingRoomRequest(chattingRequest);
    }

    public void setCategoryInformation(CategoryInfo categoryInfo){
        sendCategoryInformation(categoryInfo);
    }

    public void sendRoomListRequest(){
        RequestForm request = new RequestForm();
        request.setEmail(Constant.email);
        sendRoomListRequest(request);
    }

    public void setScheduleRequest(){
        ScheduleRequest request = new ScheduleRequest();
        request.setRoomNumber(Constant.CURRENT_ROOM_NUMBER);
        Log.v("스케줄",Constant.CURRENT_ROOM_NUMBER+"");
        sendScheduleRequest(request);
    }

    public void sendFriendsListRequest(){
        RequestForm request = new RequestForm();
        request.setEmail(Constant.email);
        sendFriendsRequest(request);
    }

    public void sendMarkerTimeMessage(){
        sendPersonLocation(Person.getInstance());
        Log.v(Constant.TAG, "전송");
    }

    public void clickItem(Building building){
        Building.setInstance(building);
        BuildingRequest buildingRequest = new BuildingRequest(building.getName(),building.getLatitude(),building.getLongitude());
        sendBuildingDetail(buildingRequest);
    }

    public void setBuildingsData(int buildingType){
        UserRequest request = new UserRequest();
        request.setType(buildingType);
        request.setMidLat(MidInfo.getInstance().getLatLng().latitude);
        request.setMidLng(MidInfo.getInstance().getLatLng().longitude);
        sendBuildingInfo(request);
    }
}