package com.safar724test.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safar724test.app.R;
import com.safar724test.app.models.NotificationData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<NotificationData> dataList;
    private int lastPosition = -1;
    private OnNotifItemClickListener onNotifItemClickListener;

    public interface OnNotifItemClickListener {
        void onItemClicked(int position, NotificationData notificationData);
    }

    public MyAdapter(
            Context context,
            List<NotificationData> dataList,
            OnNotifItemClickListener onNotifItemClickListener) {
        this.context = context;
        this.dataList = dataList;
        this.onNotifItemClickListener = onNotifItemClickListener;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        return new MyAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNotifItemClickListener.onItemClicked(position, dataList.get(position));
            }
        });
        setAnimation(holder.itemView, position);
        holder.notifText.setText(dataList.get(position).notifDescription);
        Picasso.get().load(dataList.get(position).notifIconUrl).into(holder.notifImage);
    }

    @Override
    public int getItemCount() {
        if (dataList == null) return 0;
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView notifImage;
        TextView notifText;
        LinearLayout item;

        ViewHolder(View itemView) {
            super(itemView);
            notifImage = itemView.findViewById(R.id.notif_img);
            notifText = itemView.findViewById(R.id.notif_text);
            item = itemView.findViewById(R.id.item);
        }
    }

    public void addItem(NotificationData notificationData, int index) {
        dataList.add(index, notificationData);
        notifyItemInserted(index);
    }

    public void resetData() {
        dataList.clear();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setDuration(1000);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
