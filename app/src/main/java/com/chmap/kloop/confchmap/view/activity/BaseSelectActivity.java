package com.chmap.kloop.confchmap.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.OnNavigationDrawerListener;
import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.databinding.ActivityBaseSelectBinding;
import com.chmap.kloop.confchmap.entity.LocalePreview;
import com.chmap.kloop.confchmap.service.EmailService;
import com.chmap.kloop.confchmap.view.adapter.LocalePreviewAdapter;
import com.chmap.kloop.confchmap.view.adapter.PolutionAdapter;
import com.chmap.kloop.confchmap.view.decoration.SimpleDividerItemDecoration;
import com.chmap.kloop.confchmap.viewmodel.BaseSelectViewModel;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.List;
import java.util.Observable;
import java.util.Stack;

/**
 * Created by kloop1996 on 04.07.2016.
 */
public class BaseSelectActivity extends AppCompatActivity implements BaseSelectViewModel.DataListener,OnNavigationDrawerListener {

    private BaseSelectViewModel baseSelectViewModel;
    private ActivityBaseSelectBinding activityBaseSelectBinding;
    private int levelLocation;
    private Stack<Integer> locationHistory;
    private Drawer mDrawer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBaseSelectBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_select);
        baseSelectViewModel = new BaseSelectViewModel(this, this);

        activityBaseSelectBinding.setViewModel(baseSelectViewModel);

        setSupportActionBar(activityBaseSelectBinding.toolbar);

        final android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");
        activityBaseSelectBinding.toolbar.setTitle("");

        mDrawer =  new DrawerBuilder(this)
                //this layout have to contain child layouts
                .withRootView(R.id.drawer_container)
                .withToolbar(activityBaseSelectBinding.toolbar)
                .withCloseOnClick(true)
                .withDisplayBelowStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)

                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search_base_title).withIcon(R.drawable.ic_format_list_bulleted_type).withDescription(R.string.drawer_item_search_base_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.BASE_SELECT_ACTIVITY),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search_gps_title).withIcon(R.drawable.ic_crosshairs_gps).withDescription(R.string.drawer_item_search_gps_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.MANUAL_ENTRY_ACTIVITY),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search_coordinates_title).withIcon(R.drawable.ic_map_marker_circle).withDescription(R.string.drawer_item_search_coordinates_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.MANUAL_ENTRY_ACTIVITY),
                        new PrimaryDrawerItem().withName(R.string.map_message).withIcon(R.drawable.ic_google_maps).withDescription(R.string.message_internet_connect).withTag(Constants.MAP_ACTIVITY).withDescriptionTextColorRes(R.color.description_item_text_color),
                        new PrimaryDrawerItem().withName(R.string.lghp_message).withIcon(R.drawable.ic_google_maps).withDescription(R.string.message_internet_connect).withTag(Constants.LGHP_ACTIVITY).withDescriptionTextColorRes(R.color.description_item_text_color),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_help_title).withIcon(R.drawable.ic_help_circle_outline).withDescription(R.string.drawer_item_help_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.SUPPORT_ACTION)
                ).withSavedInstance(savedInstanceState).withOnDrawerListener(new Drawer.OnDrawerListener() {
            @Override
            public void onDrawerOpened(View view) {
                BaseSelectActivity.this.onDrawerOpen();
            }

            @Override
            public void onDrawerClosed(View view) {
                BaseSelectActivity.this.onDrawerClose();
            }

            @Override
            public void onDrawerSlide(View view, float v) {

            }
        })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        changeActivity((String) drawerItem.getTag());
                        return false;
                    }
                })
                .build();

        setupRecyclerView(activityBaseSelectBinding.locationRecyclerView);



        if (savedInstanceState == null) {
            levelLocation = Constants.LEVEL_LOCATION;
            locationHistory = new Stack<Integer>();

            locationHistory.add(1);
        }

        //mock
        baseSelectViewModel.loadLocalesPreview(1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        LocalePreviewAdapter adapter = new LocalePreviewAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLocationChanged(List<LocalePreview> localePreviews) {
        LocalePreviewAdapter adapter = ((LocalePreviewAdapter) activityBaseSelectBinding.locationRecyclerView.getAdapter());
        adapter.setLocalePreviews(localePreviews);
        adapter.notifyDataSetChanged();


        baseSelectViewModel.getProgressVisibility().set(View.INVISIBLE);
        baseSelectViewModel.getRecyclerViewVisibility().set(View.VISIBLE);
    }

    @Override
    public int getLevelLocation() {
        return levelLocation;
    }

    @Override
    public void setLevelLocation(int deepLevel) {
        this.levelLocation = deepLevel;

    }

    @Override
    public void loadLocationAdapterData(int id) {
        locationHistory.add(id);
        baseSelectViewModel.loadLocalesPreview(id);
    }

    @Override
    public void onBackPressed() {
        if (locationHistory.size() <= 1)
            super.onBackPressed();
        else {
            levelLocation--;
            locationHistory.pop();
            baseSelectViewModel.loadLocalesPreview(locationHistory.peek());
        }
    }


    @Override
    public void onDrawerClose() {
        getSupportActionBar().setTitle("");
    }

    @Override
    public void onDrawerOpen() {
        getSupportActionBar().setTitle(R.string.drawer_tolbar_title);

    }

    private void changeActivity(String tag) {
        switch (tag) {
            case Constants.MANUAL_ENTRY_ACTIVITY:
                startActivity(new Intent(this, ManualEntryActivity.class));
                break;
            case Constants.BASE_SELECT_ACTIVITY:
                mDrawer.closeDrawer();
                break;
            case Constants.MAP_ACTIVITY:
                startActivity(new Intent(this,MapActivity.class));
                break;
            case Constants.SUPPORT_ACTION:
                EmailService.composeEmail(this);
                break;
            case Constants.LGHP_ACTIVITY:
                //start lghp activity
                break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        baseSelectViewModel.destroy();
    }



}

