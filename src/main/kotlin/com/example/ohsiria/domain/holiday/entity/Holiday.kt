package com.example.ohsiria.domain.holiday.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate
import java.util.UUID

@Entity(name = "tbl_holiday")
class Holiday(
    id: UUID? = null,
    date: LocalDate,
    name: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = id
        protected set

    @Column(name = "holiday_date")
    var date: LocalDate = date
        protected set

    @Column(name = "holiday_name")
    var name: String = name
        protected set
}
