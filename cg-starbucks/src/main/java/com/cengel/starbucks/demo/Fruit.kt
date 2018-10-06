package com.cengel.starbucks.demo

interface Fruit {
    val color: Color
    var name: String
}

enum class Color {
    RED,
    GREEN,
    YELLOW
}

class Apple : Fruit {
    override val color: Color = Color.RED
    override var name: String = "苹果"
}
class Watermelon:Fruit{
    override val color: Color = Color.GREEN
    override var name: String = "西瓜"
}
class Lemon:Fruit{
    override val color: Color = Color.YELLOW
    override var name: String = "柠檬"
}