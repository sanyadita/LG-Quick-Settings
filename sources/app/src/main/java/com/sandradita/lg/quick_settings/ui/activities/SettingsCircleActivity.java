package com.sandradita.lg.quick_settings.ui.activities;

import com.sandradita.lg.quick_settings.ui.abs.BaseCircleActivity;
import com.sandradita.lg.quick_settings.ui.controllers.settings.MenuController;

/**
 * @author aditkovskaya
 */

public class SettingsCircleActivity extends BaseCircleActivity {

    @Override
    protected void onActivityCreated() {
        pushController(MenuController.class);
    }

}
