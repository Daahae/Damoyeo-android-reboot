package com.daahae.damoyeo2.presenter;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.daahae.damoyeo2.SQL.ResentSearchDBHelper;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.exception.ExceptionHandle;
import com.daahae.damoyeo2.exception.ExceptionService;
import com.daahae.damoyeo2.model.Person;
import com.daahae.damoyeo2.view_pre.Constant;
import com.daahae.damoyeo2.view_pre.activity.CategoryActivity;
import com.daahae.damoyeo2.view_pre.activity.MapsActivity;

import java.text.SimpleDateFormat;

public class MapsPresenter {

    private MapsActivity view;
    private ResentSearchDBHelper dbHelper;

    public MapsPresenter(MapsActivity view){
        this.view = view;
        dbHelper = new ResentSearchDBHelper(Constant.context);
    }

    public void saveSearchName(String search){
        dbHelper.insertResentSearch(search);
    }

    public void sendToServer() {
        Log.d("start1", new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS").format(System.currentTimeMillis()));
        try {
            ExceptionService.getInstance().isSetMarker(Person.getInstance().size());
        } catch (ExceptionHandle exceptionHandle) {
            exceptionHandle.printStackTrace();
            Toast.makeText(view,exceptionHandle.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(Person.getInstance().size() > 1){
            RetrofitCommunication.getInstance().sendMarkerTimeMessage();
            Intent intent = new Intent(view, CategoryActivity.class);
            view.startActivity(intent);
        }
    }
}
