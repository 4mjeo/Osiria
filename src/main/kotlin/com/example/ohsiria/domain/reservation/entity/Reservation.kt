package com.example.ohsiria.domain.reservation.entity

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.room.entity.RoomType
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
    phoneNumber: String,
    name: String,
    company: Company,
    roomType: RoomType,
    status: ReservationStatus = ReservationStatus.WAITING,
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

    @Column(name = "phone_number", columnDefinition = "VARCHAR(12)", nullable = false)
    var phoneNumber: String = phoneNumber
        protected set

    @Column(name = "name", columnDefinition = "VARCHAR(4)", nullable = false)
    var name: String = name
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    var company: Company = company
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", columnDefinition = "VARCHAR(10)", nullable = false)
    var roomType: RoomType = roomType
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(8)", nullable = false)
    var status: ReservationStatus = ReservationStatus.WAITING
        protected set

    fun cancel() {
        this.status = ReservationStatus.CANCELED
    }

    fun confirm() {
        this.status = ReservationStatus.RESERVED
    }
}
