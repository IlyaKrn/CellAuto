package com.ilyakrn.cellauto.ui.views

import com.ilyakrn.cellauto.logic.ColorRules
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import javax.swing.JPanel


class FieldView<T>(fHeight: Int, fWidth: Int, private val cellSize: Int, private val colorRules: ColorRules<T>) : JPanel() {


    private var canvas: BufferedImage = BufferedImage(fWidth * cellSize, fHeight * cellSize, BufferedImage.TYPE_INT_ARGB)

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        g2.drawImage(canvas, null, null)
    }

    private fun drawRect(c: Color, x1: Int, y1: Int, width: Int, height: Int) {
        val color = c.rgb
        for (x in x1 until x1 + width) {
            for (y in y1 until y1 + height) {
                canvas.setRGB(x, y, color)
            }
        }
        repaint()
    }

    fun update(field: Array<Array<T>>){
        for (i in field.indices) {
            for (j in 0..<field[0].size) {
                println(field[i][j])
                drawRect(colorRules.getColor(field[i][j]), i * cellSize, j * cellSize, cellSize, cellSize)
            }
        }
    }

}