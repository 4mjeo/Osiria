package com.example.ohsiria.domain.reservation.repository

import com.example.ohsiria.domain.company.entity.Company
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

import com.example.ohsiria.domain.reservation.entity.QReservation.reservation
import com.example.ohsiria.domain.room.entity.RoomType

@Service
class ReservationRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : ReservationRepositoryCustom {

    override fun existsByDateRangeAndRoom(startDate: LocalDate, endDate: LocalDate, roomType: RoomType): Boolean {
        return queryFactory
            .selectOne()
            .from(reservation)
            .where(
                reservation.startDate.loe(endDate),
                reservation.endDate.goe(startDate),
                reservation.roomType.eq(roomType)
            )
            .fetchFirst() != null
    }

    override fun countDaysByCompanyAndDateRangeAndType(
        company: Company,
        startDate: LocalDate,
        endDate: LocalDate
    ): Long {
        return queryFactory
            .selectFrom(reservation)
            .where(
                reservation.company.eq(company),
                reservation.startDate.loe(endDate),
                reservation.endDate.goe(startDate),
            )
            .fetchCount()
    }
}
