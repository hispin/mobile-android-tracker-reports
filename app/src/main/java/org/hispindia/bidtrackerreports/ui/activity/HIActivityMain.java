package org.hispindia.bidtrackerreports.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.hisp.dhis.android.sdk.controllers.DhisService;
import org.hisp.dhis.android.sdk.controllers.LoadingController;
import org.hisp.dhis.android.sdk.controllers.PeriodicSynchronizerController;
import org.hisp.dhis.android.sdk.events.UiEvent;
import org.hisp.dhis.android.sdk.persistence.Dhis2Application;
import org.hisp.dhis.android.sdk.persistence.preferences.ResourceType;
import org.hisp.dhis.android.sdk.ui.activities.INavigationHandler;
import org.hisp.dhis.android.sdk.ui.activities.OnBackPressedListener;
import org.hisp.dhis.android.sdk.utils.UiUtils;
import org.hispindia.bidtrackerreports.HIApplication;
import org.hispindia.bidtrackerreports.R;
import org.hispindia.bidtrackerreports.dagger.DaggerHIIComponentUi;
import org.hispindia.bidtrackerreports.dagger.HIIComponentUi;
import org.hispindia.bidtrackerreports.dagger.module.HICModuleActivity;
import org.hispindia.bidtrackerreports.ui.fragment.HIFragmentMain;
import org.hispindia.bidtrackerreports.utils.HIUtils;

public class HIActivityMain extends AppCompatActivity implements INavigationHandler {

    public Toolbar toolbar;
    private OnBackPressedListener mBackPressedListener;
    private HIIComponentUi uiComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
        setContentView(R.layout.hiactivity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LoadingController.enableLoading(this, ResourceType.ASSIGNEDPROGRAMS);
        LoadingController.enableLoading(this, ResourceType.OPTIONSETS);
        LoadingController.enableLoading(this, ResourceType.PROGRAMS);
        LoadingController.enableLoading(this, ResourceType.PROGRAM);
        LoadingController.enableLoading(this, ResourceType.CONSTANTS);
        LoadingController.enableLoading(this, ResourceType.PROGRAMRULES);
        LoadingController.enableLoading(this, ResourceType.PROGRAMRULEVARIABLES);
        LoadingController.enableLoading(this, ResourceType.PROGRAMRULEACTIONS);
        LoadingController.enableLoading(this, ResourceType.RELATIONSHIPTYPES);
        LoadingController.enableLoading(this, ResourceType.TRACKEDENTITYINSTANCE);
        LoadingController.enableLoading(this, ResourceType.TRACKEDENTITYATTRIBUTES);
        LoadingController.enableLoading(this, ResourceType.EVENTS);
        LoadingController.enableLoading(this, ResourceType.EVENT);
        LoadingController.enableLoading(this, ResourceType.ENROLLMENTS);
        LoadingController.enableLoading(this, ResourceType.ENROLLMENT);
        PeriodicSynchronizerController.activatePeriodicSynchronizer(this);
        loadInitialData();
        showMainFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Dhis2Application.getEventBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Dhis2Application.getEventBus().unregister(this);

    }

    @Override
    public void switchFragment(Fragment fragment, String fragmentTag, boolean addToBackStack) {
        if (fragment != null) {
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();

            transaction
                    .setCustomAnimations(R.anim.open_enter, R.anim.open_exit)
                    .replace(R.id.fragment_container, fragment, fragmentTag);
            transaction = transaction
                    .addToBackStack(fragmentTag);
            if (!addToBackStack) {
                getSupportFragmentManager().popBackStack();
            }
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void setBackPressedListener(OnBackPressedListener backPressedListener) {
        mBackPressedListener = backPressedListener;
    }

    @Override
    public void onBackPressed() {
        if (mBackPressedListener != null) {
            if (!mBackPressedListener.doBack()) {
                return;
            }
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    //Implement method

    private void injectDependencies() {
        uiComponent = DaggerHIIComponentUi.builder()
                .hIIComponentSingleton(((HIApplication) getApplication()).getComponent())
                .hICModuleActivity(new HICModuleActivity(this))
                .build();
        uiComponent.inject(this);
    }

    public HIIComponentUi getUiComponent() {
        return uiComponent;
    }

    public void loadInitialData() {
        String message = getString(org.hisp.dhis.android.sdk.R.string.finishing_up);
        UiUtils.postProgressMessage(message);
        DhisService.loadInitialData(HIActivityMain.this);
    }

    public void showMainFragment() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setTitle("BID Tracker Report");
            }
        });
        switchFragment(new HIFragmentMain(), HIFragmentMain.TAG, true);
    }

    @Subscribe
    public void syncNotify(UiEvent uiEvent) {
        if (uiEvent.getEventType() == UiEvent.UiEventType.SYNCING_START) {
            HIUtils.setViewAndChildrenEnabled(findViewById(R.id.fragment_container), false);
        } else {
            HIUtils.setViewAndChildrenEnabled(findViewById(R.id.fragment_container), true);
        }
        Toast.makeText(getApplicationContext(), uiEvent.getEventType().toString(), Toast.LENGTH_SHORT).show();
    }
}
