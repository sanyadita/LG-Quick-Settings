package com.sandradita.lg.quick_settings.services;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.sandradita.lg.quick_settings.managers.AppNotificationManager;
import com.sandradita.lg.quick_settings.utils.AppLogger;

/**
 * Created by sandradita on 6/5/2017.
 */

public class NotificationService extends NotificationListenerService {

    public static boolean isNotificationAccessEnabled = false;

    private static final String TAG = "NotificationService";

    @Override
    public void onCreate() {
        super.onCreate();
        AppLogger.debug(TAG, "Service created");
        AppNotificationManager.getInstance().init(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        isNotificationAccessEnabled = true;
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isNotificationAccessEnabled = false;
        return super.onUnbind(intent);
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        AppLogger.debug(TAG, "Listener connected");
        AppNotificationManager.getInstance().addNotifications(getActiveNotifications());
    }

    @Override
    public void onListenerDisconnected() {
        AppLogger.debug(TAG, "Listener disconnected");
        super.onListenerDisconnected();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        AppLogger.debug(TAG, "Notification posted");
        AppNotificationManager.getInstance().addNotifications(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        AppLogger.debug(TAG, "Notification removed");
        AppNotificationManager.getInstance().removeNotification(sbn);
    }

    @Override
    public void onDestroy() {
        AppLogger.debug(TAG, "onDestroy");
        super.onDestroy();
    }
}
