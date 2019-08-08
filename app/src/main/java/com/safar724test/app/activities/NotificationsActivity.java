package com.safar724test.app.activities;

import android.content.Intent;
import android.os.Bundle;
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
import timber.log.Timber;

public class NotificationsActivity extends AppCompatActivity implements MyAdapter.OnNotifItemClickListener {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean firstStream = true;
    private NotificationDataDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        //Init Database access object
        dao = NotificationDataDatabase.getInstance(this).notificationDataDao();

        //Init Notification RecyclerView adapter
        MyAdapter adapter = new MyAdapter(this, this);

        //Init LinearLayoutManager for Notification RecyclerView layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        //Init TextViews
        TextView emptyRecyclerViewTextView = findViewById(R.id.empty_recycler_view_text_view);
        TextView badgeTextView = findViewById(R.id.unread_notification_badge_tv);

        //Init Notification RecyclerView
        RecyclerView notificationsRecyclerView = findViewById(R.id.notifications_recycler_view);
        notificationsRecyclerView.setHasFixedSize(true);
        notificationsRecyclerView.setLayoutManager(linearLayoutManager);
        notificationsRecyclerView.setAdapter(adapter);

        //Subscribe on our database stream
        compositeDisposable.add(dao.getNotificationModelList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                            emptyRecyclerViewTextView.setVisibility(View.VISIBLE);
                            if (data == null || data.size() == 0) {
                                notificationsRecyclerView.setVisibility(View.GONE);

                            } else {
                                if (firstStream) {
                                    firstStream = false;
                                    adapter.setData(data);
                                    notificationsRecyclerView.smoothScrollToPosition(data.size());

                                } else {
                                    int lastItemIndex = data.size() - 1;
                                    adapter.addItem(data.get(lastItemIndex), lastItemIndex);
                                    notificationsRecyclerView.smoothScrollToPosition(data.size());
                                }

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
                                Utils utils = new Utils(this);
                                badgeTextView.setText(utils.faToEn(String.valueOf(unreadMsgQuantity)));
                            }
                        }
                )
        );
    }

    public void toolbarBackBt(View view) {
        onBackPressed();
    }

    @Override
    public void onItemClicked(NotificationModel notificationModel) {
        NotifActions notifAction = notificationModel.getNotifActions().get(0);
        Intent intent = new Intent(this, NotificationWebViewActivity.class);
        intent.putExtra("intendedUrl", notifAction.data);
        Timber.d("INTENT%s", notifAction.data);
        startActivity(intent);
        notificationModel.setIsRead(true);
        dao.updateNotificationModel(notificationModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

}