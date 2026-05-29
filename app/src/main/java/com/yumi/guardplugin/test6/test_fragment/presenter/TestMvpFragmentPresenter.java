package com.yumi.guardplugin.test6.test_fragment.presenter;

import com.yumi.guardplugin.test6.base.presenter.BaseMvpPresenter;
import com.yumi.guardplugin.test6.test_fragment.view.ITestMvpFragmentView;

public class TestMvpFragmentPresenter extends BaseMvpPresenter<ITestMvpFragmentView> {

    public TestMvpFragmentPresenter(ITestMvpFragmentView view) {
        super(view);
    }

    public String getTestString() {
        return "TestMvpPresenter";
    }

    public void onTip() {
        getView().test();
    }

}