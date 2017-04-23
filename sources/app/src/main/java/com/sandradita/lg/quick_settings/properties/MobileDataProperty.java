package com.sandradita.lg.quick_settings.properties;

import android.content.Context;
import android.net.ConnectivityManager;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.helpers.ModeHelper;
import com.sandradita.lg.quick_settings.modes.Mode;
import com.sandradita.lg.quick_settings.properties.abs.Property;
import com.sandradita.lg.quick_settings.utils.AppLogger;

import java.lang.reflect.Method;

/**
 * @author aditkovskaya
 */

public class MobileDataProperty extends Property {

    public static final int MODE_CONNECTING = 3001;
    public static final int MODE_DISCONNECTING = 3002;

    private ConnectivityManager manager;

    public MobileDataProperty(Context context) {
        super(context, R.string.property_title_mobile_data, R.drawable.ic_mobile_data);
        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        generateModeQueue(MODE_OFF, MODE_ON);
        setChangeable(false);
    }

    @Override
    public Mode defineCurrentMode() {
        currentModeId = isMobileDataEnabled() ? MODE_ON : MODE_OFF;
        return getModeByCurrentId();
    }

    @Override
    public Mode getModeByCurrentId() {
        switch (currentModeId) {
            case MODE_ON:
                return ModeHelper.createOnMode(getTitle(), currentModeId, R.drawable.ic_mobile_data);
            case MODE_CONNECTING:
                return ModeHelper.createOffMode(getTitle(), currentModeId, R.drawable.ic_mobile_data);
            default:
                return ModeHelper.createOffMode(getTitle(), currentModeId, R.drawable.ic_mobile_data);
        }
    }

    @Override
    protected void applyMode(int mode) {
        switch (mode) {
            case MODE_ON:
                enableMobileData(true);
                break;
            case MODE_OFF:
                enableMobileData(false);
                break;
            case MODE_CONNECTING:
                break;
        }
    }

    private boolean isMobileDataEnabled() {
        boolean mobileDataEnabled = false;
        try {
            Class cmClass = Class.forName(manager.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true);
            mobileDataEnabled = (Boolean) method.invoke(manager);
        } catch (Exception e) {
            AppLogger.error("MobileData", e);
        }
        return mobileDataEnabled;
    }

    private void enableMobileData(boolean enabled) {
        // It can be changed programmatically for now. Maybe later it can become changeable
    }

}
