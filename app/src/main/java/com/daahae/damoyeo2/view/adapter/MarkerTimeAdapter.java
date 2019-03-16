package com.daahae.damoyeo2.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.model.MarkerTime;

import java.util.ArrayList;

public class MarkerTimeAdapter extends BaseAdapter {

    private TextView txtMarkerName;
    private TextView txtMarkerTime;

    private MarkerTime myItem;
    private ArrayList<MarkerTime> mItems;

    public MarkerTimeAdapter(){
        this.mItems = new ArrayList<MarkerTime>();
    }

    /* 아이템을 세트로 담기 위한 어레이 */

    public void resetList(){
        mItems.clear();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MarkerTime getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void add(MarkerTime markerTime){
        mItems.add(markerTime);
    }

    public void add(String name, String totalTime){mItems.add(new MarkerTime(name, totalTime));}

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_time_taken_marker, parent, false);
        }
        initGetView(convertView);

        myItem = (MarkerTime) mItems.get(position);

        if(Integer.parseInt(myItem.getTotalTime())==0)
            setMarkerListText(txtMarkerName,txtMarkerTime,myItem.getName(),"700m이내");
        else
            setMarkerListText(txtMarkerName,txtMarkerTime,myItem.getName(),formTakenTime(myItem.getTotalTime()));

        return convertView;
    }

    private void initGetView(View convertView){
        txtMarkerName = (TextView)convertView.findViewById(R.id.txt_marker_name_item);
        txtMarkerTime = (TextView)convertView.findViewById(R.id.txt_marker_time_about_mid_item);
    }

    private void setMarkerListText(TextView MarkerNameView, TextView MarkerTime, String nameText, String timeText){
        MarkerNameView.setText(nameText);
        MarkerTime.setText(timeText);
    }


    private String formTakenTime(String time){
        String strTime;
        int nTime = Integer.parseInt(time);
        int timeHour = nTime/60;
        int timeMin = nTime%60;

        if(timeMin==0){
            strTime = timeHour+"시간";
        }else if(timeHour==0){
            strTime = timeMin+"분";
        }else{
            strTime = timeHour+"시간"+timeMin + "분";
        }

        return strTime;
    }
}