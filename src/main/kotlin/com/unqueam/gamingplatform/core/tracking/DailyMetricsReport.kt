package com.unqueam.gamingplatform.core.tracking

class DailyMetricsReport {

    val result: List<Map<String, Any>>

    constructor(result: List<Map<String, Any>>) {
        this.result = result
    }
}