package com.daahae.damoyeo2.view_model;

import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.model.BuildingArr;
import com.daahae.damoyeo2.model.ScheduleArr;
import com.daahae.damoyeo2.model.UserArr;

public class ScheduleViewModel {

    private ScheduleCallBack scheduleCallBack;
    private SimpleScheduleCallBack simpleScheduleCallBack;

    public interface ScheduleCallBack {
        void scheduleDataPath(ScheduleArr scheduleArr);
    }

    public interface SimpleScheduleCallBack {
        void simpleScheduleDataPath(String[] titles);
    }
    public void setScheduleCallBack(ScheduleCallBack scheduleCallBack){
        this.scheduleCallBack = scheduleCallBack;
    }

    public void setSimpleScheduleCallBack(SimpleScheduleCallBack simpleScheduleCallBack){
        this.simpleScheduleCallBack = simpleScheduleCallBack;
    }

    private void connectRetrofit(){
        final RetrofitCommunication.ScheduleCallBack scheduleCallBack = new RetrofitCommunication.ScheduleCallBack() {
            @Override
            public void scheduleDataPath(ScheduleArr scheduleArr) {
                String[] titles = new String[scheduleArr.getScheduleArr().get(0).size()];
                for(int i=0;i<scheduleArr.getScheduleArr().get(0).size();i++) {
                    if(i==scheduleArr.getScheduleArr().get(0).size()-1)
                        titles[i] += "#" + scheduleArr.getScheduleArr().get(0).get(i).category;
                    else
                        titles[i] += "#" + scheduleArr.getScheduleArr().get(0).get(i).category+"\n";
                }
            }
        };
        RetrofitCommunication.getInstance().setScheduleArrCallBack(scheduleCallBack);
    }
}
