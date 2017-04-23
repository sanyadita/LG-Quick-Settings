package com.sandradita.lg.quick_settings.ui.wheel_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Layout manager for {@link RecyclerView} to show items in circle. Shows only selected number of
 * items, that can be placed in a circle. Next item after them will be placed in the center. Other
 * items will not be shown.
 *
 * @author aditkovskaya
 */
public class WheelLayoutManager extends RecyclerView.LayoutManager {

    private static final float MAX_DEGREES = 360;
    private static final float DIAMETER_SCALE = 0.6f;

    private RecyclerView.LayoutParams params;
    private double radius;
    private int itemsInCircle;

    public WheelLayoutManager(double diameter, int itemsInCircle) {
        this.radius = diameter * DIAMETER_SCALE;
        this.itemsInCircle = itemsInCircle;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (params == null) {
            params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        return params;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        placeObjects(recycler, getItemCount());
    }

    /**
     * Calculates positions for items in recycler view by selected items count. Last item will be
     * placed in center of wheel, if items count is bigger then maximum number of
     * {@link #itemsInCircle items in wheel}. Other items will be placed in circle, but only in
     * amount of {@link #itemsInCircle items in wheel}.
     * <p>
     * First element of list will be places in the bottom of the circle. Next items will be
     * placed clockwise.
     */
    private void placeObjects(RecyclerView.Recycler recycler, int itemCount) {
        removeAllViews();
        double center_x = 0;
        double center_y = 0;
        double start_x = center_x;
        double start_y = center_y + radius;
        double angle;
        addViewAtPosition(0, start_x, start_y, recycler);
        // Adds position to center button
        if (itemCount > itemsInCircle) {
            addViewAtPosition(itemsInCircle, 0, 0, recycler);
            itemCount = itemsInCircle;
        }
        for (int i = 1; i < itemCount; i++) {
            double rad_x = start_x - center_x;
            double rad_y = start_y - center_y;

            angle = getRadians((MAX_DEGREES / itemCount) * (i));
            double angSin = Math.sin(angle);
            double angCos = Math.cos(angle);
            double x = center_x + rad_x * angCos - rad_y * angSin;
            double y = center_y + rad_x * angSin + rad_y * angCos;

            addViewAtPosition(i, x, y, recycler);
        }
    }

    /**
     * Adds view position by selected coordinates.
     */
    private void addViewAtPosition(int position, double x, double y, RecyclerView.Recycler recycler) {
        View view = recycler.getViewForPosition(position);
        addView(view);
        measureChildWithMargins(view, 0, 0);
        layoutDecorated(view, (int) x, (int) y, getWidth(), getHeight());
    }

    /**
     * Coverts degrees to radians
     */
    private double getRadians(double degrees) {
        return degrees * Math.PI / 180;
    }
}
