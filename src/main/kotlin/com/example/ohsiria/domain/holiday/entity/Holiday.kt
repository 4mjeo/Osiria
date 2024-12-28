package com.example.ohsiria.domain.holiday.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDate

@Entity(name = "tbl_holiday")
class Holiday(
    date: LocalDate,
    name: String
) {
    @Id
    @Column(name = "holiday_date")
    val date: LocalDate = date

    @Column(name = "holiday_name")
    val name: String = name
}
