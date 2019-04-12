package com.daahae.damoyeo2.view_model;

import android.databinding.BaseObservable;
import android.util.Log;
import android.view.View;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.model.Category;
import com.daahae.damoyeo2.model.CategoryInfo;
import com.daahae.damoyeo2.navigator.UserNavigator;
import com.daahae.damoyeo2.view_pre.Constant;

import java.util.ArrayList;

public class InterestViewModel extends BaseObservable implements BaseViewModel {

    private UserNavigator navigator;
    private int[][] category; // 선택한 카테고리 목록
    private boolean[] isSelected;

    public InterestViewModel(UserNavigator navigator) {
        this.navigator = navigator;
        category = new int[4][3];
        isSelected = new boolean[Constant.CATEGORY_MAX];
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    public boolean isSelected(int i){
        return isSelected[i];
    }

    public View.OnClickListener selectInterestClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(isFullCategory()) {
                RetrofitCommunication.getInstance().setCategoryInformation(sendSelectedCategory());
                navigator.swapActivity();
            }
        }
    };

    public View.OnClickListener resetInterestListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resetCategory();
            resetIsSelect();
            notifyChange();
        }
    };

    private void resetCategory(){
        for(int i=0;i<category.length;i++)
            for(int j=0;j<category[i].length;j++)
                category[i][j] = 0;
    }

    private void resetIsSelect(){
        for(int i=0;i<isSelected.length;i++)
            isSelected[i]=false;
    }

    private int setCategoryArray(int categoryPart, int categoryNumber){
        for(int i=0;i<3;i++){
            if(category[categoryPart][i]!=0) continue;
            else {
                category[categoryPart][i] = categoryNumber;
                return i;
            }
        }
        navigator.toastFullMessage();
        return -1;
    }

    private int convertCategoryPart(int categoryPart){
        return categoryPart/100 - 2;
    }

    private void setClickedBox(int i){
        isSelected[i] = true;
        notifyChange();
    }

    public View.OnClickListener selectSportsListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.PLAY),Constant.SPORTS)!=-1) {
                setClickedBox(Constant.SPORTS);
            }
        }
    }; public View.OnClickListener selectKaraokeListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.PLAY),Constant.KARAOKE)!=-1) {
                setClickedBox(Constant.KARAOKE);
            }

        }
    };public View.OnClickListener selectTheaterListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.PLAY),Constant.THEATER)!=-1) {
                setClickedBox(Constant.THEATER);
            }

        }
    };public View.OnClickListener selectArcadeListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.PLAY),Constant.ARCADE)!=-1) {
                setClickedBox(Constant.ARCADE);
            }

        }
    };public View.OnClickListener selectParkListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.PLAY),Constant.PARK)!=-1) {
                setClickedBox(Constant.PARK);
            }

        }
    };public View.OnClickListener selectPCRoomListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.PLAY),Constant.PCROOM)!=-1) {
                setClickedBox(Constant.PCROOM);
            }

        }
    };public View.OnClickListener selectPlayEtcListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.PLAY),Constant.PLAY_ETC)!=-1) {
                setClickedBox(Constant.PLAY_ETC);
            }

        }
    };public View.OnClickListener selectBistroListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.DRINK),Constant.BISTRO)!=-1) {
                setClickedBox(Constant.BISTRO);
            }

        }
    };public View.OnClickListener selectBarListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.DRINK),Constant.BAR)!=-1) {
                setClickedBox(Constant.BAR);
            }

        }
    };public View.OnClickListener selectClubListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.DRINK),Constant.CLUB)!=-1) {
                setClickedBox(Constant.CLUB);
            }

        }
    };public View.OnClickListener selectDrinkEtcListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.DRINK),Constant.DRINK_ETC)!=-1) {
                setClickedBox(Constant.DRINK_ETC);
            }

        }
    };public View.OnClickListener selectDepartmentListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.SHOPPING),Constant.DEPARTMENT)!=-1) {
                setClickedBox(Constant.DEPARTMENT);
            }

        }
    };public View.OnClickListener selectSupermarketListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.SHOPPING),Constant.SUPERMARKET)!=-1) {
                setClickedBox(Constant.SUPERMARKET);
            }

        }
    };public View.OnClickListener selectMarketplaceListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.SHOPPING),Constant.MARKETPLACE)!=-1) {
                setClickedBox(Constant.MARKETPLACE);
            }

        }
    };public View.OnClickListener selectShoppingEtcListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.SHOPPING),Constant.SHOPPING_ETC)!=-1) {
                setClickedBox(Constant.SHOPPING_ETC);
            }

        }
    };public View.OnClickListener selectKoreanFoodListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.FOOD),Constant.KOREAN_FOOD)!=-1) {
                setClickedBox(Constant.KOREAN_FOOD);
            }
        }
    };public View.OnClickListener selectJapaneseFoodListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.FOOD),Constant.JAPANESE_FOOD)!=-1) {
                setClickedBox(Constant.JAPANESE_FOOD);
            }

        }
    };public View.OnClickListener selectChineseFoodListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.FOOD),Constant.CHINESE_FOOD)!=-1) {
                setClickedBox(Constant.CHINESE_FOOD);
            }

        }
    };public View.OnClickListener selectWesternFoodListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.FOOD),Constant.WESTERN_FOOD)!=-1) {
                setClickedBox(Constant.WESTERN_FOOD);
            }

        }
    };public View.OnClickListener selectFoodEtcListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(setCategoryArray(convertCategoryPart(Constant.FOOD),Constant.FOOD_ETC)!=-1) {
                setClickedBox(Constant.FOOD_ETC);
            }
        }
    };

    private boolean isFullCategory(){
        for(int i=0;i<category.length;i++){
            for(int j=0;j<category[i].length;j++){
                if(category[i][j]==0) {
                    navigator.toastCategoryEmptyMessage();
                    return false;
                }
            }
        }
        return true;
    }

    private CategoryInfo sendSelectedCategory(){
        CategoryInfo categoryInfo = new CategoryInfo();
        ArrayList<Category> arr = new ArrayList<>();

        for(int i=0;i<category.length;i++){
            Category c = new Category();
            c.setMostLike(category[i][0]);
            c.setMoreLike(category[i][1]);
            c.setLike(category[i][2]);
            arr.add(c);
        }

        categoryInfo.setCategories(arr);
        return categoryInfo;
    }

}
