package com.arlabs.raksha.domain.repository

import com.arlabs.raksha.domain.util.Result

interface ReportRepository {
    suspend fun submitReport(
        type: String,
        description: String,
        severity: Float,
        location: String,
        timestamp: Long
    ): Result<Unit>
}
