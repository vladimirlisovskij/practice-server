package com.practice.server.domain.dto

class ServiceData(
    val id: Long,
    val ownerId: Long,
    val title: String,
    val description: String,
    val startTime: Long,
    val endTime: Long,
    val categories: List<Long>
)