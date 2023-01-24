package com.jesus.villa.technicaltest.domain.entity

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by Jesús Villa on 22/01/23
 */
@Parcelize
data class UserData(
    @SerializedName("customer_id")
    val customerId: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("payment_methods")
    val paymentMethods: List<PaymentMethod>?

) : Parcelable

@Parcelize
class PaymentMethod : Parcelable {
    @SerializedName("payment_type")
    @Expose
    var paymentType: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("available_limit")
    @Expose
    var availableLimit: Double? = null

    @SerializedName("second_factor_req")
    @Expose
    var secondFactorReq: Boolean? = null

    @SerializedName("selectable")
    @Expose
    var selectable: Boolean = true

    @SerializedName("card_type")
    @Expose
    var cardType: String? = null

    @SerializedName("on_us")
    @Expose
    var onUs: Boolean? = null

    @SerializedName("creation_date")
    @Expose
    var creationDate: String? = null

    @SerializedName("card_uuid")
    @Expose
    var cardUuid: String? = null

    @SerializedName("card_name")
    @Expose
    var cardName: String? = null

    @SerializedName("card_description")
    @Expose
    var cardDescription: String? = null

    @SerializedName("first_6_digits")
    @Expose
    var first6Digits: String? = null

    @SerializedName("last_4_digits")
    @Expose
    var last4Digits: String? = null

    @SerializedName("color")
    @Expose
    var color: String? = null

    @SerializedName("above_limit_per_sva_tx")
    @Expose
    var aboveLimit: Boolean? = false

    @SerializedName("bank")
    @Expose
    var bank: String? = null

    @SerializedName("brand")
    @Expose
    var brand: String? = null
}