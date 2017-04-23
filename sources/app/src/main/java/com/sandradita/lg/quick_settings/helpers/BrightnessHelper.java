package com.sandradita.lg.quick_settings.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;

import com.sandradita.lg.quick_settings.utils.AppLogger;

import static android.provider.Settings.System.SCREEN_BRIGHTNESS;

/**
 * This class contains methods for working with device brightness.
 *
 * @author aditkovskaya
 */
public final class BrightnessHelper {

    private static final String LOG_TAG = "Brightness";

    public static final float PERCENT_MIN_VALUE = 0;
    public static final float PERCENT_MAX_VALUE = 100;

    private static final int BRIGHTNESS_MIN_VALUE = 10;
    private static final int BRIGHTNESS_MAX_VALUE = 255;

    private static final String PARAM_NAME_SCREEN_BRIGHTNESS_MIN = "config_screenBrightnessSettingMinimum";
    private static final String PARAM_NAME_SCREEN_BRIGHTNESS_MAX = "config_screenBrightnessSettingMaximum";

    private BrightnessHelper() {
    }

    /**
     * Changes value of device's screen brightness.
     * Requires permission "android.permission.READ_PHONE_STATE".
     *
     * @param context application context
     * @param percent new value of screen brightness. Can be from 0 to 100
     */
    public static void changeBrightnessValue(Context context, int percent) {
        if (percent < PERCENT_MIN_VALUE || percent > PERCENT_MAX_VALUE) return;
        if (getAutoBrightness(context)) setAutoBrightness(context, false);
        int maxValue = getMaximumScreenBrightness();
        int minValue = getMinimumScreenBrightness();
        double valueRange = maxValue - minValue;
        int newValue = (int) (percent * valueRange / PERCENT_MAX_VALUE) + minValue;
        boolean success = Settings.System.putInt(context.getContentResolver(), SCREEN_BRIGHTNESS, newValue);
        AppLogger.info(LOG_TAG, "changed =", success);
    }

    /**
     * Checks, if automatic mode of brightness is on.
     * Requires permission "android.permission.READ_PHONE_STATE".
     *
     * @param context application context
     * @return true, if automatic mode is on
     */
    public static boolean getAutoBrightness(Context context) {
        int mode = Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        return mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
    }

    /**
     * Changes brightness mode on automatic, if parameter enabled is true.
     * Requires permission "android.permission.READ_PHONE_STATE".
     *
     * @param context application context
     * @param enabled true, if automatic mode should be turned on
     */
    public static void setAutoBrightness(Context context, boolean enabled) {
        int mode = enabled ? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC : Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
        boolean success = Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
        AppLogger.info(LOG_TAG, "auto_mode_changed =", success);
    }

    /**
     * Reads current brightness value in percents.
     * Requires permission "android.permission.READ_PHONE_STATE".
     *
     * @param context application context
     * @return percent of screen brightness value
     * @throws Settings.SettingNotFoundException
     */
    public static int getCurrentBrightnessPercent(Context context) throws Settings.SettingNotFoundException {
        double currentValue = getCurrentBrightness(context);
        double maxValue = getMaximumScreenBrightness();
        double minValue = getMinimumScreenBrightness();
        double valueRange = maxValue - minValue;
        double currentInRange = currentValue - minValue;
        return (int) (currentInRange / valueRange * PERCENT_MAX_VALUE);
    }

    /**
     * Reads maximum screen brightness of device. If value can't be gotten, returns 255.
     * Requires permission "android.permission.READ_PHONE_STATE".
     *
     * @return int value from 0 to 255
     */
    public static int getMaximumScreenBrightness() {
        final Resources res = Resources.getSystem();
        final int id = res.getIdentifier(PARAM_NAME_SCREEN_BRIGHTNESS_MAX, "integer", "android");
        if (id != 0) {
            try {
                return res.getInteger(id);
            } catch (Resources.NotFoundException ignored) {
                AppLogger.error(LOG_TAG, "getMaximumScreenBrightness", ignored);
            }
        }
        return BRIGHTNESS_MAX_VALUE;
    }

    /**
     * Reads minimum screen brightness of device. If value can't be gotten, returns 10, because 0
     * is too low to screen brightness and you can't see anything on display.
     * Requires permission "android.permission.READ_PHONE_STATE".
     *
     * @return int value from 0 to 255
     */
    public static int getMinimumScreenBrightness() {
        final Resources res = Resources.getSystem();
        final int id = res.getIdentifier(PARAM_NAME_SCREEN_BRIGHTNESS_MIN, "integer", "android");
        if (id != 0) {
            try {
                return res.getInteger(id);
            } catch (Resources.NotFoundException ignored) {
                AppLogger.error(LOG_TAG, "getMinimumScreenBrightness", ignored);
            }
        }
        return BRIGHTNESS_MIN_VALUE;
    }

    /**
     * Reads current brightness value. Requires permission "android.permission.READ_PHONE_STATE".
     *
     * @param context application context
     * @return value of screen brightness
     * @throws Settings.SettingNotFoundException
     */
    private static int getCurrentBrightness(Context context) throws Settings.SettingNotFoundException {
        return Settings.System.getInt(context.getContentResolver(), SCREEN_BRIGHTNESS);
    }

}
