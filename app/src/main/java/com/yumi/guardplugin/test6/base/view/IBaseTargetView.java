package com.yumi.guardplugin.test6.base.view;

import com.yumi.guardplugin.test6.base.module.BaseModule;

/**
 * 使用对象：Fragment Activity
 */
public interface IBaseTargetView {

    void addModule(BaseModule module);
    void removeModule(BaseModule module);

}
