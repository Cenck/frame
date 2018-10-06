package com.cengel.starbucks.demo.dota

import com.cengel.starbucks.annotation.Description

interface Hero{
    var name:String
    var legion: LegionEnum
    var sex:Byte
    var story:String

    //随机说一句
    fun dialog()

}
enum class LegionEnum{
    sentinel,
    scourge
}

@Description("火枪")
class Kardel: Hero {
    override var name: String = "火枪"
    override var legion: LegionEnum = LegionEnum.sentinel
    override var sex: Byte = 0
    override var story: String = "矮人国的利器"
    override fun dialog() {
        println("hello,what do you need?")
    }
}

class Traxex: Hero {
    override var name: String = "小黑"
    override var legion: LegionEnum = LegionEnum.sentinel
    override var sex: Byte = 1
    override var story: String = "My bow is ready"
    override fun dialog() {
        println("my bow is ready")
    }
}

class Nortrom: Hero {
    override var name: String = "沉默"
    override var legion: LegionEnum = LegionEnum.sentinel
    override var sex: Byte = 0
    override var story: String = "My bow is ready"
    override fun dialog() {
        println("I smell magic near")
    }
}

class Omniknight: Hero {
    override var name: String = "全能"
    override var legion: LegionEnum = LegionEnum.sentinel
    override var sex: Byte = 0
    override var story: String = "My bow is ready"
    override fun dialog() {
        println("for my people")
    }
}
class Mirana: Hero {
    override var name: String = "白虎"
    override var legion: LegionEnum = LegionEnum.sentinel
    override var sex: Byte = 1
    override var story: String = "My bow is ready"
    override fun dialog() {
        println("Prepare to be Moonstruck! Strike! For the Godess! ")
    }
}

class Necromancer: Hero {
    override var name: String = "死灵法师"
    override var legion: LegionEnum = LegionEnum.scourge
    override var sex: Byte = 0
    override var story: String = "My bow is ready"
    override fun dialog() {
        println("The shadows beckon!  The restless dead await!")
    }
}