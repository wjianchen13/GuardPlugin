package com.yumi.guardplugin.test6.base.view;

import android.content.Context;

/**
 * MVP公共接口
 */
public interface IBaseMvpView {

    Context getContext();
    void showToast(String str);

}
