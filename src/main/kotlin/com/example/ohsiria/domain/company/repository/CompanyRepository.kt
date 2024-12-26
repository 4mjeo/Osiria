package com.example.ohsiria.domain.company.repository

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CompanyRepository: JpaRepository<Company, UUID?> {
    fun findByUser(user: User): Company?
}
