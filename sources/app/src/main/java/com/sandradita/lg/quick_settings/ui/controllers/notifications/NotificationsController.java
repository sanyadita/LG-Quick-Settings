package com.sandradita.lg.quick_settings.ui.controllers.notifications;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.notification.StatusBarNotification;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sandradita.lg.quick_settings.managers.AppNotificationManager;
import com.sandradita.lg.quick_settings.services.SafeBroadcastReceiver;
import com.sandradita.lg.quick_settings.ui.abs.CircleController;
import com.sandradita.lg.quick_settings.ui.adapters.NotificationsAdapter;
import com.sandradita.lg.quick_settings.utils.AppLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandradita on 6/5/2017.
 */

public class NotificationsController extends CircleController {

    private RecyclerView rvNotifications;
    private Activity activity;
    private List<StatusBarNotification> notificationList;
    private NotificationsAdapter notificationsAdapter;

    @Override
    protected void onCreate() {
        activity = getActivity();

        notificationList = new ArrayList<>(AppNotificationManager.getInstance().getDeviceCurrentNotifications());
        notificationsAdapter = new NotificationsAdapter(notificationList);

        rvNotifications = new RecyclerView(activity);
        rvNotifications.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        rvNotifications.setAdapter(notificationsAdapter);
        AppLogger.debug("HOONUNU", notificationList.size());
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver.register(activity, new IntentFilter(AppNotificationManager.ACTION_NOTIFICATION_LIST_UPDATED));
    }

    @Override
    public void onPause() {
        super.onPause();
        receiver.unregister(activity);
    }

    @Override
    public View getControllerView() {
        return rvNotifications;
    }

    private final SafeBroadcastReceiver receiver = new SafeBroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AppLogger.debug("HOONUNU", "broadcast");
            notificationList.clear();
            notificationList.addAll(AppNotificationManager.getInstance().getDeviceCurrentNotifications());
            notificationsAdapter.notifyDataSetChanged();
        }
    };

}
