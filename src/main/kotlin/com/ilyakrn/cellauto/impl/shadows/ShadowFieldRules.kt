package com.ilyakrn.cellauto.impl.shadows

import com.ilyakrn.cellauto.logic.FieldRules
import com.ilyakrn.cellauto.logic.Param

class ShadowFieldRules : FieldRules<Int> {
    private val params = arrayOf<Param>()
    private val listeners = hashMapOf<String, ArrayList<(Param) -> Unit>>()

    override fun updateField(field: Array<Array<Int>>) {
        for (i in 0..field.size-1){
            for (j in 0..field[0].size-1){
                var sum = 0
                var count = 0
                for(li in -1..1){
                    for(lj in -1..1){
                        try{
                            sum += field[i+lj][j+li]
                            count++
                        }catch (e: Exception){}
                    }
                }
                field[i][j] = sum/count
            }
        }
    }

    override fun getParams(): Array<Param> {
        return params
    }

    override fun getParam(key: String): Param? {
        try{
            return params.filter { it.key == key }[0]
        } catch (e: Exception){
            return null
        }
    }

    override fun setParam(param: Param) {
        for (i in params.indices){
            if (params[i].key == param.key){
                params[i] = param
                listeners[param.key]?.forEach { it.invoke(param) }
                break
            }
        }
    }

    override fun subscribeOnParams(key: String, callback: (Param) -> Unit) {
        listeners[key]?.add(callback)
        if (listeners[key] != null)
            listeners[key]!!.add(callback)
        else
            listeners[key] = arrayListOf(callback)
    }

}