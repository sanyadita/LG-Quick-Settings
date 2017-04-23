package com.sandradita.lg.quick_settings.properties;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.sandradita.lg.quick_settings.modes.Mode;
import com.sandradita.lg.quick_settings.helpers.ModeHelper;
import com.sandradita.lg.quick_settings.properties.abs.Property;

/**
 * @author aditkovskaya
 */
public class SimpleProperty extends Property {

    private Mode defaultMode;
    private ButtonAction buttonAction;

    public SimpleProperty(Context context, String title, @DrawableRes int iconRes) {
        this(context, title, iconRes, (ButtonAction) null);
    }

    public SimpleProperty(Context context, @StringRes int titleRes, @DrawableRes int iconRes) {
        this(context, context.getString(titleRes), iconRes);
    }

    public SimpleProperty(final Context context, @StringRes int titleRes, @DrawableRes int iconRes, final Class<? extends Activity> activityToOpen) {
        this(context, context.getString(titleRes), iconRes, activityToOpen);
    }

    public SimpleProperty(final Context context, String title, @DrawableRes int iconRes, final Class<? extends Activity> activityToOpen) {
        this(context, title, iconRes, new ButtonAction() {
            @Override
            public void onClick() {
                context.startActivity(new Intent(context, activityToOpen));
            }
        });
    }

    public SimpleProperty(Context context, @StringRes int titleRes, @DrawableRes int iconRes, ButtonAction listener) {
        this(context, context.getString(titleRes), iconRes, listener);
    }

    public SimpleProperty(Context context, String title, @DrawableRes int iconRes, ButtonAction listener) {
        super(context, title, iconRes);
        this.buttonAction = listener;
        defaultMode = ModeHelper.createOffMode(title, MODE_OFF, iconRes);
        generateModeQueue(MODE_OFF);
    }

    @Override
    public Mode defineCurrentMode() {
        return defaultMode;
    }

    @Override
    protected void applyMode(int mode) {
        this.currentModeId = mode;
        if (buttonAction != null) buttonAction.onClick();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        defaultMode.setTitle(title);
    }

    /**
     * Changes property onClick action.
     */
    public void setOnClickListener(ButtonAction listener) {
        this.buttonAction = listener;
    }

    /**
     * Action for property button.
     */
    public interface ButtonAction {
        void onClick();
    }
}
