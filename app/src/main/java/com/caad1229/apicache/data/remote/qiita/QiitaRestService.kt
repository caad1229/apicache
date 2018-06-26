package com.caad1229.apicache.data.remote.qiita

import com.caad1229.apicache.data.remote.qiita.response.ItemResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface QiitaRestService {

    // https://qiita.com/api/v2/docs#get-apiv2usersuser_iditems
    @GET("users/{user_id}/items")
    fun getUserItems(
            @Path("user_id") userId: String
    ): Single<List<ItemResponse>>

    @GET("items")
    fun getItems(): Single<List<ItemResponse>>
}