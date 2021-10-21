package com.joel.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var roundCounter : Int = 1
    var secuence : Array<Int> = arrayOf(1, 2, 3, 4)
    var comprobar : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startGame : Button = findViewById(R.id.play_button)
        startGame.setOnClickListener {
            Log.i("Estado", "Comenzando partida")
            showRound()
            startSecuence()
        }
    }

    private fun showRound(){
        Log.i("Estado", "Mostrar numero de rondas")
        val round : TextView = findViewById(R.id.round_number)
        round.visibility = TextView.VISIBLE
        round.text = roundCounter.toString()
    }

    private fun startSecuence(){
        Log.i("Estado", "Se ejecuta el juego")

        while (comprobar == true){
            roundCounter++
        }
    }

    private fun userMessage(){
        Log.i("Estado", "Mensaje por toast al usuario")
    }

    private fun checkSecuence(){
        Log.i("Estado", "Comprobar que la secuencia del jugador coincide")
    }
}