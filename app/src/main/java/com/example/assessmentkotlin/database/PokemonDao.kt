package com.example.assessmentkotlin.database

import com.example.assessmentkotlin.model.Pokemon
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object PokemonDao {

    private var email = FirebaseAuth.getInstance().currentUser.email
    private val collection = FirebaseFirestore
        .getInstance()
        .collection("treinadorKotlin")
        .document(email)
        .collection("Pokemon")

    private val storageReference = FirebaseStorage.getInstance().reference

    fun addPokemon(pokemon: Pokemon) : Task<Void> {
        return collection.document(pokemon.nome!!).set(pokemon)
    }

    fun isAlreadySelected(nome: String) : Task<DocumentSnapshot> {
        return collection.document(nome).get()
    }

    fun getStorageRef(nome: String) : StorageReference {
        return storageReference.child("Pokemon/${nome}.png")
    }

    fun allPokemon() : CollectionReference {
        return collection
    }

    fun getFav() : Task<QuerySnapshot> {
        return collection.whereEqualTo("favorito", true).get()
    }

    fun sortByPokedexNumber(): Query {
        return collection.orderBy("numberPokedex", Query.Direction.ASCENDING)
    }

    fun sortByPokedexNumberDesc(): Query {
        return collection.orderBy("numberPokedex", Query.Direction.DESCENDING)
    }

    fun delPokemon(pokemon: Pokemon) : Task<Void> {
        return collection.document(pokemon.nome!!).delete()
    }

    fun getPokemon(nome: String): Task<DocumentSnapshot> {
        return collection.document(nome).get()
    }



}

