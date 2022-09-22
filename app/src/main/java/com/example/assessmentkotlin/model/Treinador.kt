package com.example.assessmentkotlin.model

import com.google.firebase.firestore.DocumentId

class Treinador (
    val apelido: String? = null,
    val genero: String? = null,
    @DocumentId
    val email: String? = null
        )
