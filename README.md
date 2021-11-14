# Simon Says Game

## Descripción del proyecto

Desarrollo de un juego de Simón Dice, programado en Kotlin con Android Studio.
Juego donde se repite una secuencia introducida aletoriamente donde el jugador tiene
que reproducirla para poder continuar jugando

## Estructura del juego
![app_structure](/app/src/main/res/raw/estructura.jpg)

## Métodos del juego
### onCreate
Inicializa los elementos necesarios para el funcionamiento de la aplicación 
y detecta cuando el jugador quiere empezar

### Show round
Metodo que muestra la ronda actual durante el juego

### Start secuence
Metodo que desactiva los botones del juego, añade un número aleatorio a la 
secuencia en cada ronda y luego la reproduce con un delay. 
Cuando finaliza la secuencia vuelve a activar los botones


### Put secuence and Set listener
Metodos que reproducen un efecto de accionamiento y de sonido al pulsar los botones principales 
y luego comprueba la secuencia.

## Check secuence
Metodo que espera a que el jugador pulsa la misma cantidad de iteraciones que la secuencia.
Luego devuelve un valor _true_ si la secuencia es correcta o _false_ si la secuencia es incorrecta

### Correct and Incorrect secuence
Correct secuence limpia la secuencia introducida por el jugador y avanza el programa a la siguiente ronda
Incorrect secuence limpia la secuencia introducida por el jugador y la del programa, detiene la musica, 
oculta parte de la interfaz y vuelve a activar el boton de inicio

### Muestra visual de la aplicación
![Screenshoot](/app/src/main/res/raw/juego_completo.png)