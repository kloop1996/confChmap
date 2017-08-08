package com.chmap.kloop.confchmap.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.chmap.kloop.confchmap.controller.BackgroundDefinePolution;
import com.chmap.kloop.confchmap.databinding.ActivityResultBinding;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.Polution;
import com.chmap.kloop.confchmap.service.EmailService;
import com.chmap.kloop.confchmap.service.impl.PolutionService;
import com.chmap.kloop.confchmap.view.adapter.PolutionAdapter;
import com.chmap.kloop.confchmap.view.decoration.SimpleDividerItemDecoration;
import com.chmap.kloop.confchmap.viewmodel.ResultViewModel;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.List;

/**
 * Created by kloop1996 on 03.07.2016.
 */
public class ResultActivity extends AppCompatActivity implements ResultViewModel.DataListener, OnNavigationDrawerListener {

    private ActivityResultBinding activityResultBinding;
    private ResultViewModel resultViewModel;

    private Drawer mDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResultBinding = DataBindingUtil.setContentView(this, R.layout.activity_result);

        Coordinate coordinate = new Coordinate(Double.parseDouble(getIntent().getExtras().getString(Constants.LATITUDE)), Double.parseDouble(getIntent().getExtras().getString(Constants.LONGITUDE)));

        resultViewModel = new ResultViewModel(this, this, coordinate);
        activityResultBinding.setViewModel(resultViewModel);

        if (getIntent().getExtras().get(Constants.CITY) != null) {
            resultViewModel.getLocationField().set(getIntent().getExtras().get(Constants.CITY) + ",\n" + resultViewModel.getLocationField().get());
        }

        setSupportActionBar(activityResultBinding.toolbar);

        final android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");
        activityResultBinding.toolbar.setTitle("");

        mDrawer = new DrawerBuilder(this)
                //this layout have to contain child layouts
                .withRootView(R.id.drawer_container)
                .withToolbar(activityResultBinding.toolbar)
                .withCloseOnClick(true)
                .withDisplayBelowStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)

                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search_base_title).withIcon(R.drawable.ic_format_list_bulleted_type).withDescription(R.string.drawer_item_search_base_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.BASE_SELECT_ACTIVITY),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search_gps_title).withIcon(R.drawable.ic_crosshairs_gps).withDescription(R.string.drawer_item_search_gps_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.MANUAL_ENTRY_ACTIVITY),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search_coordinates_title).withIcon(R.drawable.ic_map_marker_circle).withDescription(R.string.drawer_item_search_coordinates_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.MANUAL_ENTRY_ACTIVITY),
                        new PrimaryDrawerItem().withName(R.string.map_message).withIcon(R.drawable.ic_google_maps).withDescription(R.string.message_internet_connect).withTag(Constants.MAP_ACTIVITY).withDescriptionTextColorRes(R.color.description_item_text_color),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_help_title).withIcon(R.drawable.ic_help_circle_outline).withDescription(R.string.drawer_item_help_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.SUPPORT_ACTION)
                ).withSavedInstance(savedInstanceState).withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View view) {
                        ResultActivity.this.onDrawerOpen();
                    }

                    @Override
                    public void onDrawerClosed(View view) {
                        ResultActivity.this.onDrawerClose();
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

        setupRecyclerView(activityResultBinding.polutionRecyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        PolutionAdapter adapter = new PolutionAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPolutionChanged(List<Polution> polutions) {
        PolutionAdapter adapter =
                (PolutionAdapter) activityResultBinding.polutionRecyclerView.getAdapter();
        adapter.setPolutions(polutions);
        adapter.notifyDataSetChanged();

        resultViewModel.setPolutions(polutions);
        resultViewModel.getStatusField().set(PolutionService.getStatus(polutions));
        resultViewModel.progressVisibility.set(View.INVISIBLE);
        resultViewModel.recyclerViewVisibility.set(View.VISIBLE);
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
                startActivity(new Intent(this, BaseSelectActivity.class));
                break;
            case Constants.MAP_ACTIVITY:
                startActivity(new Intent(this,MapActivity.class));
                break;
            case Constants.SUPPORT_ACTION:
                EmailService.composeEmail(this);
                break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        resultViewModel.destroy();
    }

}

