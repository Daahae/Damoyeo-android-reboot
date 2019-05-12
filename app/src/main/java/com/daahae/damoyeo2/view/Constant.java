package com.daahae.damoyeo2.view;

import android.content.Context;

import com.daahae.damoyeo2.model.Position;
import com.google.android.gms.maps.model.LatLng;

public class Constant {
    public static final String URL_PRE = "http://13.125.192.103/";
    public static final String TAG = "damoyeo_reboot";
    public static final String URL = "http://54.180.106.100:3443/";
  
    public static final String ALGORITHM_ERROR = "{\"error\":\"Algorithm Error\"}";

    public static final int LOG_IN = 9001;
    public static final int LOG_OUT = 9999;

    public static final String LOGIN = "Login";
    public static final int GOOGLE_LOGIN = 901;
    public static final int GUEST_LOGIN = 902;

    public static final int GPS_ENABLE_REQUEST_CODE = 2001;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    public static final int UPDATE_INTERVAL_MS = 15000;
    public static final int FASTEST_UPDATE_INTERVAL_MS = 15000;

    // default location
    public static final double latitude = 37.56666056421257;
    public static final double longitude = 126.97835471481085;
    public static final Position DEFAULT_POSITION = new Position(latitude, longitude);
    public static final LatLng DEFAULT_LOCATION = new LatLng(latitude, longitude);
    public static final String name = "세종대로";
    public static final String address = "서울특별시 중구";
    public static final String tel = "02-1234-5678";
    public static final String description = null;
    public static final double distance = 0;

    public static final String DEFAULT_MIDINFO_NAME = "중간지점";

    // MainActivity
    public static final int MAPS_PAGE = 101;
    public static final int SELECT_MID_PAGE = 102;
    public static final int CATEGORY_PAGE = 103;
    public static final int DETAIL_PAGE = 104;

    // Category
    public static final int PLAY = 200;
    public static final int SPORTS = 201;
    public static final int KARAOKE = 202;
    public static final int THEATER = 203;
    public static final int ARCADE = 204;
    public static final int PARK = 205;
    public static final int PCROOM = 206;
    public static final int PLAY_ETC = 207;

    public static final int DRINK = 300;
    public static final int BISTRO = 301;
    public static final int BAR = 302;
    public static final int CLUB = 303;
    public static final int DRINK_ETC = 304;

    public static final int SHOPPING = 400;
    public static final int DEPARTMENT = 401;
    public static final int SUPERMARKET = 402;
    public static final int MARKETPLACE = 403;
    public static final int SHOPPING_ETC = 404;

    public static final int FOOD = 500;
    public static final int KOREAN_FOOD = 501;
    public static final int JAPANESE_FOOD = 502;
    public static final int CHINESE_FOOD = 503;
    public static final int WESTERN_FOOD = 504;
    public static final int FOOD_ETC = 505;

    public static final int CATEGORY_MAX = 1000;


    // Category
    public static final int DEPARTMENT_STORE = 11;
    public static final int SHOPPING_MALL = 12;
    public static final int STADIUM = 21;
    public static final int ZOO = 22;
    public static final int MUSEUM = 23;
    public static final int MOVIE_THEATER = 24;
    public static final int AQUARIUM = 25;
    public static final int CAFE = 30;
    //public static final int DRINK = 40;
    public static final int RESTAURANT = 50;
    public static final int ETC = 60;

    public static final int SEARCH_FRIENDS_MODE = 1000;
    public static final int FRIENDS_LIST_MODE = 2000;

    public static final int SUBWAY = 1;
    public static final int BUS = 2;
    public static final int WALK = 3;

    public static int displayWidth;

    public static Context context;

    public static boolean existPerson = false;
    public static boolean existLandmarkTransport = false;
    public static boolean existTransport= false;
    public static boolean existBuilding = false;

    // 2019.04.06 Inhyeok - request handler
    public static final int REQUEST_LOCATION_SYNC_SUCCESS = 3001;
    public static final int REQUEST_LOCATION_SYNC_NONE = 3004;
    public static final int REQUEST_LOCATION_SYNC_FAIL = 3009;
  
    public static String email;
    public static String nickname;

    public static final int FRIEND_FRAGMENT = 4000;
    public static final int CHATTINGROOM_FRAGMENT = 4001;
    public static final int SETTING_FRAGMENT = 4002;
    public static int FRAGMENT=4000;
}
