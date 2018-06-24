package com.caad1229.apicache.data.local.mapper

import com.caad1229.apicache.data.local.realm.QiitaItemRealmEntity
import com.caad1229.apicache.data.mapper.Mapper
import com.caad1229.apicache.presentation.entity.QiitaItem
import org.joda.time.DateTime

class QiitaItemRealmEntityMapper(
        private val userMapper: QiitaUserRealmEntityMapper
) : Mapper<QiitaItemRealmEntity, QiitaItem> {

    override fun mapFromEntity(type: QiitaItemRealmEntity): QiitaItem =
            QiitaItem(
                    id = type.id,
                    title = type.title,
                    url = type.url,
                    likesCount = type.likesCount,
                    createdAt = DateTime(type.createdAt),
                    updatedAt = DateTime(type.updatedAt),
                    user = userMapper.mapFromEntity(type.user)
            )

    override fun mapToEntity(type: QiitaItem): QiitaItemRealmEntity =
            QiitaItemRealmEntity(
                    id = type.id,
                    title = type.title,
                    url = type.url,
                    likesCount = type.likesCount,
                    createdAt = type.createdAt.toDate(),
                    updatedAt = type.updatedAt.toDate(),
                    user = userMapper.mapToEntity(type.user)

            )
}