package com.safar724test.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.safar724test.app.R;
import com.safar724test.app.adapters.MyAdapter;
import com.safar724test.app.databases.NotificationDataDatabase;
import com.safar724test.app.interfaces.NotificationDataDao;
import com.safar724test.app.models.NotificationData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationsActivity extends AppCompatActivity implements MyAdapter.OnNotifItemClickListener {
    private MyAdapter adapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<NotificationData> notificationDataList = new ArrayList<>();
    private NotificationDataDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        FirebaseMessaging.getInstance().subscribeToTopic("safar724").addOnCompleteListener(task -> Toast.makeText(getApplicationContext(), "topic created!", Toast.LENGTH_LONG).show());
        dao = NotificationDataDatabase.getInstance(this).notificationDataDao();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView notificationsRecyclerView = findViewById(R.id.notifications_recycler_view);
        notificationsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        notificationsRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(this, this);
        notificationsRecyclerView.setAdapter(adapter);
        compositeDisposable.add(dao.getNotificationDataList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            TextView textView = findViewById(R.id.empty_recycler_view_text_view);
                            textView.setVisibility(View.VISIBLE);
                            if (data == null || data.size() == 0) {
                                notificationsRecyclerView.setVisibility(View.GONE);

                            } else {
                                Log.d("TAG", "TEST: " + data.size() );
                                notificationsRecyclerView.smoothScrollToPosition(data.size());

                                adapter.setData(data);
                                if (textView.getVisibility() == View.VISIBLE) {
                                    textView.setVisibility(View.GONE);
                                    notificationsRecyclerView.setVisibility(View.VISIBLE);
                                }
                                int unreadMsgQuantity = 0;
                                TextView badgeTv = findViewById(R.id.unread_notification_badge_tv);
                                RelativeLayout unreadNotificationBadge = findViewById(R.id.unread_notification_badge);
                                for (int i = 0; i < data.size(); i++) {
                                    if (!data.get(i).isRead()) {
                                        unreadMsgQuantity++;
                                    }
                                }
                                if (unreadMsgQuantity <= 0) {
                                    unreadNotificationBadge.setVisibility(View.INVISIBLE);
                                    return;
                                }
                                unreadNotificationBadge.setVisibility(View.VISIBLE);
                                badgeTv.setText(faToEn(String.valueOf(unreadMsgQuantity)));
                                notificationDataList = data;
                            }
                        }
                )
        );
    }

    @Override
    public void onItemClicked(int position) {
        NotificationData data = notificationDataList.get(position);
        startActivity(new Intent(this, WebViewActivity.class).putExtra("intendedUrl", data.getUrl()));
        data.setIsRead(true);
        dao.updateNotificationData(data);
    }

    public void toolbarBackBt(View view) {
        onBackPressed();
    }

    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private String faToEn(String num) {
        return num
                .replace("0", "۰")
                .replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹");
    }


}