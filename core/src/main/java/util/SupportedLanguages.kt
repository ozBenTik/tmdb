package com.example.core.util

sealed class SupportedLanguages(open val code: String, open val description: String) {
    data class English(override val code: String = "en-US", override val description: String = "English") : SupportedLanguages(code, description)
    data class Hebrew(override val code: String = "he-IL", override val description: String = "Hebrew") : SupportedLanguages(code, description)
    data class Empty(override val code: String = "", override val description: String = "") : SupportedLanguages(code, description)
}