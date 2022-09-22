package com.example.assessmentkotlin.ui.login.cadastro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assessmentkotlin.database.TreinadorDao
import com.example.assessmentkotlin.model.Treinador
import com.google.firebase.auth.FirebaseAuth

class CadastroViewModel : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    private fun addTreinador(treinador: Treinador) {
        TreinadorDao.addTreinador(treinador)
    }

    fun addUserAuth(treinador: Treinador, senha: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(treinador.email!!, senha)
            .addOnSuccessListener {
                _msg.value = "Usuario Cadastrado com sucesso ${senha}"
                addTreinador(treinador)
                _status.value = true
            }
            .addOnFailureListener {
                _msg.value = "Credenciais invalidas"
                Log.i("Cadastro", "${it.message}")
            }
    }

}