package com.safar724test.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.safar724test.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<Map<String, String>> dataList;

    public MyAdapter(Context context, List<Map<String, String>> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_item, parent, false);
        return new MyAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.notifText.setText(dataList.get(position).get("Body"));
        Picasso.get().load(dataList.get(position).get("imageSrc")).into(holder.notifImage);
    }

    @Override
    public int getItemCount() {
        if (dataList == null) return 0;
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView notifImage;
        public TextView notifText;

        public ViewHolder(View itemView) {
            super(itemView);
            notifImage = itemView.findViewById(R.id.notif_img);
            notifText = itemView.findViewById(R.id.notif_text);
        }
    }

//    public void addItem(String dataObj, int index) {
//        dataList.add(index, dataObj);
//        notifyItemInserted(index);
//    }

//    private void setAnimation(View viewToAnimate, int position) {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition) {
//            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//            animation.setDuration(1000);
//            viewToAnimate.startAnimation(animation);
//            lastPosition = position;
//        }
//    }
}
