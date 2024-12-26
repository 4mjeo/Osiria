package com.example.ohsiria.domain.reservation.entity

import com.example.ohsiria.domain.company.entity.Company
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDate
import java.util.*

@Entity(name = "tbl_reservation")
@DynamicUpdate
class Reservation(
    id: UUID? = null,
    startDate: LocalDate,
    endDate: LocalDate,
    headCount: Int,
    phoneNumber: String,
    name: String,
    isCancel: Boolean? = null,
    isHoliday: Boolean = false,
    company: Company,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = id
        protected set

    @Column(name = "start_date", columnDefinition = "DATE", nullable = false)
    var startDate: LocalDate = startDate
        protected set

    @Column(name = "end_date", columnDefinition = "DATE", nullable = false)
    var endDate: LocalDate = endDate
        protected set

    @Column(name = "head_count", columnDefinition = "INT", nullable = false)
    var headCount: Int = headCount
        protected set

    @Column(name = "phone_number", columnDefinition = "VARCHAR(12)", nullable = false)
    var phoneNumber: String = phoneNumber
        protected set

    @Column(name = "name", columnDefinition = "VARCHAR(4)", nullable = false)
    var name: String = name
        protected set

    @Column(name = "is_holiday", columnDefinition = "BIT(1)", nullable = false)
    var isHoliday: Boolean = isHoliday
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    var company: Company = company
        protected set
}
