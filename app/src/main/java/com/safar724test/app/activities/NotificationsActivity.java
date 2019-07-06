package com.safar724test.app.activities;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.safar724test.app.R;
import com.safar724test.app.adapters.MyAdapter;
import com.safar724test.app.databases.NotificationDataDatabase;
import com.safar724test.app.interfaces.NotificationDataDao;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationsActivity extends AppCompatActivity implements MyAdapter.OnNotifItemClickListener {
    private MyAdapter adapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
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
                                adapter.setData(data);
                            }
                        }
                        )
        );

    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    public void toolbarBackBt(View view) {
        onBackPressed();
    }


    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
