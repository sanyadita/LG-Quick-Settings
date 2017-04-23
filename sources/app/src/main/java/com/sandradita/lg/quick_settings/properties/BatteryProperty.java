package com.sandradita.lg.quick_settings.properties;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.annotation.DrawableRes;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.helpers.ModeHelper;
import com.sandradita.lg.quick_settings.modes.Mode;
import com.sandradita.lg.quick_settings.properties.abs.Property;

import java.util.Locale;

/**
 * @author aditkovskaya
 */

public class BatteryProperty extends Property {

    /**
     * Default value of battery level. Can be used if current battery level can't be got.
     */
    private static final float BATTERY_LEVEL_DEFAULT = 50;
    /**
     * Maximum value of battery level.
     */
    private static final float BATTERY_LEVEL_MAX = 100;
    /**
     * Lowest value of battery level. Can be used if battery wasn't found.
     */
    private static final float BATTERY_LEVEL_MIN = 0;

    private boolean isCharging;

    public BatteryProperty(Context context) {
        super(context, R.string.property_title_battery, R.drawable.ic_battery_full);
        this.currentModeId = MODE_ON;
        setChangeable(false);
    }

    @Override
    public Mode defineCurrentMode() {
        float level = getBatteryLevel();
        String title = String.format(Locale.ENGLISH, "%3.0f ", level) + "%";
        int imageRes = getBatteryStateIcon(level);
        return ModeHelper.createOnMode(title, MODE_ON, imageRes);
    }

    @Override
    protected void applyMode(int mode) {

    }

    /**
     * @return current battery level in percents
     */
    private float getBatteryLevel() {
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        if (batteryIntent == null) return BATTERY_LEVEL_MIN;

        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING) ||
                (status == BatteryManager.BATTERY_STATUS_FULL);
        float level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        float scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        if (level == -1 || scale == -1) {
            return BATTERY_LEVEL_DEFAULT;
        }

        return (level / scale) * BATTERY_LEVEL_MAX;
    }

    /**
     * @param level battery level in percents
     * @return property icon by current battery level and charging state.
     */
    @DrawableRes
    private int getBatteryStateIcon(float level) {
        int imageRes = R.drawable.ic_battery_full;
        if (isCharging) {
            if (level <= 20) {
                imageRes = R.drawable.ic_battery_charging_20;
            } else if (level <= 30) {
                imageRes = R.drawable.ic_battery_charging_30;
            } else if (level <= 50) {
                imageRes = R.drawable.ic_battery_charging_50;
            } else if (level <= 60) {
                imageRes = R.drawable.ic_battery_charging_60;
            } else if (level <= 80) {
                imageRes = R.drawable.ic_battery_charging_80;
            } else if (level <= 90) {
                imageRes = R.drawable.ic_battery_charging_90;
            } else {
                imageRes = R.drawable.ic_battery_charging_full;
            }
        } else if (level <= 20) {
            imageRes = R.drawable.ic_battery_20;
        } else if (level <= 30) {
            imageRes = R.drawable.ic_battery_30;
        } else if (level <= 50) {
            imageRes = R.drawable.ic_battery_50;
        } else if (level <= 60) {
            imageRes = R.drawable.ic_battery_60;
        } else if (level <= 80) {
            imageRes = R.drawable.ic_battery_80;
        } else if (level <= 90) {
            imageRes = R.drawable.ic_battery_90;
        }
        return imageRes;
    }

}
