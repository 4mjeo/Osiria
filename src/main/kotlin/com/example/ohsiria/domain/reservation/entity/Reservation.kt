package com.example.ohsiria.domain.reservation.entity

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.room.entity.Room
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
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
    accountNumber: String,
    company: Company,
    room: Room,
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

    @Column(name = "account_number", columnDefinition = "VARCHAR(14)", nullable = false)
    var accountNumber: String = accountNumber
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    var company: Company = company
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var room: Room = room
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
