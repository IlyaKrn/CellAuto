package com.ilyakrn.cellauto.ui

import com.ilyakrn.cellauto.ui.models.UserFieldAction
import com.ilyakrn.cellauto.ui.views.logic.BaseLogicView
import com.ilyakrn.cellauto.ui.views.logic.ShadowLogicView
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JTextField

class SelectWindow : JFrame("Cell Auto") {

    private val heightText = JTextField("100")
    private val widthText = JTextField("100")
    private val cellSizeText = JTextField("10")

    override fun show() {
        super.show()
        layout = null

        val textFieldWidth = 200
        val textFieldHeight = 20
        val textSize = 15

        minimumSize = Dimension(textFieldWidth, 120)

        fun createTextField(title: String, textField: JTextField, x: Int, y: Int){
            val panel = JPanel(GridLayout(1, 2))
            val title = JTextField(title)
            title.isEditable = false
            title.font = Font(font.family, Font.BOLD, textSize)
            textField.font = Font(font.family, font.style, textSize)
            panel.add(title)
            panel.add(textField)
            panel.setBounds(x, y, textFieldWidth, textFieldHeight)
            add(panel)
        }

        createTextField("select height: ", heightText, 0, textFieldHeight * 0)
        createTextField("select width: ", widthText, 0, textFieldHeight * 1)
        createTextField("select cell size: ", cellSizeText, 0, textFieldHeight * 2)

        val confirmButton = JButton("run")
        confirmButton.setBounds(0, textFieldHeight * 3, textFieldWidth, textFieldHeight)
        confirmButton.addActionListener {
            try{
                FieldWindow(cellSizeText.text.toInt(), "${heightText.text}x${widthText.text} (${cellSizeText.text})", ShadowLogicView(heightText.text.toInt(), widthText.text.toInt())).show()
            } catch (e: Exception){
                e.printStackTrace()
                throw RuntimeException("incorrect input")
            }
        }
        add(confirmButton)
        pack()

    }
}