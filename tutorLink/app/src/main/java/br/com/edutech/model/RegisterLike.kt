package br.com.edutech.model

data class RegisterLike (
    val userId: Int,
    val email: String,
    val like: Boolean
)