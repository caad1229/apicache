package com.caad1229.apicache.data.local.mapper

import com.caad1229.apicache.data.local.realm.QiitaUserRealmEntity
import com.caad1229.apicache.data.mapper.Mapper
import com.caad1229.apicache.presentation.entity.QiitaUser

class QiitaUserRealmEntityMapper : Mapper<QiitaUserRealmEntity, QiitaUser> {

    override fun mapFromEntity(type: QiitaUserRealmEntity): QiitaUser =
            QiitaUser(
                    name = type.name,
                    id = type.id,
                    profileImageUrl = type.profileImageUrl
            )

    override fun mapToEntity(type: QiitaUser): QiitaUserRealmEntity =
            QiitaUserRealmEntity(
                    name = type.name,
                    id = type.id,
                    profileImageUrl = type.profileImageUrl
            )
}