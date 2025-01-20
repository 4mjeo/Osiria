package com.example.ohsiria.domain.reservation.event

import com.example.ohsiria.domain.reservation.sms.dto.MessageDTO
import com.example.ohsiria.domain.reservation.sms.service.SmsService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ReservationEventListener(
    private val smsService: SmsService
) {
    @EventListener
    fun handleReservationStatusChanged(event: ReservationStatusChangedEvent) {
        val messageDto = MessageDTO(
            to = event.phoneNumber,
            content = "안녕하세요? 디오션오시리아 주인장입니다.\n" +
                    "예약은 보증금 10만원 + 호실 사용료를 입금주시면, 최종 확정됩니다.\n" +
                    "입금계좌 : 부산은행 123-456-7890  예금주 : 김재춘\n"
        )
        smsService.sendSms(messageDto)
    }
}
