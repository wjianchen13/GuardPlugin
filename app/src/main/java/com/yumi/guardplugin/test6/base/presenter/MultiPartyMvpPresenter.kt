package com.yumi.guardplugin.test6.base.presenter

import android.content.Context
import android.os.Bundle
import android.view.View
import com.yumi.guardplugin.test6.base.view.IBaseMvpView
import java.util.LinkedList

/**
 * Fragment多个部分独立公共Presenter
 */
open class MultiPartyMvpPresenter<V : IBaseMvpView?>(view: V) : BaseMultiPartMvpPresenter<V>(view) {
    protected var mChildFragmentPresenters: MutableList<BaseMultiPartMvpPresenter<*>>? = null

    protected val childFragmentPresenters: MutableList<BaseMultiPartMvpPresenter<*>>
        get() {
            if (mChildFragmentPresenters == null) mChildFragmentPresenters =
                LinkedList()
            return mChildFragmentPresenters!!
        }

    protected fun <T : BaseMultiPartMvpPresenter<*>?> initChildPresenter(presenter: T?): T? {
        if (presenter != null) childFragmentPresenters.add(presenter)
        return presenter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        findAllChildPresenter(OnFindListener { presenter: BaseMultiPartMvpPresenter<*>? ->
            presenter?.onAttach(context)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findAllChildPresenter(OnFindListener { presenter: BaseMultiPartMvpPresenter<*>? ->
            presenter?.onCreate(savedInstanceState)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        findAllChildPresenter(OnFindListener { presenter: BaseMultiPartMvpPresenter<*>? ->
            presenter?.onActivityCreated(savedInstanceState)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findAllChildPresenter(OnFindListener { presenter: BaseMultiPartMvpPresenter<*>? ->
            presenter?.onViewCreated(view, savedInstanceState)
        })
    }

    override fun onStart() {
        super.onStart()
        findAllChildPresenter(OnFindListener { presenter: BaseMultiPartMvpPresenter<*>? ->
            presenter?.onStart()
        })
    }

    override fun onResume() {
        super.onResume()
        findAllChildPresenter(OnFindListener { presenter: BaseMultiPartMvpPresenter<*>? ->
            presenter?.onResume()
        })
    }

    override fun onPause() {
        super.onPause()
        findAllChildPresenter(OnFindListener { presenter: BaseMultiPartMvpPresenter<*>? ->
            presenter?.onPause()
        })
    }

    override fun onStop() {
        super.onStop()
        findAllChildPresenter(OnFindListener { presenter: BaseMultiPartMvpPresenter<*>? ->
            presenter?.onStop()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        findAllChildPresenter(OnFindListener { presenter: BaseMultiPartMvpPresenter<*>? ->
            presenter?.onDestroyView()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        findAllChildPresenter(OnFindListener { presenter: BaseMultiPartMvpPresenter<*>? ->
            presenter?.onDestroy()
        })
    }

    override fun onDetach() {
        super.onDetach()
        findAllChildPresenter(OnFindListener { presenter: BaseMultiPartMvpPresenter<*>? ->
            presenter?.onDetach()
        })
    }

    private fun findAllChildPresenter(listener: OnFindListener?) {
        if (childFragmentPresenters != null && childFragmentPresenters.size > 0) {
            for (p in childFragmentPresenters) {
                listener?.onFindPresenter(p)
            }
        }
    }

    fun interface OnFindListener {
        fun onFindPresenter(presenter: BaseMultiPartMvpPresenter<*>?)
    }

    companion object {
        private val TAG: String = MultiPartyMvpPresenter::class.java.simpleName
    }
}
