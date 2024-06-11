package com.ilyakrn.cellauto.ui.views.logic

import com.ilyakrn.cellauto.ui.models.UserFieldAction
import com.ilyakrn.cellauto.ui.views.FieldView
import java.awt.Color
import javax.xml.bind.JAXBElement.GlobalScope

class ShadowLogicView(h: Int, w: Int) : BaseLogicView<Int>(Array(h){ Array(w){0} }) {
    override fun getColor(value: Int): Color {
        val c = if(value in 0..255) value else (if(value < 0) 0 else 255)
        return Color(c, c, c)
    }

    override fun doAction(x: Int, y: Int, action: UserFieldAction) {
        when(action){
            UserFieldAction.RIGHT_CLICK -> {
                field[x][y] -= 10000
            }
            UserFieldAction.LEFT_CLICK -> {
                field[x][y] += 10000
            }
            UserFieldAction.WHEEL_CLICK -> {

            }
            UserFieldAction.WHEEL_ROLL_FORWARD -> {

            }
            UserFieldAction.WHEEL_ROLL_BACKWARD -> {

            }
        }
    }

    override fun initUI() {
        Thread{
            while (true){
                val start = System.currentTimeMillis()
                for (i in field.indices){
                    for (j in 0..<field[0].size){
                        var sum = 0
                        var count = 0
                        for(li in -1..1){
                            for(lj in -1..1){
                                try{
                                    sum += field[i+lj][j+li]
                                    count++
                                }catch (_: Exception){}
                            }
                        }
                        field[i][j] = sum/count
                    }
                }
                notifyField()
            }
        }.start()

    }
}