package com.caad1229.apicache.presentation.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableField
import com.caad1229.apicache.BR
import com.caad1229.apicache.R
import com.caad1229.apicache.presentation.entity.QiitaItem
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class QiitaItemViewModel(private val context: Context, item: QiitaItem) : BaseObservable() {

    val user: ObservableField<String> = ObservableField(item.user.name)
    val created: ObservableField<String> = ObservableField(createPostedString(item.createdAt))
    val title: ObservableField<String> = ObservableField(item.title)
    val likeCount: ObservableField<String> = ObservableField(item.likesCount.toString())

    @get:Bindable
    var createdAt: DateTime = item.createdAt
        set(value) {
            field = value
            created.set(createPostedString(field))
            notifyPropertyChanged(BR.createdAt)
        }

    private fun createPostedString(datetime: DateTime): String {
        val date = DateTimeFormat.forPattern("yyyy/MM/dd").print(datetime)
        return context.resources.getString(R.string.qiita_item_created, date)
    }

}