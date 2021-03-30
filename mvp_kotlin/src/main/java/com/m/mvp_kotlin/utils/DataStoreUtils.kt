package com.m.mvp_kotlin.utils

import android.content.Context
import androidx.constraintlayout.helper.widget.Flow
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore


/**
 * createDate:2021/1/22
 * @author:spc
 * @describe：
 */
object DataStoreUtils {
//    private val data by lazy { preferencesKey<String>(name = "data") }
//    private val name = "user";
//    private var dataStore: DataStore<Preferences>? = null;
//
//    suspend fun writeData(context: Context, msg: String) {
//        if (dataStore == null) {
//            dataStore = context.createDataStore(name = name)
//        }
//        dataStore!!.edit { user ->
//            user[data] = msg;
//        }
//    }
//
//    fun readData(context: Context): Flow<String> {
//        if (dataStore == null) {
//            dataStore = context.createDataStore(name = name)
//        }
//        return dataStore!!.data.map { preferences: Preferences -> preferences[data] ?: "" }
//    }
}