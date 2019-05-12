package com.daahae.damoyeo2.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daahae.damoyeo2.R;

import java.util.Map;

public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtTitle;
    ImageButton btnClose;

    LinearLayout linearContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initView();
        dummy();
    }

    private void dummy(){
        addSchedule("17:00","아재 당구장","놀거리","서울광역시 광진구");
        addSchedule("19:00","이공육","음식점","서울광역시 광진구");
        addSchedule("21:00","카페베네","카페","서울광역시 광진구");
        addSchedule("22:00","아재 PC방","놀거리","서울광역시 광진구");
    }

    private LinearLayout.LayoutParams setMargin(int width, int height, int left, int top, int right, int bottom){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.bottomMargin = bottom;
        params.leftMargin = left;
        params.rightMargin = right;
        params.topMargin = top;

        return params;
    }

    private void addSchedule(String time,String name,String category,String position){
        TextView txtTime = new TextView(this);
        txtTime.setLayoutParams(setMargin(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,0,20,0,50));
        txtTime.setTextSize(18);
        txtTime.setText(time);
        txtTime.setPadding(0,0,0,20);
        linearContents.addView(txtTime);


        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(setMargin(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,0,10,0,130));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundResource(R.drawable.box_black_border);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearContents.addView(linearLayout);

        TextView txtName = new TextView(this);
        txtName.setLayoutParams(setMargin(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,10,20,10,0));
        txtName.setTextSize(25);
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
        linearContents = findViewById(R.id.linear_contents_schedule);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close_schedule:
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
