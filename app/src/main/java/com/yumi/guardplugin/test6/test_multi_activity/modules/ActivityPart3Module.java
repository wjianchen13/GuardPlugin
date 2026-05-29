package com.yumi.guardplugin.test6.test_multi_activity.modules;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.yumi.guardplugin.R;
import com.yumi.guardplugin.test6.base.module.BaseModule;
import com.yumi.guardplugin.test6.test_multi_activity.presenter.TestMultiPartMvpActivityPresenter;
import com.yumi.guardplugin.test6.test_multi_activity.view.TestMultiPartyActivity;
import com.yumi.guardplugin.test6.test_multi_fragment.view.IPart3View;

public class ActivityPart3Module extends BaseModule<TestMultiPartyActivity, TestMultiPartMvpActivityPresenter>  implements IPart3View {

    private TextView tvPart3;

    public ActivityPart3Module(View parent) {
        super(parent);
    }

    public ActivityPart3Module(TestMultiPartyActivity activity, TestMultiPartMvpActivityPresenter presenter, View parent) {
        super(activity, presenter, parent);
    }

    @Override
    public void setVisible(int animEnum) {
        super.setVisible(animEnum);
    }

    @Override
    protected void lazyInitView() {
        super.lazyInitView();
        ViewStub viewStub = mRootLayout.findViewById(R.id.vs_part3);
        viewStub.inflate();
        mCurrentLayout = mRootLayout.findViewById(R.id.llyt_part3);
        tvPart3 = mRootLayout.findViewById(R.id.tv_part3);
    }

    @Override
    public void onGetText3(String str) {
        tvPart3.setText(str);
    }
}
