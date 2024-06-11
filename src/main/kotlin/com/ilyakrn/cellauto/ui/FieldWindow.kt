package com.ilyakrn.cellauto.ui

import com.ilyakrn.cellauto.ui.models.UserFieldAction
import com.ilyakrn.cellauto.ui.views.FieldView
import com.ilyakrn.cellauto.ui.views.logic.BaseLogicView
import java.awt.Dimension
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JFrame


class FieldWindow<T>(private val cellSize: Int, title: String, private val logicView: BaseLogicView<T>) : JFrame(title) {

    private val fieldView = FieldView(logicView.getH(), logicView.getW(), cellSize, logicView)

    private fun initLayout(){
        layout = null
        logicView.initUI()
        logicView.setBounds(logicView.getW() * cellSize, 0, logicView.size.width, logicView.size.height)
        fieldView.setBounds(0, 0, logicView.getW() * cellSize, logicView.getH() * cellSize)
        fieldView.isVisible = true
        fieldView
        add(fieldView)
        add(logicView)
        minimumSize = Dimension(logicView.getW() * cellSize + logicView.size.width, logicView.getH() * cellSize)
        fieldView.addMouseListener(object : MouseListener{
            override fun mouseClicked(e: MouseEvent) {}
            override fun mouseReleased(e: MouseEvent) {}
            override fun mouseEntered(e: MouseEvent) {}
            override fun mouseExited(e: MouseEvent) {}
            override fun mousePressed(e: MouseEvent) {
                if(e.x > fieldView.width || e.y > fieldView.height)
                    return
                val x = e.x/cellSize
                val y = e.y/cellSize
                when (e.button) {
                    1 -> { logicView.doAction(x, y, UserFieldAction.LEFT_CLICK) }
                    2 -> { logicView.doAction(x, y, UserFieldAction.WHEEL_CLICK) }
                    3 -> { logicView.doAction(x, y, UserFieldAction.RIGHT_CLICK) }
                }
            }
        })
        fieldView.addMouseWheelListener { e ->
            logicView.doAction(x, y, if (e.wheelRotation < 0) UserFieldAction.WHEEL_ROLL_FORWARD else UserFieldAction.WHEEL_ROLL_BACKWARD)
        }
        pack()

    }


    override fun show(){
        super.show()
        initLayout()
        logicView.setListener {
            fieldView.update(it)
        }
    }
}