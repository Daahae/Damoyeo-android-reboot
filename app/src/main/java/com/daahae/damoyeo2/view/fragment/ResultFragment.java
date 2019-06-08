package com.daahae.damoyeo2.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.databinding.FragmentResultBinding;
import com.daahae.damoyeo2.model.FloatingActionBtn;
import com.daahae.damoyeo2.model.MidInfo;
import com.daahae.damoyeo2.model.Person;
import com.daahae.damoyeo2.model.ScheduleArr;
import com.daahae.damoyeo2.model.SimpleSchedule;
import com.daahae.damoyeo2.navigator.LoadingNavigator;
import com.daahae.damoyeo2.presenter.CategoryPresenter;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.activity.ChattingActivity;
import com.daahae.damoyeo2.view.activity.DirectionActivity;
import com.daahae.damoyeo2.view.activity.NavigationDrawerActivity;
import com.daahae.damoyeo2.view.activity.ScheduleActivity;
import com.daahae.damoyeo2.view.adapter.MarkerTimeAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;


public class ResultFragment
        extends Fragment
        implements View.OnClickListener, View.OnTouchListener, AdapterView.OnItemClickListener, SlidingDrawer.OnDrawerOpenListener, SlidingDrawer.OnDrawerCloseListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LoadingNavigator {

    private static final String ARG_PARAM1 = "param1";

    private FragmentResultBinding binding;

    private CategoryPresenter presenter;

    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient = null;
    private LatLngBounds.Builder builder;

    private ArrayList<String> totalTimes = null;

    private MarkerTimeAdapter markerTimeAdapter;
    private ArrayList<SimpleSchedule> mItems = new ArrayList();
    private static ScheduleArr scheduleArr;

    private int count=0;
    private String title="";
    private FloatingActionBtn fabtn;

    private static ResultFragment instance = new ResultFragment();

    public ListView getListMarkerTime() {
        return binding.lvList;
    }

    public MarkerTimeAdapter getMarkerTimeAdapter() {
        return markerTimeAdapter;
    }

    public RelativeLayout getRelativeMap() {
        return binding.rlMap;
    }

    public ResultFragment() {

    }

    public static ResultFragment getInstance(String title) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        instance.setArguments(args);
        return instance;
    }

    public static ResultFragment newInstance(String title) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
        }

        markerTimeAdapter = new MarkerTimeAdapter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false);

        initView();
        initListener();

        presenter.startCallback();

        return binding.getRoot();
    }

    private void initView(){
        presenter = new CategoryPresenter(title);

        binding.mapMain.getMapAsync(this);
        setLoadingAnimation();

        setMarkerTimeList(markerTimeAdapter);
        binding.lvList.setAdapter(markerTimeAdapter);

        fabtn = new FloatingActionBtn();
        fabtn.setFabChat(binding.fabChatResult);
        Log.v("init","initView");

        connectRetrofit();

        NavigationDrawerActivity.setButton().setClickable(false);

    }


    private void setData(String[] titles){
        count=0;
        Log.v("Add","setData");
        for(int i=0;i<titles.length;i++) {
            addSimpleSchedule(titles[i]);
        }
    }

    private void setLoadingAnimation(){
        binding.ivLoading.setVisibility(View.VISIBLE);
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.loading);
        binding.ivLoading.setAnimation(anim);
    }

    private void initListener(){

        binding.btnAll.setOnClickListener(this);

        binding.lvList.setOnItemClickListener(this);

        binding.content.setOnTouchListener(this);

        binding.slide.setOnDrawerCloseListener(this);
        binding.slide.setOnDrawerOpenListener(this);
        binding.slide.clearAnimation();

        fabtn.getFabChat().setOnClickListener(this);
    }

    private LinearLayout.LayoutParams setMargin(int width, int height, int left, int top, int right, int bottom){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.bottomMargin = bottom;
        params.leftMargin = left;
        params.rightMargin = right;
        params.topMargin = top;

        return params;
    }

    public void setScheduleArr(ScheduleArr scheduleArr) {
        this.scheduleArr = scheduleArr;
    }

    public static ScheduleArr getScheduleArr() {
        return scheduleArr;
    }

    private void connectRetrofit(){

        final RetrofitCommunication.ScheduleCallBack scheduleCallBack = new RetrofitCommunication.ScheduleCallBack() {
            @Override
            public void scheduleDataPath(ScheduleArr scheduleArr) {
                binding.txtLoading.setVisibility(View.GONE);
                String[] titles = new String[scheduleArr.getScheduleArr().size()];
                for(int i=0;i<titles.length;i++) titles[i] = "";
                for(int i=0;i<scheduleArr.getScheduleArr().size();i++) {
                    for(int j=0;j<scheduleArr.getScheduleArr().get(0).size();j++){
                        if(j==scheduleArr.getScheduleArr().get(i).size()-1)
                            titles[i] += "#" + scheduleArr.getScheduleArr().get(i).get(j).category;
                        else
                            titles[i] += "#" + scheduleArr.getScheduleArr().get(i).get(j).category+"\n";
                    }
                }
                setData(titles);
                setScheduleArr(scheduleArr);

                NavigationDrawerActivity.setButton().setClickable(true);
            }
        };
        RetrofitCommunication.getInstance().setScheduleArrCallBack(scheduleCallBack);
    }

    private void addSimpleSchedule(String contexts){
        RelativeLayout relativeLayout = new RelativeLayout(Constant.context);
        relativeLayout.setLayoutParams(setMargin(400,400,30,30,30,30));
        relativeLayout.setBackgroundColor(Constant.context.getResources().getColor(R.color.colorWhite));
        binding.content.addView(relativeLayout);

        final ImageButton imageBack = new ImageButton(Constant.context);
        imageBack.setLayoutParams(setMargin(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,0,0,0,0));
        imageBack.setPadding(0,0,0,0);
        imageBack.setId(++count);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ID",v.getId()+"");
                Intent intent = new Intent(getContext(), ScheduleActivity.class);
                intent.putExtra("scheduleNumber",imageBack.getId());
                startActivity(intent);
            }
        });


        Random random = new Random();
        int randomInteger = random.nextInt(5);

        switch (randomInteger){
            case 0:
                imageBack.setImageDrawable(Constant.context.getResources().getDrawable(R.drawable.img_schedule1));
                break;
            case 1:
                imageBack.setImageDrawable(Constant.context.getResources().getDrawable(R.drawable.img_schedule2));
                break;
            case 2:
                imageBack.setImageDrawable(Constant.context.getResources().getDrawable(R.drawable.img_schedule3));
                break;
            case 3:
                imageBack.setImageDrawable(Constant.context.getResources().getDrawable(R.drawable.img_schedule4));
                break;
            case 4:
                imageBack.setImageDrawable(Constant.context.getResources().getDrawable(R.drawable.img_schedule5));
                break;
        }
        imageBack.setScaleType(ImageView.ScaleType.FIT_XY);
        relativeLayout.addView(imageBack);

        TextView txtContents = new TextView(Constant.context);
        txtContents.setLayoutParams(setMargin(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,5,5,5,5));
        txtContents.setGravity(Gravity.CENTER);
        txtContents.setTextSize(14);
        txtContents.setText(contexts);
        txtContents.setTextColor(Color.parseColor("#FFFFFF"));
        relativeLayout.addView(txtContents);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MapsInitializer.initialize(getActivity().getApplicationContext());
        if(binding.mapMain != null)
            binding.mapMain.onCreate(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        binding.mapMain.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.mapMain.onStop();

        if (googleApiClient != null && googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapMain.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapMain.onResume();

        if (googleApiClient != null)
            googleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapMain.onPause();

        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.stopAutoManage(getActivity());
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapMain.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapMain.onDestroy();

        if (googleApiClient != null ) {
            googleApiClient.unregisterConnectionCallbacks(this);
            googleApiClient.unregisterConnectionFailedListener(this);

            if (googleApiClient.isConnected()) {
                googleApiClient.disconnect();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == binding.fabChatResult.getId()){
            Intent intent = new Intent(getActivity(), ChattingActivity.class);
            intent.putExtra("roomTitle",title);
            startActivity(intent);
            Log.v("roomTitle",title);
        }

        switch(v.getId()) {
            case R.id.btn_all_marker_list:
                showAllMarkers();
                setCameraState(binding.rlMap);
                break;
            case R.id.btn_down_sliding_drawer_category:
                binding.slide.animateClose();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){
            case R.id.btn_down_sliding_drawer_category:
            case R.id.content:
            case R.id.list_category:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        binding.slide.animateClose();
                        break;
                }
                break;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.equals(binding.lvList)){
            Intent intent = new Intent(getActivity(), DirectionActivity.class);
            startActivityForResult(intent, Constant.CATEGORY_PAGE);
        }
    }

    @Override
    public void onDrawerClosed() {
        binding.btnUp.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_up));
        binding.llList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDrawerOpened() {
        binding.btnUp.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down));
        binding.llList.setVisibility(View.GONE);
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if ( !checkLocationServicesStatus()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("위치 서비스 비활성화");
            builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +
                    "위치 설정을 수정하십시오.");
            builder.setCancelable(true);
            builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent callGPSSettingIntent =
                            new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(callGPSSettingIntent, Constant.GPS_ENABLE_REQUEST_CODE);
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
        }

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(Constant.UPDATE_INTERVAL_MS);
        locationRequest.setFastestInterval(Constant.FASTEST_UPDATE_INTERVAL_MS);
    }

    @Override
    public void onConnectionSuspended(int cause) {
        if ( cause ==  CAUSE_NETWORK_LOST )
            Log.e(Constant.TAG, "onConnectionSuspended(): Google Play services " +
                    "connection lost.  Cause: network lost.");
        else if (cause == CAUSE_SERVICE_DISCONNECTED )
            Log.e(Constant.TAG,"onConnectionSuspended():  Google Play services " +
                    "connection lost.  Cause: service disconnected");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "위치정보 가져올 수 없음\n위치 퍼미션과 GPS활성 여부 확인", Toast.LENGTH_SHORT).show();
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), 1,this)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

            if (hasFineLocationPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constant.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            } else {
                if ( googleApiClient == null)
                    buildGoogleApiClient();
            }
        } else {
            if (googleApiClient == null)
                buildGoogleApiClient();
        }
        CameraUpdate point = CameraUpdateFactory.newLatLngZoom(Constant.DEFAULT_LOCATION, 15.0f);
        googleMap.moveCamera(point);

        if(Constant.existPerson) {
            showAllMarkers();
            setCameraState(getRelativeMap());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constant.CATEGORY_PAGE:
                if(resultCode == -1)
                    showAllMarkers();
                else
                    showEachMarker(resultCode);
                setCameraState(binding.rlMap);
                break;
        }
    }

    public void initMarkerTime(ArrayList<String> totalTimes){
        this.totalTimes = totalTimes;
    }

    public void setMarkerTimeList(MarkerTimeAdapter markerTimeAdapter) {

        markerTimeAdapter.resetList();
        if(totalTimes!=null){
            Log.v("totalTimes",totalTimes.size()+"");
            for(int i=0; i < Person.getInstance().size();i++) {
                Log.v("시간", totalTimes.get(i));
                markerTimeAdapter.add(Person.getInstance().get(i).getNickname(), totalTimes.get(i));
            }
        }
    }

    public void setCameraState(RelativeLayout relativeMap) {
        LatLngBounds bounds = builder.build();
        int width = relativeMap.getWidth();
        int height = relativeMap.getHeight();
        int padding = (int) (height * 0.10);
        CameraUpdate point = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            googleMap.animateCamera(point);
        else
            googleMap.moveCamera(point);
    }

    // 중간지점과 사용자 전체 마커 표시
    public void showAllMarkers() {
        googleMap.clear();
        builder = new LatLngBounds.Builder();

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(MidInfo.getInstance().getLatLng());
        markerOption.title(Constant.DEFAULT_MIDINFO_NAME);
        markerOption.snippet(MidInfo.getInstance().getAddress());
        markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        googleMap.addMarker(markerOption).showInfoWindow();

        builder.include(markerOption.getPosition());

        for (Person person : Person.getInstance()) {
            String markerTitle = person.getName();
            String markerSnippet = person.getAddress();
            LatLng latLng = new LatLng(person.getAddressPosition().getX(), person.getAddressPosition().getY());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            googleMap.addMarker(markerOptions);

            builder.include(markerOptions.getPosition());
        }
    }

    // 중간지점과 선택된 사용자 마커 표시
    public void showEachMarker(int index) {

        googleMap.clear();
        builder = new LatLngBounds.Builder();

        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(MidInfo.getInstance().getLatLng());
        markerOption.title(Constant.DEFAULT_MIDINFO_NAME);
        markerOption.snippet(MidInfo.getInstance().getAddress());
        markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        googleMap.addMarker(markerOption).showInfoWindow();

        builder.include(markerOption.getPosition());

        Person person = Person.getInstance().get(index);
        String markerTitle = person.getName();
        String markerSnippet = person.getAddress();
        LatLng latLng = new LatLng(person.getAddressPosition().getX(), person.getAddressPosition().getY());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        googleMap.addMarker(markerOptions);

        builder.include(markerOptions.getPosition());

        // drawRoute(MidInfo.getInstance().getLatLng(), latLng);
    }

    @Override
    public void stopLoading() {
        binding.ivLoading.setVisibility(View.GONE);
        binding.ivLoading.clearAnimation();
        Log.v("Loading","clear");
    }
}
