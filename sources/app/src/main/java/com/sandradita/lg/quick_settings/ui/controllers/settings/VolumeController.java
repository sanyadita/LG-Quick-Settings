package com.sandradita.lg.quick_settings.ui.controllers.settings;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.helpers.TooltipHelper;
import com.sandradita.lg.quick_settings.helpers.VolumeHelper;
import com.sandradita.lg.quick_settings.services.SafeBroadcastReceiver;
import com.sandradita.lg.quick_settings.ui.abs.BaseCircleActivity;
import com.sandradita.lg.quick_settings.ui.abs.CircleController;
import com.sandradita.lg.quick_settings.ui.views.AppButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.AUDIO_SERVICE;

/**
 * @author aditkovskaya
 */

public class VolumeController extends CircleController {

    private AudioManager audioManager;

    @BindView(R.id.sbVolumeRing)
    SeekBar sbVolumeRing;
    @BindView(R.id.sbVolumeMedia)
    SeekBar sbVolumeMedia;
    @BindView(R.id.sbVolumeNotification)
    SeekBar sbVolumeNotification;

    @BindView(R.id.ivVolumeRing)
    ImageView ivVolumeRing;
    @BindView(R.id.ivVolumeMedia)
    ImageView ivVolumeMedia;
    @BindView(R.id.ivVolumeNotification)
    ImageView ivVolumeNotification;

    @BindView(R.id.appBtnBack)
    AppButton appBtnBack;

    private View content;

    @Override
    protected void onCreate() {
        BaseCircleActivity activity = getActivity();
        content = LayoutInflater.from(activity).inflate(R.layout.content_volume, null);
        ButterKnife.bind(this, content);

        audioManager = (AudioManager) activity.getSystemService(AUDIO_SERVICE);

        prepareBarForVolume(sbVolumeRing, ivVolumeRing, R.string.message_ring, AudioManager.STREAM_RING);
        prepareBarForVolume(sbVolumeMedia, ivVolumeMedia, R.string.message_media, AudioManager.STREAM_MUSIC);
        prepareBarForVolume(sbVolumeNotification, ivVolumeNotification, R.string.message_notification, AudioManager.STREAM_NOTIFICATION);

        appBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popController();
            }
        });
    }

    @Override
    public View getControllerView() {
        return content;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = VolumeHelper.getIntentFilterForAudio();
        registerReceiver(volumeReceiver, intentFilter);

        prepareBarForVolume(sbVolumeRing, ivVolumeRing, R.string.message_ring, AudioManager.STREAM_RING);
        prepareBarForVolume(sbVolumeMedia, ivVolumeMedia, R.string.message_media, AudioManager.STREAM_MUSIC);
        prepareBarForVolume(sbVolumeNotification, ivVolumeNotification, R.string.message_notification, AudioManager.STREAM_NOTIFICATION);


    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(volumeReceiver);
    }

    /**
     * Configures selected seek bar to show level by selected stream type and change volume level
     * on change progress of seek bar.
     */
    private void prepareBarForVolume(SeekBar seekBar, ImageView imageView, @StringRes int descriptionRes, final int streamType) {
        String description = getActivity().getString(descriptionRes);
        imageView.setOnLongClickListener(onLongClickListener);
        imageView.setTag(description);
        seekBar.setMax(audioManager.getStreamMaxVolume(streamType));
        seekBar.setProgress(audioManager.getStreamVolume(streamType));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(streamType, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    /**
     * Shows volume level for all types: ring, media and notifications.
     */
    private void setVolumeForAll() {
        sbVolumeRing.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_RING));
        sbVolumeMedia.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        sbVolumeNotification.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
    }

    /**
     * Receiver that gets updates of volume level or sound mode.
     */
    private final SafeBroadcastReceiver volumeReceiver = new SafeBroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (VolumeHelper.wasVolumeChanged(intent)) setVolumeForAll();
        }
    };

    private final View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Object tag = v.getTag();
            if (tag != null) TooltipHelper.showMessage(tag.toString(), v, Gravity.END);
            return false;
        }
    };

}
