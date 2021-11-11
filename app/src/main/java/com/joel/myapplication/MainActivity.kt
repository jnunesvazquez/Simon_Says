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

    /**
     * Metodo para mostrar la ronda vigente
     */
    private fun showRound(){
        Log.i("Estado", "Mostrar numero de rondas")
        if (round.visibility == TextView.INVISIBLE){
            round.visibility = TextView.VISIBLE
        }
        round.text = (roundCounter + 1).toString()
    }

    /**
     * Metodo para comenzar la secuencia
     */
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

    /**
     * Metodo que muestra un mensaje
     * al final de la ronda dependiendo
     * de si la secuencia introducida por el usuario
     * es correcta o incorrecta
     */
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

    /**
     * Metodo que recibe la secuencia
     * introducida por el usuario
     * y comprueba la secuencia
     */
    private suspend fun putSecuence() {
        Log.i("Estado", "Comprobando que la secuencia del jugador coincide")
        setListener(greenButton, lightGreenColor, greenColor)
        setListener(redButton, lightRedColor, redColor)
        setListener(blueButton, lightBlueColor, blueColor)
        setListener(yellowButton, lightYellowColor , yellowColor)
        if (checkSecuence()){
            correctSecuence()
        } else {
            incorrectSecuence()
        }
    }

    /**
     * Metodo que detecta las interaccion
     * del usuario con un boton de juego
     *
     * @param button Boton de juego específico
     * @param colorChange Iluminación del boton por interacción
     * @param colorDefault Color por defecto del boton
     */
    private fun setListener(button: Button, colorChange : Int, colorDefault: Int){
        button.setOnClickListener {
            if (button.isPressed){
                job = GlobalScope.launch(Dispatchers.Main) {
                    secuence(button, colorChange, colorDefault, 200)
                }
            }
            aux.add(buttons.keys.first {button == buttons[it]})     //Devuelve la primera clave que se comprueba en la iteración
        }
    }

    /**
     * Metodo que comprueba la secuencia
     * introducida por el usuario con
     * la secuencia creada por la aplicación
     *
     * @return La comprobación de la secuencia
     */
    private suspend fun checkSecuence() : Boolean{
        while (selectColor.size != aux.size){
            delay(500)
        }
        return aux == selectColor
    }

    /**
     * Metodo que ejecuta la secuencia definida
     * por la aplicación con corrutinas
     *
     * @param button Boton de juego específico
     * @param colorChange Iluminación del boton por interacción
     * @param colorDefault Color por defecto del boton
     * @param del Retardo temporal
     */
    private suspend fun secuence(button: Button, colorChange : Int, colorDefault: Int, del: Long){
        job = GlobalScope.launch(Dispatchers.Main) {
            button.setBackgroundColor(colorChange)
            delay(del)
            button.setBackgroundColor(colorDefault)
            delay(500)
        }
        job?.join()    //Esperamos a que la corrutina activa termine
    }

    /**
     * Metodo para habilitar o deshabilitar la interaccion del usuario
     * con los botones mientras se ejecuta la secuencia
     *
     * @param hashMap Estructura de datos donde se almacenan los botones
     * @param boolean True para habilitar y False para deshabilitar
     */
    private fun enableDisableButtons(hashMap: HashMap<Int, Button>, boolean: Boolean) {
        hashMap.forEach { (_, button) -> button.isEnabled = boolean }
    }

    /**
     * Metodo a ejecutar cuando la secuencia introducida
     * por el usuario sea incorrecta
     */
    private suspend fun incorrectSecuence(){
        userMessage()
        roundCounter = 0
        selectColor.clear()
        aux.clear()
        round.visibility = TextView.INVISIBLE
        startGame.isEnabled = true
    }

    /**
     * Metodo a ejecutar cuando la secuencia introducida
     * por el usuario sea correcta
     */
    private suspend fun correctSecuence(){
        userMessage()
        aux.clear()
        roundCounter++
        showRound()
        startSecuence()
        putSecuence()
    }
}