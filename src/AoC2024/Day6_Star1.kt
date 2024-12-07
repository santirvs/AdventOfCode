package AoC2024

import java.util.*
import kotlin.collections.HashMap

fun main() {
    val scan = Scanner(System.`in`)

    val tablero: MutableList<MutableList<Char>> = mutableListOf()
    var fila : Int = 0
    var columna : Int = 0
    var numFila : Int = 0
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

    //Simular el movimiento del guarda
    var direccion = "Up"
    var celdasVisitadas = 1
    var final : Boolean = false

    while (!final) {

        //Si aún no había pasado por esta celda, incremento el número de celdas visitadas
        if (tablero[fila][columna] == '.') celdasVisitadas++
        //Marcar la fila como visitada
        tablero[fila][columna] = 'X'

        //Moverse
        when (direccion) {
            "Up" -> fila--
            "Down" -> fila++
            "Left" -> columna--
            "Right" -> columna++
        }

        //Comprobar si la celda es válida
        if (fila >= 0 && fila <= tablero.lastIndex &&
            columna >= 0 && columna<= tablero[0].lastIndex) {

            //Comprobar si la celda es un obstáculo
            if (tablero[fila][columna] == '#') {
                //se retrocede una celda y se rota a la derecha, sin avanzar (ya se avanzará en la siguiente iteración)
                when (direccion) {
                    "Up" -> {direccion = "Right"; fila++}
                    "Down" -> {direccion = "Left"; fila--}
                    "Left" -> {direccion = "Up"; columna++}
                    "Right" ->  {direccion = "Down"; columna --}
                }
            }

        }
        else {
            //Se ha salido del tablero. Finalizar
            final = true
        }

        //println("Fila: $fila, Columna: $columna, Dirección: $direccion")
        //for (fila in tablero) {
        //    println(fila)
        //}

    }

    println(celdasVisitadas)


}