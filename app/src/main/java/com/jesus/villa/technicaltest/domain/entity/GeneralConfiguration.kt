package com.jesus.villa.technicaltest.domain

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale.ROOT

/**
 * Created by Jesús Villa on 22/01/23
 */
object GeneralConfiguration {

    private val DEFAULT_MAX_FRACTION_DIGITS = 2

    var country: Country? = null
    var defaultDocumentsType: DocumentsType? = null
    var availableDocumentsType: List<DocumentsType> = mutableListOf()
    var availableLanguages: List<Language> = mutableListOf()
    var defaultCurrency: Currency? = null
    var availableCurrencies: List<Currency> = mutableListOf()

    /**
     * Returns the format of the amounts configured, according to the currency
     * @param: Currency to be formatted. Optional, if not defined, takes the default currency
     *
     * @return: java.text.NumberFormat
     */
    fun getCurrencyFormat(current: Currency? = null): NumberFormat {
        var format = NumberFormat.getInstance()

        defaultCurrency?.let {
            format =  setFormat(it)
        }
        current?.let {
            format = setFormat(it)

        }
        return format
    }

    private fun setFormat(cur: Currency): DecimalFormat {
        val otherSymbols = DecimalFormatSymbols(ROOT)
        otherSymbols.setDecimalSeparator(cur.decimalSeparator)
        otherSymbols.setGroupingSeparator(cur.groupingSeparator)
        val formatSring ="#,##0.00"
        val df = DecimalFormat(formatSring, otherSymbols)
        df.maximumFractionDigits = cur.defaultFractionDigits
        df.minimumFractionDigits = cur.defaultFractionDigits
        df.currency = java.util.Currency.getInstance(cur.code)
        return df

    }
}

data class Language (
    var code: String,
    var description: String = ""
)