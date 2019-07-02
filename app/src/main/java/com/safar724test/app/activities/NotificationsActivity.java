package com.safar724test.app.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.safar724test.app.R;
import com.safar724test.app.adapters.MyAdapter;
import com.safar724test.app.models.NotificationData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {
    private MyAdapter adapter;
    public static final String MY_PREFS = "MyPrefs";
    private RecyclerView notificationsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        notificationsRecyclerView = findViewById(R.id.notifications_recycler_view);
        notificationsRecyclerView.setHasFixedSize(true);

        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, new ArrayList<NotificationData>(), new MyAdapter.OnNotifItemClickListener() {
            @Override
            public void onItemClicked(int position, NotificationData notificationData) {
                Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
            }
        });
        final SharedPreferences sharedPref = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        try {
            JSONArray array = new JSONArray(sharedPref.getString("notif_data", ""));
            for (int i = 0; i <= array.length(); i++) {
                NotificationData data = new NotificationData(
                        array.getJSONObject(i).getString("body"),
                        array.getJSONObject(i).getString("notif_icon")
                );
                adapter.addItem(data, i);
                adapter.notifyDataSetChanged();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        notificationsRecyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.resetData();
                try {
                    JSONArray array = new JSONArray(sharedPref.getString("notif_data", ""));
                    for (int i = 0; i <= array.length(); i++) {
                        NotificationData data = new NotificationData(
                                array.getJSONObject(i).getString("body"),
                                array.getJSONObject(i).getString("notif_icon")
                        );
                        adapter.addItem(data, i);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                notificationsRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void toolbar_back_bt(View view) {
        onBackPressed();
    }


}
