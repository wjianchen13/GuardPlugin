package com.yumi.guardplugin.test6.test_multi_fragment.presenter;

import com.yumi.guardplugin.test6.base.presenter.BaseMultiPartMvpPresenter;
import com.yumi.guardplugin.test6.test_multi_fragment.view.IPart2View;

public class FragmentPart2Presenter extends BaseMultiPartMvpPresenter<IPart2View> {

    public FragmentPart2Presenter(IPart2View view) {
        super(view);
    }

    public String getTestString() {
        return "TestMvpPresenter";
    }

    public void getPart2Text() {
        getView().onGetText2("part2");
    }

}