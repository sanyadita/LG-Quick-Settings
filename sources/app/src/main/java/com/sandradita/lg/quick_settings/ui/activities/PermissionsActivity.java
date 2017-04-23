package com.sandradita.lg.quick_settings.ui.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lge.qcircle.utils.QCircleFeature;
import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.constants.AppConstants;
import com.sandradita.lg.quick_settings.helpers.PermissionHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PermissionsActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 1001;

    @BindView(R.id.tvPermissionsResult)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        ButterKnife.bind(this);

        if (QCircleFeature.isQuickCircleAvailable(this)) {
            checkPermissions();
        } else {
            tvResult.setText(R.string.message_quick_circle_disabled);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    tvResult.setText(R.string.message_app_still_not_allowed);
                    return;
                }
            }
            tvResult.setText(R.string.message_using_allowed);
        }
    }

    private void checkPermissions() {
        if (PermissionHelper.allPermissionsAllowed(this, AppConstants.APP_PERMISSIONS)) {
            tvResult.setText(R.string.message_using_allowed);
        } else if (PermissionHelper.systemChangedNotAllowed(this)) {
            openWriteSettingsPermission();
        } else {
            ActivityCompat.requestPermissions(this, AppConstants.APP_PERMISSIONS, REQUEST_PERMISSIONS);
        }
    }

    /**
     * Opens activity that allows user enable this application to change system settings.
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void openWriteSettingsPermission() {
        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
