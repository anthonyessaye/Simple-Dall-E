package com.anthonyessaye.simpledall_e.Database.Tables

import io.realm.kotlin.types.RealmObject

class AppSettings: RealmObject {
    private var apiToken: String = ""

    constructor(apiToken: String): this() {
        this.apiToken = apiToken
    }

    constructor() { }

    fun getAPIToken(): String {
        return apiToken
    }
}