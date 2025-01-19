package com.example.ohsiria.domain.reservation.repository

import com.example.ohsiria.domain.company.entity.Company
import com.example.ohsiria.domain.reservation.entity.QReservation.reservation
import com.example.ohsiria.domain.reservation.entity.ReservationStatus
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
//                reservation.room.eq(room),
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
}
