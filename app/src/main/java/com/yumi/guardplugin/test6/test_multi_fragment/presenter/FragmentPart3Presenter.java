package com.yumi.guardplugin.test6.test_multi_fragment.presenter;

import com.yumi.guardplugin.test6.base.presenter.BaseMultiPartMvpPresenter;
import com.yumi.guardplugin.test6.test_multi_fragment.view.IPart3View;

public class FragmentPart3Presenter extends BaseMultiPartMvpPresenter<IPart3View> {

    public FragmentPart3Presenter(IPart3View view) {
        super(view);
    }

    public String getTestString() {
        return "TestMvpPresenter";
    }

    public void getPart3Text() {
        getView().onGetText3("part3");
    }

}