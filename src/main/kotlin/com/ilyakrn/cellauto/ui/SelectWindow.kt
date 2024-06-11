package com.ilyakrn.cellauto.ui

import com.ilyakrn.cellauto.ui.views.logic.LifeGameLogicView
import com.ilyakrn.cellauto.ui.views.logic.ShadowLogicView
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

        val textFieldWidth = 400
        val textFieldHeight = 40
        val textSize = 30

        minimumSize = Dimension(textFieldWidth, textFieldHeight * 6)

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

        val shadowButton = JButton("shadow")
        shadowButton.setBounds(0, textFieldHeight * 3, textFieldWidth, textFieldHeight)
        shadowButton.addActionListener {
            try{
                FieldWindow(cellSizeText.text.toInt(), "${heightText.text}x${widthText.text} (${cellSizeText.text})", ShadowLogicView(heightText.text.toInt(), widthText.text.toInt())).show()
            } catch (e: Exception){
                e.printStackTrace()
                throw RuntimeException("incorrect input")
            }
        }
        add(shadowButton)

        val lifeGameButton = JButton("life game")
        lifeGameButton.setBounds(0, textFieldHeight * 4, textFieldWidth, textFieldHeight)
        lifeGameButton.addActionListener {
            try{
                FieldWindow(cellSizeText.text.toInt(), "${heightText.text}x${widthText.text} (${cellSizeText.text})", LifeGameLogicView(heightText.text.toInt(), widthText.text.toInt())).show()
            } catch (e: Exception){
                e.printStackTrace()
                throw RuntimeException("incorrect input")
            }
        }
        add(lifeGameButton)

        pack()

    }
}