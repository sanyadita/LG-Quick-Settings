package com.sandradita.lg.quick_settings.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.sandradita.lg.quick_settings.utils.AppLogger;

/**
 * This class contains methods for working with permissions.
 *
 * @author aditkovskaya
 */
public final class PermissionHelper {

    private PermissionHelper() {
    }

    /**
     * Checks if user granted selected permissions.
     *
     * @param context     application context
     * @param permissions required permissions
     * @return false if user denied one of the permissions
     */
    public static boolean allPermissionsAllowed(Context context, @Nullable String... permissions) {
        if (permissions == null || context == null) return true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                AppLogger.info(permission, "wasn't granted");
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if application can change system settings.
     *
     * @param context application context
     * @return true if application can change system settings
     */
    public static boolean systemChangesAllowed(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.System.canWrite(context);
    }

    /**
     * Checks if application can change system settings and all required permissions are allowed.
     *
     * @param context     application context
     * @param permissions required permissions
     * @return true if all permissions are allowed
     */
    public static boolean checkAllPermissions(Context context, @Nullable String... permissions) {
        return systemChangesAllowed(context) && allPermissionsAllowed(context, permissions);
    }

    public static void openNotificationSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
