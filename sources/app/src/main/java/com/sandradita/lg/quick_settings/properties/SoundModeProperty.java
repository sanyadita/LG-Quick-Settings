package com.sandradita.lg.quick_settings.properties;

import android.content.Context;
import android.media.AudioManager;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.modes.Mode;
import com.sandradita.lg.quick_settings.helpers.ModeHelper;
import com.sandradita.lg.quick_settings.properties.abs.Property;

/**
 * @author aditkovskaya
 */

public class SoundModeProperty extends Property {

    public static final int MODE_VIBRATION = 5001;

    private AudioManager manager;

    public SoundModeProperty(Context context) {
        super(context, R.string.property_title_sound_mode, R.drawable.ic_sound_off);
        manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        generateModeQueue(MODE_OFF, MODE_ON, MODE_VIBRATION);
    }

    @Override
    public Mode defineCurrentMode() {
        switch (manager.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:
                currentModeId = MODE_ON;
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                currentModeId = MODE_VIBRATION;
                break;
            default:
                currentModeId = MODE_OFF;
                break;
        }
        return getModeByCurrentId();
    }

    @Override
    public Mode getModeByCurrentId() {
        String title = null;
        switch (currentModeId) {
            case MODE_ON:
                title = context.getString(R.string.property_title_sound);
                return ModeHelper.createOnMode(title, currentModeId, R.drawable.ic_sound_on);
            case MODE_VIBRATION:
                title = context.getString(R.string.property_title_vibrate);
                return ModeHelper.createOnMode(title, currentModeId, R.drawable.ic_sound_vibro);
            default:
                title = context.getString(R.string.property_title_silent);
                return ModeHelper.createOffMode(title, currentModeId, R.drawable.ic_sound_off);
        }
    }

    @Override
    protected void applyMode(int mode) {
        int soundMode = AudioManager.RINGER_MODE_NORMAL;
        switch (mode) {
            case MODE_OFF: soundMode = AudioManager.RINGER_MODE_SILENT; break;
            case MODE_VIBRATION: soundMode = AudioManager.RINGER_MODE_VIBRATE; break;
        }
        manager.setRingerMode(soundMode);
    }

}
