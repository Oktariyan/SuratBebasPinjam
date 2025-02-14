package com.example.suratbebaspinjam.shared

import com.google.gson.annotations.SerializedName

data class GoogleRequest(
    @SerializedName("token")
    val token: String
)
