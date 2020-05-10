package com.readwithfriends.model.api.model

import androidx.annotation.Keep
import java.lang.invoke.MethodHandleInfo

@Keep
data class BookBackendRequest(
    val rawInfo: String?
)