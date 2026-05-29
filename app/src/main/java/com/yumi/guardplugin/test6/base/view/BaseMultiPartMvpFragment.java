package com.yumi.guardplugin.test6.base.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
public abstract class BaseMultiPartMvpFragment<V extends IBaseMultiPartMvpView, P extends MultiPartyMvpPresenter<V>>
        extends BaseMvpFragment<V, P> implements IBaseMultiPartMvpView, IBaseTargetView  {

    public static final String TAG = BaseMultiPartMvpFragment.class.getSimpleName();

    protected List<BaseModule> mModules;

    public BaseMultiPartMvpFragment() {

    }

    public List<BaseModule> getModules() {
        if(mModules == null)
            mModules = new LinkedList<>();
        return mModules;
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addModule(BaseModule module){
        getModules().add(module);
    }

    @Override
    public void removeModule(BaseModule module){
        if (module != null) {
            getModules().remove(module);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        findAllModule(m -> {
            if(m != null)
                m.onAttach(context);
        });
        getPresenter().onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findAllModule(m -> {
            if(m != null)
                m.onCreate(savedInstanceState);
        });
        getPresenter().onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findAllModule(m -> {
            if(m != null)
                m.onActivityCreated(savedInstanceState);
        });
        getPresenter().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findAllModule(m -> {
            if(m != null)
                m.onViewCreated(view, savedInstanceState);
        });
        getPresenter().onViewCreated(view, savedInstanceState);
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
    public void onDestroyView() {
        super.onDestroyView();
        findAllModule(m -> {
            if(m != null)
                m.onDestroyView();
        });
        getPresenter().onDestroyView();
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

    @Override
    public void onDetach() {
        super.onDetach();
        findAllModule(m -> {
            if(m != null)
                m.onDetach();
        });
        getPresenter().onDetach();
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