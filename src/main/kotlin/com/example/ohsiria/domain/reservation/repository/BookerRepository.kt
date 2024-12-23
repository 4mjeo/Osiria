package com.example.ohsiria.domain.reservation.repository

import com.example.ohsiria.domain.reservation.entity.Booker
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookerRepository: JpaRepository<Booker, UUID?> {
}
