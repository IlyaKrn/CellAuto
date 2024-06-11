package com.ilyakrn.cellauto.ui.views.logic

import com.ilyakrn.cellauto.logic.ColorRules
import com.ilyakrn.cellauto.ui.models.UserFieldAction
import javax.swing.JPanel

abstract class BaseLogicView<T>(initField: Array<Array<T>>) : JPanel(), ColorRules<T> {

    private var listener: (Array<Array<T>>) -> Unit = {}
    protected val field = initField
    protected fun notifyField() {
        listener(field)
    }
    fun setListener(listener: (Array<Array<T>>) -> Unit) {
        this.listener = listener
    }
    fun getH(): Int{
        return field.size
    }
    fun getW(): Int{
        return if(field.isEmpty()) 0 else field[0].size
    }

    abstract fun doAction(x: Int, y: Int, action: UserFieldAction)
    abstract fun initUI()

}