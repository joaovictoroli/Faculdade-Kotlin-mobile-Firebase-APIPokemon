package com.example.assessmentkotlin.database

import com.example.assessmentkotlin.model.Treinador
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

object TreinadorDao {
    private val collection = FirebaseFirestore
        .getInstance()
        .collection("treinadorKotlin")

    fun addTreinador(treinador: Treinador) : Task<Void> {
        return collection.document(treinador.email!!).set(treinador)
    }

    fun selecionarTreinadorPorEmail(email: String) : Task<DocumentSnapshot> {
        return collection.document(email).get()
    }

}