package com.sandradita.lg.quick_settings.constants;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;

/**
 * @author aditkovskaya
 */

public interface AppConstants {

    /**
     * Action to catch changes of default sim card.
     */
    String ACTION_SIM_CARD_CHANGED = "android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED";
    /**
     * Action to catch changes of mobile data connection state.
     */
    String ACTION_MOBILE_DATA_CHANGE_STATE = "android.net.conn.CONNECTIVITY_CHANGE";

    /**
     * Array with permissions, that should be requested before using application in QuickCircle.
     */
    String[] APP_PERMISSIONS = { Manifest.permission.READ_PHONE_STATE };

    String[] MENU_OBSERVE_ACTIONS = {
            LocationManager.MODE_CHANGED_ACTION, // gps
            WifiManager.WIFI_STATE_CHANGED_ACTION, // wifi
            BluetoothAdapter.ACTION_STATE_CHANGED, //bluetooth
            AudioManager.RINGER_MODE_CHANGED_ACTION, //sound mode
            Intent.ACTION_BATTERY_CHANGED, // battery state
            ACTION_SIM_CARD_CHANGED, // sim card
            ACTION_MOBILE_DATA_CHANGE_STATE // mobile data
    };

}
