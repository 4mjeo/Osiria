package com.example.ohsiria.domain.service

import jakarta.persistence.*
import java.util.UUID

@Entity(name = "tbl_service")
class Service(
    id: UUID? = null,
    keyword: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = id
        protected set

    @Column(name = "keyword", columnDefinition = "VARCHAR(20)", nullable = false)
    var keyword: String = keyword
        protected set

    @OneToMany(mappedBy = "service", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    var roomServices: MutableList<RoomService> = mutableListOf()
        protected set
}
