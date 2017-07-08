package com.sandradita.lg.quick_settings.ui.activities;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.sandradita.lg.quick_settings.helpers.PermissionHelper;
import com.sandradita.lg.quick_settings.services.NotificationService;
import com.sandradita.lg.quick_settings.ui.abs.BaseCircleActivity;
import com.sandradita.lg.quick_settings.ui.controllers.notifications.NotificationsController;
import com.sandradita.lg.quick_settings.utils.AppLogger;

/**
 * Created by sandradita on 6/5/2017.
 */

public class NotificationsCircleActivity extends BaseCircleActivity {

    @Override
    protected void onActivityCreated() {
        AppLogger.debug("NotificationActivity", "onActivityCreated");
        if (!NotificationService.isNotificationAccessEnabled) {
            PermissionHelper.openNotificationSettings(this);
        } else
        pushController(NotificationsController.class);

//        String notificationListenerString = Settings.Secure.getString(this.getContentResolver(),"enabled_notification_listeners");
////Check notifications access permission
//        if (notificationListenerString == null || !notificationListenerString.contains(getPackageName())) {
//            //The notification access has not acquired yet!
//            Log.d(TAG, "no access");
//            requestPermission();
//        }
//        else {
//            //Your application has access to the notifications
//            Log.d(TAG, "has access");
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppLogger.debug("NotificationActivity", "onStart");
//        bindService(new Intent(this, NotificationService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AppLogger.debug("NotificationActivity", "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            AppLogger.debug("NotificationActivity", "onServiceDisconnected");

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        AppLogger.debug("NotificationActivity", "onStop");
//        unbindService(serviceConnection);
//        stopService(new Intent(this, NotificationService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppLogger.debug("NotificationActivity", "Destroy");
    }

}
