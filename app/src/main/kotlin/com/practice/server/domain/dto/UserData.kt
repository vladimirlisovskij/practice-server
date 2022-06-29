package com.practice.server.domain.dto

import com.practice.network_entities.params.UserType

class UserData(
    val nickName: String,
    val userType: UserType,
    val uid: Long,
    val password: ByteArray,
    val salt: ByteArray
)

