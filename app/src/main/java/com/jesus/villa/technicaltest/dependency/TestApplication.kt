package com.jesus.villa.technicaltest.dependency

import android.app.Application
import com.jesus.villa.technicaltest.domain.DocumentsType
import com.jesus.villa.technicaltest.domain.GeneralConfiguration
import com.jesus.villa.technicaltest.BuildConfig
import com.jesus.villa.technicaltest.domain.Country
import com.jesus.villa.technicaltest.domain.Currency


/**
 * Created by Jesús Villa on 22/01/23
 */
class TestApplication : Application() {

    companion object {
        val TAG = this::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        initBuildConfig()
    }


    private fun initBuildConfig() {

        GeneralConfiguration.defaultDocumentsType = when (BuildConfig.DOCUMENT_TYPE) {
            "RUT" -> DocumentsType.RUT
            "DNI" -> DocumentsType.DNI_PE
            "CC" -> DocumentsType.CC
            else -> DocumentsType.RUT
        }

        if (BuildConfig.COUNTRY_CODE == "CO") {
            GeneralConfiguration.availableDocumentsType = listOf(DocumentsType.CC, DocumentsType.CE)
        } else
            GeneralConfiguration.availableDocumentsType =
                listOf(GeneralConfiguration.defaultDocumentsType!!)

        GeneralConfiguration.defaultCurrency = Currency.valueOf(BuildConfig.CURRENCY_CODE)
        GeneralConfiguration.availableCurrencies = listOf(GeneralConfiguration.defaultCurrency!!)
        GeneralConfiguration.country = when (BuildConfig.COUNTRY_CODE) {
            "CL" -> Country.CHILE
            "PE" -> Country.PERU
            "CO" -> Country.COLOMBIA
            else -> Country.CHILE
        }

    }
}