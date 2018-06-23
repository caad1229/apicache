package com.caad1229.apicache.presentation.entity

import org.joda.time.DateTime

data class QiitaItem(
        val id: String,
        val title: String,
        val url: String,
        val likesCount: Int,
        val createdAt: DateTime,
        val updatedAt: DateTime,
        val user: QiitaUser
)
