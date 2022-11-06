package com.anthonyessaye.simpledall_e.Database

import com.anthonyessaye.simpledall_e.Database.Tables.AppSettings
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject
import kotlin.reflect.KClass

object DatabaseManager {
    object RealmCompanion {
        private var mRealm: Realm? = null

        fun open(realmObject: KClass<RealmObject>): Realm {
            val config = RealmConfiguration.Builder(schema = setOf(realmObject))
                .build()
            return Realm.open(config)
        }

        fun close() {
            if (mRealm != null) {
                mRealm!!.close()
            }
        }
    }

    object Read {
        fun getAppSettings(): AppSettings? {
            val appSettingsRealm = RealmCompanion.open(AppSettings::class as KClass<RealmObject>)
            val appSettings = appSettingsRealm.query<AppSettings>().find().firstOrNull()
            appSettingsRealm.close()
            return appSettings
        }
    }

    object Write {
        fun setAppSettings(apiToken: String) {
            val appSettingsRealm = RealmCompanion.open(AppSettings::class as KClass<RealmObject>)
            val appSettings = AppSettings(apiToken)

            appSettingsRealm.writeBlocking {
                this.copyToRealm(appSettings)
            }

            appSettingsRealm.close()
        }
    }
}