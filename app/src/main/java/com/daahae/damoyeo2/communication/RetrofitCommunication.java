package com.daahae.damoyeo2.communication;

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
import com.daahae.damoyeo2.model.Data;
import com.daahae.damoyeo2.model.Landmark;
import com.daahae.damoyeo2.model.LoginCheck;
import com.daahae.damoyeo2.model.MidInfo;
import com.daahae.damoyeo2.model.Person;
import com.daahae.damoyeo2.model.RequestForm;
import com.daahae.damoyeo2.model.TransportInfoList;
import com.daahae.damoyeo2.model.TransportLandmarkInfoList;
import com.daahae.damoyeo2.model.UserLoginInfo;
import com.daahae.damoyeo2.model.UserRequest;
import com.daahae.damoyeo2.view.Constant;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCommunication {

    private RetrofitService retrofitService;
    private Retrofit retrofit;

    private ArrayList<String> totalTimes;
    private TransportInfoList transportList;
    private BuildingArr buildingList;
    private BuildingDetail buildingDetail;
    private TransportLandmarkInfoList transportLandmarkInfoList;
    private LoginCheck loginCheck;

    private UserCallBack userCallBack;
    private BuildingCallBack buildingCallBack;
    private BuildingDetailCallBack buildingDetailCallBack;
    private UserLandmarkBack userLandmarkBack;
    private LoginCallBack loginCallBack;
    private FriendsListCallBack friendsListCallBack;

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
        void friendsListDataPath(FriendsListCallBack friendsListCallBack);
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

    private void init() {
        retrofitService = retrofit.create(RetrofitService.class);
        totalTimes = new ArrayList<>();
    }

    private void connectServer(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(Constant.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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
                        transportList = new Gson().fromJson(json, TransportInfoList.class);
                        try {
                            ExceptionService.getInstance().isExistTransportInformation(transportList);
                        } catch (ExceptionHandle e) {
                            e.printStackTrace();
                            if (userCallBack != null) userCallBack.userDataPath(null);
                        }
                        if (transportList != null) {
                            Log.v("총 시간 개수", String.valueOf(transportList.getUserArr().size()));
                            TransportInfoList.getInstance().setUserArr(transportList.getUserArr());
                            // set MidInfo
                            LatLng latLng = new LatLng(transportList.getMidInfo().getMidLat(), transportList.getMidInfo().getMidLng());
                            MidInfo midInfo = new MidInfo(latLng, transportList.getMidInfo().getAddress());
                            MidInfo.setMidInfo(midInfo);
                            // set Landmark
                            if (transportList.getLandmark() != null) {
                                LatLng lmlatlng = new LatLng(transportList.getLandmark().getLatitude(), transportList.getLandmark().getLongitude());
                                Landmark landmark = new Landmark(lmlatlng, transportList.getLandmark().getName(), transportList.getLandmark().getAddress());
                                Landmark.setLandMark(landmark);
                            }
                            //* set TransportInfo
                            for (Data data : transportList.getUserArr())
                                totalTimes.add(String.valueOf(data.getTotalTime()));

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


    private void sendPersonLocationAndLandMark(ArrayList<Person> persons){
        try{
            ExceptionService.getInstance().isExistPerson(persons.size());
        }catch (ExceptionHandle e){
            e.printStackTrace();
            return;
        }
        String strMessage = makeLandMarkForm(persons);
        Log.v("메시지",strMessage);
        totalTimes = new ArrayList<>();

        final Call<JsonObject> comment = retrofitService.getLandMarkTransportData(strMessage);
        comment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.v("알림", response.toString());
                    Log.v("전체", response.body().toString());
                    JsonObject json = response.body();
                    if(response.body().toString().equals(Constant.ALGORITHM_ERROR)){
                        if (userLandmarkBack != null) userLandmarkBack.disconnectServer();
                        Log.e("algorithm","알고리즘 오류");
                    }else {
                        transportLandmarkInfoList = new Gson().fromJson(json, TransportLandmarkInfoList.class);
                        try {
                            ExceptionService.getInstance().isExistTransportLandmarkInformation(transportLandmarkInfoList);
                        } catch (ExceptionHandle e) {
                            e.printStackTrace();
                            if (userLandmarkBack != null) userLandmarkBack.userLandmarkDataPath(null);
                        }
                        if (transportLandmarkInfoList != null) {
                            Log.v("총 시간 개수", String.valueOf(transportLandmarkInfoList.getUserArr().size()));
                            TransportLandmarkInfoList.getInstance().setUserArr(transportLandmarkInfoList.getUserArr());

                            //* set TransportInfo
                            for (Data data : transportLandmarkInfoList.getUserArr())
                                totalTimes.add(String.valueOf(data.getTotalTime()));

                            if (userLandmarkBack != null) userLandmarkBack.userLandmarkDataPath(totalTimes);

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
                    //JsonObject json = response.body();
                    String json = response.body().toString();
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

    public void sendLoginInformation(){
        sendUserLoginInformation(UserLoginInfo.getInstance());
    }


    private String makeForm(ArrayList<Person> persons){
        String strMessage="{\"userArr\":[";
        for(int i=0;i<persons.size();i++){
            strMessage += persons.get(i).getAddressPosition().toString();
            if(i!=persons.size()-1)
                strMessage += ",";
        }
        strMessage+="]}";
        return strMessage;
    }

    private String makeLandMarkForm(ArrayList<Person> persons){
        String strMessage="{\"userArr\":[";
        for(int i=0;i<persons.size();i++){
            strMessage += persons.get(i).getAddressPosition().toString();
            if(i!=persons.size()-1)
                strMessage += ",";
        }
        strMessage +=
                "],\"midLat\":"+Landmark.getInstance().getLatLng().latitude+",\"midLng\":"+Landmark.getInstance().getLatLng().longitude+"}";
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

    public void setCategoryInformation(CategoryInfo categoryInfo){
        sendCategoryInformation(categoryInfo);
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

    public void setBuildingsDataInLandmark(){
        sendPersonLocationAndLandMark(Person.getInstance());
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

    public void setBuildingsDataInLandmark(int buildingType){
        UserRequest request = new UserRequest();
        request.setType(buildingType);
        request.setMidLat(Landmark.getInstance().getLatLng().latitude);
        request.setMidLng(Landmark.getInstance().getLatLng().longitude);
        sendBuildingInfo(request);
    }
}