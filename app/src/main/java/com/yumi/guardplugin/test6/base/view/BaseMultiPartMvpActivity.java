package com.yumi.guardplugin.test6.base.view;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.yumi.guardplugin.test6.base.module.BaseModule;
import com.yumi.guardplugin.test6.base.presenter.MultiPartyMvpPresenter;

import java.util.LinkedList;
import java.util.List;

/**
 * 多个部分独立
 * @param <V>
 * @param <P>
 */
public abstract class BaseMultiPartMvpActivity<V extends IBaseMultiPartMvpView, P extends MultiPartyMvpPresenter<V>>
        extends BaseMvpActivity<V, P> implements IBaseMultiPartMvpView, IBaseTargetView {

    public static final String TAG = BaseMultiPartMvpActivity.class.getSimpleName();

    protected List<BaseModule> mModules;

    public BaseMultiPartMvpActivity() {

    }

    public List<BaseModule> getModules() {
        if(mModules == null)
            mModules = new LinkedList<>();
        return mModules;
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
    public void addModule(BaseModule feature){
        getModules().add(feature);
    }

    @Override
    public void removeModule(BaseModule feature){
        if (feature != null) {
            getModules().remove(feature);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findAllModule(m -> {
            if(m != null)
                m.onCreate(savedInstanceState);
        });
        getPresenter().onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        findAllModule(m -> {
            if(m != null)
                m.onStart();
        });
        getPresenter().onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        findAllModule(m -> {
            if(m != null)
                m.onStart();
        });
        getPresenter().onRestart();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        findAllModule(m -> {
            if(m != null)
                m.onStart();
        });
        getPresenter().onPostResume();
    }

    @Override
    public void onResume() {
        super.onResume();
        findAllModule(m -> {
            if(m != null)
                m.onResume();
        });
        getPresenter().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        findAllModule(m -> {
            if(m != null)
                m.onPause();
        });
        getPresenter().onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        findAllModule(m -> {
            if(m != null)
                m.onStop();
        });
        getPresenter().onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        findAllModule(m -> {
            if(m != null)
                m.onDestroy();
        });
        getPresenter().onDestroy();
    }

    private void findAllModule(OnModuleListener listener) {
        if(getModules() != null && getModules().size() > 0) {
            for (BaseModule m : getModules()){
                if(listener != null)
                    listener.onModuleFind(m);
            }
        }
    }

    public interface OnModuleListener {
        void onModuleFind(BaseModule m);
    }

}