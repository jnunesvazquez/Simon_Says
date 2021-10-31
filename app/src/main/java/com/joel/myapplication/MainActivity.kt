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

    var roundCounter : Int = 0
    var selectColor = mutableListOf<Int>()
    var aux = mutableListOf<Int>()

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
        val checkGame : Button = findViewById(R.id.check_button)
        startGame.setOnClickListener {
            Log.i("Estado", "Comenzando partida")
            job = GlobalScope.launch(Dispatchers.Main) {
                checkGame.isEnabled = false
                startGame.isEnabled = false
                showRound()
                startSecuence()
                checkGame.isEnabled = true
                putSecuence()
            }
        }
        checkGame.setOnClickListener {
            job = GlobalScope.launch(Dispatchers.Main) {
                if (checkSecuence()) {
                    roundCounter++
                    showRound()
                    aux.clear()
                    Log.i("Prueba", "Secuencia correcta")
                    Log.i("Prueba", selectColor.toString())
                    Log.i("Prueba", aux.toString())
                    startSecuence()
                } else {
                    Log.i("Prueba2", "Secuencia mala")
                    Log.i("Prueba2", selectColor.toString())
                    Log.i("Prueba2", aux.toString())
                    userMessage()
                    roundCounter = 0
                    selectColor.clear()
                    aux.clear()
                    round.visibility = TextView.INVISIBLE
                    showRound()
                    startGame.isEnabled = true
                }
            }
        }
    }

    private fun showRound(){
        Log.i("Estado", "Mostrar numero de rondas")
        if (round.visibility == TextView.INVISIBLE){
            round.visibility = TextView.VISIBLE
        }
        //roundCounter++
        Log.i("Estado", "roundCounter: " + roundCounter)
        round.text = (roundCounter + 1).toString()
        //roundCounter--
        Log.i("Estado", "roundCounter: " + roundCounter)
    }

    private suspend fun startSecuence() {
        Log.i("Estado", "Se ejecuta el juego")
        selectColor.add(roundCounter, (0..3).random())
        Log.i("Estado", selectColor.toString())
        for (i in 0..roundCounter){
            when (selectColor[i]) {
                0 -> {
                    secuence(greenButton, lightGreenColor, greenColor)
                }
                1 -> {
                    secuence(redButton, lightRedColor, redColor)
                }
                2 -> {
                    secuence(blueButton, lightBlueColor, blueColor)
                }
                3 -> {
                    secuence(yellowButton, lightYellowColor, yellowColor)
                }
            }
        }
        Toast.makeText(this, "Repetir la secuencia", Toast.LENGTH_LONG).show()
    }

    private fun userMessage(){
        Log.i("Estado", "Mensaje por toast al usuario")
        Toast.makeText(this, "Has cometido un error", Toast.LENGTH_LONG).show()
        Toast.makeText(this, "Empieza de nuevo", Toast.LENGTH_LONG).show()
    }

    private fun putSecuence() {
        Log.i("Estado", "Comprobar que la secuencia del jugador coincide")
        setListener(greenButton)
        setListener(redButton)
        setListener(blueButton)
        setListener(yellowButton)
    }

    private fun setListener(button: Button){
        var value : Int
        button.setOnClickListener {
            value = buttons.keys.first(){button == buttons[it]}
            Log.i("Estado", value.toString())
            aux.add(value)
        }
    }

    private fun checkSecuence() : Boolean{
        //Toast.makeText(this, "Secuencia correcta", Toast.LENGTH_LONG).show()
        return aux == selectColor
    }

    private suspend fun secuence(button: Button, colorChange : Int, colorDefault: Int){
        job = GlobalScope.launch(Dispatchers.Main) {
            delay(500)
            button.setBackgroundColor(colorChange)
            delay(1000)
            button.setBackgroundColor(colorDefault)
        }
        //Esperamos a que la corrutina activa termine
        job?.join()
    }
}