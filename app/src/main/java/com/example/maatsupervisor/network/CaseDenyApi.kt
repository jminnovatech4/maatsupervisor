package com.example.maatsupervisor.network

import com.example.maatsupervisor.data.model.TodayCaseDeny
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object CaseDenyApi {

    private val client = OkHttpClient()

    fun fetchTodayCaseDeny(
        gid: String,
        callback: (Boolean, List<TodayCaseDeny>) -> Unit
    ) {
        val request = Request.Builder()
            .url("http://wbjssk.emri.in/MAATSUPERVISOR/public/api/dashboard/case-deny/today/$gid")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                callback(false, emptyList())
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val body = response.body?.string() ?: ""
                    val json = JSONObject(body)
                    val data = json.getJSONArray("data")

                    val list = mutableListOf<TodayCaseDeny>()

                    for (i in 0 until data.length()) {
                        val it = data.getJSONObject(i)

                        list.add(
                            TodayCaseDeny(
                                vehicleNo = it.getString("vehicle_no"),
                                todayCount = it.getInt("today_count"),
                                remark = it.optString("feedback_remarks_description"),
                                attemptedTime = it.getString("attempted_time")
                            )
                        )
                    }

                    callback(true, list)

                } catch (e: Exception) {
                    callback(false, emptyList())
                }
            }
        })
    }
}
