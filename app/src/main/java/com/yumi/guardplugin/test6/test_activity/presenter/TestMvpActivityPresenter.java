package com.yumi.guardplugin.test6.test_activity.presenter;

import com.yumi.guardplugin.test6.base.presenter.BaseMvpPresenter;
import com.yumi.guardplugin.test6.test_activity.view.ITestMvpActivityView;

/**
 * MVP公共Presenter
 */
public class TestMvpActivityPresenter extends BaseMvpPresenter<ITestMvpActivityView> {

    public TestMvpActivityPresenter(ITestMvpActivityView view) {
        super(view);
    }

    public String getTestString() {
        return "TestMvpPresenter";
    }

    public void onTip() {
        getView().test();
    }

}
