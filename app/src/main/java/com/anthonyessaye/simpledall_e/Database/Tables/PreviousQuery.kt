package com.anthonyessaye.simpledall_e.Database.Tables

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class PreviousQuery: RealmObject {
    var queryID: ObjectId = ObjectId.create()
        private set

    var queryText: String = ""
        private set

    constructor(queryText: String): this() {
        this.queryText = queryText
    }

    constructor() { }
}