package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LeadDao {

    @Query("SELECT * FROM customer_leads ORDER BY created_at DESC")
    fun getAllLeads(): Flow<List<LeadEntity>>

    @Query("SELECT * FROM customer_leads WHERE name LIKE '%' || :query || '%' OR mobile LIKE '%' || :query || '%' ORDER BY created_at DESC")
    fun searchLeads(query: String): Flow<List<LeadEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLead(lead: LeadEntity)

    @Update
    suspend fun updateLead(lead: LeadEntity)

    @Query("UPDATE customer_leads SET status = :newStatus WHERE id = :leadId")
    suspend fun updateStatus(leadId: String, newStatus: String)

    @Query("DELETE FROM customer_leads WHERE id = :leadId")
    suspend fun deleteLeadById(leadId: String)

    @Query("SELECT COUNT(*) FROM customer_leads")
    suspend fun getLeadCount(): Int

    @Query("DELETE FROM customer_leads")
    suspend fun deleteAllLeads()
}
