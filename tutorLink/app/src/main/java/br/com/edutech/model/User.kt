package br.com.edutech.model

data class User(
    val id: String? = null,
    val email: String? = null,
    val password: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val description: String? = null,
    val role: Boolean? = null,
    val degree: String? = null,
    val availability: Number? = null,
    val location: String? = null,
    val seen: Boolean? = null,
    val interests: List<String> = listOf(),
    val liked: Boolean? = null
)