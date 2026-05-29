package com.yumi.guardplugin.test6.test_multi_activity.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.yumi.guardplugin.R
import com.yumi.guardplugin.test6.base.module.Constants.MODULE_VISIABLE
import com.yumi.guardplugin.test6.base.view.BaseMultiPartMvpActivity
import com.yumi.guardplugin.test6.test_multi_activity.modules.ActivityPart1Module
import com.yumi.guardplugin.test6.test_multi_activity.modules.ActivityPart2Module
import com.yumi.guardplugin.test6.test_multi_activity.modules.ActivityPart3Module
import com.yumi.guardplugin.test6.test_multi_activity.presenter.TestMultiPartMvpActivityPresenter

class TestMultiPartyActivity :
    BaseMultiPartMvpActivity<ITestMultiPartMvpActivityView?, TestMultiPartMvpActivityPresenter?>(),
    ITestMultiPartMvpActivityView, View.OnClickListener {
    private val LOG = "=============================> "

    private var mRootView: View? = null
    private var btnTest1: Button? = null
    private var btnTest2: Button? = null
    private var btnTest3: Button? = null
    private var btnTest4: Button? = null

    private var mPart1Module: ActivityPart1Module? = null
    private var mPart2Module: ActivityPart2Module? = null
    private var mPart3Module: ActivityPart3Module? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_multi_part)
        initView()
        initModules()
    }

    fun initView() {
        mRootView = findViewById(R.id.rlyt_frame)
        btnTest1 = findViewById(R.id.btn_test1)
        btnTest2 = findViewById(R.id.btn_test2)
        btnTest3 = findViewById(R.id.btn_test3)
        btnTest4 = findViewById(R.id.btn_test4)
        btnTest1?.setOnClickListener(this)
        btnTest2?.setOnClickListener(this)
        btnTest3?.setOnClickListener(this)
        btnTest4?.setOnClickListener(this)
    }

    override fun initPresenter(): TestMultiPartMvpActivityPresenter {
        return TestMultiPartMvpActivityPresenter(this)
    }

    private fun initModules() {
        mPart1Module = ActivityPart1Module(this, presenter, mRootView)
        mPart1Module!!.init()
        mPart2Module = ActivityPart2Module(this, presenter, mRootView)
        mPart2Module!!.init()
        mPart3Module = ActivityPart3Module(this, presenter, mRootView)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_test1) {
            presenter!!.getPart1Text()
        } else if (v.id == R.id.btn_test2) {
            presenter!!.getPart2Text()
        } else if (v.id == R.id.btn_test3) {
            mPart3Module!!.setVisible(MODULE_VISIABLE)
        } else if (v.id == R.id.btn_test4) {
            presenter!!.getPart3Text()
        }
    }

    override fun test() {
    }

    override fun onGetText(str: String) {
        mPart1Module!!.onGetText(str)
    }


    override fun onGetText2(str: String) {
        mPart2Module!!.onGetText2(str)
    }

    override fun onGetText3(str: String) {
        mPart3Module!!.onGetText3(str)
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onPostResume() {
        super.onPostResume()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        val TAG: String = TestMultiPartyActivity::class.java.simpleName
    }
}