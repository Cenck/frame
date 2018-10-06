package com.cengel.starbucks.demo

import com.cengel.starbucks.annotation.Description
import java.io.Serializable
import java.math.BigDecimal

open class Goods : Serializable {
    @Description("商品库存")
    var stock = 5
    var name = "农夫山泉"
    var price: BigDecimal = BigDecimal(2.00)

    @Synchronized
    fun deduction() {
        if (stock > 0) {
            Thread.sleep(1000)
            println("[deduction] 商品${name}扣减了一个库存")
            --stock
        }
    }

    @Description("打折")
    @Synchronized
    fun discount() {
        if (stock <= 2) {
            price -= (price * BigDecimal(stock / 5))
            println("商品打折")
        }
    }

    fun print():String {
        val str = "$name 剩余库存为：$stock 价格为：$price"
        println("[print] $str")
        return str
    }
}