package com.example.ohsiria.domain.room.entity

import com.example.ohsiria.domain.service.entity.RoomService
import jakarta.persistence.*
import java.util.*

@Entity(name = "tbl_room")
class Room(
    id: UUID? = null,
    number: Long,
    introduction: String,
    guide: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = id
        protected set

    @Column(name = "number", columnDefinition = "INTEGER", nullable = false)
    var number: Long = number
        protected set

    @Column(name = "introduction", columnDefinition = "TEXT", nullable = false)
    var introduction: String = introduction
        protected set

    @Column(name = "guide", columnDefinition = "TEXT", nullable = false)
    var guide: String = guide
        protected set

    @OneToMany(mappedBy = "room", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    var roomServices: MutableList<RoomService> = mutableListOf()
        protected set

    @OneToMany(mappedBy = "room", cascade = [CascadeType.ALL], orphanRemoval = true)
    var attachments: MutableList<RoomAttachment> = mutableListOf()
        protected set

    fun update(introduction: String?, guide: String?) {
        introduction?.let { this.introduction = it }
        guide?.let { this.guide = it }
    }

    fun addAttachment(imageUrl: String) {
        attachments.add(RoomAttachment(room = this, imageUrl = imageUrl))
    }

    fun clearAttachments() {
        attachments.clear()
    }
}
