package com.sandradita.lg.quick_settings.managers;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.service.notification.StatusBarNotification;

import com.sandradita.lg.quick_settings.utils.AppLogger;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by sandradita on 6/6/2017.
 */

public final class AppNotificationManager {

    public static final String ACTION_NOTIFICATION_LIST_UPDATED = "com.sandradita.lg.quick_settings.managers.ACTION_NOTIFICATION_LIST_UPDATED";

    private static AppNotificationManager instance;

    private Context context;
    private Set<StatusBarNotification> deviceCurrentNotifications;

    private AppNotificationManager() {
        deviceCurrentNotifications = new LinkedHashSet<>();
    }

    public static AppNotificationManager getInstance() {
        if (instance == null) {
            instance = new AppNotificationManager();
        }
        return instance;
    }

    public void init(Context context) {
        if (this.context == null) {
            this.context = context;
        } else {
            AppLogger.error(getClass().getSimpleName(), "The manager was already initialized", null);
//            throw new Error("The manager was already initialized");
        }
    }

    public Context getContext() {
        if (context == null) {
            throw new IllegalStateException("You should call init() first");
        }
        return context;
    }

    public Set<StatusBarNotification> getDeviceCurrentNotifications() {
        return deviceCurrentNotifications;
    }

    public void addNotifications(StatusBarNotification... barNotifications) {
        if (barNotifications == null) return;
        for (StatusBarNotification item: barNotifications) {
            if (item.getNotification().extras.getString(Notification.EXTRA_TITLE) != null) {
                deviceCurrentNotifications.add(item);
            }
        }
        broadcastListChanged();
    }

    public void addNotifications(Collection<StatusBarNotification> barNotifications) {
        if (barNotifications == null) return;
        StatusBarNotification[] array = new StatusBarNotification[barNotifications.size()];
        addNotifications(barNotifications.toArray(array));
    }

    public void removeNotification(StatusBarNotification barNotification) {
        deviceCurrentNotifications.remove(barNotification);
        broadcastListChanged();
    }

    private void broadcastListChanged() {
        Intent intent = new Intent(ACTION_NOTIFICATION_LIST_UPDATED);
        getContext().sendBroadcast(intent);
    }

}
