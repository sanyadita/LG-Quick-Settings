package com.sandradita.lg.quick_settings.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.annotation.NonNull;

/**
 * @author aditkovskaya
 */

public final class VolumeHelper {

    /**
     * Intent action to get volume updates.
     */
    private static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    /**
     * Intent param name to get volume stream type, that was changed.
     */
    private static final String EXTRA_VOLUME_STREAM = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    private static final String EXTRA_VOLUME_VALUE = "android.media.EXTRA_VOLUME_STREAM_VALUE";

    private VolumeHelper() {
    }

    /**
     * @return {@link IntentFilter} instance for broadcast receiver to get volume updates
     */
    @NonNull
    public static IntentFilter getIntentFilterForAudio() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(VOLUME_CHANGED_ACTION);
        intentFilter.addAction(AudioManager.RINGER_MODE_CHANGED_ACTION);
        return intentFilter;
    }

    /**
     * Checks if intent got volume level or mode updates.
     *
     * @param intent intent, which broadcast receiver got as argument in
     *               {@link android.content.BroadcastReceiver#onReceive(Context, Intent) onReceive} method.
     * @return true if intent was gotten by volume change
     */
    public static boolean wasVolumeChanged(@NonNull Intent intent) {
        if (intent.getAction().equals(AudioManager.RINGER_MODE_CHANGED_ACTION)) {
            return true;
        } else if (intent.getAction().equals(VOLUME_CHANGED_ACTION)) {
            int streamType = intent.getIntExtra(EXTRA_VOLUME_STREAM, 0);
            if (streamType == AudioManager.STREAM_NOTIFICATION
                    || streamType == AudioManager.STREAM_RING
                    || streamType == AudioManager.STREAM_MUSIC) {
                return true;
            }
        }
        return false;
    }

}
