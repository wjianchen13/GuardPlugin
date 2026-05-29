package com.yumi.guardplugin.test6.base.presenter

import android.content.Context
import android.os.Bundle
import android.view.View
import com.yumi.guardplugin.test6.base.view.IBaseMvpView
import com.yumi.lib_b.Utils

/**
 * MVP公共Presenter
 */
open class BaseMvpPresenter<V : IBaseMvpView?>(view: V) {
    var view: V?
        protected set

    init {
        this.view = view
    }

    fun showToast(str: String?) {
        if (view != null) view!!.showToast(str)
    }

    val context: Context?
        get() = if (view != null) view!!.context else null

    open fun onCreate(savedInstanceState: Bundle?) {
        Utils.i(TAG, "onCreate")
    }

    open fun onStart() {
        Utils.i(TAG, "onStart")
    }

    fun onRestart() {
        Utils.i(TAG, "onRestart")
    }

    fun onPostResume() {
        Utils.i(TAG, "onPostResume")
    }

    open fun onResume() {
        Utils.i(TAG, "onResume")
    }

    open fun onPause() {
        Utils.i(TAG, "onPause")
    }

    open fun onStop() {
        Utils.i(TAG, "onStop")
    }

    open fun onDestroy() {
        Utils.i(TAG, "onDestroy")
    }

    open fun onAttach(context: Context) {
        Utils.i(TAG, "onAttach")
    }

    open fun onActivityCreated(savedInstanceState: Bundle?) {
        Utils.i(TAG, "onAttach")
    }

    open fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Utils.i(TAG, "onAttach")
    }

    open fun onDestroyView() {
        Utils.i(TAG, "onAttach")
    }

    open fun onDetach() {
        Utils.i(TAG, "onAttach")
    }

    companion object {
        private val TAG: String = BaseMvpPresenter::class.java.simpleName
    }
}
