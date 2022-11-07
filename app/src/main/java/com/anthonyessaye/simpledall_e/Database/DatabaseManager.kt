package com.anthonyessaye.simpledall_e.Database

import com.anthonyessaye.simpledall_e.Database.Tables.AppSettings
import com.anthonyessaye.simpledall_e.Database.Tables.Image
import com.anthonyessaye.simpledall_e.Database.Tables.PreviousQuery
import com.anthonyessaye.simpledall_e.Enumerations.ImageSize
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.migration.RealmMigration
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import kotlin.reflect.KClass

object DatabaseManager {
    object RealmCompanion {
        private var mRealm: Realm? = null

        fun open(realmObject: KClass<RealmObject>): Realm {
            val config = RealmConfiguration
                .Builder(schema = setOf(realmObject))
                .schemaVersion(2)
                .build()
            mRealm = Realm.open(config)
            return mRealm!!
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

            var imagesToReturn = arrayListOf<Image>()

            for(image in images) {
                imagesToReturn.add(Image(image.imageURLString,image.parentQueryID))
            }

            return imagesToReturn
        }

        fun getPreviousQueries(): List<PreviousQuery> {
            val previousQueryRealm = RealmCompanion.open(PreviousQuery::class as KClass<RealmObject>)
            val previousQueries = previousQueryRealm.query<PreviousQuery>().find()
            return previousQueries.subList(0,previousQueries.size)
        }
    }

    object Write {
        fun setAppSettings(apiToken: String): AppSettings {
            val appSettingsRealm = RealmCompanion.open(AppSettings::class as KClass<RealmObject>)
            val appSettings = AppSettings(apiToken)

            appSettingsRealm.writeBlocking {
                this.copyToRealm(appSettings)
            }

            return appSettings
        }

        fun setPreviousQuery(queryText: String, queryQualityRequested: ImageSize): PreviousQuery {
            val previousQueryRealm = RealmCompanion.open(PreviousQuery::class as KClass<RealmObject>)

            val previousQuery = PreviousQuery(queryText, queryQualityRequested.value)

            previousQueryRealm.writeBlocking {
                this.copyToRealm(previousQuery)
            }

            return previousQuery
        }

        fun setImage(urlString: String, parentQueryID: ObjectId): Image {
            val imagesRealm = RealmCompanion.open(Image::class as KClass<RealmObject>)

            val image = Image(urlString, parentQueryID)

            imagesRealm.writeBlocking {
                this.copyToRealm(image)
            }

            return image
        }
    }
}