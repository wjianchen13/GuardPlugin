package com.yumi.guardplugin.test1

/**
 * infix函数由于其语法糖格式的特殊性，有两个比较严格的限制：首先， infix函数是
 * 不能定义成顶层函数的，它必须是某个类的成员函数，可以使用扩展函数的方式将它定义到某
 * 个类当中；其次， infix函数必须接收且只能接收一个参数，至于参数类型是没有限制的
 * 当您调用 beginsWith 函数时，它会自动将调用者（即调用 beginsWith 的字符串对象）作为函数的接收者（即 this）传递给函数体
 */
infix fun String.beginsWith(prefix: String) = startsWith(prefix)


infix fun <T> Collection<T>.has(element: T) = contains(element)

//public infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)

infix fun <A, B> A.with(that: B): Pair<A, B> = Pair(this, that)

