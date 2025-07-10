package com.example.spinnerex

import retrofit2.Response
import retrofit2.http.GET

interface LabelsService {
    @GET("prefix")
    suspend fun getLabels(): Response<LabelsCredit2DTO>
}

