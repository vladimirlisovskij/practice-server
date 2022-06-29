package com.practice.server.domain.utils

import org.bouncycastle.crypto.generators.BCrypt
import java.security.SecureRandom

object PasswordHashService {
    private const val SALT_LENGTH = 16
    private const val HASH_COST = 10

    fun createSalt(): ByteArray = SecureRandom().generateSeed(SALT_LENGTH)

    fun createPasswordHash(password: String, salt: ByteArray): ByteArray = BCrypt.generate(
        password.encodeToByteArray(),
        salt,
        HASH_COST
    )
}