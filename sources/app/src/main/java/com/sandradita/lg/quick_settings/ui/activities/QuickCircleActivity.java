package com.sandradita.lg.quick_settings.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.lge.qcircle.template.QCircleTemplate;
import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.constants.AppConstants;
import com.sandradita.lg.quick_settings.helpers.PermissionHelper;
import com.sandradita.lg.quick_settings.ui.abs.BaseCircleActivity;
import com.sandradita.lg.quick_settings.ui.controllers.MenuController;

/**
 * @author aditkovskaya
 */

public class QuickCircleActivity extends BaseCircleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (PermissionHelper.checkAllPermissions(this, AppConstants.APP_PERMISSIONS)) {
            pushController(MenuController.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PermissionHelper.checkAllPermissions(this, AppConstants.APP_PERMISSIONS)) {
            showNoPermissionsMessage();

            QCircleTemplate circleTemplate = getCircleTemplate();
            if (circleTemplate != null) {
                circleTemplate.setFullscreenIntent(new Intent(this, PermissionsActivity.class));
            }
            startActivity(new Intent(this, PermissionsActivity.class));
        }
    }

    private void showNoPermissionsMessage() {
        TextView textView = new TextView(this);
        textView.setText(R.string.message_no_permissions);
        textView.setPadding(100, 100, 100, 100);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        setContent(textView);
    }

}
