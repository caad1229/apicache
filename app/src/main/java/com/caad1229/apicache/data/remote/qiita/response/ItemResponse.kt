package com.caad1229.apicache.data.remote.qiita.response

import com.caad1229.apicache.presentation.entity.QiitaUser
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime

data class ItemResponse(
        val id: String,
        val title: String,
        val url: String,
        val likesCount: Int,
        val createdAt: DateTime,
        val updatedAt: DateTime,
        @SerializedName("user")
        val user: QiitaUser
)