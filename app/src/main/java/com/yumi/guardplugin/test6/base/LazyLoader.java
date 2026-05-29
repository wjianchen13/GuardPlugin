package com.yumi.guardplugin.test6.base;

public abstract class LazyLoader {

    private boolean isLazyInit;

    public LazyLoader() {

    }

    public void init() {
        if (isLazyInit)
            return;
        lazyInitView();
        isLazyInit = true;
    }

    protected abstract void lazyInitView();

    public boolean isLazyInit() {
        return isLazyInit;
    }

    public void destroy() {

    }

}
