package com.daahae.damoyeo2.presenter;

import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.model.BuildingDetail;
import com.daahae.damoyeo2.view_pre.activity.DetailActivity;

public class DetailPresenter {

    private DetailActivity view;

    public DetailPresenter(DetailActivity view) {
        this.view = view;
    }

    public void startCallback() {
        RetrofitCommunication.BuildingDetailCallBack buildingDetailCallBack = new RetrofitCommunication.BuildingDetailCallBack() {
            @Override
            public void buildingDetailDataPath(BuildingDetail buildingDetail) {

                view.setBuildingInfo();
                view.setBuildingDetail(buildingDetail);

            }
        };
        RetrofitCommunication.getInstance().setBuildingDetailData(buildingDetailCallBack);
    }
}