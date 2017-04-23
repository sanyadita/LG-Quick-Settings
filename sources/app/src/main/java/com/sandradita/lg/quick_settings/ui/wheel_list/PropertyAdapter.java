package com.sandradita.lg.quick_settings.ui.wheel_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.helpers.TooltipHelper;
import com.sandradita.lg.quick_settings.modes.Mode;
import com.sandradita.lg.quick_settings.properties.abs.Property;
import com.sandradita.lg.quick_settings.ui.views.AppButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author aditkovskaya
 */

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {

    private Context context;
    private List<Property> visibleProperties;

    public PropertyAdapter(Context context, List<Property> properties) {
        this.context = context;
        this.visibleProperties = properties;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_property, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder holder, int position) {
        Property property = visibleProperties.get(position);
        holder.setProperty(property);
    }

    @Override
    public int getItemCount() {
        return visibleProperties.size();
    }

    public class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.appBtnPropertyItem)
        AppButton appBtnPropertyItem;

        private Property property;

        public PropertyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            appBtnPropertyItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int[] loc = new int[2];
            itemView.getLocationOnScreen(loc);
            int gravity = loc[1] > 0 ? Gravity.TOP : Gravity.BOTTOM;
            if (property != null && property.isChangeable()) {
                applyMode(property.nextMode());
            } else {
                TooltipHelper.showMessage(R.string.message_cant_be_changed, appBtnPropertyItem, gravity);
            }
        }

        public void setProperty(final Property property) {
            this.property = property;
            if (property != null) {
                applyMode(property.defineCurrentMode());
            }
        }

        private void applyMode(Mode mode) {
            if (mode != null) {
                appBtnPropertyItem.setButtonBackgroundResource(mode.getBtnBackgroundRes());
                appBtnPropertyItem.setIconResource(mode.getIconRes());
                appBtnPropertyItem.setText(mode.getTitle());
            }
        }
    }

}
