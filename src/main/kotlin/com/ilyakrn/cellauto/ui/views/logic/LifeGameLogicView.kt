package com.ilyakrn.cellauto.ui.views.logic

import com.ilyakrn.cellauto.ui.models.UserFieldAction
import com.ilyakrn.cellauto.ui.views.FieldView
import java.awt.Color
import javax.xml.bind.JAXBElement.GlobalScope

class LifeGameLogicView(h: Int, w: Int) : BaseLogicView<Boolean>(Array(h){ Array(w){false} }) {


    private var run = false
    private var delay = 300


    override fun getColor(value: Boolean): Color {
        val c = if(value) 255 else 0
        return Color(c, c, c)
    }

    override fun doAction(x: Int, y: Int, action: UserFieldAction) {
        when(action){
            UserFieldAction.RIGHT_CLICK -> {
                field[x][y] = false
                notifyField()
            }
            UserFieldAction.LEFT_CLICK -> {
                field[x][y] = true
                notifyField()
            }
            UserFieldAction.WHEEL_CLICK -> {
                run = !run
            }
            UserFieldAction.WHEEL_ROLL_FORWARD -> {
                if(delay >= 10)
                    delay -= 10
            }
            UserFieldAction.WHEEL_ROLL_BACKWARD -> {
                delay += 10

            }
        }
    }

    override fun initUI() {
        Thread{
            notifyField()
            while (true){
                if(run) {
                    val start = System.currentTimeMillis()
                    for (i in field.indices) {
                        for (j in 0..<field[0].size) {
                            var count = 0
                            for (li in -1..1) {
                                for (lj in -1..1) {
                                    try {
                                        if (field[i + li][j + lj])
                                            count++
                                    } catch (_: Exception) { }
                                }
                            }
                            if(field[i][j])
                                count--


                            if (count in 2..2)
                                field[i][j] = true
                            else
                                field[i][j] = false

                        }
                    }
                    notifyField()
                    Thread.sleep(delay.toLong())
                }
                else
                    Thread.sleep(100)
            }
        }.start()

    }
}