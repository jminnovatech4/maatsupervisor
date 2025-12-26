package com.example.maatsupervisor.network

import com.example.maatsupervisor.data.model.VehicleDashboard
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object DashboardApi {

    private val client = OkHttpClient()

    fun fetchVehicles(
        gid: String,
        callback: (Boolean, List<VehicleDashboard>) -> Unit
    ) {

        val request = Request.Builder()
            .url("http://wbjssk.emri.in/MAATSUPERVISOR/public/api/dashboard/vehicles/$gid")
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
                    val dataArray = json.getJSONArray("data")

                    val vehicles = mutableListOf<VehicleDashboard>()

                    for (i in 0 until dataArray.length()) {
                        val it = dataArray.getJSONObject(i)

                        vehicles.add(
                            VehicleDashboard(
                                sl = it.getInt("Sl"),
                                vehicleNo = it.getString("Vehicles no"),
                                dist = it.getString("Dist"),
                                baseLocation = it.getString("Baselocation"),
                                type = it.getString("type"),
                                mobile = it.get("Mobile").toString(),
//                                todayCase = it.getInt("today case count"),
//                                yesterdayCase = it.getInt("yesterday casecount"),
//                                monthCase = it.getInt("month casecount"),
//                                todayKm = it.getInt("today_km"),
//                                yesterdayKm = it.getInt("yesterday_km"),
//                                monthKm = it.getInt("Month_km"),
//                                todayServiceDeny = it.getInt("today Service Deny"),
//                                monthServiceDeny = it.getInt("Month Service Deny"),
//                                todayUad = it.getInt("today_UAD"),
//                                monthUad = it.getInt("Month UAD")
                            )
                        )
                    }

                    callback(true, vehicles)

                } catch (e: Exception) {
                    callback(false, emptyList())
                }
            }
        })
    }
}
