package com.daahae.damoyeo2.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.model.SubPath;

import java.util.ArrayList;

public class SubPathAdpater extends RecyclerView.Adapter<SubPathAdpater.SubPathViewHolder> {

    private Context mContext;
    private ArrayList<SubPath> mSubPathList;

    public SubPathAdpater(Context context, ArrayList<SubPath> subPathList) {
        mContext = context;
        mSubPathList = subPathList;
    }

    @NonNull
    @Override
    public SubPathViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_subpath, viewGroup, false);
        SubPathViewHolder viewHolder = new SubPathViewHolder(view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mSubPathList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SubPathViewHolder viewHolder, int i) {
        if (mSubPathList.get(i).getTrafficType() == 1) { // 지하철
            viewHolder.tvTransportNo.setVisibility(View.GONE);

            switch (mSubPathList.get(i).getSubwayCode()) {
                case 1:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_1);
                    break;
                case 2:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_2);
                    break;
                case 3:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_3);
                    break;
                case 4:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_4);
                    break;
                case 5:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_5);
                    break;
                case 6:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_6);
                    break;
                case 7:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_7);
                    break;
                case 8:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_8);
                    break;
                case 9:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_9);
                    break;
                case 100:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_bundang);
                    break;
                case 101:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_airport);
                    break;
                case 102:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_zaki);
                    break;
                case 104:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_gyeong_ui);
                    break;
                case 107:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_ever);
                    break;
                case 108:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_gyeong_chun);
                    break;
                case 109:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_sin);
                    break;
                case 110:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_uijeong);
                    break;
                case 111:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_suin);
                    break;
                case 112:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_gyeonggang);
                    break;
                case 113:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_wui);
                    break;
                case 114:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_seohae);
                    break;
                case 21:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_in_1);
                    break;
                case 22:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_in_2);
                    break;
                case 31:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_daejeon_1);
                    break;
                case 41:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_dae_1);
                    break;
                case 42:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_dae_2);
                    break;
                case 43:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_dae_3);
                    break;
                case 51:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_gwangju);
                    break;
                case 71:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_busan_1);
                    break;
                case 72:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_busan_2);
                    break;
                case 73:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_busan_3);
                    break;
                case 74:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_busan_4);
                    break;
                case 78:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_donghae);
                    break;
                case 79:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_bukim);
                    break;
                default:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_subway_1);
                    break;
            }
        } else if (mSubPathList.get(i).getTrafficType() == 2) { // 버스
            viewHolder.tvTransportNo.setText(mSubPathList.get(i).getTransportNumber());

            switch (mSubPathList.get(i).getBusType()) {
                case 1:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_bus_normal);
                    break;
                case 3:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_bus_village);
                    break;
                case 5:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_bus_airport);
                    break;
                case 6:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_bus_line);
                    break;
                case 11:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_bus_line);
                    break;
                case 14:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_bus_wide);
                    break;
                case 26:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_bus_line);
                    break;
                default:
                    viewHolder.ivImage.setImageResource(R.drawable.icn_bus_normal);
                    break;
            }
        }

        viewHolder.tvStartName.setText(mSubPathList.get(i).getStartStation());
        if (i == 0)
            viewHolder.tvSubpathHow.setText("승차");
        else
            viewHolder.tvSubpathHow.setText("환승");

        if (i == mSubPathList.size() - 1) { // 마지막 인덱스
            viewHolder.layoutEnd.setVisibility(View.VISIBLE);
            viewHolder.tvEndName.setText(mSubPathList.get(i).getEndStation());
        }
    }

    class SubPathViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private TextView tvTransportNo;
        private TextView tvStartName;
        private TextView tvSubpathHow;

        private ConstraintLayout layoutEnd;
        private TextView tvEndName;

        public SubPathViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvTransportNo = itemView.findViewById(R.id.tv_transport_no);
            tvStartName = itemView.findViewById(R.id.tv_start_name);
            tvSubpathHow = itemView.findViewById(R.id.tv_subpath_how);

            layoutEnd = itemView.findViewById(R.id.layout_end);
            tvEndName = itemView.findViewById(R.id.tv_end_name);
        }
    }
}
