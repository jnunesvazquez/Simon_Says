package com.joel.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    var roundCounter : Int = 3
    var selectColor : Array<Int> = arrayOf(1, 3, 3, 2)
    lateinit var redButton : Button
    lateinit var yellowButton : Button
    lateinit var greenButton : Button
    lateinit var blueButton : Button
    lateinit var round : TextView

    val redColor = Color.alpha(R.color.red)
    val greenColor = Color.alpha(R.color.green)
    val yellowColor = Color.alpha(R.color.yellow)
    val blueColor = Color.alpha(R.color.blue)

    val lightRedColor = Color.parseColor("#FF4B4B")
    val lightGreenColor = Color.parseColor("#93FF4B")
    val lightYellowColor = Color.parseColor("#FFFE00")
    val lightBlueColor = Color.parseColor("#00FFF9")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        redButton = findViewById(R.id.red_button)
        yellowButton = findViewById(R.id.yellow_button)
        greenButton = findViewById(R.id.green_button)
        blueButton = findViewById(R.id.blue_button)
        round = findViewById(R.id.round_number)
        val startGame : Button = findViewById(R.id.play_button)
        startGame.setOnClickListener {
            Log.i("Estado", "Comenzando partida")
            showRound()
            startSecuence()
            /*if (checkSecuence()){
                roundCounter++
                showRound()
                startSecuence()
            }else {
                userMessage()
                round.visibility = TextView.INVISIBLE
                roundCounter = 1
            }*/
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
        runBlocking {
            launch {
                //selectColor += (1..5).random()
                for (i : Int in 1..roundCounter){
                    when (selectColor[i]) {
                        1 -> {
                            greenButton.setBackgroundColor(lightGreenColor)
                            delay(2000)
                            greenButton.setBackgroundColor(lightRedColor)
                            delay(1000)
                        }
                        2 -> {
                            redButton.setBackgroundColor(lightRedColor)
                            delay(2000)
                            redButton.setBackgroundColor(lightGreenColor)
                            delay(1000)
                        }
                        3 -> {
                            blueButton.setBackgroundColor(lightBlueColor)
                            delay(2000)
                            blueButton.setBackgroundColor(lightYellowColor)
                            delay(1000)
                        }
                        4 -> {
                            yellowButton.setBackgroundColor(lightYellowColor)
                            delay(2000)
                            yellowButton.setBackgroundColor(lightBlueColor)
                            delay(1000)
                        }
                    }
                }
            }
        }
    }

    private fun userMessage(){
        Log.i("Estado", "Mensaje por toast al usuario")
        Toast.makeText(this, "Has cometido un error", Toast.LENGTH_LONG).show()
        Toast.makeText(this, "Empieza de nuevo", Toast.LENGTH_LONG).show()
    }

    private fun checkSecuence() : Boolean{
        Log.i("Estado", "Comprobar que la secuencia del jugador coincide")
        Toast.makeText(this, "Repetir secuencia", Toast.LENGTH_LONG).show()
        return true
    }
}