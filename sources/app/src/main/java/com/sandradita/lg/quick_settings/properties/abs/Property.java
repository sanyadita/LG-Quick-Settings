package com.sandradita.lg.quick_settings.properties.abs;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.sandradita.lg.quick_settings.modes.Mode;
import com.sandradita.lg.quick_settings.helpers.ModeHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aditkovskaya
 */

public abstract class Property {

    /**
     * Property is turned off.
     */
    public static final int MODE_OFF = 0;
    /**
     * Property is turned on.
     */
    public static final int MODE_ON = 1;

    private String title;
    private int iconRes;
    private boolean isChangeable = true;

    protected Context context;
    /**
     * Mode of current property state.
     */
    protected int currentModeId;
    /**
     * Queue of modes. Is used to switch several modes in turn.
     */
    protected List<Integer> modeQueue;

    public Property(Context context, String title, @DrawableRes int iconRes) {
        this.context = context;
        this.title = title;
        this.iconRes = iconRes;
        this.currentModeId = MODE_OFF;
        this.modeQueue = new ArrayList<>();
        generateModeQueue(MODE_OFF, MODE_ON);
    }

    public Property(Context context, @StringRes int titleRes, @DrawableRes int iconRes) {
        this(context, context.getString(titleRes), iconRes);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return false, if property can't be changed programmatically
     */
    public boolean isChangeable() {
        return isChangeable;
    }

    /**
     * @param changeable false, if property can't be changed programmatically
     */
    public void setChangeable(boolean changeable) {
        isChangeable = changeable;
    }

    /**
     * Switches next property mode in turn.
     *
     * @return current property mode
     */
    public Mode nextMode() {
        int currentIdx = modeQueue.indexOf(currentModeId);
        if (isChangeable && ++currentIdx > modeQueue.size() - 1) {
            currentIdx = 0;
        }

        currentModeId = modeQueue.get(currentIdx);
        applyMode(currentModeId);

        return getModeByCurrentId();
    }

    /**
     * Creates {@link Mode} instance to show current property state by {@link #currentModeId}.
     * Should be override if property has modes more than 2.
     */
    public Mode getModeByCurrentId() {
        if (currentModeId == MODE_ON) {
            return ModeHelper.createOnMode(title, currentModeId, iconRes);
        }
        return ModeHelper.createOffMode(title, currentModeId, iconRes);
    }

    /**
     * Defines current property state and returns its information
     *
     * @return mode by current mode id
     */
    public abstract Mode defineCurrentMode();

    /**
     * Run changes by selected mode
     */
    protected abstract void applyMode(int mode);

    /**
     * Save modes in current queue. Is used when property has several states, which should be changed by turn.
     *
     * @param modes all property modes in sequence. If null, queue stays with default modes
     */
    protected void generateModeQueue(int... modes) {
        if (modes != null) {
            modeQueue.clear();
            for (int mode : modes) {
                modeQueue.add(mode);
            }
        }
    }

}
