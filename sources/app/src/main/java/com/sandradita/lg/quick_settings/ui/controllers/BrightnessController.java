package com.sandradita.lg.quick_settings.ui.controllers;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.helpers.BrightnessHelper;
import com.sandradita.lg.quick_settings.ui.abs.BaseCircleActivity;
import com.sandradita.lg.quick_settings.ui.abs.CircleController;
import com.sandradita.lg.quick_settings.ui.views.AppButton;
import com.sandradita.lg.quick_settings.utils.AppLogger;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.Settings.System.SCREEN_BRIGHTNESS;

/**
 * @author aditkovskaya
 */

public class BrightnessController extends CircleController implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.sbBrightness)
    SeekBar sbBrightness;
    @BindView(R.id.tvBrightness)
    TextView tvBrightness;
    @BindView(R.id.appBtnBack)
    AppButton appBtnBack;
    @BindView(R.id.appBtnAutoBrightness)
    AppButton appBtnAutoBrightness;

    private Uri setting;
    private View content;

    @Override
    protected void onCreate() {
        BaseCircleActivity activity = getActivity();
        content = activity.getLayoutInflater().inflate(R.layout.content_brightness, null);
        ButterKnife.bind(this, content);

        sbBrightness.setMax((int) BrightnessHelper.PERCENT_MAX_VALUE);
        sbBrightness.setOnSeekBarChangeListener(this);

        appBtnBack.setOnClickListener(this);
        appBtnAutoBrightness.setOnClickListener(this);

        setting = Settings.System.getUriFor(SCREEN_BRIGHTNESS);

        setAutoBrightnessButton(BrightnessHelper.getAutoBrightness(activity));
        showCurrentBrightness();
    }

    @Override
    public View getControllerView() {
        return content;
    }

    @Override
    public void onResume() {
        super.onResume();
        getContentResolver().registerContentObserver(setting, false, contentObserver);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getContentResolver() != null) {
            getContentResolver().unregisterContentObserver(contentObserver);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appBtnAutoBrightness:
                boolean enabled = !BrightnessHelper.getAutoBrightness(getActivity());
                changeAutoBrightnessMode(enabled);
                break;
            case R.id.appBtnBack:
                popController();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            BrightnessHelper.changeBrightnessValue(getActivity(), progress);
            setAutoBrightnessButton(false);
            tvBrightness.setText(String.format(Locale.getDefault(), "%d %%", progress));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        getContentResolver().unregisterContentObserver(contentObserver);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        getContentResolver().registerContentObserver(setting, false, contentObserver);
    }

    /**
     * Turns on automatic brightness mode, if parameter <code>enabled</code> is true.
     *
     * @param enabled flag that shows if automatic brightness should be turned on or off
     */
    private void changeAutoBrightnessMode(boolean enabled) {
        BrightnessHelper.setAutoBrightness(getActivity(), enabled);
        setAutoBrightnessButton(enabled);
    }

    /**
     * Changes color of {@link #appBtnAutoBrightness} button depending on automatic brightness mode status.
     *
     * @param autoEnabled true if automatic brightness mode is on
     */
    private void setAutoBrightnessButton(boolean autoEnabled) {
        int bgResource = autoEnabled ? R.drawable.bg_btn_on : R.drawable.bg_btn_off;
        appBtnAutoBrightness.setButtonBackgroundResource(bgResource);
    }

    /**
     * Shows percent of current screen brightness.
     */
    private void showCurrentBrightness() {
        try {
            int percent = BrightnessHelper.getCurrentBrightnessPercent(getActivity());
            tvBrightness.setText(String.format(Locale.getDefault(), "%d %%", percent));
            sbBrightness.setProgress(percent);
        } catch (Settings.SettingNotFoundException e) {
            AppLogger.error(getClass().getSimpleName(), "showCurrentBrightness", e);
        }
    }

    /**
     * Receives changes of screen brightness value.
     */
    private final ContentObserver contentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            showCurrentBrightness();
        }
    };

}
