package com.github.tckim.expandable_list_demo.network

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.text.SimpleDateFormat
import java.util.*

const val FORMAT_DATE_DASH = "yyyy-MM-dd"

class DateDashJsonAdapter : TypeAdapter<Long>() {

    override fun write(desc: JsonWriter?, value: Long?) {
        value?.let {
            desc?.value(SimpleDateFormat(FORMAT_DATE_DASH, Locale.getDefault()).format(Date(it)))
        }
    }

    override fun read(src: JsonReader?): Long? {
        return src?.let {
            if (it.peek() == JsonToken.NULL) {
                it.nextNull()
                null
            } else {
                SimpleDateFormat(FORMAT_DATE_DASH, Locale.getDefault()).parse(it.nextString()).time
            }
        }
    }
}