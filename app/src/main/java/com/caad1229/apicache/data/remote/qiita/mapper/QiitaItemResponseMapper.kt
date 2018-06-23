package com.caad1229.apicache.data.remote.qiita.mapper

import com.caad1229.apicache.data.mapper.Mapper
import com.caad1229.apicache.data.remote.qiita.response.ItemResponse
import com.caad1229.apicache.presentation.entity.QiitaItem
import java.lang.UnsupportedOperationException

class QiitaItemResponseMapper : Mapper<QiitaItem, ItemResponse> {

    override fun mapFromEntity(type: QiitaItem): ItemResponse =
            throw UnsupportedOperationException()

    override fun mapToEntity(type: ItemResponse): QiitaItem =
            QiitaItem(
                    id = type.id,
                    title = type.title,
                    url = type.url,
                    likesCount = type.likesCount,
                    createdAt = type.createdAt,
                    updatedAt = type.updatedAt,
                    user = type.user
            )
}