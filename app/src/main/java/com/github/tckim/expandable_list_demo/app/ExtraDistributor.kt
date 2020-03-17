package com.github.tckim.expandable_list_demo.app

import android.os.Bundle
import android.os.Parcelable

private const val KEY_TYPE = "type"
private const val KEY_INDEX = "index"
private const val KEY_KEYS = "keys"
private const val KEY_VALUE = "value"
private const val KEY_VALUES = "values"
private const val KEY_AVAILABLE = "available"
private const val KEY_SEQUENCE = "sequence"
private const val KEY_PARCELABLE = "parcelable"

private const val KEY_NAME = "name"
private const val KEY_CODE = "code"
private const val KEY_IS_ALL_OPEN = "is_all_open"

fun Bundle.putType(type: Int) = this.apply { putInt(KEY_TYPE, type) }

fun Bundle?.getType(defaultValue: Int = 0) = this?.getInt(KEY_TYPE) ?: defaultValue

fun Bundle.putIndex(index: Int) = this.apply { putInt(KEY_INDEX, index) }

fun Bundle?.getIndex(defaultValue: Int = 0) = this?.getInt(KEY_INDEX) ?: defaultValue

fun Bundle.putAvailable(available: Boolean) = this.apply { putBoolean(KEY_AVAILABLE, available) }

fun Bundle?.isAvailable(defaultValue: Boolean = false) = this?.getBoolean(KEY_AVAILABLE) ?: defaultValue

fun Bundle.putKeys(keys: Array<String>) = this.apply { putStringArray(KEY_KEYS, keys) }

fun Bundle?.getKeys(): Array<String> = this?.getStringArray(KEY_KEYS) ?: emptyArray()

fun Bundle.putValue(value: String) = this.apply { putString(KEY_VALUE, value) }

fun Bundle?.getValue() = this?.getString(KEY_VALUE).orEmpty()

fun Bundle.putSequence(value: String) = this.apply { putString(KEY_SEQUENCE, value) }

fun Bundle?.getSequence() = this?.getString(KEY_SEQUENCE).orEmpty()

fun Bundle.putValues(values: Array<String>) = this.apply { putStringArray(KEY_VALUES, values) }

fun Bundle?.getValues(): Array<String> = this?.getStringArray(KEY_VALUES) ?: emptyArray()

fun Bundle?.putParcelable(parcelable: Parcelable) = this?.apply { putParcelable(KEY_PARCELABLE, parcelable) }

fun Bundle.putCode(value: String) = this.apply { putString(KEY_CODE, value) }

fun Bundle?.getCode() = this?.getString(KEY_CODE).orEmpty()

fun Bundle.putName(value: String) = this.apply { putString(KEY_NAME, value) }

fun Bundle?.getName() = this?.getString(KEY_NAME).orEmpty()

fun Bundle.putIsAllOpen(value: Boolean) = this.apply { putBoolean(KEY_IS_ALL_OPEN, value) }

fun Bundle?.isAllOpen() = this?.getBoolean(KEY_IS_ALL_OPEN)