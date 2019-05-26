package com.daahae.damoyeo2.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.model.FloatingActionBtn;
import com.daahae.damoyeo2.model.Person;
import com.daahae.damoyeo2.model.Position;
import com.daahae.damoyeo2.model.UserArr;
import com.daahae.damoyeo2.model.UserPos;
import com.daahae.damoyeo2.presenter.MapsPresenter;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.function.GPSInfo;
import com.daahae.damoyeo2.view_model.ChattingRoomViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity
        extends AppCompatActivity
        implements View.OnClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
                    GoogleMap.OnMapClickListener {

    private static final String TAG = "MAIN_ACTIVITY";

    private MapsPresenter presenter;

    private GoogleMap googleMap = null;
    private MapView mapView = null;
    private GoogleApiClient googleApiClient = null;
    private Marker userMarker = null;
    private LatLngBounds.Builder builder;

    private ArrayList<Marker> markerList;

    private FloatingActionBtn fabtn;
    private LinearLayout linearBtnSearchMid;

    private GPSInfo gps;

    private Geocoder geocoder;

    private FirebaseUser user;

    private boolean isSend = true;

    private ImageButton btnBack;
    private TextView txtTitle;
    private String title;

    private ArrayList<String> emails = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private TextView txtMarkerTotal;
    private TextView txtPeopleTotal;

    //private ChattingRoomViewModel chattingRoomViewModel;
    //private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        title = intent.getStringExtra("roomTitle");
        emails = intent.getStringArrayListExtra("roomEmails");

        Log.v("roomEmails(Maps)",emails.toString());

        Constant.context = this;

        gps = new GPSInfo(this);
        markerList = new ArrayList<Marker>();
        fabtn = new FloatingActionBtn();

        geocoder = new Geocoder(this);
        presenter = new MapsPresenter(this);

        initView();
        initAnimation();
        initListenter();

        //bindingView();

        MapsInitializer.initialize(getApplicationContext());
        if(mapView != null)
            mapView.onCreate(savedInstanceState);

        setPlaceAutoComplete();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            setResult(Constant.LOG_OUT);
            finish();
        }

        txtTitle.setText(title);

        connectRetrofit();
    }

    private void connectRetrofit(){
        RetrofitCommunication.DetailChattingRoomCallBack detailChattingRoomCallBack = new RetrofitCommunication.DetailChattingRoomCallBack() {
            @Override
            public void detailChattingRoomDataPath(UserArr userArr) {
                //txtPeopleTotal.setText(userArr.getCount()+"");
                for(int i=0;i<userArr.getUserArrayList().get(0).size();i++) {
                    //emails.add(userArr.getUserArrayList().get(0).get(i).email);
                    //names.add(userArr.getUserArrayList().get(0).get(i).nickname);
                }
            }
        };
        RetrofitCommunication.getInstance().setDetailChattingRoomCallBack(detailChattingRoomCallBack);
    }

    private void initView() {

        mapView = (MapView) findViewById(R.id.map_main);
        mapView.getMapAsync(this);

        fabtn.setFabMenu((FloatingActionButton) findViewById(R.id.fab_menu));
        fabtn.setFabGPS((FloatingActionButton) findViewById(R.id.fab_gps));
        fabtn.setFabUser((FloatingActionButton) findViewById(R.id.fab_user));
        fabtn.setFabFull((FloatingActionButton) findViewById(R.id.fab_full));
        fabtn.setFabChat((FloatingActionButton) findViewById(R.id.fab_chat));

        linearBtnSearchMid = findViewById(R.id.linear_search_mid);

        btnBack = findViewById(R.id.btn_back_maps);
        txtTitle = findViewById(R.id.txt_chatting_room_name);

        txtMarkerTotal = findViewById(R.id.txt_maker_total_map);
        txtPeopleTotal = findViewById(R.id.txt_people_total_map);
        //chattingRoomViewModel = new ChattingRoomViewModel();
    }

/*
    private void bindingView(){
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_maps);
        binding.setModel(chattingRoomViewModel);

        chattingRoomViewModel.onCreate();
    }
    */


    private void initAnimation() {

        fabtn.setFabOpen(AnimationUtils.loadAnimation(this, R.anim.fab_open));
        fabtn.setFabClose(AnimationUtils.loadAnimation(this, R.anim.fab_close));
    }

    private void initListenter() {

        fabtn.getFabMenu().setOnClickListener(this);
        fabtn.getFabGPS().setOnClickListener(this);
        fabtn.getFabUser().setOnClickListener(this);
        fabtn.getFabFull().setOnClickListener(this);
        fabtn.getFabChat().setOnClickListener(this);

        linearBtnSearchMid.setOnClickListener(this);

        btnBack.setOnClickListener(this);
    }

    // 구글 자동완성
    private void setPlaceAutoComplete() {

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                setUserMarker(true, latLng, user.getEmail(), place.getName().toString());
                presenter.saveSearchName(place.getName().toString());
            }

            @Override
            public void onError(Status status) {
                Log.i(Constant.TAG, "An error occurred: " + status);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mapView.onStop();

        if (googleApiClient != null && googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        isSend = true;

        if (googleApiClient != null)
            googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.stopAutoManage(this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        isSend = false;

        if (googleApiClient != null ) {
            googleApiClient.unregisterConnectionCallbacks(this);
            googleApiClient.unregisterConnectionFailedListener(this);

            if ( googleApiClient.isConnected()) {
                googleApiClient.disconnect();
            }
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if ( !checkLocationServicesStatus()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

        setUserMarker(true, Constant.DEFAULT_LOCATION, user.getEmail(),
                "위치정보 가져올 수 없음");
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if ( hasFineLocationPermission == PackageManager.PERMISSION_DENIED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constant.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            else {
                if ( googleApiClient == null)
                    buildGoogleApiClient();
            }
        } else {
            if ( googleApiClient == null)
                buildGoogleApiClient();
        }
        googleMap.setOnMapClickListener(this);

        CameraUpdate point = CameraUpdateFactory.newLatLngZoom(Constant.DEFAULT_LOCATION, 15.0f);
        googleMap.moveCamera(point);

        if(Person.getInstance().size() > 0) {
            showAllMarkersOnState();
            showAllMarkers();
        } else {
            builder = new LatLngBounds.Builder();
            setGPS();
            sendMarkerGetSync(); // call sync method
        }
    }

    @Override
    public void onMapClick(LatLng point) {

        Point screenPt = googleMap.getProjection().toScreenLocation(point);

        LatLng latLng = googleMap.getProjection().fromScreenLocation(screenPt);

        setUserMarker(false, latLng, user.getEmail(), null);
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent i = new Intent(this,  ChattingActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                overridePendingTransition(R.anim.sliding_up, R.anim.stay);
                finish();
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }
    */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fab_chat:
                Intent intent = new Intent(this, ChattingActivity.class);
                intent.putExtra("roomTitle",title);
                startActivity(intent);
                break;
            /*
            case R.id.fab_logout:
                setResult(Constant.LOG_OUT);
                finish();
                break;
                */
            case R.id.fab_menu:
                fabtn.anim();
                break;
            case R.id.fab_gps:
                setGPS();
                fabtn.anim();
                break;
            case R.id.fab_user:
                showUser();
                fabtn.anim();
                break;
            case R.id.fab_full:
                showAllMarkersOnState();
                showAllMarkers();
                fabtn.anim();
                break;
            case R.id.linear_search_mid:
                isSend = false;
                setAddressToPerson();
                // 통신
                presenter.sendToServer();
                break;
            case R.id.btn_back_maps:
                onBackPressed();
                break;
        }
    }

    // 사용자리스트(마커리스트) 좌표 - 주소 변환
    private void setAddressToPerson() {
        ArrayList<Person> personList = Person.getInstance();
        for (Person p:personList) {
            double lat = p.getAddressPosition().getX();
            double lng = p.getAddressPosition().getY();
            LatLng latLng = new LatLng(lat, lng);
            Address address = null;
            while(address == null) {
                address = getFromLocationToName(latLng);
                latLng = new LatLng(lat, lng+=0.0001);
            }
            p.setAddress(address.getAddressLine(0));
        }
    }

    // 좌표 - 주소 변환
    private Address getFromLocationToName(LatLng latLng) {
        List<Address> list = null;
        Address address = null;
        try {
            list = geocoder.getFromLocation(
                    latLng.latitude, // 위도
                    latLng.longitude, // 경도
                    1); // 얻어올 값의 개수
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(Constant.TAG, "입출력 오류 - 서버에서 주소변환시 에러발생");
        }
        if (list != null) {
            if (list.size()==0)
                Log.d(Constant.TAG, "해당되는 주소 정보는 없습니다");
            else {
                address = list.get(0);
                Log.d(Constant.TAG, list.get(0).toString());
            }
        }
        return address;
    }

    // GPS를 통한 마커 추가
    private void setGPS() {
        if (gps.isGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            LatLng latLng = new LatLng(latitude, longitude);
            setUserMarker(true, latLng,  user.getEmail(), null);
        } else
            setUserMarker(true, Constant.DEFAULT_LOCATION, user.getEmail(), getResources().getString(R.string.msg_gps_disable));
    }

    private void showUser() {
        if (userMarker != null) {
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                userMarker.showInfoWindow();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userMarker.getPosition(), 15.0f));
            }
            else {
                userMarker.showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userMarker.getPosition(), 15.0f));
            }
        }
    }

    // 마커 전체보기
    private void showAllMarkers() {

        if(markerList.size() > 0) {
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (height * 0.10);
            CameraUpdate point = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                googleMap.animateCamera(point);
            else
                googleMap.moveCamera(point);
        }
    }

    // 인스턴트 마커 표시
    private void setUserMarker(boolean flag, LatLng latLng, String markerTitle, String markerSnippet) {

        if ( userMarker != null ) userMarker.remove();

        userMarker = null;

        if ( latLng != null) {

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            userMarker = googleMap.addMarker(markerOptions);
            userMarker.showInfoWindow();

            if(flag) {
                if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                else
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
            }
            instantSendMarkerGetSync();
            return;
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Constant.DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        userMarker = googleMap.addMarker(markerOptions);
        userMarker.showInfoWindow();

        if(flag) {
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Constant.DEFAULT_LOCATION, 15.0f));
            else
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constant.DEFAULT_LOCATION, 15.0f));
        }
    }

    // 다음페이지로부터 뒤로가기 실행시 초기화된 메인페이지에 저장된 마커리스트 동기화
    public void showAllMarkersOnState() {

        googleMap.clear();
        builder = new LatLngBounds.Builder();
        markerList.clear();

        for (Person person : Person.getInstance()) {
            String markerTitle = person.getName();
            String markerSnippet = person.getAddress();
            LatLng latLng = new LatLng(person.getAddressPosition().getX(), person.getAddressPosition().getY());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            Marker marker = googleMap.addMarker(markerOptions);
            marker.showInfoWindow();
            markerList.add(marker);

            if (person.getName().equals(user.getEmail())) {
                if (userMarker != null)
                    userMarker.remove();
                userMarker = marker;
            }

            builder.include(markerOptions.getPosition());
        }
    }

    // 2019.04.07 Inhyeok - add for getting sync
    private void sendMarkerGetSync() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            setResult(Constant.LOG_OUT);
            finish();
        } else {
            UserPos userPos;
            if (userMarker == null)
                userPos = new UserPos(user.getEmail(), -1, -1);
            else
                userPos = new UserPos(user.getEmail(), userMarker.getPosition().latitude, userMarker.getPosition().longitude);
            RetrofitCommunication.getInstance().sendUserPosForSync(userPos, handler);
        }

        if (isSend) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // recursive call
                    sendMarkerGetSync();
                }
            }, 10000); // 10seconds
        }
    }

    // 2019.04.14 Inhyeok - add for getting sync immediately
    private void instantSendMarkerGetSync() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            setResult(Constant.LOG_OUT);
            finish();
        } else {
            UserPos userPos;
            if (userMarker == null)
                userPos = new UserPos(user.getEmail(), -1, -1);
            else
                userPos = new UserPos(user.getEmail(), userMarker.getPosition().latitude, userMarker.getPosition().longitude);
            RetrofitCommunication.getInstance().sendUserPosForSync(userPos, handler);
        }
    }

    private void countMarker(){
        int markerCount = Person.getInstance().size();
        int personCount = emails.size();
        txtMarkerTotal.setText(markerCount + "");
        txtPeopleTotal.setText(personCount + "");

        TextView txt1 = findViewById(R.id.txt_search_mid_1);
        TextView txt2 = findViewById(R.id.txt_search_mid_2);
        TextView txt3 = findViewById(R.id.txt_search_mid_3);

        if(markerCount==personCount) {
            linearBtnSearchMid.setBackgroundResource(R.color.appMainColor);
            txt1.setTextColor(getResources().getColor(R.color.colorWhite));
            txt2.setTextColor(getResources().getColor(R.color.colorWhite));
            txt3.setTextColor(getResources().getColor(R.color.colorWhite));
            txtMarkerTotal.setTextColor(getResources().getColor(R.color.colorWhite));
            txtPeopleTotal.setTextColor(getResources().getColor(R.color.colorWhite));

            linearBtnSearchMid.setClickable(true);
        }
        else{
            linearBtnSearchMid.setBackgroundResource(R.color.grayButtonEdge);
            txt1.setTextColor(getResources().getColor(R.color.grayButtonText));
            txt2.setTextColor(getResources().getColor(R.color.grayButtonText));
            txt3.setTextColor(getResources().getColor(R.color.grayButtonText));
            txtMarkerTotal.setTextColor(getResources().getColor(R.color.grayButtonText));
            txtPeopleTotal.setTextColor(getResources().getColor(R.color.grayButtonText));

            linearBtnSearchMid.setClickable(false);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.REQUEST_LOCATION_SYNC_SUCCESS:
                    Log.d("MAPS_ACTIVITY", "REQUEST_LOCATION_SYNC_SUCCESS");
                    Person.getInstance().clear();

                    for (String email:emails) {
                        for(UserPos userPos: UserPos.getInstance()) {
                            if (email.trim().equals(userPos.getEmail())){
                                Person.getInstance().add(new Person(userPos.getEmail(), userPos.getAddress(), new Position(userPos.getStartLat(), userPos.getStartLng())));
                                break;
                            }
                        }
                    }
                    countMarker();
                    break;
                case Constant.REQUEST_LOCATION_SYNC_NONE:
                    Log.d("MAPS_ACTIVITY", "REQUEST_LOCATION_SYNC_NONE");
                    break;
                case Constant.REQUEST_LOCATION_SYNC_FAIL:
                    Log.d("MAPS_ACTIVITY", "REQUEST_LOCATION_SYNC_FAIL");
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
