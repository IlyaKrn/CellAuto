package com.ilyakrn.cellauto.impl.shadows

import com.ilyakrn.cellauto.logic.ColorRules
import com.ilyakrn.cellauto.logic.FieldRules
import com.ilyakrn.cellauto.logic.Param
import java.awt.Color

class ShadowColorRules : ColorRules<Int> {
    override fun getColor(value: Int): Color {
        val c = if(value in 0..255) value else (if(value < 0) 0 else 255)
        return Color(c, c, c)
    }
}