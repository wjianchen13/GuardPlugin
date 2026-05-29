package com.yumi.guardplugin.test6.base.view;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yumi.guardplugin.test6.base.presenter.BaseMvpPresenter;

/**
 * MVP公共父类
 * 最开始这样写有问题
 * P extends BaseMvpPresenter<IBaseMvpView>
 * 因为子类需要使用BaseMvpPresenter<ITestMvpView>
 * 而父类限定了IBaseMvpView，所以类型不匹配，正确是是使用V extends IBaseMvpView
 */
public abstract class BaseMvpActivity<V extends IBaseMvpView, P extends BaseMvpPresenter<V>> extends AppCompatActivity implements IBaseMvpView {

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
    }

    protected abstract P initPresenter();

    public P getPresenter() {
        if(mPresenter == null)
            mPresenter = initPresenter();
        return mPresenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mPresenter != null)
            mPresenter.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(mPresenter != null)
            mPresenter.onRestart();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(mPresenter != null)
            mPresenter.onPostResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mPresenter != null)
            mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mPresenter != null)
            mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mPresenter != null)
            mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null)
            mPresenter.onDestroy();
    }
}