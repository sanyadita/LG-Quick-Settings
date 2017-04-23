package com.sandradita.lg.quick_settings.modes;

import android.support.annotation.DrawableRes;

/**
 * @author aditkovskaya
 */

public class Mode {

    private int modeId;
    private int btnBackgroundRes;
    @DrawableRes
    private int iconRes;
    private String title;

    public Mode(int modeId) {
        this.modeId = modeId;
    }

    public int getModeId() {
        return modeId;
    }

    public int getBtnBackgroundRes() {
        return btnBackgroundRes;
    }

    public void setBtnBackgroundRes(int btnBackgroundRes) {
        this.btnBackgroundRes = btnBackgroundRes;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(@DrawableRes int iconRes) {
        this.iconRes = iconRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mode mode = (Mode) o;
        return modeId == mode.modeId;
    }

    @Override
    public int hashCode() {
        return modeId;
    }

}
