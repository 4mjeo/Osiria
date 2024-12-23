package com.example.ohsiria.domain.company.entity

import com.example.ohsiria.domain.user.entity.User
import jakarta.persistence.*
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @MapsId
    var user: User = user
        protected set
}
