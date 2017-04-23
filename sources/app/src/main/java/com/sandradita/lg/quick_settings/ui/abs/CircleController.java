package com.sandradita.lg.quick_settings.ui.abs;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sandradita.lg.quick_settings.services.SafeBroadcastReceiver;
import com.sandradita.lg.quick_settings.utils.AppLogger;

/**
 * @author aditkovskaya
 */

public abstract class CircleController {

    BaseCircleActivity activity;

    boolean resumed;
    boolean paused;

    boolean destroyed;

    @Nullable
    public static <T extends CircleController> T createController(@NonNull Class<T> type, @NonNull BaseCircleActivity activity) {
        try {
            T object = type.newInstance();
            object.activity = activity;
            object.onCreate();
            return object;
        } catch (InstantiationException | IllegalAccessException e) {
            AppLogger.error("OpenController", e);
        }
        return null;
    }

    protected abstract void onCreate();

    public abstract View getControllerView();

    /**
     * Prepares view before pause. If there are some threads in class, that shouldn't work when view
     * is hidden, override this method and stop these threads.
     */
    public void onPause() {
        paused = true;
        resumed = false;
    }

    /**
     * Prepares view before being shown
     */
    protected void onResume() {
        resumed = true;
        paused = false;
    }

    public void onDestroy() {
        if (!paused) onPause();
        destroyed = true;
    }

    protected Intent registerReceiver(SafeBroadcastReceiver receiver, IntentFilter intentFilter) {
        if (activity != null) {
            return receiver.register(activity, intentFilter);
        }
        return null;
    }

    protected void unregisterReceiver(SafeBroadcastReceiver receiver) {
        if (activity != null) receiver.unregister(activity);
    }

    protected BaseCircleActivity getActivity() {
        return activity;
    }

    /**
     * Opens view of selected controller.
     */
    protected void pushController(CircleController controller) {
        if (activity != null) activity.pushController(controller);
    }

    /**
     * Opens view of selected controller type.
     */
    protected <T extends CircleController> void pushController(Class<T> controllerType) {
        if (activity != null) activity.pushController(controllerType);
    }

    /**
     * Turns back to previous view.
     */
    protected void popController() {
        if (activity != null) activity.popContent();
    }

    protected ContentResolver getContentResolver() {
        if (activity != null) {
            return activity.getContentResolver();
        }
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (!destroyed) onDestroy();
    }

}
