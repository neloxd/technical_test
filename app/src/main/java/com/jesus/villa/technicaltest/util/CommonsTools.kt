package com.jesus.villa.technicaltest.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.provider.Settings
import android.util.Log
import java.util.*


/**
 * Created by Jesús Villa on 22/01/23
 */
val longitudDeClave = 6

fun validatePassword(password: String, documentId: String = "", phone: String = ""): Boolean {

    try {
        checkPassword(password, documentId, phone)
    } catch (ex: IllegalStateException) {
        return false
    }
    return true
}

fun checkPassword(password: String, documentId: String = "", phone: String = "", date: Date = Date()) {
    check(password.isNotEmpty()) { "La clave no puede estar vacia" }
    check(password.matches(Regex("\\d+(\\.\\d+)?"))) { "La clave debe ser numerica" }
    check(!validateConsecutiveNumbers(password)) { "La clave no debe contener dígitos consecutivos" }
    check(validateMaxOccurrences(password, 4)) { "La clave no debe contener 4 o más número iguales" }
    check(password.length == longitudDeClave) { "La clave debe contener $longitudDeClave digitos" }
    check(!documentId.contains(password)) { "La clave no puede contener datos del documento" }
    check(!phone.contains(password)) { "La clave no puede contener datos del número de teléfono o parte de él" }
    check(validateWithDate(password, date)) { "La clave no puede contener fecha de cumpleaños" }
}

fun validateConsecutiveNumbers(cadena: String): Boolean {
    if (cadena.length <= 1) return true

    return (cadena.get(0) == (cadena.get(1)-1) ||
            (cadena.get(0) == '9' && cadena.get(1) == '0')) &&
            validateConsecutiveNumbers(cadena.substring(1))
}

fun validateMaxOccurrences(words: String, max: Int) : Boolean {

    var supero = false
    for(el in words.toSet()) {
        val cont = words.toList().count { it == el }
        supero = supero || cont >= max
    }

    return !supero
}

fun validateWithDate(password: String, date: Date) : Boolean {
    return true
}

private val UNIQUE_ID = "key_unique_id"

fun uniqueID(context: Context): String {

    return registerUniquedID(context, UUID.randomUUID().toString())
}

fun registerUniquedID(context: Context, uuid: String): String {
    val sharedPref = context.getSharedPreferences(
        getDefaultSharedPreferencesName(context),
        getDefaultSharedPreferencesMode()
    )
    sharedPref.getString(UNIQUE_ID, null)?.let { return it }
    with (sharedPref.edit()) {
        val uniqueID: String = uuid
        putString(UNIQUE_ID, uniqueID)
        commit()
        return uniqueID
    }
}

fun deviceID(): String {
    return Settings.Secure.ANDROID_ID
}

fun getDefaultSharedPreferencesName(context: Context): String {
    return context.packageName + "_preferences"
}

fun getDefaultSharedPreferencesMode(): Int {
    return Context.MODE_PRIVATE
}

fun String.getTwoCharacteres(): String {
    val tokenizer = StringTokenizer(this)
    Log.i("String-TAG"," tokenizer:${tokenizer.countTokens()}")
    var first: String? = null
    var last: String? = null

    var index = 0
    var size = tokenizer.countTokens() - 1
    while(tokenizer.hasMoreTokens()) {
        val str = tokenizer.nextToken()
        if(index == 0)
            first = str.first().toString()

        if(index == size)
            last = str.first().toString()
        ++index;
    }

    return ("$first$last")
}

fun Context.copyText(text: String) {
    val clipboard: ClipboardManager =
        this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip: ClipData = ClipData.newPlainText("Goiar", text)
    clipboard.setPrimaryClip(clip)
}
