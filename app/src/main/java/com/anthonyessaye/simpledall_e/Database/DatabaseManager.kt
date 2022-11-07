package com.anthonyessaye.simpledall_e.Database

import com.anthonyessaye.simpledall_e.Database.Tables.AppSettings
import com.anthonyessaye.simpledall_e.Database.Tables.Image
import com.anthonyessaye.simpledall_e.Database.Tables.PreviousQuery
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.ObjectId
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
            return appSettings
        }

        fun getImages(): List<Image> {
            val imagesRealm = RealmCompanion.open(Image::class as KClass<RealmObject>)
            val images = imagesRealm.query<Image>().find()
            return images
        }

        fun getPreviousQueries(): List<PreviousQuery> {
            val previousQueryRealm = RealmCompanion.open(PreviousQuery::class as KClass<RealmObject>)
            val previousQueries = previousQueryRealm.query<PreviousQuery>().find()
            return previousQueries
        }
    }

    object Write {
        fun setAppSettings(apiToken: String): AppSettings {
            val appSettingsRealm = RealmCompanion.open(AppSettings::class as KClass<RealmObject>)

            val previousSettings = appSettingsRealm.query<AppSettings>().find()
            val appSettings = AppSettings(apiToken)

            appSettingsRealm.writeBlocking {
                this.delete(previousSettings)
                this.copyToRealm(appSettings)
            }

            appSettingsRealm.close()

            return appSettings
        }

        fun setPreviousQuery(queryText: String): PreviousQuery {
            val previousQueryRealm = RealmCompanion.open(PreviousQuery::class as KClass<RealmObject>)

            val previousQuery = PreviousQuery(queryText)

            previousQueryRealm.writeBlocking {
                this.copyToRealm(previousQuery)
            }

            previousQueryRealm.close()
            return previousQuery
        }

        fun setImage(urlString: String, parentQueryID: ObjectId): Image {
            val imagesRealm = RealmCompanion.open(Image::class as KClass<RealmObject>)

            val image = Image(urlString, parentQueryID)

            imagesRealm.writeBlocking {
                this.copyToRealm(image)
            }

            imagesRealm.close()

            return image
        }
    }
}