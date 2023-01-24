package com.jesus.villa.technicaltest.util

import com.jesus.villa.technicaltest.domain.DocumentsType


/**
 * Created by Jesús Villa on 22/01/23
 */
class ValidationLayer {
    companion object {

        private val patternMail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
        private val patternName = "^[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ\\-]{2,30}$"
        private val patternTwoNumber = "^[0-9]{2}$"
        private val patternOneNumber = "^[1-9]{1}$"
        private val patternEightNumber = "^[0-9]{8}$"
        private val patternMessage = "^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚüÜÀ-ÿ\$%#()-_/&+*\\[\\]\\,\\.\\-]{0,70}$"

        fun validateRUT(rut: String) = ValidateRUT().validate(rut)

        fun validateMail(mail: String):Boolean {
            val mailPattern = Regex(patternMail, RegexOption.IGNORE_CASE)
            return mailPattern.containsMatchIn(mail)
        }

        fun validateNames(name: String):Boolean {
            var trimmedName:String = name.replace(" ", "")
            val namePattern = Regex(patternName, RegexOption.IGNORE_CASE)
            return (name.length <= 30) && (namePattern.containsMatchIn(trimmedName))
        }

        fun validateCountryNumber(prefix:String):Boolean{
            val countryPattern = Regex(patternTwoNumber, RegexOption.IGNORE_CASE)

            return if(countryPattern.containsMatchIn(prefix)){
                DocumentsType.values().any { it.prefixPhone.toString().contentEquals(prefix) }
            }else
                false
        }

        fun validateAreaNumber(area:String):Boolean{
            val areaPattern = Regex(patternOneNumber, RegexOption.IGNORE_CASE)
            return areaPattern.containsMatchIn(area)
        }

        fun validatePhoneNumber(phone: String):Boolean {
            val phonePattern = Regex(patternEightNumber)
            return phonePattern.containsMatchIn(phone)
        }

        fun validatePhone(number: String):Boolean{
            var newNumber = removeCharacter(number)
            if(newNumber.length == 11){
                var countryNumber = newNumber.substring(0,2)
                var areaNumber = newNumber.substring(2,3)
                var phoneNumber = newNumber.substring(3,newNumber.length)
                return (validateCountryNumber(countryNumber) &&
                        validateAreaNumber(areaNumber) &&
                        validatePhoneNumber(phoneNumber))
            }else{
                return false
            }

        }

        fun validateMessage(message: String):Boolean{
            var trimmed:String = message.replace(" ", "")
            trimmed = trimmed.replace("\n", "")
            var messagePattern = Regex(patternMessage, RegexOption.IGNORE_CASE)
            return messagePattern.containsMatchIn(trimmed)
        }

        fun removeCharacter(number: String):String {
            var charsToRemove:Regex = Regex("[\\(\\)\\+\\-\\*#@]")
            var newNumber = charsToRemove.replace(number.replace(" ", ""), "")
            return newNumber
        }

        fun validatePhone(countryNumber: String, areaNumber: String, phoneNumber: String, fullNumber: String):Boolean{
            val concatNumber = countryNumber + areaNumber + phoneNumber
            return (validateCountryNumber(countryNumber) &&
                    validateAreaNumber(areaNumber) &&
                    validatePhoneNumber(phoneNumber) &&
                    concatNumber == fullNumber)
        }

        fun validateStrongPassword(password: String, documentId: String = "", phone: String = ""): Boolean {
            return validatePassword(password, documentId, phone)
        }

        fun validateDocument(documentType: String, documentId: String): Boolean {
            val docType = DocumentsType.values().first { it.code.contentEquals(documentType) }
            return docType.validator.validate(documentId)
        }

        fun validateDocument(documentType: DocumentsType, documentId: String): Boolean {
            return documentType.validator.validate(documentId)
        }
    }
}