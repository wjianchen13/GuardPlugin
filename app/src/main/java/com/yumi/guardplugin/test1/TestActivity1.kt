package com.yumi.guardplugin.test1;

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yumi.guardplugin.R
import com.yumi.lib_a.kotlin.Student1

/**
 * 中缀函数
 */
class TestActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test1)
    }

    /**
     * 中缀函数测试
     */
    fun onTest1(v : View) {
//        if ("Hello Kotlin" beginsWith "Hello") {
//            // 处理具体的逻辑
//            println("=======================> beginsWith")
//        }
        val p = Student1()
        p.name = "Jack"
        p.age = 19
        p.grade=2
        p.sno="1000"
        p.info()
    }

    /**
     * 中缀函数测试 集合
     */
    fun onTest2(v : View) {
        val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape")
        if (list has "Banana") {
            // 处理具体的逻辑
            println("=======================> Banana")
        }
    }

    /**
     * 模拟 A to B 实现
     */
    fun onTest3(v : View) {
        val map = mapOf("Apple" with 1, "Banana" with 2, "Orange" with 3, "Pear" with 4,
            "Grape" with 5)
        for ((fruit, number) in map) {
            println("fruit is $fruit, number is $number")
        }
    }

}