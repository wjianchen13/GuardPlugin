package com.yumi.guardplugin.test6.base.module

import androidx.annotation.IntDef

/**
 *
 */
object Constants {
    const val MODULE_INIT: Int = 1
    const val MODULE_VISIABLE: Int = 2
    const val MODULE_IN_VISIABLE: Int = 3

    @IntDef(
        MODULE_INIT, MODULE_VISIABLE, MODULE_IN_VISIABLE
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class AnimEnum

    object PayType {
        const val PAY_TYPE_GOOGLE: Int = 1
        const val PAY_TYPE_THIRD: Int = 2
        const val PAY_TYPE_COIN: Int = 3
    }
}
