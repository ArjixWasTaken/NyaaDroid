package com.arjixwastaken.nyaaapi.models

import java.security.InvalidParameterException

class DataSize(val value: Int, val unit: DataUnit) {
    companion object {
        fun fromString(str: String): DataSize {
            if (str.trim().split(" ").size != 2) throw InvalidParameterException("`str` must be a string in the format of \"1 Bytes\"")
            val (count, unit) = str.trim().split(" ")
            return DataSize(count.toInt(), DataUnit.valueOf(unit))
        }
    }

    enum class DataUnit(val unitName: String) {
        BYTE("Bytes"),
        KILOBYTE("KiB"),
        MEGABYTE("MiB"),
        GIGABYTE("GiB"),
        TERABYTE("TiB");
    }

    override fun toString(): String {
        return "$value ${unit.unitName}"
    }
}