package com.yumi.guardplugin.test6.test_multi_fragment.modules;

import android.view.View;
import android.widget.TextView;

import com.yumi.guardplugin.R;
import com.yumi.guardplugin.test6.base.module.BaseModule;
import com.yumi.guardplugin.test6.test_multi_fragment.presenter.TestMultiPartMvpFragmentPresenter;
import com.yumi.guardplugin.test6.test_multi_fragment.view.IPart2View;
import com.yumi.guardplugin.test6.test_multi_fragment.view.TestMultiPartMvpFragment;

public class FragmentPart2Module extends BaseModule<TestMultiPartMvpFragment, TestMultiPartMvpFragmentPresenter> implements IPart2View {

    private TextView tvPart2;

    public FragmentPart2Module(View parent) {
        super(parent);
    }

    public FragmentPart2Module(TestMultiPartMvpFragment fragment, TestMultiPartMvpFragmentPresenter presenter, View parent) {
        super(fragment, presenter, parent);
    }

    @Override
    public void setVisible(int animEnum) {
        super.setVisible(animEnum);
    }

    @Override
    protected void lazyInitView() {
        super.lazyInitView();
        tvPart2 = mRootLayout.findViewById(R.id.tv_part2);
    }

    @Override
    public void onGetText2(String str) {
        tvPart2.setText(str);
    }
}
