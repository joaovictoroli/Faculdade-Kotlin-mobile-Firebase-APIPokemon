package com.example.assessmentkotlin.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assessmentkotlin.model.AppUtil
import com.example.assessmentkotlin.database.TreinadorDao
import com.example.assessmentkotlin.model.Treinador
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg


    fun authUsuario(email: String, senha: String) {
        val task = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnSuccessListener {
                TreinadorDao.selecionarTreinadorPorEmail(email)
                    .addOnSuccessListener {
                        _msg.value = "Usuário autenticado com sucesso!"
                        _status.value = true
                        Log.i("LOGIN", "ISSO AI")
                        }
                        .addOnFailureListener {
                            _msg.value = "${it.message}"
                        }

        }
    }
}
