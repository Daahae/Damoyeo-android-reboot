package com.daahae.damoyeo2.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.model.TransPathList;
import com.daahae.damoyeo2.view.RecyclerItemClickListener;
import com.daahae.damoyeo2.view.adapter.DirectionRecylcerAdapter;

public class DirectionActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvDirection;

    private ImageButton btnBack;
    private DirectionRecylcerAdapter directionRecylcerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        initView();
        initListener();
    }

    private void initView() {
        rvDirection = findViewById(R.id.recyclerview_direction);
        btnBack = findViewById(R.id.btn_back_transport);

        rvDirection.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        directionRecylcerAdapter = new DirectionRecylcerAdapter(getApplicationContext(), getWindowManager().getDefaultDisplay(), TransPathList.getInstance().getUserArr());
        rvDirection.setAdapter(directionRecylcerAdapter);
        rvDirection.addOnItemTouchListener(new RecyclerItemClickListener(this, rvDirection, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setResult(position);
                finish();
            }

            @Override
            public void onLongItemClick(View view, int position) {}
        }));
    }

    private void initListener(){
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back_transport:
                setResult(-1);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(-1);
        finish();
    }
}
