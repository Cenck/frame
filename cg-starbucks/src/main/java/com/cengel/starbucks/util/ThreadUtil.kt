package com.cengel.starbucks.util

import com.cengel.starbucks.annotation.Description

fun setName(name: String) {
    Thread.currentThread().name = name
}

fun getName(): String {
    return Thread.currentThread().name
}

@Description("去检查异常sleep")
fun sleep(long: Long = 1000) {
    Thread.sleep(long)
}

@Description("一直等到main线程以外所有线程都执行完毕")
fun waitFinish() {
    while (Thread.activeCount() > 1) {

    }
}

