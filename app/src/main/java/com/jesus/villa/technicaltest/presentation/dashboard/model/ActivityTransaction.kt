package com.jesus.villa.technicaltest.presentation.dashboard.model


/**
 * Created by Jesús Villa on 30/01/23
 */
data class ActivityTransaction(val id: Int, val income: Boolean = false,
                               val title: String, val detail: String,
                               val amount:String, val date: String)
