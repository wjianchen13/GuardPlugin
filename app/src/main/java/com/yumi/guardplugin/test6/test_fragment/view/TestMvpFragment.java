package com.yumi.guardplugin.test6.test_fragment.view;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yumi.guardplugin.R;
import com.yumi.guardplugin.test6.base.view.BaseMvpFragment;
import com.yumi.guardplugin.test6.test_fragment.presenter.TestMvpFragmentPresenter;
import com.yumi.lib_b.Utils;

/**
 * Fragment MVP框架测试
 */
public class TestMvpFragment extends BaseMvpFragment<ITestMvpFragmentView, TestMvpFragmentPresenter> implements ITestMvpFragmentView, View.OnClickListener {

    public static final String TAG = TestMvpFragment.class.getSimpleName();

    private String LOG = "=============================> ";

    private TextView tvTip1;
    private Button btnToast;
    private Button btnTest;
    private Button btnContext;
    private Button btnTip;

    public TestMvpFragment() {
    }

    @Override
    protected TestMvpFragmentPresenter initPresenter() {
        return new TestMvpFragmentPresenter(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, LOG + "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, LOG + "onCreate");
    }

    @Nullable
    @Override
    public View onCreateViewExp(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, LOG + "onCreateView");
        View v = inflater.inflate(R.layout.fragment_test1, container, false);
        tvTip1 = v.findViewById(R.id.tv_tip1);
        btnToast = v.findViewById(R.id.btn_toast);
        btnTest = v.findViewById(R.id.btn_test);
        btnContext = v.findViewById(R.id.btn_context);
        btnTip = v.findViewById(R.id.btn_tip);
        btnToast.setOnClickListener(this);
        btnTest.setOnClickListener(this);
        btnContext.setOnClickListener(this);
        btnTip.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_toast) {
            getPresenter().showToast("toast");
        } else if(v.getId() == R.id.btn_test) {
            String str = mPresenter.getTestString();
            Utils.i(TAG, "str: " + str);
        } else if(v.getId() == R.id.btn_context) {
            Context context = mPresenter.getContext();
            Utils.i(TAG, "context: " + context);
        } else if(v.getId() == R.id.btn_tip) {
            mPresenter.onTip();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, LOG + "onActivityCreated");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, LOG + "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, LOG + "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, LOG + "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, LOG + "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, LOG + "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, LOG + "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, LOG + "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, LOG + "onDetach");
    }

    @Override
    public void test() {
        tvTip1.setVisibility(View.VISIBLE);
    }
}