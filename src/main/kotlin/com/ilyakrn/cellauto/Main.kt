package com.ilyakrn.com.ilyakrn.ui.logic

import com.ilyakrn.cellauto.ui.SelectWindow
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.JFrame
import javax.swing.JTextField
import kotlin.system.exitProcess


fun main() {
     try{
          val main = SelectWindow()
          main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          main.show()
     }catch (e: Exception){
          val err = JFrame("ERROR")
          err.add(JTextField(e.message))
          err.show()
          err.isVisible = true
          err.pack()
     }
}