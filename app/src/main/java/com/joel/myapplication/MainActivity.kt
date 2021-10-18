package com.joel.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var roundCounter : Int = 0
        var secuence = arrayOf(4)
        val startGame : Button = findViewById(R.id.play_button)
        startGame.setOnClickListener {
            Log.i("Estado", "Comenzando partida")
            showRound()
            startSecuence()
        }
    }

    private fun showRound(){
        Log.i("Estado", "Mostrar numero de rondas")
    }

    private fun startSecuence(){
        Log.i("Estado", "Se ejecuta el juego")
    }

    private fun userMessage(){
        Log.i("Estado", "Mensaje por toast al usuario")
    }

    private fun checkSecuence(){
        Log.i("Estado", "Comprobar que la secuencia del jugador coincide")
    }
}