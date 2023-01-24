package com.jesus.villa.technicaltest.util

import com.jesus.villa.technicaltest.domain.Country
import java.lang.Exception


/**
 * Created by Jesús Villa on 22/01/23
 */
interface ValidatorDocument {
    val MIN_LENGTH: Int
    val MAX_LENGTH: Int

    open fun validate(documentId: String): Boolean {
        val pattern = "[0-9]{$MIN_LENGTH,$MAX_LENGTH}"
        var dni: String = documentId
        if (dni.isNullOrEmpty()) return false
        dni = dni.trim()
        dni = dni.replace(".", "")
        dni = dni.replace("-", "")

        val numberPattern = Regex(pattern)
        return numberPattern.matches(dni)
    }
}

class ValidateRUT : ValidatorDocument {

    override val MIN_LENGTH = 8
    override val MAX_LENGTH = 9
    companion object {
        private val patternRUT = "[0-9]"
        private val patternVC = "[0-9[k[K]]]{1}"
    }

    override fun validate(documentId: String): Boolean {
        try {
            var rutParam: String = documentId
            if (!rutParam.isNullOrEmpty()) {
                rutParam = rutParam.trim()
                rutParam = rutParam.replace(".", "")
                rutParam = rutParam.replace("-", "")
                while (rutParam.get(0) == '0') rutParam = rutParam.substring(1..rutParam.length - 1)
                if (!(rutParam.length < MIN_LENGTH || rutParam.length > MAX_LENGTH)) {
                    val verificationCode =
                        rutParam.substring(rutParam.length - 1, rutParam.length).toUpperCase()
                    rutParam = rutParam.substring(0, rutParam.length - 1).reversed()
                    val numberPattern = Regex(patternRUT)
                    val vcPattern = Regex(patternVC)
                    if ((numberPattern.containsMatchIn(rutParam)) && (vcPattern.containsMatchIn(
                            verificationCode
                        ))
                    ) {
                        var suma = 0
                        var j = 2
                        for (n in 0 until rutParam.length) {
                            var number = Integer.parseInt(rutParam[n].toString())
                            if (j > 7) j = 2
                            suma += number * j
                            j++
                        }
                        val digit = 11 - (suma % 11)
                        val result = when (digit) {
                            11 -> "0"
                            10 -> "K"
                            else -> digit.toString()
                        }
                        if (!verificationCode.isNullOrEmpty() && !result.isNullOrEmpty() && verificationCode.toUpperCase() == result) {
                            return true
                        }
                    }
                }
            }
            return false
        }catch (e: Exception){
            return false
        }
    }
}

class ValidateDNI : ValidatorDocument {
    override val MAX_LENGTH = 8
    override val MIN_LENGTH = 1
}

class ValidateCC : ValidatorDocument {
    override val MIN_LENGTH = 4
    override val MAX_LENGTH = 10
}

class ValidateCE : ValidatorDocument {
    override val MIN_LENGTH = 4
    override val MAX_LENGTH = 7
}

abstract class ValidatorPhoneNumber {
    open val patternOneNumber: String = "^[1-9]{1}$"
    open val patternEightNumber: String = "^[0-9]{8}$"
    open val phoneLengthNumber: Int = 11
    open val firstPhoneNumber: String = "3"

    open fun validate(phone: String): Boolean {
        val newNumber = ValidationLayer.removeCharacter(phone)
        if(newNumber.length == phoneLengthNumber){
            val countryNumber = newNumber.substring(0,2)
            val areaNumber = newNumber.substring(2,3)
            val phoneNumber = newNumber.substring(3,newNumber.length)
            return (validateCountryNumber(countryNumber) &&
                    validateAreaNumber(areaNumber) &&
                    areaNumber == firstPhoneNumber &&
                    validatePhoneNumber(phoneNumber))
        }else{
            return false
        }

    }

    abstract fun validateCountryNumber(prefix:String):Boolean

    open fun validateAreaNumber(area:String):Boolean{
        val areaPattern = Regex(patternOneNumber, RegexOption.IGNORE_CASE)
        return areaPattern.containsMatchIn(area)
    }

    open fun validatePhoneNumber(phone: String):Boolean {
        val phonePattern = Regex(patternEightNumber)
        return phonePattern.containsMatchIn(phone)
    }
}

class ValidatePhonePeru() : ValidatorPhoneNumber() {

    private val patternTwoNumber = "^[0-9]{2}$"
    override val firstPhoneNumber: String = "9"

    override fun validateCountryNumber(prefix:String):Boolean{
        val countryPattern = Regex(patternTwoNumber, RegexOption.IGNORE_CASE)

        return if(countryPattern.containsMatchIn(prefix)){
            Country.PERU.prefixPhone.toString().contentEquals(prefix)
        }else
            false
    }
}

class ValidatePhoneChile() : ValidatorPhoneNumber() {

    private val patternTwoNumber = "^[0-9]{2}$"
    override val firstPhoneNumber: String = "9"


    override fun validateCountryNumber(prefix:String):Boolean{
        val countryPattern = Regex(patternTwoNumber, RegexOption.IGNORE_CASE)

        return if(countryPattern.containsMatchIn(prefix)){
            Country.CHILE.prefixPhone.toString().contentEquals(prefix)
        }else
            false
    }
}

class ValidatePhoneColombia(): ValidatorPhoneNumber(){

    private val patternTwoNumber = "^[0-9]{2}$"
    override val patternEightNumber: String = "^[0-9]{9}$"
    override val phoneLengthNumber: Int = 12
    override val firstPhoneNumber: String = "3"

    override fun validateCountryNumber(prefix:String):Boolean{
        val countryPattern = Regex(patternTwoNumber, RegexOption.IGNORE_CASE)

        return if(countryPattern.containsMatchIn(prefix)){
            Country.COLOMBIA.prefixPhone.toString().contentEquals(prefix)
        }else
            false
    }
}

class ValidateByMask(val mask: String) : ValidatorPhoneNumber() {

    override fun validate(phoneNumber: String): Boolean {
        var phoneParam: String = phoneNumber
        if (phoneParam.isNullOrEmpty()) return false

        phoneParam = phoneParam.replace(".", "")
        phoneParam = phoneParam.replace("-", "")

        val numberPattern = Regex(mask)
        return numberPattern.matches(phoneParam)

    }

    override fun validateAreaNumber(area: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun validateCountryNumber(prefix: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun validatePhoneNumber(phone: String): Boolean {
        TODO("Not yet implemented")
    }
}