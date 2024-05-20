package com.ahon.iaccounts.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtService {

    @Value("\${jwt.secret}")
    private lateinit var secretKey: String

    @Value("\${jwt.expire}")
    private lateinit var expireTime: String

    fun validate(headerToken: String): Boolean {


        val bearerToken = headerToken.replace("Bearer ", "")

        return if (bearerToken.isBlank()) {
            false

        } else {
            try {
                val whatever = Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(bearerToken)
                    .payload

                whatever.expiration.time > System.currentTimeMillis()

            } catch (e: Exception) {
                false
            }
        }
    }

    fun bearerHeader(username: String): MultiValueMap<String, String> {
        val bearerToken = generateToken(username)
        val multiValueMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        multiValueMap.add("Authorization", "Bearer $bearerToken")

        return multiValueMap
    }

    private fun generateToken(username: String): String =
        Jwts.builder()
            .subject(username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expireTime.toLong()))
            .signWith(generateKey())
            .compact()

    private fun generateKey(): SecretKey = Keys.hmacShaKeyFor(
        Decoders.BASE64.decode(secretKey)
    )
}