package com.sandradita.lg.quick_settings.helpers;

import android.graphics.Color;
import android.support.annotation.StringRes;
import android.view.View;

import com.tooltip.Tooltip;


/**
 * @author aditkovskaya
 */

public final class TooltipHelper {

    private TooltipHelper() {
    }

    public static void showMessage(String message, View baseView) {
        makeBuilder(baseView, message).show();
    }

    public static void showMessage(@StringRes int messageRes, View baseView) {
        makeBuilder(baseView, messageRes).show();
    }

    public static void showMessage(String message, View baseView, int gravity) {
        makeBuilder(baseView, message).setGravity(gravity).show();
    }

    public static void showMessage(@StringRes int messageRes, View baseView, int gravity) {
        makeBuilder(baseView, messageRes).setGravity(gravity).show();
    }

    private static Tooltip.Builder makeBuilder(View baseView) {
        return new Tooltip.Builder(baseView)
                .setDismissOnClick(true)
                .setBackgroundColor(Color.parseColor("#306EAC"))
                .setTextColor(Color.WHITE)
                .setCancelable(true)
                .setCornerRadius(20f);
    }

    private static Tooltip.Builder makeBuilder(View baseView, String message) {
        return makeBuilder(baseView).setText(message);
    }

    private static Tooltip.Builder makeBuilder(View baseView, @StringRes int messageRes) {
        return makeBuilder(baseView).setText(messageRes);
    }

}
