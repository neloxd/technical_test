package com.jesus.villa.technicaltest.domain.entity

import android.content.Context


/**
 * Created by Jesús Villa on 22/01/23
 */
class Core private constructor() {

    lateinit var context: Context

    companion object {

        val TAG = "Core"
        val instance by lazy {
            Core()
        }

    }

    fun init(context: Context) {
        this.context = context
    }
}