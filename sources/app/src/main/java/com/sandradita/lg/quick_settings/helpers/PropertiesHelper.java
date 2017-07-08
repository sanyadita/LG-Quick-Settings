package com.sandradita.lg.quick_settings.helpers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.properties.BatteryProperty;
import com.sandradita.lg.quick_settings.properties.BluetoothProperty;
import com.sandradita.lg.quick_settings.properties.LocationProperty;
import com.sandradita.lg.quick_settings.properties.MobileDataProperty;
import com.sandradita.lg.quick_settings.properties.abs.Property;
import com.sandradita.lg.quick_settings.properties.SimProperty;
import com.sandradita.lg.quick_settings.properties.SimpleProperty;
import com.sandradita.lg.quick_settings.properties.SoundModeProperty;
import com.sandradita.lg.quick_settings.properties.WiFiProperty;
import com.sandradita.lg.quick_settings.ui.controllers.settings.BrightnessController;
import com.sandradita.lg.quick_settings.ui.controllers.settings.VolumeController;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods for working with properties that are displaying in application.
 *
 * @author aditkovskaya
 */
public final class PropertiesHelper {

    /**
     * Property types, that should be shown in list.
     */
    public enum Type {
        BRIGHTNESS, SOUND_MODE, WIFI, MOBILE_DATA, VOLUME, BLUETOOTH, GPS, BATTERY, SIM_CARD
    }

    private PropertiesHelper() {
    }

    /**
     * Creates property by selected type. It creates object by type class except {@link Type#BRIGHTNESS} and
     * {@link Type#VOLUME}, for which this method creates instance of class {@link SimpleProperty}.
     * SimpleProperty of Type.BRIGHTNESS should open view of {@link BrightnessController} and of
     * Type.VOLUME open view of {@link VolumeController}.
     *
     * @param context application context
     * @param type    property type
     */
    @NonNull
    public static Property createProperty(final Context context, Type type, final OpenPropertyListener listener) {
        switch (type) {
            case WIFI:
                return new WiFiProperty(context);
            case SOUND_MODE:
                return new SoundModeProperty(context);
            case BLUETOOTH:
                return new BluetoothProperty(context);
            case GPS:
                return new LocationProperty(context);
            case MOBILE_DATA:
                return new MobileDataProperty(context);
            case SIM_CARD:
                return new SimProperty(context);
            case BATTERY:
                return new BatteryProperty(context);
            case BRIGHTNESS:
                String brightnessTitle = context.getString(R.string.property_title_brightness);
                return new SimpleProperty(context, brightnessTitle, R.drawable.ic_brightness, new SimpleProperty.ButtonAction() {
                    @Override
                    public void onClick() {
                        if (listener != null) listener.onOpenProperty(Type.BRIGHTNESS);
                    }
                });
            case VOLUME:
                String volumeTitle = context.getString(R.string.property_title_volume);
                return new SimpleProperty(context, volumeTitle, R.drawable.ic_volume, new SimpleProperty.ButtonAction() {
                    @Override
                    public void onClick() {
                        if (listener != null) listener.onOpenProperty(Type.VOLUME);
                    }
                });
        }
        return new SimpleProperty(context, null, 0);
    }

    /**
     * Creates list with all properties types. Each type will be added only once.
     *
     * @param context application context. Is required because properties change device settings and
     *                use some resources of application.
     * @return list with properties, that can be changed or monitored
     */
    @NonNull
    public static List<Property> createListOfAll(Context context, OpenPropertyListener listener) {
        List<Property> list = new ArrayList<>();
        for (Type type : Type.values()) {
            Property property = createProperty(context, type, listener);
            if (type == Type.SIM_CARD) {
                SimProperty simProperty = (SimProperty) property;
                if (!simProperty.isDualSim()) continue;
            }
            list.add(property);
        }
        return list;
    }

    public interface OpenPropertyListener {
        void onOpenProperty(Type type);
    }

}
