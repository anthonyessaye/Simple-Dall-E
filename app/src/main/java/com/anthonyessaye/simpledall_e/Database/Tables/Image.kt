package com.anthonyessaye.simpledall_e.Database.Tables

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Image: RealmObject {

    @PrimaryKey
    private val imageID: ObjectId = ObjectId.create()
    private var imageURLString: String = ""
    private var parentQueryID: ObjectId = ObjectId.create()

    constructor(urlString: String, parentQueryID: ObjectId): this() {
        this.imageURLString = urlString
        this.parentQueryID = parentQueryID
    }

    constructor() { }
}