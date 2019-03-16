package com.daahae.damoyeo2.presenter;

import android.util.Log;
import android.widget.Toast;

import com.daahae.damoyeo2.SQL.ResentSearchDBHelper;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.exception.ExceptionHandle;
import com.daahae.damoyeo2.exception.ExceptionService;
import com.daahae.damoyeo2.model.Person;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.fragment.MapsFragment;

import java.text.SimpleDateFormat;

public class MapsPresenter {

    private MapsFragment view;
    private ResentSearchDBHelper dbHelper;

    public MapsPresenter(MapsFragment view){
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
            Toast.makeText(view.getActivity(),exceptionHandle.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(Person.getInstance().size() > 1){
            RetrofitCommunication.getInstance().sendMarkerTimeMessage();
            view.getParentView().changeView(Constant.CATEGORY_PAGE);
        }
    }
}
