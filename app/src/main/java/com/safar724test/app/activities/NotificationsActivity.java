package com.safar724test.app.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.safar724test.app.R;
import com.safar724test.app.adapters.MyAdapter;
import com.safar724test.app.databases.NotificationDataDatabase;
import com.safar724test.app.interfaces.NotificationDataDao;
import com.safar724test.app.models.NotificationData;
import com.safar724test.app.tools.JalaliTimeStamp;

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
        TextView badgeTv = findViewById(R.id.unread_notification_badge_tv);
        badgeTv.setText("۵۵");
        String date = "2019-07-08";
        JalaliTimeStamp jalaliTimeStamp = new JalaliTimeStamp(date);

        Log.d("TAG", "onCreate: " + jalaliTimeStamp.getDateInPersian());
        dao = NotificationDataDatabase.getInstance(this).notificationDataDao();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView notificationsRecyclerView = findViewById(R.id.notifications_recycler_view);
        notificationsRecyclerView.setHasFixedSize(true);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                                if (textView.getVisibility() == View.VISIBLE) {
                                    textView.setVisibility(View.GONE);
                                    notificationsRecyclerView.setVisibility(View.VISIBLE);
                                }
                                notificationDataList = data;
                                adapter.setData(notificationDataList);
                            }
                        }
                )
        );
    }

    @Override
    public void onItemClicked(int position) {
        NotificationData data = notificationDataList.get(position);
        dao.deleteNotificationData(data);
    }

    public void toolbarBackBt(View view) {
        onBackPressed();
    }

    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }


}