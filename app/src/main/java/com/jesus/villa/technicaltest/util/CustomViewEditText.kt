package com.jesus.villa.technicaltest.util

import android.content.Context
import android.text.InputType
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


/**
 * Created by Jesús Villa on 30/01/23
 */
class CustomViewEditText(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    EditText(context, attrs, defStyleAttr) {

    private val TAG = CustomViewEditText::class.java.simpleName
    var mText: SpannableStringBuilder? = null

    constructor(context: Context?) : this(context, null, 0) {}
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {}

    init {
        init()
    }

    private fun init() {
        setFocusableInTouchMode(true)
        mText = SpannableStringBuilder()

        // handle key presses not handled by the InputConnection
        setOnKeyListener(object : OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                Log.i(TAG,"setOnKeyListener - > keyCode:$keyCode - event:$event ")
                if (event.getAction() === KeyEvent.ACTION_DOWN) {
                    if (event.getUnicodeChar() === 0) { // control character
                        if (keyCode == KeyEvent.KEYCODE_DEL) {
                            mText!!.delete(mText!!.length - 1, mText!!.length)
                            Log.i(TAG, "text: $mText (keycode)")
                            return true
                        }
                        // TODO handle any other control keys here
                    } else { // text character
                        mText!!.append(event.getUnicodeChar() as Char)
                        Log.i(TAG, "text: $mText (keycode)")
                        return true
                    }
                }
                return false
            }
        })
    }

    // toggle whether the keyboard is showing when the view is clicked
    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i(TAG,"onTouchEvent -> event:${event.action}")
        if (event.action == MotionEvent.ACTION_UP) {
            val imm: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(
                InputMethodManager.SHOW_IMPLICIT,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }
        return true
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        outAttrs.inputType = InputType.TYPE_CLASS_NUMBER
        // outAttrs.inputType = InputType.TYPE_CLASS_TEXT; // alternate (show number pad rather than text)
        return CustomInputConnection(this, true)
    }
}