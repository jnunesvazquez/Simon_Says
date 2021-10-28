package com.joel.myapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {

    var roundCounter : Int = 4
    var selectColor : Array<Int> = arrayOf(1, 3, 3, 2, 4)
    lateinit var redButton : Button
    lateinit var yellowButton : Button
    lateinit var greenButton : Button
    lateinit var blueButton : Button
    lateinit var round : TextView
    //lateinit var buttons : HashMap<Int, Button>

    var job : Job? = null
    var job2 : Job? = null
    var job3 : Job? = null
    var job4 : Job? = null
    var jobExtra : Job? = null

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


        /*buttons.put(1, greenButton)
        buttons.put(2, redButton)
        buttons.put(3, blueButton)
        buttons.put(4, yellowButton)*/

        round = findViewById(R.id.round_number)

        val startGame : Button = findViewById(R.id.play_button)
        startGame.setOnClickListener {
            Log.i("Estado", "Comenzando partida")
            showRound()
            job = GlobalScope.launch(Dispatchers.Main) {
                startSecuence()
            }
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

    private suspend fun startSecuence() {
        Log.i("Estado", "Se ejecuta el juego")
        selectColor += (1..4).random()
        for (i in 1..roundCounter){
            when (selectColor[i]) {
                1 -> {
                    job = GlobalScope.launch(Dispatchers.Main) {
                        suspendingTask(greenButton, lightGreenColor,  greenColor)
                    }
                }
                2 -> {
                    job = GlobalScope.launch(Dispatchers.Main) {
                        suspendingTask(redButton, lightRedColor,  redColor)
                    }
                }
                3 -> {
                    job = GlobalScope.launch(Dispatchers.Main) {
                        suspendingTask(blueButton, lightBlueColor,  blueColor)
                    }
                }
                4 -> {
                    job = GlobalScope.launch(Dispatchers.Main) {
                        suspendingTask(yellowButton, lightYellowColor,  yellowColor)
                    }
                }
            }
            //Esperamos a que la corrutina activa termine
            job?.join()
        }
    }

    private fun userMessage(){
        Log.i("Estado", "Mensaje por toast al usuario")
        Toast.makeText(this, "Has cometido un error", Toast.LENGTH_LONG).show()
        Toast.makeText(this, "Empieza de nuevo", Toast.LENGTH_LONG).show()
    }

    /*private fun checkSecuence() : Boolean{
        Log.i("Estado", "Comprobar que la secuencia del jugador coincide")
        var aux = 0
        var value : Button
        if (greenButton.isPressed){
            value = greenButton
            aux = buttons.keys.first{value == buttons[it]}
        }
        if (redButton.isPressed){
            value = redButton
            aux = buttons.keys.first{value == buttons[it]}
        }
        if (blueButton.isPressed){
            value = blueButton
            aux = buttons.keys.first{value == buttons[it]}
        }
        if (yellowButton.isPressed){
            value = yellowButton
            aux = buttons.keys.first{value == buttons[it]}
        }
        for(i in 1..roundCounter){
            return aux == selectColor[i]
        }
        Toast.makeText(this, "Repetir secuencia", Toast.LENGTH_LONG).show()
        return true
    }*/

    private suspend fun suspendingTask(button : Button, colorChange : Int, colorDefault: Int){
        delay(1000)
        button.setBackgroundColor(colorChange)
        delay(2000)
        button.setBackgroundColor(colorDefault)
    }

    private suspend fun suspendingTaskExtra(){
        delay(2000)
    }
}