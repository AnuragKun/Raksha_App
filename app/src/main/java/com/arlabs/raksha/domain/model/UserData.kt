package com.arlabs.raksha.domain.model

data class UserData(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val dob: Long = 0L, // Timestamp
    val bloodGroup: String = "",
    val isVerified: Boolean = false
)
