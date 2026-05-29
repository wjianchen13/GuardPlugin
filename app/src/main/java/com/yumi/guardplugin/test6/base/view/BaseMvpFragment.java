package com.yumi.guardplugin.test6.base.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yumi.guardplugin.test6.base.presenter.BaseMvpPresenter;

public abstract class BaseMvpFragment<V extends IBaseMvpView, P extends BaseMvpPresenter<V>>  extends Fragment implements IBaseMvpView  {

    public static final String TAG = BaseMvpFragment.class.getSimpleName();

    protected P mPresenter;
    protected View mRootView;

    public BaseMvpFragment() {

    }

    protected abstract P initPresenter();

    public P getPresenter() {
        if(mPresenter == null)
            mPresenter = initPresenter();
        return mPresenter;
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getPresenter().onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = onCreateViewExp(inflater, container, savedInstanceState);
        return mRootView;
    }

    protected abstract View onCreateViewExp(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getPresenter().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPresenter().onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getPresenter().onDetach();
    }


}