package com.ilyakrn.cellauto.logic

import java.awt.Color

interface ColorRules<T> {
    fun getColor(value: T): Color
}
