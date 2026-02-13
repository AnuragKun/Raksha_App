package com.arlabs.raksha.data.repositoryImpl

import com.arlabs.raksha.data.repositoryImpl.ReportRepositoryImpl
import com.arlabs.raksha.domain.repository.ReportRepository
import com.arlabs.raksha.domain.util.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ReportRepository {
    override suspend fun submitReport(
        type: String,
        description: String,
        severity: Float,
        location: String,
        timestamp: Long
    ): Result<Unit> {
        return try {
            val report = hashMapOf(
                "type" to type,
                "description" to description,
                "severity" to severity,
                "location" to location,
                "timestamp" to timestamp,
                "status" to "PENDING"
            )
            
            firestore.collection("reports").add(report).await()
            firestore.collection("reports").add(report).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(e.message ?: "Unknown Error")
        }
    }
}
