package com.yumi.guardplugin.test6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yumi.guardplugin.R;
import com.yumi.guardplugin.test6.test_activity.view.TestMvpActivity;
import com.yumi.guardplugin.test6.test_fragment.view.TestFragmentActivity1;
import com.yumi.guardplugin.test6.test_multi_activity.view.TestMultiPartyActivity;
import com.yumi.guardplugin.test6.test_multi_fragment.view.TestMultiPartyFragmentActivity;

/**
 * 测试继承结构 kotlin
 */
public class TestActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test6);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * 测试项目中使用复杂Activity的情况
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, TestMvpActivity.class));
    }

    /**
     * 测试项目中使用复杂fragment的情况
     * @param v
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, TestFragmentActivity1.class));
    }

    /**
     * 测试项目中多部分Activity的情况
     * @param v
     */
    public void onTest3(View v) {
        startActivity(new Intent(this, TestMultiPartyActivity.class));
    }

    /**
     * 测试项目中多部分fragment的情况
     * @param v
     */
    public void onTest4(View v) {
        startActivity(new Intent(this, TestMultiPartyFragmentActivity.class));
    }

}