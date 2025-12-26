package com.example.maatsupervisor.network

import com.example.maatsupervisor.data.model.DashboardSummary
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object DashboardSummaryApi {

    private val client = OkHttpClient()

    fun fetchSummary(
        emeGid: String,
        callback: (DashboardSummary?) -> Unit
    ) {
        val request = Request.Builder()
            .url("http://wbjssk.emri.in/MAATSUPERVISOR/public/api/dashboard/summary/$emeGid")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val body = response.body?.string() ?: return callback(null)
                    val json = JSONObject(body)

                    if (!json.getBoolean("status")) {
                        callback(null)
                        return
                    }

                    val data = json.getJSONObject("data")

                    callback(
                        DashboardSummary(
                            totalVehicle = data.getInt("totalVehicle"),
                            jssk = data.getInt("jssk"),
                            backup = data.getInt("backup"),
                            vip = data.getInt("vip"),
                            totalCaseDeny30 = data.getInt("totalCaseDeny30"),
                            yourAction = data.getInt("yourAction")
                        )
                    )
                } catch (e: Exception) {
                    callback(null)
                }
            }
        })
    }
}
