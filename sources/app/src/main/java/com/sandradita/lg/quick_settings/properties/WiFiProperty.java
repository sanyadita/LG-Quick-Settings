package com.sandradita.lg.quick_settings.properties;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.modes.Mode;
import com.sandradita.lg.quick_settings.properties.abs.Property;
import com.sandradita.lg.quick_settings.utils.AppLogger;

/**
 * @author aditkovskaya
 */

public class WiFiProperty extends Property {

    private WifiManager manager;

    public WiFiProperty(Context context) {
        super(context, R.string.property_title_wifi, R.drawable.ic_wifi);
        manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public Mode defineCurrentMode() {
        currentModeId = (manager.isWifiEnabled()) ? MODE_ON : MODE_OFF;
        return getModeByCurrentId();
    }

    @Override
    protected void applyMode(int mode) {
        boolean state = (mode == MODE_ON);
        boolean changed = manager.setWifiEnabled(state);
        AppLogger.info("WiFi", "changed =", changed);
    }

}
