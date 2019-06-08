package com.daahae.damoyeo2.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.model.ScheduleArr;
import com.daahae.damoyeo2.view.fragment.ResultFragment;

import java.util.Map;

public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtTitle;
    private ImageButton btnClose;

    private LinearLayout linearContents;
    private int scheduleNumber=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Intent intent = getIntent();
        scheduleNumber = intent.getIntExtra("scheduleNumber",0)-1;

        initView();
        setData(ResultFragment.getScheduleArr());
    }

    private void setData(ScheduleArr scheduleArr){
        String title ="";
        Log.v("scheduleNumber",scheduleNumber+"");
        Log.v("scheduleArrSize",scheduleArr.getScheduleArr().get(scheduleNumber).size()+"");
        for(int j=0;j<scheduleArr.getScheduleArr().get(scheduleNumber).size();j++){
            title += "#" + scheduleArr.getScheduleArr().get(scheduleNumber).get(j).category+" ";
        }
        txtTitle.setText(title);

        int time = 18;
        for(int i=0;i<scheduleArr.getScheduleArr().get(scheduleNumber).size();i++){
            //String time = scheduleArr.getScheduleArr().get(scheduleNumber).get(i).startTime;
            String name = scheduleArr.getScheduleArr().get(scheduleNumber).get(i).storeName;
            String category = scheduleArr.getScheduleArr().get(scheduleNumber).get(i).category;
            String address = scheduleArr.getScheduleArr().get(scheduleNumber).get(i).address;
            addSchedule(time+":00",name,category,address,i);
            time+=2;
        }
    }

    private LinearLayout.LayoutParams setMargin(int width, int height, int left, int top, int right, int bottom){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.bottomMargin = bottom;
        params.leftMargin = left;
        params.rightMargin = right;
        params.topMargin = top;

        return params;
    }

    private void addSchedule(String time,String name,String category,String position,int count){

        LinearLayout linearWhole = new LinearLayout(this);
        linearWhole.setLayoutParams(setMargin(LinearLayout.LayoutParams.MATCH_PARENT,600,0,0,0,0));
        linearWhole.setBackgroundResource(R.color.colorWhite);
        linearWhole.setOrientation(LinearLayout.HORIZONTAL);
        linearWhole.setGravity(Gravity.TOP);
        linearWhole.setPadding(0,0,0,0);
        linearContents.addView(linearWhole);

        TextView txtTime = new TextView(this);
        txtTime.setLayoutParams(setMargin(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT,0,20,50,50));
        txtTime.setTextSize(15);
        txtTime.setGravity(Gravity.TOP);
        txtTime.setText(time);
        txtTime.setPadding(0,0,0,20);
        linearWhole.addView(txtTime);

        LinearLayout linearLine = new LinearLayout(this);
        linearLine.setLayoutParams(setMargin(50,LinearLayout.LayoutParams.MATCH_PARENT,0,50,80,0));
        linearLine.setBackgroundResource(R.color.colorWhite);
        linearLine.setOrientation(LinearLayout.VERTICAL);
        linearLine.setGravity(Gravity.CENTER);
        txtTime.setPadding(0,0,0,0);
        linearWhole.addView(linearLine);

        TextView txtCircle = new TextView(this);
        txtCircle.setLayoutParams(setMargin(50,50,0,0,0,0));
        txtCircle.setPadding(0,0,0,0);
        linearLine.addView(txtCircle);

        TextView txtLine = new TextView(this);
        txtLine.setLayoutParams(setMargin(10,LinearLayout.LayoutParams.MATCH_PARENT,0,0,0,0));
        txtLine.setPadding(0,0,0,0);
        linearLine.addView(txtLine);

        switch (count){
            case 0:
                txtCircle.setBackground(getResources().getDrawable(R.drawable.circle_first));
                txtLine.setBackgroundColor(getResources().getColor(R.color.first_schedule));
                break;
            case 1:
                txtCircle.setBackground(getResources().getDrawable(R.drawable.circle_second));
                txtLine.setBackgroundColor(getResources().getColor(R.color.second_schedule));
                break;
            case 2:
                txtCircle.setBackground(getResources().getDrawable(R.drawable.circle_third));
                txtLine.setBackgroundColor(getResources().getColor(R.color.third_schedule));
                break;
        }

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(setMargin(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,0,50,0,50));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundResource(R.drawable.box_black_border);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearWhole.addView(linearLayout);

        TextView txtName = new TextView(this);
        txtName.setLayoutParams(setMargin(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,10,20,10,0));
        txtName.setTextSize(17);
        txtName.setText(name);
        txtName.setPadding(5,5,5,5);
        linearLayout.addView(txtName);

        TextView txtCategory = new TextView(this);
        txtCategory.setLayoutParams(setMargin(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,10,5,10,0));
        txtCategory.setTextSize(13);
        txtCategory.setText(category);
        txtCategory.setPadding(5,5,5,5);
        linearLayout.addView(txtCategory);

        TextView txtPosition = new TextView(this);
        txtPosition.setLayoutParams(setMargin(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,10,5,10,20));
        txtPosition.setTextSize(13);
        txtPosition.setText(position);
        txtPosition.setPadding(5,5,5,5);
        linearLayout.addView(txtPosition);
    }

    private void initView(){
        txtTitle = findViewById(R.id.txt_title_schedule);
        btnClose = findViewById(R.id.btn_close_schedule);
        btnClose.setOnClickListener(this);
        linearContents = findViewById(R.id.linear_contents_schedule);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close_schedule:
                onBackPressed();
                break;
        }
    }
}
