package com.daahae.damoyeo2.view.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.model.SearchPubTransPath;
import com.daahae.damoyeo2.model.SubPath;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DirectionRecylcerAdapter extends RecyclerView.Adapter<DirectionRecylcerAdapter.DirectionViewHolder> {

    private Context mContext;
    private Display mDisplay;
    private List<SearchPubTransPath> mTransPath;

    public DirectionRecylcerAdapter(Context context, Display display, List<SearchPubTransPath> transPath) {
        mContext = context;
        mDisplay = display;
        mTransPath = transPath;
    }

    private int getWidth() {
        Point point = new Point();
        mDisplay.getSize(point);

        return point.x - 90;
    }

    @NonNull
    @Override
    public DirectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_direction, viewGroup, false);
        DirectionViewHolder viewHolder = new DirectionViewHolder(view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mTransPath.size();
    }

    @Override
    public void onBindViewHolder(@NonNull DirectionViewHolder holder, int i) {
        int totalLength = getWidth() - 80 * mTransPath.get(i).getSubPathList().size();
        double partOfLength = totalLength / mTransPath.get(i).getTotalTime();

        /*
         * 길찾기 간략 정보 시작
         */
        if (mTransPath.get(i).getTotalHour() != 0) {
            holder.tvDirectionTotalTime.setText(
                    mTransPath.get(i).getTotalHour() + "시간 " + mTransPath.get(i).getTotalMinute() + "분");
        } else {
            holder.tvDirectionTotalTime.setText(
                    mTransPath.get(i).getTotalMinute() + "분");
        }
        holder.tvDirectionWalkTime.setText("도보 " + mTransPath.get(i).getTimeByWalk() + "분");

        DecimalFormat dc = new  DecimalFormat("###,###");
        String payment = dc.format(mTransPath.get(i).getPayment());
        holder.tvDirectionCost.setText(payment + "원");
        /*
         * 종료
         */

        for (int j = 0; j < mTransPath.get(i).getSubPathList().size(); j++) {
            TextView sectionTime = new TextView(mContext);
            double width = partOfLength * mTransPath.get(i).getSubPathList().get(j).getSectionTime();
            width -= width / 10.0;
            sectionTime.setLayoutParams(new LinearLayout.LayoutParams(80 + (int) width, LinearLayout.LayoutParams.MATCH_PARENT));

            if (mTransPath.get(i).getSubPathList().get(j).getTrafficType() == 1) {
                switch (mTransPath.get(i).getSubPathList().get(j).getSubwayCode()) {
                    case 1:
                        sectionTime.setBackgroundResource(R.color.subway_line1);
                        break;
                    case 2:
                        sectionTime.setBackgroundResource(R.color.subway_line2);
                        break;
                    case 3:
                        sectionTime.setBackgroundResource(R.color.subway_line3);
                        break;
                    case 4:
                        sectionTime.setBackgroundResource(R.color.subway_line4);
                        break;
                    case 5:
                        sectionTime.setBackgroundResource(R.color.subway_line5);
                        break;
                    case 6:
                        sectionTime.setBackgroundResource(R.color.subway_line6);
                        break;
                    case 7:
                        sectionTime.setBackgroundResource(R.color.subway_line7);
                        break;
                    case 8:
                        sectionTime.setBackgroundResource(R.color.subway_line8);
                        break;
                    case 9:
                        sectionTime.setBackgroundResource(R.color.subway_line9);
                        break;
                    case 100:
                        sectionTime.setBackgroundResource(R.color.subway_bundang);
                        break;
                    case 101:
                        sectionTime.setBackgroundResource(R.color.subway_airport);
                        break;
                    case 102:
                        sectionTime.setBackgroundResource(R.color.subway_jagi);
                        break;
                    case 104:
                        sectionTime.setBackgroundResource(R.color.subway_gyeongui);
                        break;
                    case 107:
                        sectionTime.setBackgroundResource(R.color.subway_everline);
                        break;
                    case 108:
                        sectionTime.setBackgroundResource(R.color.subway_gyeongchoon);
                        break;
                    case 109:
                        sectionTime.setBackgroundResource(R.color.subway_shinbundang);
                        break;
                    case 110:
                        sectionTime.setBackgroundResource(R.color.subway_uijeongbu);
                        break;
                    case 111:
                        sectionTime.setBackgroundResource(R.color.subway_suin);
                        break;
                    case 112:
                        sectionTime.setBackgroundResource(R.color.subway_geonggang);
                        break;
                    case 113:
                        sectionTime.setBackgroundResource(R.color.subway_wooee);
                        break;
                    case 114:
                        sectionTime.setBackgroundResource(R.color.subway_seohae);
                        break;
                    case 21:
                        sectionTime.setBackgroundResource(R.color.subway_incheon1);
                        break;
                    case 22:
                        sectionTime.setBackgroundResource(R.color.subway_incheon2);
                        break;
                    case 31:
                        sectionTime.setBackgroundResource(R.color.subway_daejeon1);
                        break;
                    case 41:
                        sectionTime.setBackgroundResource(R.color.subway_daegu1);
                        break;
                    case 42:
                        sectionTime.setBackgroundResource(R.color.subway_daegu2);
                        break;
                    case 43:
                        sectionTime.setBackgroundResource(R.color.subway_daegu3);
                        break;
                    case 51:
                        sectionTime.setBackgroundResource(R.color.subway_gwangju1);
                        break;
                    case 71:
                        sectionTime.setBackgroundResource(R.color.subway_busan1);
                        break;
                    case 72:
                        sectionTime.setBackgroundResource(R.color.subway_busan2);
                        break;
                    case 73:
                        sectionTime.setBackgroundResource(R.color.subway_busan3);
                        break;
                    case 74:
                        sectionTime.setBackgroundResource(R.color.subway_busan4);
                        break;
                    case 78:
                        sectionTime.setBackgroundResource(R.color.subway_east);
                        break;
                    case 79:
                        sectionTime.setBackgroundResource(R.color.subway_gimhae);
                    default:
                        sectionTime.setBackgroundResource(R.color.subway_line1);
                        break;
                }

                sectionTime.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                sectionTime.setGravity(Gravity.CENTER);
                sectionTime.setTextSize(12f);
                sectionTime.setText(mTransPath.get(i).getSubPathList().get(j).getSectionTime() + "분");
            } else if (mTransPath.get(i).getSubPathList().get(j).getTrafficType() == 2) {
                switch (mTransPath.get(i).getSubPathList().get(j).getBusType()) {
                    case 1:
                        sectionTime.setBackgroundResource(R.color.bus_normal);
                        break;
                    case 3:
                        sectionTime.setBackgroundResource(R.color.bus_maeul);
                        break;
                    case 5:
                        sectionTime.setBackgroundResource(R.color.bus_airport);
                        break;
                    case 6:
                        sectionTime.setBackgroundResource(R.color.bus_gansun);
                        break;
                    case 11:
                        sectionTime.setBackgroundResource(R.color.bus_gansun);
                        break;
                    case 14:
                        sectionTime.setBackgroundResource(R.color.bus_gwangyeok);
                        break;
                    case 26:
                        sectionTime.setBackgroundResource(R.color.bus_gansun);
                        break;
                    default:
                        sectionTime.setBackgroundResource(R.color.bus_normal);
                        break;
                }

                sectionTime.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                sectionTime.setGravity(Gravity.CENTER);
                sectionTime.setTextSize(12f);
                sectionTime.setText(mTransPath.get(i).getSubPathList().get(j).getSectionTime() + "분");
            } else {
                sectionTime.setBackgroundResource(R.color.walk);

                sectionTime.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
                sectionTime.setGravity(Gravity.CENTER);
                sectionTime.setTextSize(12f);
                sectionTime.setText(mTransPath.get(i).getSubPathList().get(j).getSectionTime() + "분");
            }
            holder.layoutBarDirection.addView(sectionTime);
        }
        /*
         * 종료
         */

        /*
         * 길찾기 환승 정보 시작
         */
        ArrayList<SubPath> subPathListExceptWalk = new ArrayList<>();
        for (SubPath subPath: mTransPath.get(i).getSubPathList()) {
            if (subPath.getTrafficType() != 3)
                subPathListExceptWalk.add(subPath);
        }
        holder.rvSubpath.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rvSubpath.setAdapter(new SubPathAdpater(mContext, subPathListExceptWalk));
        /*
         * 종료
         */
    }

    class DirectionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDirectionTotalTime;
        private TextView tvDirectionWalkTime;
        private TextView tvDirectionCost;

        private LinearLayout layoutBarDirection;
        private RecyclerView rvSubpath;

        public DirectionViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDirectionTotalTime = itemView.findViewById(R.id.tv_direction_total_time);
            tvDirectionWalkTime = itemView.findViewById(R.id.tv_direction_walk_time);
            tvDirectionCost = itemView.findViewById(R.id.tv_direction_cost);

            layoutBarDirection = itemView.findViewById(R.id.linearlayout_bar_direction);
            rvSubpath = itemView.findViewById(R.id.recyclerview_subpath);
        }
    }
}