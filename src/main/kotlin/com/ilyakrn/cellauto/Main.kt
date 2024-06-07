package com.ilyakrn.com.ilyakrn.ui.logic

import com.ilyakrn.cellauto.ui.SelectWindow
import javax.swing.JFrame
import javax.swing.JTextField


fun main() {
     try{
          SelectWindow().show()
     }catch (e: Exception){
          val err = JFrame("ERROR")
          err.add(JTextField(e.message))
          err.show()
          err.isVisible = true
          err.pack()
     }
}