package com.joel.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    var roundCounter : Int = 1
    var secuence = arrayOf<Int>()
    var comprobar : Boolean = true
    val redButton : Button = findViewById(R.id.red_button)
    val yellowButton : Button = findViewById(R.id.yellow_button)
    val greenButton : Button = findViewById(R.id.green_button)
    val blueButton : Button = findViewById(R.id.blue_button)
    val round : TextView = findViewById(R.id.round_number)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startGame : Button = findViewById(R.id.play_button)
        startGame.setOnClickListener {
            Log.i("Estado", "Comenzando partida")
            showRound()
            startSecuence()
            if (checkSecuence()){
                roundCounter++
                showRound()
                startSecuence()
            }else {
                userMessage()
                round.visibility = TextView.INVISIBLE
                roundCounter = 1
            }
        }
    }

    private fun showRound(){
        Log.i("Estado", "Mostrar numero de rondas")
        if (round.visibility == TextView.INVISIBLE){
            round.visibility = TextView.VISIBLE
        }
        round.text = roundCounter.toString()
    }

    private fun startSecuence(){
        Log.i("Estado", "Se ejecuta el juego")

    }

    private fun userMessage(){
        Log.i("Estado", "Mensaje por toast al usuario")
        Toast.makeText(this, "Repetir secuencia", Toast.LENGTH_LONG).show()
    }

    private fun checkSecuence() : Boolean{
        Log.i("Estado", "Comprobar que la secuencia del jugador coincide")
    }
}