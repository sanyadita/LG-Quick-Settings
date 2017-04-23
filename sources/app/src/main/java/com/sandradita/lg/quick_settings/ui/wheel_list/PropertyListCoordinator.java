package com.sandradita.lg.quick_settings.ui.wheel_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.sandradita.lg.quick_settings.R;
import com.sandradita.lg.quick_settings.properties.abs.Property;
import com.sandradita.lg.quick_settings.properties.SimpleProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aditkovskaya
 */

public class PropertyListCoordinator {

    /**
     * Max amount of properties that can be shown on one page.
     */
    private static final int MAX_ITEMS_COUNT_ON_PAGE = 5;

    private Context context;
    private double wheelDiameter = 0;

    private List<List<Property>> propertiesByPages;
    private List<Property> shownItems;
    private SimpleProperty backButton;
    private SimpleProperty moreButton;
    private PropertyAdapter adapter;
    private RecyclerView recyclerView;

    private int currentPage = 0;

    public PropertyListCoordinator(Context context, List<Property> properties, double wheelDiameter) {
        this.context = context;
        this.wheelDiameter = wheelDiameter;

        backButton = new SimpleProperty(context, R.string.property_title_back, R.drawable.ic_back, backAction);
        moreButton = new SimpleProperty(context, R.string.property_title_more, R.drawable.ic_more, moreAction);

        prepareList(properties);
    }

    public RecyclerView getRecyclerView(ViewGroup parent) {
        if (recyclerView == null) {
            recyclerView = new RecyclerView(context);
            // Is incremented because every page should have back button, but max properties count
            // on page is MAX_ITEMS_COUNT_ON_PAGE.
            int itemsInCircle = MAX_ITEMS_COUNT_ON_PAGE + 1;
            recyclerView.setLayoutManager(new WheelLayoutManager(wheelDiameter, itemsInCircle));

            shownItems = new ArrayList<>();
            adapter = new PropertyAdapter(context, shownItems);
            recyclerView.setAdapter(adapter);
            changePage(0);
        }
        return recyclerView;
    }

    public void updateCurrentPage() {
        changePage(currentPage);
    }

    /**
     * Divides properties list into parts and saves them to list {@link #propertiesByPages}.
     * Each part is page content.
     *
     * @param properties all properties, that should be shown
     */
    private void prepareList(List<Property> properties) {
        this.propertiesByPages = new ArrayList<>();
        List<Property> tempList = new ArrayList<>();
        for (int i = 0; i < properties.size(); i++) {
            tempList.add(properties.get(i));
            if (tempList.size() == MAX_ITEMS_COUNT_ON_PAGE || i == properties.size() - 1) {
                propertiesByPages.add(tempList);
                tempList = new ArrayList<>();
            }
        }
    }

    /**
     * Changes shown content with properties by selected page number. Each page can have different number
     * of items
     */
    private void changePage(int pageNumber) {
        if (pageNumber < 0 || pageNumber >= propertiesByPages.size()) return;
        this.currentPage = pageNumber;

        shownItems.clear();
        shownItems.addAll(getPagePropertyList(pageNumber));
        adapter.notifyDataSetChanged();
    }

    /**
     * Creates list with properties, that should be shown on selected page. Adds back/exit button to
     * beginning of list and more button to the end if page is not the last.
     */
    private List<Property> getPagePropertyList(int pageNumber) {
        List<Property> list = new ArrayList<>();
        list.add(backButton);
        list.addAll(propertiesByPages.get(pageNumber));
        if (pageNumber != propertiesByPages.size() - 1) {
            list.add(moreButton);
        }
        int backBtnTitle = (pageNumber == 0) ? R.string.property_title_exit : R.string.property_title_back;
        backButton.setTitle(context.getString(backBtnTitle));
        return list;
    }

    /**
     * Action to return to previous page or exit from application
     */
    private final SimpleProperty.ButtonAction backAction = new SimpleProperty.ButtonAction() {
        @Override
        public void onClick() {
            if (currentPage == 0) {
                System.exit(0);
            } else {
                changePage(currentPage - 1);
            }
        }
    };

    /**
     * Action to show next part of properties list
     */
    private final SimpleProperty.ButtonAction moreAction = new SimpleProperty.ButtonAction() {
        @Override
        public void onClick() {
            changePage(currentPage + 1);
        }
    };

}
