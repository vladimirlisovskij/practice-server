package com.practice.server

import com.practice.server.di.DaggerAppComponent

const val DB_PATH = "--database"
const val DEFAULT_DB_PATH = "./myServicesBd.sqlite3"

const val INTERFACE = "--interface"
const val DEFAULT_INTERFACE = "127.0.0.1"

const val PORT = "--port"
const val DEFAULT_PORT = "8080"

private fun extractArgumentValueByKey(arguments: Array<String>, key: String, defaultValue: String): String {
    val argument = arguments.firstOrNull { it.startsWith(key) } ?: return defaultValue
    if (argument.length <= key.length + 1 || argument[key.length] != '=') return defaultValue
    val value = argument.substring(key.length + 1, argument.length)
    return value.ifBlank { defaultValue }
}

fun main(args: Array<String>) {
    val appComponent = DaggerAppComponent.factory().create(
        databasePath = extractArgumentValueByKey(args, DB_PATH, DEFAULT_DB_PATH),
        hostInterface = extractArgumentValueByKey(args, INTERFACE, DEFAULT_INTERFACE),
        hostPort = extractArgumentValueByKey(args, PORT, DEFAULT_PORT).toInt()
    )

    appComponent.getAppEngine().start(wait = true)
}

