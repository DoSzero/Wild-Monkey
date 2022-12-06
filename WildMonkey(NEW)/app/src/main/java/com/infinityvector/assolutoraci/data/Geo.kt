package com.infinityvector.assolutoraci.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Geo(

    @SerializedName("geo")
    val geo: String,

    @SerializedName("view")
    val view: String,

    @SerializedName("appsChecker")
    val appsChecker: String,

)
