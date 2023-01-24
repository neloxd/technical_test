package com.jesus.villa.technicaltest.domain

import com.jesus.villa.technicaltest.util.*


/**
 * Created by Jesús Villa on 22/01/23
 */
/**
 *
 * Document classes according to the country. The country code is used ISO 3166-1.
 *
 * @param code: Code that identifies the type of document
 * @param countryCode: Country code according to The country code is used ISO 3166-1, the nomenclature Alpha-3 is used
 * @param validator: Class responsible for executing document validation
 */
enum class DocumentsType (val code: String, val countryCode: String, val prefixPhone: Int, val validator: ValidatorDocument, val documentType: String) {
    RUT("RUT", "CHL", 56, ValidateRUT(),"01"),
    DNI_PE("DNI", "PER", 51, ValidateDNI(), "01"),
    CC("CC", "COL", 57, ValidateCC(),"01"),
    CE("CE", "COL", 57, ValidateCE(),"03")
    // ARGENTINA("DNI", "ARG", 54, ValidateDNI()),
}

/**
 *
 * The country code is used ISO 3166-1 and the global telephone prefixes.
 *
 * @param iso3: Code that identifies the country in ISO 3166-1 alpha 3
 * @param iso2: Code that identifies the country in ISO 3166-1 alpha 2
 * @param prefixPhone: Worldwide telephone prefixes
 * @param validationPhone
 */
enum class Country(val iso3: String, val iso2: String, val prefixPhone: Int, val validationPhone: ValidatorPhoneNumber) {
    CHILE("CHL", "CL", 56, ValidatePhoneChile()),
    PERU("PER", "PE", 51, ValidatePhonePeru()),
    COLOMBIA ("COL", "CO", 57, ValidatePhoneColombia())
}

/**
 * The currency code is used ISO 4217.
 *
 * @param code: The currency code is used ISO 4217.
 * @param numericCode: The currency code is used ISO 4217.
 * @param defaultFractionDigits: Number of digits used for fractions
 * @param symbol: Symbol used for currency
 */
enum class Currency(val code: String, val numericCode: Int, val defaultFractionDigits: Int, val symbol: String,
                    val decimalSeparator: Char = ',', val groupingSeparator: Char = '.') {
    CLP("CLP", 152, 0, "$"),
    PEN("PEN", 604, 2, "S/", '.', ','),
    ARS("ARS", 32, 2, "$"),
    USD("USD", 840, 2, "$"),
    COP("COP", 170, 2, "$")
}