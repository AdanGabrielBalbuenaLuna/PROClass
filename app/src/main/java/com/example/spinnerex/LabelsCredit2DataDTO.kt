package com.example.spinnerex

import com.google.gson.annotations.SerializedName

data class LabelsCredit2DTO(
    @SerializedName("code")
    var code: Int?,

    @SerializedName("status")
    var status: StatusDTO?,

    @SerializedName("data")
    var data: LabelsCredit2DataDTO?
)

data class StatusDTO(
    @SerializedName("status")
    var status: String?,

    @SerializedName("errorDescription")
    var errorDescription: String?,

    @SerializedName("statusCode")
    var statusCode: Int?
)

data class LabelsCredit2DataDTO(
    @SerializedName("labels")
    var labels: List<LabelCredit2DTO>?
)

data class LabelCredit2DTO(
    @SerializedName("id")
    var id: Int?,

    @SerializedName("key")
    var key: String?,

    @SerializedName("site")
    var site: String?,

    @SerializedName("value")
    var value: String?,

    @SerializedName("brand")
    var brand: String?
)

