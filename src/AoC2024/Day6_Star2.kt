package AoC2024

import java.util.*
import kotlin.collections.HashMap


//Sabemos que la matriz hace 130 x 130 = 16900 celdas, si se supera ese número es que se ha entrado en un bucle
//Por tanto, se puede asumir que el recorrido no superará las 16900 celdas

//Pondremos obstáculos en las celdas libres y probamos a ver si consigue salir del tablero

fun main() {
    val scan = Scanner(System.`in`)

    val tablero: MutableList<MutableList<Char>> = mutableListOf()
    var fila: Int = 0
    var columna: Int = 0
    var numFila: Int = 0
    //Cargar matriz de datos
    while (scan.hasNextLine()) {
        val linea = scan.nextLine()
        if (linea == "-1") break  //Cambio de sección

        //Añade la fila
        tablero.add(linea.toMutableList())

        //Si tiene el caracter "^" guarda la fila y columna
        if (linea.contains("^")) {
            fila = numFila
            columna = linea.indexOf("^")
        }
        numFila++

    }

    var buclesInfinitos = 0
    var filaInicial = fila
    var columnaInicial = columna

    for (indiceFila in 0..tablero.lastIndex) {
        for (indiceColumna in 0..tablero[0].lastIndex) {
            //Si la posición está libre, simulamos un obstáculo
            if (tablero[indiceFila][indiceColumna] == '.') {

                fila = filaInicial
                columna = columnaInicial

                tablero[indiceFila][indiceColumna] = '#'

                //Simular el movimiento del guarda
                var direccion = "Up"
                var movimientos = 1
                var final: Boolean = false

                while (!final && movimientos < 20000) {

                    //Si aún no había pasado por esta celda, incremento el número de celdas visitadas
                    movimientos++

                    //Moverse
                    when (direccion) {
                        "Up" -> fila--
                        "Down" -> fila++
                        "Left" -> columna--
                        "Right" -> columna++
                    }

                    //Comprobar si la celda es válida
                    if (fila >= 0 && fila <= tablero.lastIndex &&
                        columna >= 0 && columna <= tablero[0].lastIndex
                    ) {

                        //Comprobar si la celda es un obstáculo
                        if (tablero[fila][columna] == '#') {
                            //se retrocede una celda y se rota a la derecha, sin avanzar (ya se avanzará en la siguiente iteración)
                            when (direccion) {
                                "Up" -> {
                                    direccion = "Right"; fila++
                                }

                                "Down" -> {
                                    direccion = "Left"; fila--
                                }

                                "Left" -> {
                                    direccion = "Up"; columna++
                                }

                                "Right" -> {
                                    direccion = "Down"; columna--
                                }
                            }
                        }

                    } else {
                        //Se ha salido del tablero. Finalizar
                        final = true
                    }

                }

                if (movimientos >= 20000) {
                    buclesInfinitos++
                }

                //Restaurar la posición inicial
                tablero[indiceFila][indiceColumna] = '.'
            }
        }
    }

    println(buclesInfinitos)

}




