package com.example.maatsupervisor.data.model

data class VehicleDashboard(
    val sl: Int,
    val vehicleNo: String,
    val dist: String,
    val baseLocation: String,
    val type: String,
    val mobile: String,
    val todayDenyCount: Int = 0
//    val todayCase: Int,
//    val yesterdayCase: Int,
//    val monthCase: Int,
//    val todayKm: Int,
//    val yesterdayKm: Int,
//    val monthKm: Int,
//    val todayServiceDeny: Int,
//    val monthServiceDeny: Int,
//    val todayUad: Int,
//    val monthUad: Int
)
