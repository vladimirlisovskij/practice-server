package com.practice.server.domain.dto

import com.practice.network_entities.headers.Locale
import com.practice.network_entities.headers.Version

class DefaultHeaders(
    val locale: Locale,
    val version: Version
)

