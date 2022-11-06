package com.anthonyessaye.simpledall_e.Database.Tables

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class PreviousQuery: RealmObject {
    @PrimaryKey
    private val queryID: ObjectId = ObjectId.create()
    private var queryText: String = ""

    constructor(queryText: String): this() {
        this.queryText = queryText
    }

    constructor() { }
}