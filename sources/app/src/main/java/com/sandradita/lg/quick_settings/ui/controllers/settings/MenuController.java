package com.sandradita.lg.quick_settings.ui.controllers.settings;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lge.qcircle.template.QCircleTemplate;
import com.sandradita.lg.quick_settings.constants.AppConstants;
import com.sandradita.lg.quick_settings.helpers.PropertiesHelper;
import com.sandradita.lg.quick_settings.properties.abs.Property;
import com.sandradita.lg.quick_settings.services.SafeBroadcastReceiver;
import com.sandradita.lg.quick_settings.ui.abs.BaseCircleActivity;
import com.sandradita.lg.quick_settings.ui.abs.CircleController;
import com.sandradita.lg.quick_settings.ui.wheel_list.PropertyListCoordinator;

import java.util.List;

/**
 * @author aditkovskaya
 */

public class MenuController extends CircleController {

    private PropertyListCoordinator listController;

    private RecyclerView content;

    public void onCreate() {
        BaseCircleActivity activity = getActivity();
        List<Property> properties = PropertiesHelper.createListOfAll(activity, openPropertyListener);
        QCircleTemplate template = activity.getCircleTemplate();
        listController = new PropertyListCoordinator(activity, properties, template.getDiameter());
        content = listController.getRecyclerView(activity.getMainLayout());
    }

    public View getControllerView() {
        return content;
    }

    @Override
    public void onPause() {
        unregisterReceiver(receiver);
    }

    @Override
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter();
        for (String action : AppConstants.MENU_OBSERVE_ACTIONS) {
            intentFilter.addAction(action);
        }
        registerReceiver(receiver, intentFilter);
    }

    public void openProperty(PropertiesHelper.Type type) {
        switch (type) {
            case BRIGHTNESS:
                pushController(BrightnessController.class);
                break;
            case VOLUME:
                pushController(VolumeController.class);
                break;
        }
    }

    private final PropertiesHelper.OpenPropertyListener openPropertyListener = new PropertiesHelper.OpenPropertyListener() {
        @Override
        public void onOpenProperty(PropertiesHelper.Type type) {
            openProperty(type);
        }
    };

    private final SafeBroadcastReceiver receiver = new SafeBroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (listController != null) listController.updateCurrentPage();
        }
    };

}
