package com.example.data.repository

import com.example.data.local.LeadDao
import com.example.data.local.LeadEntity
import kotlinx.coroutines.flow.Flow

class LeadRepository(private val leadDao: LeadDao) {

    val allLeads: Flow<List<LeadEntity>> = leadDao.getAllLeads()

    fun searchLeads(query: String): Flow<List<LeadEntity>> = leadDao.searchLeads(query)

    suspend fun insertLead(lead: LeadEntity) = leadDao.insertLead(lead)

    suspend fun updateStatus(leadId: String, newStatus: String) = leadDao.updateStatus(leadId, newStatus)

    suspend fun deleteLead(leadId: String) = leadDao.deleteLeadById(leadId)

    suspend fun getCount(): Int = leadDao.getLeadCount()

    suspend fun deleteAll() = leadDao.deleteAllLeads()
}
