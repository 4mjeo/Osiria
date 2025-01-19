package com.example.ohsiria.domain.service.entity

import com.example.ohsiria.domain.room.entity.Room
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity(name = "tbl_room_service")
@DynamicUpdate
class RoomService(
    room: Room,
    service: Service,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    var room: Room = room
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    var service: Service = service
        protected set
}
