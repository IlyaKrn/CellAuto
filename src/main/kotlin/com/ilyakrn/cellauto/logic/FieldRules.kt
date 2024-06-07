package com.ilyakrn.cellauto.logic

interface FieldRules<T> {
    fun updateField(field: Array<Array<T>>)

    fun getParams(): Array<Param>
    fun getParam(key: String): Param?
    fun setParam(param: Param)
    fun subscribeOnParams(key: String, callback: (Param) -> Unit)
}
