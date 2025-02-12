package com.example.ohsiria.domain.company.entity

import com.example.ohsiria.domain.holiday.repository.HolidayRepository
import com.example.ohsiria.domain.reservation.entity.Reservation
import com.example.ohsiria.domain.user.entity.User
import jakarta.persistence.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity(name = "tbl_company")
class Company(
    id: UUID? = null,
    remainWeekday: Int = 20,
    remainWeekend: Int = 10,
    user: User,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = id
        protected set

    @Column(name = "remain_weekday", columnDefinition = "INTEGER", nullable = false)
    var remainWeekday = remainWeekday
        protected set

    @Column(name = "remain_weekend", columnDefinition = "INTEGER", nullable = false)
    var remainWeekend = remainWeekend
        protected set

    @Column(name = "created_at", columnDefinition = "DATETIME", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    @MapsId
    var user: User = user
        protected set

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], orphanRemoval = true)
    val reservations: MutableList<Reservation> = mutableListOf()

    fun update(remainWeekday: Int, remainWeekend: Int) {
        this.remainWeekend = remainWeekend
        this.remainWeekday = remainWeekday
    }

    fun resetRemainingDays() {
        remainWeekday = 20
        remainWeekend = 10
    }

    fun updateRemainingDays(dates: List<Pair<LocalDate, Boolean>>) {
        dates.dropLast(1).forEach { (date, isHoliday) ->
            if (date.dayOfWeek in listOf(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY) || isHoliday) {
                remainWeekend = (remainWeekend - 1).coerceAtLeast(0)
            } else {
                remainWeekday = (remainWeekday - 1).coerceAtLeast(0)
            }
        }
    }

    fun hasEnoughRemainingDays(dates: List<Pair<LocalDate, Boolean>>): Boolean {
        val (weekdayCount, weekendCount) = dates.dropLast(1).partition { (date, isHoliday) ->
            date.dayOfWeek !in listOf(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY) && !isHoliday
        }.let { (weekdays, weekends) -> weekdays.size to weekends.size }

        return remainWeekday >= weekdayCount && remainWeekend >= weekendCount
    }

    fun returnRemainingDays(dates: List<Pair<LocalDate, Boolean>>) {
        dates.dropLast(1).forEach { (date, isHoliday) ->
            if (date.dayOfWeek in listOf(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY) || isHoliday) {
                remainWeekend++
            } else {
                remainWeekday++
            }
        }
    }

    fun getDatesWithHolidayInfo(startDate: LocalDate, endDate: LocalDate, holidayRepository: HolidayRepository): List<Pair<LocalDate, Boolean>> {
        val holidays = holidayRepository.findByDateBetween(startDate, endDate)
        return startDate.datesUntil(endDate.plusDays(1))
            .map { it to holidays.any { holiday -> holiday.date == it } }
            .toList()
    }
}
