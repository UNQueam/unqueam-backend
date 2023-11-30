package com.unqueam.gamingplatform.core.tracking

class DailyMetricsReport {

    val total: Long
    val result: List<Map<String, Any>>

    constructor(totalCount: Long, result: List<Map<String, Any>>) {
        this.result = result
        this.total = totalCount
    }
}