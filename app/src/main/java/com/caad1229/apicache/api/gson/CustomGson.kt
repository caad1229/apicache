package com.caad1229.apicache.api.gson

import android.support.annotation.VisibleForTesting
import com.caad1229.apicache.api.converter.DateTimeConverter
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.joda.time.DateTime

class CustomGson {
    companion object {
        val gson = createGson()

        @VisibleForTesting
        fun createGson(): Gson =
                GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .registerTypeAdapter(DateTime::class.java, DateTimeConverter())
                        .create()

    }
}