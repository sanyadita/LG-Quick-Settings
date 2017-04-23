package com.sandradita.lg.quick_settings.properties;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.modes.Mode;
import com.sandradita.lg.quick_settings.properties.abs.Property;
import com.sandradita.lg.quick_settings.utils.AppLogger;

/**
 * @author aditkovskaya
 */

public class BluetoothProperty extends Property {

    private BluetoothAdapter adapter;

    public BluetoothProperty(Context context) {
        super(context, R.string.property_title_bluetooth, R.drawable.ic_bluetooth);
        BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        adapter = manager.getAdapter();
    }

    @Override
    public Mode defineCurrentMode() {
        currentModeId = (adapter.isEnabled()) ? MODE_ON : MODE_OFF;
        return getModeByCurrentId();
    }

    @Override
    protected void applyMode(int mode) {
        boolean changed = mode == MODE_ON ? adapter.enable() : adapter.disable();
        AppLogger.info("Bluetooth", "mode =", mode, " changed =", changed);
    }

}
