package com.yumi.guardplugin.test6.base.module

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.yumi.guardplugin.test6.base.LazyLoader
import com.yumi.guardplugin.test6.base.module.Constants.AnimEnum
import com.yumi.guardplugin.test6.base.presenter.BaseMultiPartMvpPresenter
import com.yumi.guardplugin.test6.base.view.IBaseMvpView
import com.yumi.guardplugin.test6.base.view.IBaseTargetView

/**
 * 模块公共父类
 *
 */
abstract class BaseModule<V : IBaseTargetView?, P : BaseMultiPartMvpPresenter<*>?>(@JvmField protected var mRootLayout: View?) :
    LazyLoader(), IBaseMvpView, View.OnClickListener {
    protected var mContext: Context? = null
    protected var mTarget: V? = null
    @JvmField
    protected var mPresenter: P? = null

    @JvmField
    protected var mCurrentLayout: View? = null

    constructor(target: V, presenter: P, parent: View?) : this(parent) {
        this.mTarget = target
        mTarget!!.addModule(this)
        this.mPresenter = presenter
    }

    override fun showToast(str: String) {
        if (context() != null) Toast.makeText(context(), str, Toast.LENGTH_SHORT).show()
    }

    override fun lazyInitView() {
    }

    val isOnShowing: Boolean
        get() {
            if (mCurrentLayout == null) return false
            return mCurrentLayout!!.visibility == View.VISIBLE
        }

    override fun getContext(): Context? {
        return null
    }

    val applicationContext: Context?
        get() = null

    open fun setVisible(@AnimEnum animEnum: Int) {
        if (animEnum == Constants.MODULE_VISIABLE) init()
        if (!isLazyInit && animEnum == Constants.MODULE_IN_VISIABLE) return

        if (mRootLayout != null) mRootLayout!!.clearAnimation()

        when (animEnum) {
            Constants.MODULE_VISIABLE -> {}
            Constants.MODULE_IN_VISIABLE -> {}
        }
    }

    /**********************************************************************************************
     * Activity生命周期
     */
    fun onCreate(savedInstanceState: Bundle?) {
    }

    fun onStart() {
    }

    fun onRestart() {
    }

    fun onResume() {
    }

    fun onPause() {
    }

    fun onStop() {
    }

    fun onDestroy() {
    }

    /**********************************************************************************************
     * Fragment 生命周期
     */
    fun onAttach(context: Context) {
    }

    fun onActivityCreated(savedInstanceState: Bundle?) {
    }

    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    fun onDestroyView() {
    }

    fun onDetach() {
    }

    override fun onClick(v: View) {
    }

    private fun context(): Context? {
        return mContext
    }
}
