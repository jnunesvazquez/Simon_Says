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

    var roundCounter : Int = 2
    var checkCounter : Int = roundCounter
    var selectColor : Array<Int> = arrayOf(3,2,1,0)
    var aux = 0

    lateinit var redButton : Button
    lateinit var yellowButton : Button
    lateinit var greenButton : Button
    lateinit var blueButton : Button
    lateinit var round : TextView
    lateinit var buttons : HashMap<Int, Button>

    var job : Job? = null

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

        buttons = HashMap()
        buttons.put(0, greenButton)
        buttons.put(1, redButton)
        buttons.put(2, blueButton)
        buttons.put(3, yellowButton)

        round = findViewById(R.id.round_number)

        val startGame : Button = findViewById(R.id.play_button)
        startGame.setOnClickListener {
            Log.i("Estado", "Comenzando partida")
            job = GlobalScope.launch(Dispatchers.Main) {
                do {
                    showRound()
                    roundCounter++
                    checkCounter = roundCounter
                    startSecuence()
                    putSecuence()
                    checkSecuence()
                }while (checkSecuence())
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

    private suspend fun startSecuence() {
        Log.i("Estado", "Se ejecuta el juego")
        //selectColor += (0..3).random()
        for (i in 0..roundCounter){
                when (selectColor[i]) {
                    0 -> {
                        secuence(greenButton, lightGreenColor, greenColor)
                        Log.i("Estado", "Has pulsado el boton amarillo")
                    }
                    1 -> {
                        secuence(redButton, lightRedColor, redColor)
                        Log.i("Estado", "Has pulsado el boton amarillo")
                    }
                    2 -> {
                        secuence(blueButton, lightBlueColor, blueColor)
                        Log.i("Estado", "Has pulsado el boton amarillo")
                    }
                    3 -> {
                        secuence(yellowButton, lightYellowColor, yellowColor)
                        Log.i("Estado", "Has pulsado el boton amarillo")
                    }
                }
            //Esperamos a que la corrutina activa termine
        }
    }

    private fun userMessage(){
        Log.i("Estado", "Mensaje por toast al usuario")
        Toast.makeText(this, "Has cometido un error", Toast.LENGTH_LONG).show()
        Toast.makeText(this, "Empieza de nuevo", Toast.LENGTH_LONG).show()
    }

    private fun putSecuence() {
        Log.i("Estado", "Comprobar que la secuencia del jugador coincide")
        aux = 0
        while (checkCounter > 0){
            setListener(greenButton)
            setListener(redButton)
            setListener(blueButton)
            setListener(yellowButton)
        }
    }

    private fun setListener(button: Button){
        var value : Button
        button.setOnClickListener {
            checkCounter--
            value = greenButton
            aux = buttons.keys.first{value == buttons[it]}
            Log.i("Estado", aux.toString())
        }
    }

    private fun checkSecuence() : Boolean{
        for(i in 0..roundCounter){
            return aux == selectColor[i]
        }
        Toast.makeText(this, "Repetir secuencia", Toast.LENGTH_LONG).show()
        return true
    }

    private suspend fun secuence(button: Button, colorChange : Int, colorDefault: Int){
        job = GlobalScope.launch(Dispatchers.Main) {
            delay(500)
            button.setBackgroundColor(colorChange)
            delay(1000)
            button.setBackgroundColor(colorDefault)
        }
        job?.join()
    }
}