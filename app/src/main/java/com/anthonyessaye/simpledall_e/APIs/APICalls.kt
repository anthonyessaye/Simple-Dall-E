package com.anthonyessaye.simpledall_e.APIs

import com.anthonyessaye.simpledall_e.Database.DatabaseManager
import com.anthonyessaye.simpledall_e.Database.Tables.AppSettings
import com.anthonyessaye.simpledall_e.Enumerations.ImageSize
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Parameters
import org.json.JSONObject

object APICalls {
    object POST {
        fun generateImages(query: String, numberOfImages: Int, imageSize: ImageSize, completion: (success: Boolean, urlArrayList: ArrayList<String>?) -> Void) {
            val appSettings: AppSettings? = DatabaseManager.Read.getAppSettings()

            if (appSettings != null) {
                val apiToken = appSettings!!.getAPIToken()
                var params: ArrayList<Pair<String, Any>> = arrayListOf()
                var headers: Map<String, Any>

                var contentType = Pair("Content-Type", "application/json")
                var authorization = Pair("Authorization", "Bearer ${apiToken}")
                headers = mapOf(contentType,authorization)

                params.add(Pair("prompt",query))
                params.add(Pair("n", numberOfImages))
                params.add(Pair("size", "${(imageSize.ordinal + 1) * 256}x${(imageSize.ordinal + 1) * 256}"))

                Fuel.post(APIEndpoints.generateURL, params).header(headers).response { request, response, result ->
                    result.fold(
                        success = {
                            if (it != null) {
                                val str = String(it)
                                val obj = JSONObject(str)
                                val dataObj = obj.optJSONArray("data")

                                if (dataObj != null) {
                                    var urlArrayList: ArrayList<String> = arrayListOf()

                                    for(n in 0..dataObj!!.length() - 1) {
                                        val urlJSONObject = dataObj!!.getJSONObject(n);
                                        val urlString = urlJSONObject.getString("url")

                                        urlArrayList.add(urlString)
                                    }

                                    completion(true, urlArrayList)
                                }

                                completion(false, null)
                            }

                            completion(false, null)
                        },
                        failure = {
                            completion(false, null)
                        }
                    )
                }
            }
        }
    }
}