package com.sandradita.lg.quick_settings.properties;

import android.content.Context;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.helpers.ModeHelper;
import com.sandradita.lg.quick_settings.modes.Mode;
import com.sandradita.lg.quick_settings.properties.abs.Property;

/**
 * @author aditkovskaya
 */

public class SimProperty extends Property {

    private SubscriptionManager subscriptionManager;
    private TelephonyManager telephonyManager;

    public SimProperty(Context context) {
        super(context, R.string.property_title_sim_card, R.drawable.ic_sim_card);
        subscriptionManager = SubscriptionManager.from(context);
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        setChangeable(false);
    }

    @Override
    public Mode defineCurrentMode() {
        return getModeByCurrentId();
    }

    @Override
    public Mode getModeByCurrentId() {
        return ModeHelper.createOnMode(telephonyManager.getSimOperatorName(), 1, R.drawable.ic_sim_card);
    }

    @Override
    protected void applyMode(int mode) {
        // It can't be changed programmatically for now.
    }

    /**
     * @return true, if device has two sim cards.
     */
    public boolean isDualSim() {
        return subscriptionManager.getActiveSubscriptionInfoCount() == 2;
    }

}
