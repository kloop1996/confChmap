package com.chmap.kloop.confchmap.view.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.OnNavigationDrawerListener;
import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.dao.factory.DaoFactory;
import com.chmap.kloop.confchmap.databinding.ActivityManualEntryBinding;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.Lghp;
import com.chmap.kloop.confchmap.entity.LghpCutting;
import com.chmap.kloop.confchmap.entity.PolutionLevel;
import com.chmap.kloop.confchmap.service.EmailService;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.service.impl.CoordinateService;
import com.chmap.kloop.confchmap.service.impl.MapProviderService;
import com.chmap.kloop.confchmap.viewmodel.ManualEntryViewModel;
import com.chmap.kloop.confchmap.viewmodel.OnStopPulsatorListener;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by kloop1996 on 27.06.2016.
 */
public class ManualEntryActivity extends AppCompatActivity implements OnNavigationDrawerListener,OnStopPulsatorListener {

    private ActivityManualEntryBinding activityManualEntryBinding;
    private ManualEntryViewModel manualEntryViewModel;
    private PulsatorLayout pulsator;

    private int countRepeat;
    private Drawer mDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManualEntryBinding = DataBindingUtil.setContentView(this, R.layout.activity_manual_entry);

        manualEntryViewModel = new ManualEntryViewModel(this);
        activityManualEntryBinding.setViewModel(manualEntryViewModel);

        setSupportActionBar(activityManualEntryBinding.toolbar);

        final android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.enter_coordinate);

        try {
            ArrayList<String> arrayList = DaoFactory.getDaoFactory().getBaseDao().getRecomendations((new PolutionLevel(0.5, 0.5)));
        } catch (DaoException e) {
            e.printStackTrace();
            String string = e.toString();
        }
        activityManualEntryBinding.toolbar.setTitle(R.string.enter_coordinate);

        mDrawer = new DrawerBuilder(this)
                //this layout have to contain child layouts
                .withRootView(R.id.drawer_container)
                .withToolbar(activityManualEntryBinding.toolbar)
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
                        ManualEntryActivity.this.onDrawerOpen();
                    }

                    @Override
                    public void onDrawerClosed(View view) {
                        ManualEntryActivity.this.onDrawerClose();
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

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //hideSoftKeyboard();

    }

    @Override
    public void onStart() {
        super.onStart();
        manualEntryViewModel.notifyActivityOnStart();
    }


    @Override
    public void onStop() {
        manualEntryViewModel.notifyActivityOnStop();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onDrawerClose() {
        getSupportActionBar().setTitle(R.string.enter_coordinate);
    }

    @Override
    public void onDrawerOpen() {
        getSupportActionBar().setTitle(R.string.drawer_tolbar_title);

    }

    private void changeActivity(String tag) {
        switch (tag) {
            case Constants.MANUAL_ENTRY_ACTIVITY:
                mDrawer.closeDrawer();
                break;
            case Constants.BASE_SELECT_ACTIVITY:
                startActivity(new Intent(this, BaseSelectActivity.class));
                break;
            case Constants.MAP_ACTIVITY:
                startActivity(new Intent(this, MapActivity.class));
                break;
            case Constants.SUPPORT_ACTION:
                EmailService.composeEmail(this);
                break;
            case Constants.LGHP_ACTIVITY:
                startActivity(new Intent(this,LghpActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manualEntryViewModel.destroy();
    }

    public void startAnimation(){
        pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();

    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void stopAnimation() {
        if (pulsator!=null){
            pulsator.stop();
        }
    }
}
