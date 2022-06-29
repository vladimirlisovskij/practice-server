package com.practice.server

import com.practice.server.di.DaggerAppComponent

fun main() {
    val appComponent = DaggerAppComponent.create()
    appComponent.getAppEngine().start(wait = true)
}

