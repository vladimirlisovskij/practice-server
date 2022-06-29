package com.practice.server.domain

import com.practice.network_entities.headers.Locale


object Messages {
    val errorHeaders = LocalizedMessage { locale ->
        when(locale) {
            Locale.RU -> "неправильно указан заголовок %s"
        }
    }

    val errorParam = LocalizedMessage { locale ->
        when(locale) {
            Locale.RU -> "неправильно указан параметр %s"
        }
    }

    val nicknameAlreadyUse = LocalizedMessage { locale ->
        when(locale) {
            Locale.RU -> "такой никнейм уже используется"
        }
    }

    val errorPasswordValidation = LocalizedMessage { locale ->
        when(locale) {
            Locale.RU -> "пароль не удовлетворяет условиям валидации"
        }
    }

    val invalidLoginPassword = LocalizedMessage { locale ->
        when(locale) {
            Locale.RU -> "неверная комбинация никнейма и пароля"
        }
    }

    fun interface LocalizedMessage {
        fun localise(locale: Locale): String
    }
}