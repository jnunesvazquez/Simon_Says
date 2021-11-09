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

    lateinit var startGame : Button
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
        startGame = findViewById(R.id.play_button)

        startGame.setOnClickListener {
            Log.i("Estado", "Comenzando partida")
            job = GlobalScope.launch(Dispatchers.Main) {
                startGame.isEnabled = false
                showRound()
                startSecuence()
                putSecuence()
            }
        }
    }

    private fun showRound(){
        Log.i("Estado", "Mostrar numero de rondas")
        if (round.visibility == TextView.INVISIBLE){
            round.visibility = TextView.VISIBLE
        }
        round.text = (roundCounter + 1).toString()
    }

    private suspend fun startSecuence() {
        Log.i("Estado", "Empieza la secuencia")
        enableDisableButtons(buttons,false)
        selectColor.add(roundCounter, (0..3).random())
        Log.i("Estado", selectColor.toString())
        for (i in 0..roundCounter){
            when (selectColor[i]) {
                0 -> {secuence(greenButton, lightGreenColor, greenColor, 1000)}
                1 -> {secuence(redButton, lightRedColor, redColor, 1000)}
                2 -> {secuence(blueButton, lightBlueColor, blueColor, 1000)}
                3 -> {secuence(yellowButton, lightYellowColor, yellowColor, 1000)}
            }
        }
        enableDisableButtons(buttons, true)
        Toast.makeText(this, "Repetir la secuencia", Toast.LENGTH_LONG).show()
    }

    private suspend fun userMessage(){
        Log.i("Estado", "Mensaje por toast al usuario")
        if (checkSecuence()){
            Toast.makeText(this, "Secuencia correcta", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(applicationContext, "No has introducido la secuencia correcta", Toast.LENGTH_LONG).show()
            Toast.makeText(this, "Empieza de nuevo", Toast.LENGTH_LONG).show()
        }
    }

    private suspend fun putSecuence() {
        Log.i("Estado", "Comprobando que la secuencia del jugador coincide")
        setListener(greenButton, lightGreenColor, greenColor, "verde")
        setListener(redButton, lightRedColor, redColor, "rojo")
        setListener(blueButton, lightBlueColor, blueColor, "azul")
        setListener(yellowButton, lightYellowColor , yellowColor, "amarillo")
        if (checkSecuence()){
            correctSecuence()
        } else {
            incorrectSecuence()
        }
    }

    private fun setListener(button: Button, colorChange : Int, colorDefault: Int, nameButton: String){
        var value : Int
        button.setOnClickListener {
            if (button.isPressed){
                job = GlobalScope.launch(Dispatchers.Main) {
                    secuence(button, colorChange, colorDefault, 200)
                }
            }
            Toast.makeText(this, "Has pulsado el boton $nameButton", Toast.LENGTH_LONG).show()
            value = buttons.keys.first {button == buttons[it]}
            Log.i("Valor", value.toString())
            aux.add(value)
        }
    }

    private suspend fun checkSecuence() : Boolean{
        Toast.makeText(this, "Secuencia correcta", Toast.LENGTH_LONG).show()
        while (selectColor.size != aux.size){
            delay(500)
            Log.i("Auxiliar", aux.toString())
        }
        return aux == selectColor
    }

    private suspend fun secuence(button: Button, colorChange : Int, colorDefault: Int, del: Long){
        job = GlobalScope.launch(Dispatchers.Main) {
            button.setBackgroundColor(colorChange)
            delay(del)
            button.setBackgroundColor(colorDefault)
            delay(500)
        }
        //Esperamos a que la corrutina activa termine
        job?.join()
    }

    private fun enableDisableButtons(hashMap: HashMap<Int, Button>, boolean: Boolean) {
        hashMap.forEach { (_, u) -> u.isEnabled = boolean }
    }

    private suspend fun incorrectSecuence(){
        userMessage()
        roundCounter = 0
        selectColor.clear()
        aux.clear()
        round.visibility = TextView.INVISIBLE
        startGame.isEnabled = true
    }

    private suspend fun correctSecuence(){
        userMessage()
        aux.clear()
        roundCounter++
        showRound()
        startSecuence()
        putSecuence()
    }
}