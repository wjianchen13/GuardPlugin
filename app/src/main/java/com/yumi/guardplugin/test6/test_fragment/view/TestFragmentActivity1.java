package com.yumi.guardplugin.test6.test_fragment.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yumi.guardplugin.R;


public class TestFragmentActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flyt_test_fragment, new TestMvpFragment(), TestMvpFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();

    }

}