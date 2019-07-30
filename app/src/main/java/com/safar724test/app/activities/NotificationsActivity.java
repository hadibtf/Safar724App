package com.safar724test.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.safar724test.app.R;
import com.safar724test.app.adapters.MyAdapter;
import com.safar724test.app.databases.NotificationDataDatabase;
import com.safar724test.app.interfaces.NotificationDataDao;
import com.safar724test.app.models.NotifActions;
import com.safar724test.app.models.NotificationModel;
import com.safar724test.app.tools.Utils;

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

        Utils utils = new Utils(this);

        TextView actionBarTextView = findViewById(R.id.action_bar_text_view);
        TextView emptyRecyclerViewTextView = findViewById(R.id.empty_recycler_view_text_view);
        TextView badgeTextView = findViewById(R.id.unread_notification_badge_tv);

        utils.setTextViewFont(actionBarTextView, 3);
        utils.setTextViewFont(emptyRecyclerViewTextView, 3);
        utils.setTextViewFont(badgeTextView, 3);


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

        compositeDisposable.add(dao.getNotificationModelList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            emptyRecyclerViewTextView.setVisibility(View.VISIBLE);
                            if (data == null || data.size() == 0) {
                                notificationsRecyclerView.setVisibility(View.GONE);

                            } else {
                                Log.d("TAG", "TEST: " + data.size());
                                notificationsRecyclerView.smoothScrollToPosition(data.size());
                                adapter.setData(data);

                                if (emptyRecyclerViewTextView.getVisibility() == View.VISIBLE) {
                                    emptyRecyclerViewTextView.setVisibility(View.GONE);
                                    notificationsRecyclerView.setVisibility(View.VISIBLE);
                                }
                                int unreadMsgQuantity = 0;
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
                                badgeTextView.setText(utils.faToEn(String.valueOf(unreadMsgQuantity)));
                            }
                        }
                )
        );
    }

    @Override
    public void onItemClicked(NotificationModel notificationModel) {
        NotifActions notifAction = notificationModel.getNotifActions().get(0);
        startActivity(
                new Intent(this, WebViewActivity.class)
                        .putExtra("intendedUrl", notifAction.data));
        notificationModel.setIsRead(true);
        dao.updateNotificationModel(notificationModel);
    }

    public void toolbarBackBt(View view) {
        onBackPressed();
    }

    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

}