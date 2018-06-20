package com.caad1229.apicache.api.converter

import com.google.gson.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat
import java.lang.reflect.Type

class DateTimeConverter : JsonDeserializer<DateTime>, JsonSerializer<DateTime> {
    private val formatter: DateTimeFormatter = ISODateTimeFormat.dateTimeParser().withOffsetParsed()

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): DateTime? {
        if (json == null) return null
        try {
            return if (json.asString.isNotEmpty()) {
                formatter.parseDateTime(json.asString)
            } else {
                null
            }
        } catch (e: IllegalArgumentException) {
            throw JsonParseException(e)
        }
    }

    override fun serialize(src: DateTime, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(dateTimeToString(src))
    }

    companion object {
        fun dateTimeToString(src: DateTime): String {
            return ISODateTimeFormat.dateTimeNoMillis().print(src)
        }
    }
}