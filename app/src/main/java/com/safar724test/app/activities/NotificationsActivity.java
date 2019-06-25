package com.safar724test.app.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safar724test.app.R;
import com.safar724test.app.adapters.MyAdapter;
import com.safar724test.app.models.InAppNotificationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<InAppNotificationModel> dataList = new ArrayList<>();
    public static final String MyPREFERENCES = "MyPrefs";


    private RecyclerView notificationsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        try {
            JSONArray array = new JSONArray(sharedPref.getString("notif_data", ""));
            editor.commit();
            for (int i = 0; i <= array.length(); i++) {
                InAppNotificationModel data = new InAppNotificationModel(
                        array.getJSONObject(i).getString("body"),
                        array.getJSONObject(i).getString("notif_icon")
                );
                dataList.add(data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        notificationsRecyclerView = findViewById(R.id.notifications_recycler_view);
        notificationsRecyclerView.setHasFixedSize(true);

        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(this, dataList);
        notificationsRecyclerView.setAdapter(adapter);
    }
}
