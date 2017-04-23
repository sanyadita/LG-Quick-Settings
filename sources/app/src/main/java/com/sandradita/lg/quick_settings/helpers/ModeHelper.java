package com.sandradita.lg.quick_settings.helpers;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.modes.Mode;

/**
 * @author aditkovskaya
 */

public final class ModeHelper {

    private ModeHelper() {
    }

    /**
     * Creates mode, that shows property in turn off state.
     * Background color - {@link com.sandradita.lg.quick_settings.R.drawable#bg_btn_off}.
     *
     * @param title   title of current state
     * @param modeId  state id
     * @param iconRes sate icon resource
     * @return instance with selected arguments
     */
    @NonNull
    public static Mode createOffMode(String title, int modeId, @DrawableRes int iconRes) {
        Mode mode = createMode(title, modeId, iconRes);
        mode.setBtnBackgroundRes(R.drawable.bg_btn_off);
        return mode;
    }

    /**
     * Creates mode, that shows property in turn on state.
     * Background color - {@link com.sandradita.lg.quick_settings.R.drawable#bg_btn_on}.
     *
     * @param title   title of current state
     * @param modeId  state id
     * @param iconRes sate icon resource
     * @return instance with selected arguments
     */
    @NonNull
    public static Mode createOnMode(String title, int modeId, @DrawableRes int iconRes) {
        Mode mode = createMode(title, modeId, iconRes);
        mode.setBtnBackgroundRes(R.drawable.bg_btn_on);
        return mode;
    }

    @NonNull
    private static Mode createMode(String title, int modeId, @DrawableRes int iconRes) {
        Mode mode = new Mode(modeId);
        if (iconRes != 0) mode.setIconRes(iconRes);
        mode.setTitle(title);
        return mode;
    }

}
