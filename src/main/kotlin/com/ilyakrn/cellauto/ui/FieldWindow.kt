package com.ilyakrn.cellauto.ui

import com.ilyakrn.cellauto.ui.models.UserFieldAction
import com.ilyakrn.cellauto.ui.views.FieldView
import com.ilyakrn.cellauto.ui.views.logic.base.BaseLogicView
import java.awt.Dimension
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
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
        addKeyListener(object : KeyListener {
            override fun keyTyped(e: KeyEvent) {}
            override fun keyReleased(e: KeyEvent) {}
            override fun keyPressed(e: KeyEvent) {
                when(e.keyCode){
                    KeyEvent.VK_A -> logicView.doAction(x, y, UserFieldAction.A)
                    KeyEvent.VK_B -> logicView.doAction(x, y, UserFieldAction.B)
                    KeyEvent.VK_C -> logicView.doAction(x, y, UserFieldAction.C)
                    KeyEvent.VK_D -> logicView.doAction(x, y, UserFieldAction.D)
                    KeyEvent.VK_E -> logicView.doAction(x, y, UserFieldAction.E)
                    KeyEvent.VK_F -> logicView.doAction(x, y, UserFieldAction.F)
                    KeyEvent.VK_G -> logicView.doAction(x, y, UserFieldAction.G)
                    KeyEvent.VK_H -> logicView.doAction(x, y, UserFieldAction.H)
                    KeyEvent.VK_I -> logicView.doAction(x, y, UserFieldAction.I)
                    KeyEvent.VK_J -> logicView.doAction(x, y, UserFieldAction.J)
                    KeyEvent.VK_K -> logicView.doAction(x, y, UserFieldAction.K)
                    KeyEvent.VK_L -> logicView.doAction(x, y, UserFieldAction.L)
                    KeyEvent.VK_M -> logicView.doAction(x, y, UserFieldAction.M)
                    KeyEvent.VK_N -> logicView.doAction(x, y, UserFieldAction.N)
                    KeyEvent.VK_O -> logicView.doAction(x, y, UserFieldAction.O)
                    KeyEvent.VK_P -> logicView.doAction(x, y, UserFieldAction.P)
                    KeyEvent.VK_Q -> logicView.doAction(x, y, UserFieldAction.Q)
                    KeyEvent.VK_R -> logicView.doAction(x, y, UserFieldAction.R)
                    KeyEvent.VK_S -> logicView.doAction(x, y, UserFieldAction.S)
                    KeyEvent.VK_T -> logicView.doAction(x, y, UserFieldAction.T)
                    KeyEvent.VK_U -> logicView.doAction(x, y, UserFieldAction.U)
                    KeyEvent.VK_V -> logicView.doAction(x, y, UserFieldAction.V)
                    KeyEvent.VK_W -> logicView.doAction(x, y, UserFieldAction.W)
                    KeyEvent.VK_X -> logicView.doAction(x, y, UserFieldAction.X)
                    KeyEvent.VK_Y -> logicView.doAction(x, y, UserFieldAction.Y)
                    KeyEvent.VK_Z -> logicView.doAction(x, y, UserFieldAction.Z)

                    KeyEvent.VK_SPACE -> logicView.doAction(x, y, UserFieldAction.SPACE)
                    KeyEvent.VK_ENTER -> logicView.doAction(x, y, UserFieldAction.ENTER)
                    KeyEvent.VK_SHIFT -> logicView.doAction(x, y, UserFieldAction.SHIFT)
                    KeyEvent.VK_CONTROL -> logicView.doAction(x, y, UserFieldAction.CONTROL)
                    KeyEvent.VK_ALT -> logicView.doAction(x, y, UserFieldAction.ALT)
                    KeyEvent.VK_DELETE -> logicView.doAction(x, y, UserFieldAction.DELETE)

                    KeyEvent.VK_UP -> logicView.doAction(x, y, UserFieldAction.UP)
                    KeyEvent.VK_DOWN -> logicView.doAction(x, y, UserFieldAction.DOWN)
                    KeyEvent.VK_RIGHT -> logicView.doAction(x, y, UserFieldAction.RIGHT)
                    KeyEvent.VK_LEFT -> logicView.doAction(x, y, UserFieldAction.LEFT)
                    else -> {}
                }
            }
        })
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