package com.arjixwastaken.nyaaapi.models

import java.security.InvalidParameterException

class DataSize(val value: Float, val unit: DataUnit) {
    companion object {
        fun fromString(str: String): DataSize {
            if (str.trim().split(" ").size != 2) throw InvalidParameterException("`str` must be a string in the format of \"1 Bytes\"")
            val (count, unit) = str.trim().split(" ")
            println("CONVERTING STR TO ENUM $str -> $unit")
            return DataSize(count.toFloat(), DataUnit.valueOf(unit))
        }
    }

    enum class DataUnit(val unitName: String) {
        Bytes("Bytes"),
        KiB("KiB"),
        MiB("MiB"),
        GiB("GiB"),
        TiB("TiB");
    }

    override fun toString(): String {
        return "$value ${unit.unitName}"
    }
}