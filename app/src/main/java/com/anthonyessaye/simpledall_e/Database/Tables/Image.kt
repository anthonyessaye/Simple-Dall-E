package com.anthonyessaye.simpledall_e.Database.Tables

import android.os.Parcelable
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class Image(): RealmObject, Parcelable {
    var imageURLString: String = ""
        private set

    var parentQueryID: ObjectId = ObjectId.create()
        private set

    constructor(urlString: String, parentQueryID: ObjectId): this() {
        this.imageURLString = urlString
        this.parentQueryID = parentQueryID
    }


}