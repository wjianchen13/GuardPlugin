package com.yumi.guardplugin.test2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yumi.guardplugin.R;
import com.yumi.lib_a.java.Dog;
import com.yumi.lib_a.java.Person;

/**
 * 测试Java
 */
public class TestActivity2 extends AppCompatActivity {

    private String mText;
    private TextView tvTest1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvTest1 = findViewById(R.id.tv_dog);
    }

    /**
     * 测试1
     * @param v
     */
    public void onTest1(View v) {
        Person p1 = new Person("hello", 15, "100");
        Dog d = new Dog("109", "kittoy");
        Toast.makeText(this, p1.getName(), Toast.LENGTH_SHORT).show();
        tvTest1.setText(d.name);
    }

    /**
     * 测试2
     * @param v
     */
    public void onTest2(View v) {
        mText = "hello";
        Toast.makeText(this, mText, Toast.LENGTH_SHORT).show();
    }

    /**
     * 测试3
     * @param v
     */
    public void onTest3(View v) {

    }

}