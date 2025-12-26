package com.example.maatsupervisor.network

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

object LoginApi {

    private val client = OkHttpClient()

    fun login(
        gid: String,
        passcode: String,
        callback: (Boolean, JSONObject?) -> Unit
    ) {
        val json = JSONObject().apply {
            put("gid", gid)
            put("passcode", passcode)
        }

        val requestBody = json.toString()
            .toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("http://wbjssk.emri.in/MAATSUPERVISOR/public/api/auth/login")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: java.io.IOException) {
                callback(false, null)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                try {
                    val jsonResp = JSONObject(body ?: "{}")
                    callback(response.isSuccessful, jsonResp)
                } catch (e: Exception) {
                    callback(false, null)
                }
            }
        })
    }
}
