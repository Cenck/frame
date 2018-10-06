package com.cengel.starbucks.demo.dota

interface Soldier{
    var name:String
    var price:Double
    fun dialog()
}

class Rifleman:Soldier{
    override var name: String="步兵"
    override var price: Double=200.0
    override fun dialog() {
        println("ok")
    }
}
class Knight:Soldier{
    override var name: String="骑士"
    override var price: Double=800.0
    override fun dialog() {
        println("ok")
    }
}
