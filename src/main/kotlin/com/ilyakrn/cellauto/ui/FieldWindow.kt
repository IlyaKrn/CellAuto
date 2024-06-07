package com.ilyakrn.cellauto.ui

import com.ilyakrn.cellauto.logic.ColorRules
import com.ilyakrn.cellauto.logic.FieldRules
import com.ilyakrn.cellauto.logic.Param
import java.awt.Dimension
import java.awt.Font
import java.awt.GridLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JTextField


class FieldWindow(private val h: Int, private val w: Int, private val cellSize: Int, title: String, private val fieldRules: FieldRules<Int>, private val colorRules: ColorRules<Int>) : JFrame(title) {

    private val field = Array(w) { Array(h) {0} }
        @Synchronized get

    private val fieldView = FieldView(h, w, cellSize, colorRules)
    private val clickPowerText = JTextField("no")
    private val stepsPerSecondText = JTextField("no")
    private val selectedCellValueText = JTextField("no")
    private val selectedCellXText = JTextField("x: 0")
    private val selectedCellYText = JTextField("y: 0")

    private var clickPower = 10000
    private var delay = 20

    private object selectedCell{
        var x: Int = 0
            @Synchronized get
        var y: Int = 0
            @Synchronized get
    }

    private val lastStepsPerSecond = arrayListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)


    private fun initLayout(){
        val textFieldWidth = 300
        val textFieldHeight = 20
        val textSize = 15
        layout = null
        minimumSize = Dimension(w * cellSize + textFieldWidth, h * cellSize)
        add(fieldView)
        fieldView.size = Dimension(w * cellSize, h * cellSize)
        fieldView.addMouseListener(object : MouseListener{
            override fun mouseClicked(e: MouseEvent?) {}
            override fun mouseReleased(e: MouseEvent?) {}
            override fun mouseEntered(e: MouseEvent?) {}
            override fun mouseExited(e: MouseEvent?) {}
            override fun mousePressed(e: MouseEvent?) {
                if(e!!.x > fieldView.width || e.y > fieldView.height)
                    return
                val x = e.x/cellSize
                val y = e.y/cellSize
                when (e.button) {
                    1 -> { field[x][y] += clickPower }
                    2 -> {
                        selectedCell.x = x
                        selectedCell.y = y
                        selectedCellXText.text = "x: ${x}"
                        selectedCellYText.text = "y: ${y}"
                        selectedCellValueText.text = field[x][y].toString()
                    }
                    3 -> { field[x][y] -= clickPower }
                }
            }
        })
        fieldView.addMouseWheelListener(object : MouseWheelListener{
            override fun mouseWheelMoved(e: MouseWheelEvent?) {
                delay = if(delay + e!!.wheelRotation < 0) 0 else delay + e.wheelRotation
            }
        })

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
        createTextField("click power: ", clickPowerText, w * cellSize, textFieldHeight * 0)
        clickPowerText.text = clickPower.toString()
        clickPowerText.addActionListener {
            try {
                clickPower = clickPowerText.text.toInt()
            } catch (nfe: NumberFormatException) {
                clickPowerText.text = clickPower.toString()
            }
        }
        createTextField("steps per second: ", stepsPerSecondText, w * cellSize, textFieldHeight * 1)
        stepsPerSecondText.isEditable = false

        var count = 2
        for (param in fieldRules.getParams()){
            val editableTextField = JTextField(param.value)
            editableTextField.addActionListener {
                try {
                    fieldRules.setParam(Param(param.key, editableTextField.text.toInt(), param.editable))
                } catch (nfe: NumberFormatException) {
                    editableTextField.text = fieldRules.getParam(param.key)?.value.toString()
                }
            }
            fieldRules.subscribeOnParams(param.key) { par ->
                editableTextField.text = par.value.toString()
                editableTextField.isEditable = param.editable
            }
            editableTextField.text = param.value.toString()
            editableTextField.isEditable = param.editable
            createTextField(param.key, editableTextField, w * cellSize, textFieldHeight * count)
            count++
        }

        val selectedCellPanel = JPanel(GridLayout(1, 3))
        selectedCellXText.text = ""
        selectedCellYText.text = ""
        selectedCellValueText.text = ""

        selectedCellXText.isEditable = false
        selectedCellYText.isEditable = false
        selectedCellValueText.isEditable = false

        selectedCellXText.font = Font(font.family, Font.BOLD, textSize)
        selectedCellYText.font = Font(font.family, Font.BOLD, textSize)
        selectedCellValueText.font = Font(font.family, font.style, textSize)

        selectedCellPanel.add(selectedCellXText)
        selectedCellPanel.add(selectedCellYText)
        selectedCellPanel.add(selectedCellValueText)
        selectedCellPanel.setBounds(w * cellSize, textFieldHeight * count, textFieldWidth, textFieldHeight)
        add(selectedCellPanel)

        pack()

    }


    override fun show(){
        super.show()
        initLayout()
        fieldView.update(field)
        Thread {
            while (true){
                val start = System.currentTimeMillis()
                Thread.sleep(delay.toLong())
                fieldRules.updateField(field)
                selectedCellValueText.text = field[selectedCell.x][selectedCell.y].toString()
                fieldView.update(field)
                lastStepsPerSecond.removeAt(0)
                lastStepsPerSecond.add((1000 / (if(System.currentTimeMillis() - start == 0L) 1 else System.currentTimeMillis() - start)).toInt())
                var sumLasts = 0
                lastStepsPerSecond.forEach {
                    sumLasts += it
                }
                stepsPerSecondText.text = (sumLasts/10).toString()
            }
        }.start()

    }
}