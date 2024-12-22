package com.example.ohsiria.domain.user.entity

import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity(name = "tbl_user")
@DynamicUpdate
class User(
    id: UUID? = null,
    name: String,
    accountId: String,
    password: String,
    type: UserType
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: UUID? = id
        protected set

    @Column(name = "name", columnDefinition = "VARCHAR(20)", nullable = false)
    var name: String = name
        protected set

    @Column(name = "account_id", columnDefinition = "VARCHAR(15)", nullable = false)
    var accountId: String = accountId
        protected set

    @Column(name = "password", columnDefinition = "CHAR(60)", nullable = false)
    var password: String = password
        protected set

    @Column(name = "type", columnDefinition = "VARCHAR(9)", nullable = false)
    @Enumerated(value = EnumType.STRING)
    var type: UserType = type
        protected set
}
