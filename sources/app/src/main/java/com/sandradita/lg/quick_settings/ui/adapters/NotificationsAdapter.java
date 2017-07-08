package com.sandradita.lg.quick_settings.ui.adapters;

import android.app.Notification;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sandradita.lg.quick_settings.R;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sandradita on 6/5/2017.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {

    private List<StatusBarNotification> notificationList;

    public NotificationsAdapter(List<StatusBarNotification> notificationList) {
        this.notificationList = notificationList;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(NotificationViewHolder.LAYOUT_RES, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        if (notificationList != null && position < notificationList.size()) {
            StatusBarNotification notification = notificationList.get(position);
            holder.setItem(notification);
        }
    }

    @Override
    public int getItemCount() {
        if (notificationList == null) return 0;
        return notificationList.size();
    }

    public void replaceCollection(Collection<StatusBarNotification> statusBarNotifications) {
        notificationList.clear();
        notificationList.addAll(statusBarNotifications);
        notifyDataSetChanged();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        public static final int LAYOUT_RES = R.layout.item_notification;

        @BindView(R.id.ivNotificationSmallIcon)
        ImageView ivNotificationSmallIcon;
        @BindView(R.id.ivNotificationLargeIcon)
        ImageView ivNotificationLargeIcon;
        @BindView(R.id.tvNotificationTitle)
        TextView tvNotificationTitle;
        @BindView(R.id.tvNotificationBody)
        TextView tvNotificationBody;

        private StatusBarNotification item;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setItem(StatusBarNotification item) {
            this.item = item;
            String title = null;
            String body = null;
            Icon smallIcon = null;
            Icon largeIcon = null;
            if (item != null) {
                Bundle notificationBundle = item.getNotification().extras;
                title = notificationBundle.getString(Notification.EXTRA_TITLE);
                body = notificationBundle.getString(Notification.EXTRA_TEXT);
                smallIcon = item.getNotification().getSmallIcon();
                largeIcon = item.getNotification().getLargeIcon();
            }
            tvNotificationTitle.setText(title);
            tvNotificationBody.setText(body);
            setIcon(ivNotificationSmallIcon, smallIcon);
            setIcon(ivNotificationLargeIcon, largeIcon);
        }
    }

    private void setIcon(ImageView imageView, Icon icon) {
        if (icon != null) {
            imageView.setImageIcon(icon);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

}
