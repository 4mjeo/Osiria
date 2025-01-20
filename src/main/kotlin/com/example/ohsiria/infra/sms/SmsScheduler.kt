package com.example.ohsiria.infra.sms

import com.example.ohsiria.domain.reservation.repository.ReservationRepository
import com.example.ohsiria.domain.reservation.sms.dto.MessageDTO
import com.example.ohsiria.domain.reservation.sms.service.SmsService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class SmsScheduler(
    private val reservationRepository: ReservationRepository,
    private val smsService: SmsService
) {
    @Scheduled(cron = "0 0 15 * * ?")
    fun sendReminderSms() {
        val tomorrow = LocalDate.now().plusDays(1)
        val reservations = reservationRepository.findByStartDate(tomorrow)

        reservations.forEach { reservation ->
            val content = """
                안녕하세요, 내일 예약하신 숙소 이용 안내 드립니다.
                
                1. 주차장은 지하주차타워 이용가능합니다.
                2. 공동현관 비번은 ##2580 이며, 이용호실 비번은 7212* 입니다.
                3. 안에있는 집기는 모두 편하게 이용하시면 됩니다.
                4. 수건은 숙박기간동안 보충이 어려우니, 여유분을 챙겨주세요
                5. 퇴실시, 
                  - 쓰레기는 1층 분리수거장에 분리배출 요청드립니다.
                    (음식물쓰레기는 비닐팩에 담아서 버려주세요)
                  - 에어컨 / 보일러 전원을 꺼주세요
                  - 전체소등 버튼을 꺼주세요
                
                즐거운 여행 되세요!
            """.trimIndent()

            val messageDto = MessageDTO(
                to = reservation.phoneNumber,
                content = content
            )

            try {
                smsService.sendSms(messageDto)
                println("Reminder sms sent.")
            } catch (e: Exception) {
                println("Failed to send reminder sms.")
            }
        }
    }
}
