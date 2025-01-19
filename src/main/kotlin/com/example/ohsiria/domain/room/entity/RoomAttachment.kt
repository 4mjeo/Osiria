package com.example.ohsiria.domain.room.entity

import jakarta.persistence.*
import java.util.*

@Entity(name = "tbl_room_attachment")
class RoomAttachment (
    id: UUID? = null,
    imageUrl: String,
    room: Room
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = id
        protected set

    @Column(name = "image_url", columnDefinition = "VARCHAR(300)")
    var imageUrl: String = imageUrl
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    var room: Room = room
        protected set
}
