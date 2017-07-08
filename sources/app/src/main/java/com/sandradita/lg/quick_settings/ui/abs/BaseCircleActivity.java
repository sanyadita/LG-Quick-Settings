package com.sandradita.lg.quick_settings.ui.abs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lge.qcircle.template.QCircleTemplate;
import com.lge.qcircle.template.TemplateTag;
import com.lge.qcircle.template.TemplateType;
import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.constants.AppConstants;
import com.sandradita.lg.quick_settings.helpers.PermissionHelper;
import com.sandradita.lg.quick_settings.services.SafeBroadcastReceiver;
import com.sandradita.lg.quick_settings.ui.activities.PermissionsActivity;
import com.sandradita.lg.quick_settings.ui.controllers.settings.TransitionsCoordinator;

/**
 * Base activity for activities that should be working in quick circle.
 *
 * @author aditkovskaya
 */
public abstract class BaseCircleActivity extends Activity {

    private QCircleTemplate circleTemplate;
    private RelativeLayout mainLayout;
    private View content;

    /**
     * Is used to control store queue of view that was shown in activity.
     */
    private TransitionsCoordinator transitionsCoordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        circleTemplate = new QCircleTemplate(this, TemplateType.CIRCLE_EMPTY);
        setContentView(circleTemplate.getView());

        circleTemplate.setBackgroundColor(Color.BLACK);
//        circleTemplate.registerIntentReceiver();

        mainLayout = circleTemplate.getLayoutById(TemplateTag.CONTENT_MAIN);
        transitionsCoordinator = new TransitionsCoordinator(transitions);

        // To turn off screen after time out
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (PermissionHelper.checkAllPermissions(this, AppConstants.APP_PERMISSIONS)) {
            onActivityCreated();
        }
    }

    protected abstract void onActivityCreated();

    @Override
    protected void onPause() {
        super.onPause();
        transitionsCoordinator.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        transitionsCoordinator.onResume();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        lockScreenReceiver.register(this, intentFilter);
        if (!PermissionHelper.checkAllPermissions(this, AppConstants.APP_PERMISSIONS)) {
            showNoPermissionsMessage();

            QCircleTemplate circleTemplate = getCircleTemplate();
            if (circleTemplate != null) {
//                circleTemplate.setFullscreenIntent(new Intent(this, PermissionsActivity.class));
            }
            startActivity(new Intent(this, PermissionsActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        circleTemplate.unregisterReceiver();
        lockScreenReceiver.unregister(this);
        transitionsCoordinator.onDestroy();
    }

    @Override
    public View findViewById(int id) {
        if (content == null) return null;
        return content.findViewById(id);
    }

    /**
     * Shows message that not all required permissions are enabled.
     */
    private void showNoPermissionsMessage() {
        TextView textView = new TextView(this);
        textView.setText(R.string.message_no_permissions);
        textView.setPadding(100, 100, 100, 100);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        setContent(textView);
    }

    /**
     * @return parent {@link QCircleTemplate} object
     */
    public QCircleTemplate getCircleTemplate() {
        return circleTemplate;
    }

    public RelativeLayout getMainLayout() {
        return mainLayout;
    }

    /**
     * @return layout params to stretch view by parent
     */
    protected RelativeLayout.LayoutParams getMatchParentParams() {
        return new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    /**
     * Configures circle activity to show content view by selected layout resource.
     */
    protected void setContentResource(@LayoutRes int layoutRes) {
        setContent(getLayoutInflater().inflate(layoutRes, null));
    }

    /**
     * Configures circle activity to show selected content view.
     */
    public void setContent(View content) {
        this.content = content;
        mainLayout.removeAllViews();
        mainLayout.addView(content, getMatchParentParams());
    }

    public void pushController(CircleController controller) {
        transitionsCoordinator.pushController(controller);
    }

    /**
     * Opens view of selected controller type.
     */
    public <T extends CircleController> void pushController(Class<T> controllerType) {
        T controller = CircleController.createController(controllerType, this);
        if (controller != null) pushController(controller);
    }

    public void popContent() {
        transitionsCoordinator.popController();
    }

    /**
     * Listener that opens selected controller.
     */
    private final TransitionsCoordinator.QCircleTransitions transitions = new TransitionsCoordinator.QCircleTransitions() {
        @Override
        public void openController(CircleController controller) {
            setContent(controller.getControllerView());
        }
    };

    /**
     * Broadcast receiver for getting {@link Intent#ACTION_SCREEN_OFF} updates.
     */
    private final SafeBroadcastReceiver lockScreenReceiver = new SafeBroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                finish();
            }
        }
    };

}
