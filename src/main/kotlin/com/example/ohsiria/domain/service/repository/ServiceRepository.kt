package com.example.ohsiria.domain.service.repository

import com.example.ohsiria.domain.service.entity.Service
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ServiceRepository: JpaRepository<Service, UUID?> {
    fun findByKeyword(keyword: String): Service?

    @Query("SELECT s FROM tbl_service s WHERE s.keyword IN :keywords")
    fun findByKeywords(keywords: List<String>): List<Service>
}
