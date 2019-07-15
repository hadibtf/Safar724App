package com.safar724test.app.adapters;

import android.content.Context;
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
import com.safar724test.app.tools.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<NotificationData> dataList;
    private int lastPosition = -1;
    private final String TAG = "ADAPTER";

    private OnNotifItemClickListener onNotifItemClickListener;

    public interface OnNotifItemClickListener {
        void onItemClicked(NotificationData notificationData);
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
        Utils utils = new Utils(context);
        NotificationData currentData = dataList.get(position);

        setAnimation(holder.item, position);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(holder.notificationDateStamp, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(holder.notificationTitle, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        JalaliTimeStamp jalaliTimeStamp = new JalaliTimeStamp(currentData.getDate().substring(0, 10).trim());
        if (currentData.isRead()) {
            Log.d(TAG, "onBindViewHolderp: " + position);
            utils.setTextViewFont(holder.notificationTitle, utils.LIGHT);
            holder.notificationTitle.setTextColor(context.getResources().getColor(R.color.readNotificationTextColor));
        } else if (!currentData.isRead()) {
            utils.setTextViewFont(holder.notificationTitle, utils.REGULAR);
            holder.notificationTitle.setTextColor(context.getResources().getColor(R.color.notReadNotificationTextColor));
        }
        utils.setTextViewFont(holder.notificationDescription, utils.REGULAR);
        holder.notificationDescription.setText(currentData.getDescription());
        holder.notificationTitle.setText(currentData.getTitle());
        utils.setTextViewFont(holder.notificationDateStamp, utils.LIGHT);
        holder.notificationDateStamp.setText(jalaliTimeStamp.getDateInPersian());
        Picasso.get().load(currentData.getIconUrl()).placeholder(R.drawable.ic_notifications_grey).into(holder.notifImage);
        Picasso.get().load(currentData.getTypeIconUrl()).placeholder(R.drawable.ic_notifications_grey).into(holder.notifTypeImage);
    }

    @Override
    public int getItemCount() {
        if (dataList == null) return 0;
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout item;
        ImageView notifImage;
        ImageView notifTypeImage;
        TextView notificationTitle;
        TextView notificationDateStamp;
        TextView notificationDescription;
        OnNotifItemClickListener onNotifItemClickListener;

        ViewHolder(View itemView, OnNotifItemClickListener onNotifItemClickListener) {
            super(itemView);
            notifImage = itemView.findViewById(R.id.notif_img);
            notifTypeImage = itemView.findViewById(R.id.notification_item_type_icon);
            notificationTitle = itemView.findViewById(R.id.notification_item_title);
            notificationDateStamp = itemView.findViewById(R.id.notification_item_date_stamp);
            notificationDescription = itemView.findViewById(R.id.notification_item_description);
            item = itemView.findViewById(R.id.item);
            this.onNotifItemClickListener = onNotifItemClickListener;
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNotifItemClickListener.onItemClicked(dataList.get(getAdapterPosition()));
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
