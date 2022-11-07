package com.anthonyessaye.simpledall_e.Database.Tables

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class PreviousQuery: RealmObject {
    var queryID: ObjectId = ObjectId.create()
        private set

    var queryText: String = ""
        private set

    var queryQualityRequested: Int = 0
        private set

    constructor(queryText: String, queryQualityRequested: Int): this() {
        this.queryText = queryText
        this.queryQualityRequested = queryQualityRequested
    }

    constructor() { }
}