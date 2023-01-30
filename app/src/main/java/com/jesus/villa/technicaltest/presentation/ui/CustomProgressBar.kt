package com.jesus.villa.technicaltest.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.Nullable
import com.jesus.villa.technicaltest.R


/**
 * Created by Jesús Villa on 30/01/23
 */
class CustomProgressBar : FrameLayout {

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(
        context: Context,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        this.init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.custom_progress_bar_layout, this)
    }

    fun getProgressBar(): CustomProgressBar {
        return this
    }

    fun show(){
        this.getProgressBar().visibility = View.VISIBLE
    }

    fun hide(){
        this.getProgressBar().visibility = View.GONE
    }
}