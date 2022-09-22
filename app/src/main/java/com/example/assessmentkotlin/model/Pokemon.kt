package com.example.assessmentkotlin.model

import com.google.firebase.firestore.DocumentId

class Pokemon(
    var numberPokedex: Int? = null,
    var type: String? = null,
    var urlImage: String? = null,
    var favorito: Boolean? = false,
    @DocumentId
    var nome: String? = null
)