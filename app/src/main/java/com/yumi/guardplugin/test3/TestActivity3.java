package com.yumi.guardplugin.test3;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.yumi.guardplugin.GlideApp;
import com.yumi.guardplugin.R;

/**
 * 显示图片
 */
public class TestActivity3 extends AppCompatActivity {

    private String url = "https://twww1.ayomet.com/public/images/gameIcon/wheel.png";
    private String url1 = "https://s11.ax1x.com/2024/01/18/pFkLu6K.png";
    private ImageView imgvTest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgvTest2 = findViewById(R.id.imgv_test2);
    }

    public void onTest1(View v) {
        RequestOptions options = new RequestOptions()
                .transform(new CircleCrop())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        GlideApp.with(this)
                .load(url1)
                .circleCrop()
//                .fitCenter()
                .apply(options)
                .into(imgvTest2);
    }

}