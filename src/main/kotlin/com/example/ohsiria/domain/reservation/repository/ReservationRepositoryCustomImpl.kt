package com.example.ohsiria.domain.reservation.repository

import com.example.ohsiria.domain.company.entity.Company
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

import com.example.ohsiria.domain.reservation.entity.QReservation.reservation

@Service
class ReservationRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : ReservationRepositoryCustom {

    override fun existsByDateRange(startDate: LocalDate, endDate: LocalDate): Boolean {
        return queryFactory
            .selectOne()
            .from(reservation)
            .where(
                reservation.startDate.loe(endDate),
                reservation.endDate.goe(startDate)
            )
            .fetchFirst() != null
    }

    override fun isHoliday(startDate: LocalDate, endDate: LocalDate): Boolean {
        return false
    }

    override fun countDaysByCompanyAndDateRange(
        company: Company,
        startDate: LocalDate,
        endDate: LocalDate,
        isHoliday: Boolean
    ): Long {
        return queryFactory
            .selectFrom(reservation)
            .where(
                reservation.company.id.eq(company.id),
                reservation.startDate.loe(endDate),
                reservation.endDate.goe(startDate),
                reservation.isHoliday.eq(isHoliday)
            )
            .fetchCount()
    }
}
