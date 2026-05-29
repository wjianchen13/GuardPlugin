package com.yumi.guardplugin.test6.test_multi_fragment.modules;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yumi.guardplugin.R;
import com.yumi.guardplugin.test6.base.module.BaseModule;
import com.yumi.guardplugin.test6.test_multi_fragment.presenter.TestMultiPartMvpFragmentPresenter;
import com.yumi.guardplugin.test6.test_multi_fragment.view.IPart1View;
import com.yumi.guardplugin.test6.test_multi_fragment.view.TestMultiPartMvpFragment;

public class FragmentPart1Module extends BaseModule<TestMultiPartMvpFragment, TestMultiPartMvpFragmentPresenter> implements IPart1View {

    private TextView tvPart1;
    private Button btnPart1;

    public FragmentPart1Module(View parent) {
        super(parent);
    }

    public FragmentPart1Module(TestMultiPartMvpFragment fragment, TestMultiPartMvpFragmentPresenter presenter, View parent) {
        super(fragment, presenter, parent);
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
