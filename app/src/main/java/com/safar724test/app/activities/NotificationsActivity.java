package com.safar724test.app.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.safar724test.app.R;
import com.safar724test.app.adapters.MyAdapter;
import com.safar724test.app.databases.NotificationDataDatabase;
import com.safar724test.app.models.NotificationData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity implements MyAdapter.OnNotifItemClickListener {
    private MyAdapter adapter;
    public static final String MY_PREFS = "MyPrefs";
    private RecyclerView notificationsRecyclerView;
    private List<NotificationData> notificationDataList = new ArrayList<>();
    private NotificationDataDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        database = NotificationDataDatabase.getInstance(this);
        notificationDataList = database.notificationDataDao().getNotificationDataList();
        Toast.makeText(this,notificationDataList.get(0).iconUrl,Toast.LENGTH_SHORT).show();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        notificationsRecyclerView = findViewById(R.id.notifications_recycler_view);
        notificationsRecyclerView.setHasFixedSize(true);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        updateNotificationDataList();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateNotificationDataList();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }

        });
    }

    private void updateNotificationDataList() {
//        notificationDataList.clear();
//        final SharedPreferences sharedPref = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
//        try {
//            JSONArray array = new JSONArray(sharedPref.getString("notif_data", ""));
//
//            for (int i = 0; i <= array.length(); i++) {
//                NotificationData data = new NotificationData(
//                        array.getJSONObject(i).getString("body"),
//                        array.getJSONObject(i).getString("notif_icon")
//                );
//                Log.d("Notif", data.description);
//                notificationDataList.add(data);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        adapter.updateDataList(notificationDataList);
//        adapter.notifyDataSetChanged();
//        adapter = new MyAdapter(this, notificationDataList, this);
//        notificationsRecyclerView.setAdapter(adapter);
    }

    public void toolbarBackBt(View view) {
        onBackPressed();
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}
