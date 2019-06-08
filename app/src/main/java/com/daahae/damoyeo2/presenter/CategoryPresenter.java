package com.daahae.damoyeo2.presenter;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.model.TransPathList;
import com.daahae.damoyeo2.navigator.LoadingNavigator;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.activity.NavigationDrawerActivity;
import com.daahae.damoyeo2.view.fragment.ResultFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CategoryPresenter {

    private ResultFragment view;
    private CheckTypesTask loading;
    private LoadingNavigator loadingNavigator;

    public CategoryPresenter(String title) {
        this.view = ResultFragment.getInstance(title);
        loading = new CheckTypesTask();
        loading.execute();
        this.loadingNavigator = view;
    }

    public void setSelectCategory(int category) {
        Log.d("start2", new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS").format(System.currentTimeMillis()));
        RetrofitCommunication.getInstance().setBuildingsData(category);
    }

    public void setMidInfoTransport(){
        ArrayList<String> totalTimes = new ArrayList<>();
        for(int i = 0; i< TransPathList.getInstance().getUserArr().size(); i++){
            totalTimes.add(String.valueOf(TransPathList.getInstance().getUserArr().get(i).getTotalTime()));
        }
        view.initMarkerTime(totalTimes);
        view.setMarkerTimeList(view.getMarkerTimeAdapter());
        view.getListMarkerTime().setAdapter(view.getMarkerTimeAdapter());
    }

    public void startCallback() {
        if(Constant.existPerson) return;
        loading.onPreExecute();

        RetrofitCommunication.UserCallBack userCallBack = new RetrofitCommunication.UserCallBack() {
            @Override
            public void userDataPath(ArrayList<String> totalTimes) {
                loadingNavigator.stopLoading();
                Log.v("loading","stop");
                loading.onPostExecute(null);
                loading.stop();
                view.initMarkerTime(totalTimes);
                view.setMarkerTimeList(view.getMarkerTimeAdapter());
                view.getListMarkerTime().setAdapter(view.getMarkerTimeAdapter());
                view.showAllMarkers();
                view.setCameraState(view.getRelativeMap());
                Constant.existPerson = true;

                RetrofitCommunication.getInstance().setScheduleRequest();

            }

            @Override
            public void disconnectServer() {
                loadingNavigator.stopLoading();
                loading.onPostExecute(null);
                loading.stop();
                //view.getActivity().finish(); // 뒤로가기
                if (view != null)
                    ((NavigationDrawerActivity) view.getActivity()).backView(view);
                Toast.makeText(Constant.context,"중간지점 탐색에 실패했습니다", Toast.LENGTH_SHORT).show();
            }
        };
        RetrofitCommunication.getInstance().setUserData(userCallBack);
    }


    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(Constant.context, R.style.progress_bar_style);

        @Override
        protected void onPreExecute() {

            asyncDialog.setCancelable(false);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("중간지점을 찾고 있습니다");

            // show dialog
            //asyncDialog.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                for (int i = 0; i < 5; i++) {
                    asyncDialog.setProgress(i*30);
                    Thread.sleep(30000);
                }

            } catch (InterruptedException e) {
                //view.getActivity().finish();
                if (view != null) {
                    ((NavigationDrawerActivity) view.getActivity()).backView(view);
                    if (asyncDialog.isShowing())
                        asyncDialog.dismiss();
                }
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (asyncDialog.isShowing())
                asyncDialog.dismiss();
            loadingNavigator.stopLoading();
            super.onPostExecute(result);
        }

        protected void stop(){
            asyncDialog.dismiss();
        }
    }
}
