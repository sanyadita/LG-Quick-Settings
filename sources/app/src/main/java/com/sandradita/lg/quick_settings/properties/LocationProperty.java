package com.sandradita.lg.quick_settings.properties;

import android.content.Context;
import android.location.LocationManager;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.modes.Mode;
import com.sandradita.lg.quick_settings.properties.abs.Property;

/**
 * @author aditkovskaya
 */

public class LocationProperty extends Property {

    private LocationManager manager;

    public LocationProperty(Context context) {
        super(context, R.string.property_title_gps, R.drawable.ic_place);
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        setChangeable(false);
    }

    @Override
    public Mode defineCurrentMode() {
        currentModeId = (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) ? MODE_ON : MODE_OFF;
        return getModeByCurrentId();
    }

    @Override
    protected void applyMode(int mode) {
        //Can't be changed now
    }

}
