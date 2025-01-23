package com.example.ohsiria.domain.reservation.repository

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.reservation.entity.QReservation.reservation
import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.reservation.entity.ReservationStatus
import com.example.ohsiria.domain.room.entity.QRoom.room
import com.example.ohsiria.domain.room.entity.Room
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReservationRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : ReservationRepositoryCustom {

    override fun existsActiveReservationByDateRangeAndRoom(startDate: LocalDate, endDate: LocalDate, room: Room): Boolean {
        return queryFactory
            .selectOne()
            .from(reservation)
            .where(
                reservation.startDate.loe(endDate),
                reservation.endDate.goe(startDate),
                reservation.room.eq(room),
                reservation.status.`in`(ReservationStatus.WAITING, ReservationStatus.RESERVED)
            )
            .fetchFirst() != null
    }

    override fun countDaysByCompanyAndDateRangeAndType(
        company: Company,
        startDate: LocalDate,
        endDate: LocalDate
    ): Long {
        return queryFactory
            .select(reservation.count())
            .from(reservation)
            .where(
                reservation.company.eq(company),
                reservation.startDate.loe(endDate),
                reservation.endDate.goe(startDate),
                reservation.status.`in`(ReservationStatus.RESERVED, ReservationStatus.WAITING)
            )
            .fetchOne() ?: 0L
    }

    override fun findAvailableRooms(startDate: LocalDate, endDate: LocalDate): List<Room> {
        return queryFactory
            .selectFrom(room)
            .where(room.id.notIn(
                queryFactory
                    .select(reservation.room.id)
                    .from(reservation)
                    .where(
                        reservation.startDate.lt(endDate),
                        reservation.endDate.gt(startDate),
                        reservation.status.`in`(ReservationStatus.CANCELED, ReservationStatus.WAITING)
                    )
            ))
            .fetch()
    }

    override fun findByCompanyAndHistoryAndPaymentStatus(
        company: Company,
        isHistory: Boolean,
        isPaid: Boolean?
    ): List<Reservation> {
        val currentDate = LocalDate.now()

        return queryFactory
            .selectFrom(reservation)
            .where(
                reservation.company.eq(company),
                if (isHistory) reservation.endDate.before(currentDate)
                else reservation.endDate.goe(currentDate),
                when (isPaid) {
                    true -> reservation.paidAt.isNotNull
                    false -> reservation.paidAt.isNull
                    null -> null
                }
            )
            .fetch()
    }
}
