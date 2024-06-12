package com.ilyakrn.cellauto.ui.views.logic.base

import java.awt.Color

interface ColorRules<T> {
    fun getColor(value: T): Color
}
