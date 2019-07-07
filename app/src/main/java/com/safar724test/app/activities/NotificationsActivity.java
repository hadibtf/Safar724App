package com.safar724test.app.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        dao = NotificationDataDatabase.getInstance(this).notificationDataDao();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView notificationsRecyclerView = findViewById(R.id.notifications_recycler_view);
        notificationsRecyclerView.setHasFixedSize(true);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, this);
        notificationsRecyclerView.setAdapter(adapter);
        compositeDisposable.add(
                dao.getNotificationDataList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data -> {
                                    if (data == null || data.size() == 0) {
                                        Toast.makeText(this, "Empty data", Toast.LENGTH_SHORT).show();
                                    } else {
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
        NotificationData updatedData = new NotificationData("سلام هه هه هه", data.iconUrl, data.url);
        dao.updateNotificationData(updatedData);
    }

    public void toolbarBackBt(View view) {
        onBackPressed();
    }

    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}