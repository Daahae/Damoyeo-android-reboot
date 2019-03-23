package com.daahae.damoyeo2.view.activity;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
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
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.model.FloatingActionBtn;
import com.daahae.damoyeo2.model.Person;
import com.daahae.damoyeo2.model.Position;
import com.daahae.damoyeo2.presenter.MapsPresenter;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.function.GPSInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
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
                    com.google.android.gms.location.LocationListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    public static int LOGIN_FLG = Constant.GUEST_LOGIN;

    private MapsPresenter presenter;

    private GoogleMap googleMap = null;
    private MapView mapView = null;
    private GoogleApiClient googleApiClient = null;
    private Marker currentMarker = null;
    private Marker clickedMarker = null;
    private LatLngBounds.Builder builder;

    private ArrayList<Marker> markerList;

    private FloatingActionBtn fabtn;
    private LinearLayout linearBtnSearchMid;

    private GPSInfo gps;

    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // set LoginFlg
        LOGIN_FLG = getIntent().getIntExtra(Constant.LOGIN, Constant.GUEST_LOGIN);

        Constant.context = this;

        gps = new GPSInfo(this);
        markerList = new ArrayList<Marker>();
        fabtn = new FloatingActionBtn();

        geocoder = new Geocoder(this);
        presenter = new MapsPresenter(this);

        initView();
        initAnimation();
        initListenter();

        MapsInitializer.initialize(getApplicationContext());
        if(mapView != null)
            mapView.onCreate(savedInstanceState);

        setPlaceAutoComplete();
    }

    private void initView() {

        mapView = (MapView) findViewById(R.id.map_main);
        mapView.getMapAsync(this);

        fabtn.setFabMenu((FloatingActionButton) findViewById(R.id.fab_menu));
        fabtn.setFabGPS((FloatingActionButton) findViewById(R.id.fab_gps));
        fabtn.setFabPick((FloatingActionButton) findViewById(R.id.fab_pick));
        fabtn.setFabClear((FloatingActionButton) findViewById(R.id.fab_clear));
        fabtn.setFabFull((FloatingActionButton) findViewById(R.id.fab_full));
        fabtn.setFabFix((FloatingActionButton) findViewById(R.id.fab_fix));
        fabtn.setFabLogout((FloatingActionButton) findViewById(R.id.fab_logout));
        fabtn.setFabMypage((FloatingActionButton) findViewById(R.id.fab_mypage));

        // 게스트 로그인 시 마이페이지 안보임
        if(LOGIN_FLG == Constant.GUEST_LOGIN) {
            fabtn.getFabMypage().setVisibility(View.GONE);
            // TODO 로그인 버튼 필요
            fabtn.getFabLogout().setImageDrawable(getResources().getDrawable(R.drawable.ic_logout, null));
        }

        linearBtnSearchMid = findViewById(R.id.linear_search_mid);
    }

    private void initAnimation() {

        fabtn.setFabOpen(AnimationUtils.loadAnimation(this, R.anim.fab_open));
        fabtn.setFabClose(AnimationUtils.loadAnimation(this, R.anim.fab_close));
    }

    private void initListenter() {

        fabtn.getFabMenu().setOnClickListener(this);
        fabtn.getFabGPS().setOnClickListener(this);
        fabtn.getFabPick().setOnClickListener(this);
        fabtn.getFabClear().setOnClickListener(this);
        fabtn.getFabFull().setOnClickListener(this);
        fabtn.getFabFix().setOnClickListener(this);
        fabtn.getFabLogout().setOnClickListener(this);
        fabtn.getFabMypage().setOnClickListener(this);

        linearBtnSearchMid.setOnClickListener(this);
    }

    // 구글 자동완성
    private void setPlaceAutoComplete() {

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng latLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                setCurrentMarker(true, latLng, place.getName().toString(), place.getAddress().toString());
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

        if (googleApiClient != null)
            googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
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

        if (googleApiClient != null ) {
            googleApiClient.unregisterConnectionCallbacks(this);
            googleApiClient.unregisterConnectionFailedListener(this);

            if ( googleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
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

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ( ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                LocationServices.FusedLocationApi
                        .requestLocationUpdates(googleApiClient, locationRequest, this);
            }
        } else {
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(googleApiClient, locationRequest, this);
        }
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

        setCurrentMarker(true, Constant.DEFAULT_LOCATION, "위치정보 가져올 수 없음",
                "위치 퍼미션과 GPS활성 여부 확인");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(Constant.TAG, "onLocationChanged call..");
        if(LOGIN_FLG == Constant.GOOGLE_LOGIN) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                // 다이어로그 로그인 토큰 만료 로 인한 재 로그인 유도
                setResult(Constant.LOG_OUT);
                finish();
            }
        }
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
        googleMap.setOnMarkerClickListener(this);

        CameraUpdate point = CameraUpdateFactory.newLatLngZoom(Constant.DEFAULT_LOCATION, 15.0f);
        googleMap.moveCamera(point);

        if(Person.getInstance().size() > 0) {
            showAllMarkersOnState();
            showAllMarkers();
        } else {
            builder = new LatLngBounds.Builder();
            setGPS();
        }
    }

    @Override
    public void onMapClick(LatLng point) {

        Point screenPt = googleMap.getProjection().toScreenLocation(point);

        LatLng latLng = googleMap.getProjection().fromScreenLocation(screenPt);

        setCurrentMarker(false, latLng, getResources().getString(R.string.msg_add), null);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        clickedMarker = marker;
        if(markerList.contains(clickedMarker))
            fabtn.getFabFix().setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_minus, null));
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fab_logout:
                setResult(Constant.LOG_OUT);
                finish();
                break;
            case R.id.fab_mypage:
                Intent intent = new Intent(this, MyPageActivity.class);
                startActivity(intent);
            case R.id.fab_menu:
                fabtn.anim();
                break;
            case R.id.fab_gps:
                setGPS();
                fabtn.anim();
                break;
            case R.id.fab_pick:
                pickMarker();
                fabtn.anim();
                break;
            case R.id.fab_clear:
                setGoogleMapClear();
                fabtn.anim();
                break;
            case R.id.fab_full:
                showAllMarkers();
                fabtn.anim();
                break;
            case R.id.fab_fix:
                fixMarker();
                break;
            case R.id.linear_search_mid:
                setAddressToPerson();
                // 통신
                presenter.sendToServer();
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
            setCurrentMarker(true, latLng, getResources().getString(R.string.msg_add), null);
        } else
            Toast.makeText(this,  getResources().getString(R.string.msg_gps_disable), Toast.LENGTH_SHORT).show();
    }

    // 지도에 직접 지정을 통한 마커 추가
    private void pickMarker() {
        LatLng latLng = googleMap.getCameraPosition().target;
        setCurrentMarker(false, latLng, getResources().getString(R.string.msg_default), null);
    }

    // 마커설정 초기화
    private void setMarkerClear() {
        currentMarker = null;
        clickedMarker = null;
        fabtn.getFabFix().setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_plus, null));
    }

    // 구글맵 설정 초기화
    private void setGoogleMapClear() {
        googleMap.clear();
        markerList.clear();
        Person.getInstance().clear();
        setMarkerClear();

        CameraUpdate point = CameraUpdateFactory.newLatLngZoom(Constant.DEFAULT_LOCATION, 15.0f);
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            googleMap.animateCamera(point);
        else
            googleMap.moveCamera(point);
        builder = new LatLngBounds.Builder();
    }

    // 각 마커에 대한 저장 또는 삭제
    private void fixMarker() {
        if(clickedMarker != null) {
            if(markerList.contains(clickedMarker))
                removeMarker();
            else
                saveMarker(clickedMarker);
            setMarkerClear();
        } else {
            if(currentMarker != null) {
                saveMarker(currentMarker);
                currentMarker = null;
            }
            else
                Toast.makeText(this, getResources().getString(R.string.msg_checkmarker), Toast.LENGTH_SHORT).show();
        }
    }

    // 마커 추가
    private void saveMarker(Marker marker) {
        ArrayList<Person> personList = Person.getInstance();
        int id = personList.size() + 1;
        String address = marker.getSnippet();
        Position position = new Position(marker.getPosition().latitude, marker.getPosition().longitude);
        Person person = new Person(getResources().getString(R.string.guest) + id, address, position);
        personList.add(person);
        markerList.add(marker);
        marker.setTitle(person.getName());
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        marker.showInfoWindow();

        builder.include(marker.getPosition());

        Toast.makeText(this, person.getName() + getResources().getString(R.string.msg_savemarker), Toast.LENGTH_SHORT).show();
    }

    // 마커 삭제
    private void removeMarker() {
        int targetIndex = markerList.indexOf(clickedMarker);
        markerList.remove(targetIndex);
        ArrayList<Person> personList = Person.getInstance();
        Person person = personList.remove(targetIndex);
        Toast.makeText(this, person.getName() + getResources().getString(R.string.msg_removemarker), Toast.LENGTH_SHORT).show();
        clickedMarker.remove();
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
    private void setCurrentMarker(boolean flag, LatLng latLng, String markerTitle, String markerSnippet) {

        if ( currentMarker != null ) currentMarker.remove();

        setMarkerClear();

        if ( latLng != null) {

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            currentMarker = googleMap.addMarker(markerOptions);
            currentMarker.showInfoWindow();

            if(flag) {
                if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                else
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
            return;
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Constant.DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = googleMap.addMarker(markerOptions);

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(Constant.DEFAULT_LOCATION));
        else
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(Constant.DEFAULT_LOCATION));
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
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            Marker marker = googleMap.addMarker(markerOptions);
            markerList.add(marker);

            builder.include(markerOptions.getPosition());
        }
    }
}
