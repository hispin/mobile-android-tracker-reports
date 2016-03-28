package org.hispindia.bidtrackerreports.dagger;

import org.hispindia.bidtrackerreports.dagger.module.HICModuleActivity;
import org.hispindia.bidtrackerreports.ui.activity.HIActivityMain;
import org.hispindia.bidtrackerreports.ui.fragment.HIFragmentMain;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hitodayschedule.HIFragmentTodayScheduleReport;
import org.hispindia.bidtrackerreports.ui.fragment.hibidreport.hivaccinestatus.HIFragmentVaccineStatusReport;
import org.hispindia.bidtrackerreports.ui.fragment.hioverduereport.HIFragmentOverdueReport;
import org.hispindia.bidtrackerreports.ui.fragment.hioverduereport.HIFragmentOverdueSelectProgram;
import org.hispindia.bidtrackerreports.ui.fragment.hischvaccinereport.HIFragmentSchvaccineReport;
import org.hispindia.bidtrackerreports.ui.fragment.hischvaccinereport.HIFragmentSchvaccineSelectProgram;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.HIFragmentStockReport;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.HIFragmentStockSelectProgram;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhand.HIFragmentStockInHandReport;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockinhandvsdemand.HIFragmentStockInHandvsDemandReport;
import org.hispindia.bidtrackerreports.ui.fragment.histockreport.histockdemand.HIFragmentStockDemandReport;
import dagger.Component;

/**
 * Created by nhancao on 1/18/16.
 */
@HICIPerActivity
@Component(modules = HICModuleActivity.class, dependencies = HIIComponentSingleton.class)
public interface HIIComponentUi {

    //inject activity
    void inject(HIActivityMain activityMain);

    //inject fragment
    void inject(HIFragmentMain fragmentMain);

    void inject(HIFragmentStockSelectProgram fragmentStockSelectProgram);

    void inject(HIFragmentStockReport fragmentStockReport);

    void inject(HIFragmentSchvaccineSelectProgram fragmentSchvaccineSelectProgram);

    void inject(HIFragmentSchvaccineReport fragmentSchvaccineReport);

    void inject(HIFragmentOverdueSelectProgram fragmentOverdueSelectProgram);

    void inject(HIFragmentOverdueReport fragmentOverdueReport);

    void inject(HIFragmentTodayScheduleReport fragmentTodayScheduleReport);

    void inject(HIFragmentVaccineStatusReport fragmentVaccineStatusReport);

    void inject(HIFragmentStockInHandReport fragmentStockInHandReport);

    void inject(HIFragmentStockInHandvsDemandReport fragmentStockInHandvsDemandReport);

    void inject(HIFragmentStockDemandReport fragmentStockDemandReport);


}
