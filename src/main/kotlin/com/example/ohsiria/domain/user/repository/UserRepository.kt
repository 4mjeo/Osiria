package com.example.ohsiria.domain.user.repository

import com.example.ohsiria.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, UUID?> {
    fun findByAccountId(accountId: String): User?
}
