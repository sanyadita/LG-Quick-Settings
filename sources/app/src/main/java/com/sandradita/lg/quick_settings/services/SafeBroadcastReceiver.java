package com.sandradita.lg.quick_settings.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @author aditkovskaya
 */

public abstract class SafeBroadcastReceiver extends BroadcastReceiver {

    private boolean isRegistered;
    private Intent lastIntent;

    /**
     * Registers receiver if it is not registered before.
     *
     * @return The first sticky intent found that matches <var>filter</var>,
     * or null if there are none.
     */
    public Intent register(Context context, IntentFilter filter) {
        if (!isRegistered && context != null) {
            isRegistered = true;
            lastIntent = context.registerReceiver(this, filter);
        }
        return lastIntent;
    }

    /**
     * Unregisters current receiver if it wasn't unregistered before.
     * @return true, if it was unregistered
     */
    public boolean unregister(Context context) {
        if (isRegistered && context != null) {
            context.unregisterReceiver(this);
            isRegistered = false;
            return true;
        }
        return false;
    }

}
