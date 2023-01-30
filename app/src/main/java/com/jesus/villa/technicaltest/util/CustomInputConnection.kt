package com.jesus.villa.technicaltest.util

import android.text.Editable

import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View

import android.view.inputmethod.BaseInputConnection





/**
 * Created by Jesús Villa on 30/01/23
 */
class CustomInputConnection internal constructor(targetView: View, fullEditor: Boolean) :
    BaseInputConnection(targetView, fullEditor) {
    private val TAG = CustomInputConnection::class.java.simpleName
    private val mEditable: SpannableStringBuilder?

    init {
        val customView: CustomViewEditText = targetView as CustomViewEditText
        mEditable = customView.mText!!
    }

    override fun getEditable(): Editable {
        return mEditable!!
    }

    // just adding this to show that text is being committed.
    override fun commitText(text: CharSequence, newCursorPosition: Int): Boolean {
        Log.i(TAG,"commitText -> text:$text - newCursorPosition:$newCursorPosition")
        val returnValue = super.commitText(text, newCursorPosition)
        Log.i(TAG, "text: $mEditable")
        return returnValue
    }
}