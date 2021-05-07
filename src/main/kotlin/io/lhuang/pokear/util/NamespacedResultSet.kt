package io.lhuang.pokear.util

import java.sql.ResultSet
import java.sql.Timestamp
import java.time.Instant
import java.util.*

/**
 * This class provides a wrapper around a [ResultSet], with the additional
 * capability of namespacing by table name.
 *
 * The problem with a [ResultSet] is that if you have the same column name in
 * multiple tables, it's hard to differentiate between them.
 *
 * The [NamespacedResultSet] remaps the column names to be <table-name>.<column.name>
 */
class NamespacedResultSet {

    private val mapping: Map<String, Any?>

    constructor(resultSet: ResultSet) {
        val mutableMapping: MutableMap<String, Any?> = mutableMapOf()
        for (i in 1..resultSet.metaData.columnCount) {
            val columnName = resultSet.metaData.getColumnName(i)
            val tableName = resultSet.metaData.getTableName(i)
            mutableMapping.putIfAbsent("${tableName.toLowerCase()}.${columnName.toLowerCase()}", resultSet.getObject(i))
        }

        this.mapping = mutableMapping.toMap()
    }

    fun hasColumn(columnName: String): Boolean {
        return mapping.containsKey(columnName) && mapping[columnName] != null
    }

    fun get(columnName: String): Any? {
        return mapping[columnName]
    }

    fun getInt(columnName: String): Int {
        return mapping[columnName] as Int
    }

    fun getIntOrNull(columnName: String): Int? {
        return mapping[columnName] as Int?
    }

    fun getLong(columnName: String): Long {
        return mapping[columnName] as Long
    }

    fun getLongOrNull(columnName: String): Long? {
        return mapping[columnName] as Long?
    }

    fun getDouble(columnName: String): Double {
        return mapping[columnName] as Double
    }

    fun getDoubleOrNull(columnName: String): Double? {
        return mapping[columnName] as Double?
    }

    fun getFloat(columnName: String): Float {
        return mapping[columnName] as Float
    }

    fun getFloatOrNull(columnName: String): Float? {
        return mapping[columnName] as Float?
    }

    fun getBoolean(columnName: String): Boolean {
        return mapping[columnName] as Boolean
    }

    fun getBooleanOrNull(columnName: String): Boolean? {
        return mapping[columnName] as Boolean?
    }

    fun getString(columnName: String): String {
        return mapping[columnName] as String
    }

    fun getStringOrNull(columnName: String): String? {
        return mapping[columnName] as String?
    }

    fun getUuid(columnName: String): UUID {
        return mapping[columnName] as UUID
    }

    fun getUuidOrNull(columnName: String): UUID? {
        return mapping[columnName] as UUID?
    }

    fun getInstant(columnName: String): Instant {
        return (mapping[columnName] as Timestamp).toInstant()
    }

    fun getInstantOrNull(columnName: String): Instant? {
        return (mapping[columnName] as Timestamp?)?.toInstant()
    }
}