package com.yumi.guardplugin.test6.test_multi_activity.modules;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yumi.guardplugin.R;
import com.yumi.guardplugin.test6.base.module.BaseModule;
import com.yumi.guardplugin.test6.test_multi_activity.presenter.TestMultiPartMvpActivityPresenter;
import com.yumi.guardplugin.test6.test_multi_activity.view.TestMultiPartyActivity;
import com.yumi.guardplugin.test6.test_multi_fragment.view.IPart1View;

public class ActivityPart1Module extends BaseModule<TestMultiPartyActivity, TestMultiPartMvpActivityPresenter> implements IPart1View {

    private TextView tvPart1;
    private Button btnPart1;

    public ActivityPart1Module(View parent) {
        super(parent);
    }

    public ActivityPart1Module(TestMultiPartyActivity activity, TestMultiPartMvpActivityPresenter presenter, View parent) {
        super(activity, presenter, parent);
//        this.mActivityPresenter = mTarget.getPresenter();
//        mTarget.initModules
    }

    @Override
    public void setVisible(int animEnum) {
        super.setVisible(animEnum);
    }

    @Override
    protected void lazyInitView() {
        super.lazyInitView();
        tvPart1 = mRootLayout.findViewById(R.id.tv_part1);
        btnPart1 = mRootLayout.findViewById(R.id.btn_part10);
        btnPart1.setOnClickListener(this);
    }

    @Override
    public void onGetText(String str) {
        tvPart1.setText(str);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.btn_part10) {
            if(mPresenter != null)
                mPresenter.getPart1Text();
        }
    }
}
