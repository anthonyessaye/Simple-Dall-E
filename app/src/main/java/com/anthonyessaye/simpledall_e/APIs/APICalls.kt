package com.anthonyessaye.simpledall_e.APIs

import com.anthonyessaye.simpledall_e.Database.DatabaseManager
import com.anthonyessaye.simpledall_e.Database.Tables.AppSettings
import com.anthonyessaye.simpledall_e.Enumerations.ImageSize
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.core.extensions.jsonBody
import org.json.JSONObject

object APICalls {
    object POST {
        /*
            Call ported from API documentation through the CURL method on the following:
            https://beta.openai.com/docs/guides/images/usage
         */

        fun generateImages(query: String, numberOfImages: Int, imageSize: ImageSize, completion: (success: Boolean, urlArrayList: ArrayList<String>?) -> Unit) {
            val appSettings: AppSettings? = DatabaseManager.Read.getAppSettings()

            if (appSettings != null) {
                val apiToken = appSettings.getAPIToken()
                var params = JSONObject()
                var headers: Map<String, String> = mapOf("Content-Type" to "application/json",
                    "Authorization" to "Bearer ${apiToken}")

                val size = (imageSize.resolution).toString()
                params.put("prompt", query)
                params.put("n", numberOfImages)
                params.put("size", size + "x" + size)

                Fuel.post(APIEndpoints.generateURL)
                    .jsonBody(params.toString())
                    .header(headers)
                    .timeout(60000)
                    .timeoutRead(60000)
                    .responseString { request, response, result ->
                    result.fold(
                        success = {
                            if (it != null) {
                                val obj = JSONObject(it)
                                val dataObj = obj.optJSONArray("data")

                                if (dataObj != null) {
                                    var urlArrayList: ArrayList<String> = arrayListOf()

                                    for(n in 0..dataObj.length() - 1) {
                                        val urlJSONObject = dataObj.getJSONObject(n);
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