package com.yumi.guardplugin.test6.base.presenter

import com.yumi.guardplugin.test6.base.view.IBaseMvpView

/**
 * MVP公共Presenter
 */
open class BaseMultiPartMvpPresenter<V : IBaseMvpView?>(view: V) : BaseMvpPresenter<V>(view) {
    companion object {
        private val TAG: String = BaseMultiPartMvpPresenter::class.java.simpleName
    }
}
