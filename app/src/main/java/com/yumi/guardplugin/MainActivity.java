package com.yumi.guardplugin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yumi.guardplugin.test1.TestActivity1;
import com.yumi.guardplugin.test2.TestActivity2;
import com.yumi.guardplugin.test3.TestActivity3;
import com.yumi.guardplugin.test4.TestActivity4;
import com.yumi.guardplugin.test5.TestActivity5;
import com.yumi.guardplugin.test6.TestActivity6;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * 测试Kotlin
     * @param v
     */
    public void onTest1(View v) {
        startActivity(new Intent(this, TestActivity1.class));
    }

    /**
     * 测试Java
     * @param v
     */
    public void onTest2(View v) {
        startActivity(new Intent(this, TestActivity2.class));

    }

    /**
     * 显示图片
     * @param v
     */
    public void onTest3(View v) {
        startActivity(new Intent(this, TestActivity3.class));
    }

    /**
     * 无用
     * @param v
     */
    public void onTest4(View v) {
        startActivity(new Intent(this, TestActivity4.class));
    }

    /**
     * 显示图片
     * @param v
     */
    public void onTest5(View v) {
        startActivity(new Intent(this, TestActivity5.class));
    }

    /**
     * 测试继承结构 kotlin
     * @param v
     */
    public void onTest6(View v) {
        startActivity(new Intent(this, TestActivity6.class));
    }


}