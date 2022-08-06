package com.arjixwastaken.nyaaapi.models

import java.util.*

data class AccountInfo(
    val name: String,
    val userId: Int,
    val registrationDate: Date,
    val avatarUrl: String,
    val userClass: String,
    val email: String
)
