package com.yumi.guardplugin.test6.test_multi_fragment.view;

import static com.yumi.guardplugin.test6.base.module.Constants.MODULE_VISIABLE;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yumi.guardplugin.R;
import com.yumi.guardplugin.test6.base.view.BaseMultiPartMvpFragment;
import com.yumi.guardplugin.test6.test_multi_fragment.modules.FragmentPart1Module;
import com.yumi.guardplugin.test6.test_multi_fragment.modules.FragmentPart2Module;
import com.yumi.guardplugin.test6.test_multi_fragment.modules.FragmentPart3Module;
import com.yumi.guardplugin.test6.test_multi_fragment.presenter.TestMultiPartMvpFragmentPresenter;

/**
 * Fragment MVP框架测试
 */
public class TestMultiPartMvpFragment extends BaseMultiPartMvpFragment<ITestMultiPartMvpFragmentView, TestMultiPartMvpFragmentPresenter> implements ITestMultiPartMvpFragmentView, View.OnClickListener {

    public static final String TAG = TestMultiPartMvpFragment.class.getSimpleName();

    private String LOG = "=============================> ";

    private Button btnTest1;
    private Button btnTest2;
    private Button btnTest3;
    private Button btnTest4;

    private FragmentPart1Module mPart1Module;
    private FragmentPart2Module mPart2Module;
    private FragmentPart3Module mPart3Module;

    public TestMultiPartMvpFragment() {
    }

    @Override
    protected TestMultiPartMvpFragmentPresenter initPresenter() {
        return new TestMultiPartMvpFragmentPresenter(this);
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
        View v = inflater.inflate(R.layout.fragment_test_multi_part, container, false);
        btnTest1 = v.findViewById(R.id.btn_test1);
        btnTest2 = v.findViewById(R.id.btn_test2);
        btnTest3 = v.findViewById(R.id.btn_test3);
        btnTest4 = v.findViewById(R.id.btn_test4);
        btnTest1.setOnClickListener(this);
        btnTest2.setOnClickListener(this);
        btnTest3.setOnClickListener(this);
        btnTest4.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_test1) {
            getPresenter().getPart1Text();
        } else if(v.getId() == R.id.btn_test2) {
            getPresenter().getPart2Text();
        } else if(v.getId() == R.id.btn_test3) {
            mPart3Module.setVisible(MODULE_VISIABLE);
        } else if(v.getId() == R.id.btn_test4) {
            getPresenter().getPart3Text();
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
        initModules();
    }

    private void initModules() {
        mPart1Module = new FragmentPart1Module(this, getPresenter(), mRootView);
        mPart1Module.init();
        mPart2Module = new FragmentPart2Module(this, getPresenter(), mRootView);
        mPart2Module.init();
        mPart3Module = new FragmentPart3Module(this, getPresenter(), mRootView);
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

    }

    @Override
    public void onGetText(String str) {
        mPart1Module.onGetText(str);
    }

    @Override
    public void onGetText2(String str) {
        mPart2Module.onGetText2(str);
    }

    @Override
    public void onGetText3(String str) {
        mPart3Module.onGetText3(str);
    }

}