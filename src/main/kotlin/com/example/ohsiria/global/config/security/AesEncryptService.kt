package com.example.ohsiria.global.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import jakarta.annotation.PostConstruct

@Service
class AESEncryptionService {
    private lateinit var key: SecretKeySpec
    private lateinit var cipher: Cipher

    @Value("\${aes.secret.key}")
    private lateinit var secretKeyString: String

    @PostConstruct
    fun init() {
        val keyBytes = secretKeyString.toByteArray(Charsets.UTF_8).copyOf(32) // Ensure 32 bytes for AES-256
        key = SecretKeySpec(keyBytes, "AES")
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    }

    fun encrypt(strToEncrypt: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedBytes = cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(strToDecrypt: String): String {
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decodedBytes = Base64.getDecoder().decode(strToDecrypt)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}
