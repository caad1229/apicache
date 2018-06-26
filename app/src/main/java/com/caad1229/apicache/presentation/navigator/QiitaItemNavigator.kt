package com.caad1229.apicache.presentation.navigator

import com.caad1229.apicache.presentation.entity.QiitaItem
import com.caad1229.apicache.presentation.entity.QiitaUser

interface QiitaItemNavigator {
    fun navigateToUserDetail(user: QiitaUser)
    fun navigateToItemDetail(item: QiitaItem)
}