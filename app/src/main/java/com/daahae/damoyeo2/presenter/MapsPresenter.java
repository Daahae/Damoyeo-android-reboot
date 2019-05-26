package com.daahae.damoyeo2.presenter;

import android.widget.Toast;

import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.exception.ExceptionHandle;
import com.daahae.damoyeo2.exception.ExceptionService;
import com.daahae.damoyeo2.model.Person;
import com.daahae.damoyeo2.view.Constant;
import com.daahae.damoyeo2.view.activity.NavigationDrawerActivity;
import com.daahae.damoyeo2.view.fragment.MapsFragment;

public class MapsPresenter {

    private MapsFragment view;

    public MapsPresenter(MapsFragment view){
        this.view = view;
    }

    public void sendToServer() {
        try {
            ExceptionService.getInstance().isSetMarker(Person.getInstance().size());
        } catch (ExceptionHandle exceptionHandle) {
            exceptionHandle.printStackTrace();
            Toast.makeText(view.getActivity(), exceptionHandle.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(Person.getInstance().size() > 1){
            RetrofitCommunication.getInstance().sendMarkerTimeMessage();
            //Intent intent = new Intent(view.getActivity(), CategoryActivity.class);
            //view.startActivity(intent);
            ((NavigationDrawerActivity) view.getActivity()).changeView(Constant.RESULT_PAGE);
        }
    }
}
