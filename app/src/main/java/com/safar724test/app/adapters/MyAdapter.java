package com.safar724test.app.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.safar724test.app.R;
import com.safar724test.app.models.NotificationData;
import com.safar724test.app.tools.JalaliTimeStamp;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<NotificationData> dataList;
    private int lastPosition = -1;
    private final String TAG = "ADAPTER";

    private OnNotifItemClickListener onNotifItemClickListener;

    public interface OnNotifItemClickListener {
        void onItemClicked(int position);
    }

    public MyAdapter(Context context, OnNotifItemClickListener onNotifItemClickListener) {
        this.context = context;
        this.onNotifItemClickListener = onNotifItemClickListener;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        return new MyAdapter.ViewHolder(view, onNotifItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {
        NotificationData currentData = dataList.get(position);
//        Log.d(TAG, "onBindViewHolder: " + currentData.getIconUrl());
//        Log.d(TAG, "onBindViewHolder: " + currentData.getUrl());
//        Log.d(TAG, "onBindViewHolder: " + currentData.getTitle());
        setAnimation(holder.item, position);
        Typeface bold = Typeface.createFromAsset(context.getAssets(),"fonts/iran_sans_mobile_medium.ttf");
        Typeface light = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_mobile_ultralight.ttf");
        TextViewCompat.setAutoSizeTextTypeWithDefaults(holder.notificationDateStamp, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(holder.notificationTitle, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        JalaliTimeStamp jalaliTimeStamp = new JalaliTimeStamp(currentData.getDate().substring(0, 10).trim());
        if (dataList.get(position).isRead()){
            Log.d(TAG, "onBindViewHolderp: " + position);
            holder.notificationTitle.setTypeface(light);
            holder.notificationTitle.setTextColor(context.getResources().getColor(R.color.readNotificationTextColor));
        }
        holder.notificationTitle.setText(currentData.getTitle());
        holder.notificationDateStamp.setText(jalaliTimeStamp.getDateInPersian());
        Picasso.get().load(currentData.getIconUrl()).placeholder(R.drawable.ic_notifications_grey).into(holder.notifImage);
    }

    @Override
    public int getItemCount() {
        if (dataList == null) return 0;
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView notifImage;
        TextView notificationTitle;
        TextView notificationDateStamp;
        LinearLayout item;
        OnNotifItemClickListener onNotifItemClickListener;

        ViewHolder(View itemView, OnNotifItemClickListener onNotifItemClickListener) {
            super(itemView);
            notifImage = itemView.findViewById(R.id.notif_img);
            notificationTitle = itemView.findViewById(R.id.notification_item_title);
            notificationDateStamp = itemView.findViewById(R.id.notification_item_date_stamp);
            item = itemView.findViewById(R.id.item);
            this.onNotifItemClickListener = onNotifItemClickListener;
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNotifItemClickListener.onItemClicked(getAdapterPosition());
        }
    }

    public void addItem(NotificationData notificationData, int index) {
        dataList.add(index, notificationData);
        notifyItemInserted(index);
        notifyDataSetChanged();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
            animation.setDuration(1000);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void setData(List<NotificationData> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
