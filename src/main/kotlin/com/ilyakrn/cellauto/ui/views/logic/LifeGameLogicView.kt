package com.ilyakrn.cellauto.ui.views.logic

import com.ilyakrn.cellauto.ui.models.UserFieldAction
import com.ilyakrn.cellauto.ui.views.logic.base.BaseLogicView
import java.awt.Color

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
            UserFieldAction.WHEEL_ROLL_FORWARD -> { if(delay >= 10) delay -= 10 }
            UserFieldAction.WHEEL_ROLL_BACKWARD -> { delay += 10 }
            UserFieldAction.SPACE -> { run = !run }
            UserFieldAction.UP -> {delay += 10 }
            UserFieldAction.DOWN -> {delay = if(delay - 10 >= 0) delay - 10 else 0 }
            UserFieldAction.ENTER -> {
                for (i in field.indices){
                    for (j in field[0].indices){
                        field[i][j] = false
                    }
                }
                run = false
                notifyField()
            }

            else -> { }
        }
    }

    override fun initUI() {
        Thread{
            notifyField()
            val tempField = Array(field.size) {Array(if(field.size > 0) field[0].size else 0){ false }}
            while (true){
                if(run) {
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


                            if (field[i][j]
                                && (count == 2
                                        || count == 3)) {
                                tempField[i][j] = true
                            }

                            else if (!field[i][j]
                                && count == 3) {
                                tempField[i][j] = true
                            }

                            else {
                                tempField[i][j] = false
                            }
                        }
                    }
                    for (i in field.indices) {
                        for (j in field[0].indices) {
                            field[i][j] = tempField[i][j]
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