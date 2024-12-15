package AoC2024

import java.io.File
import java.util.*
import java.util.function.IntPredicate
import kotlin.collections.HashMap
import kotlin.collections.HashSet

//Cargar el mapa del almacén
//Identificar la posición del robot
//Leer los movimientos del robot y mover las cajas en consequencia
//Al finalizar, recorrer el mapa y, para cada caja, calcular su posición GPS (fila*100 + columna)
//Sumas todas las posiciones GPS de las cajas y devolver el resultado

data class Posicion(var fila:Int, var columna:Int)

fun main() {
    val scan = Scanner(System.`in`)

    var mapa : MutableList<MutableList<Char>> = mutableListOf()
    var posRobot:Posicion = Posicion(0,0)

    //Leer el mapa
    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "") break  //Final del mapa

        mapa.add(linea.toMutableList())

        //Buscar la posición del robot
        if (linea.contains('@')) {
            posRobot.fila = mapa.size-1
            posRobot.columna = linea.indexOf('@')
        }
    }

    imprimirMapa(mapa)

    //Leer los movimientos del robot
    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "-1") break  //Final de la entrada interactiva

        //Mover el robot
        for (movimiento in linea) {

            println("Movimiento: $movimiento")

            moverRobot(mapa, posRobot, movimiento)

            imprimirMapa(mapa)
        }
    }


    //Recorrer el mapa y calcular la posición GPS de las cajas
    var suma = 0
    for (fila in mapa.indices) {
        for (columna in mapa[fila].indices) {
            if (mapa[fila][columna] == 'O') {
                suma += fila*100 + columna
            }
        }
    }

    println(suma)

}

fun moverRobot(mapa:MutableList<MutableList<Char>>, posRobot: Posicion, movimiento:Char) {

    var deltaFila = 0
    var deltaColumna = 0

    var fila = posRobot.fila
    var columna = posRobot.columna

    when (movimiento) {
        '^' -> deltaFila--
        'v' -> deltaFila++
        '<' -> deltaColumna--
        '>' -> deltaColumna++
    }

    //Si la casilla a la que se quiere mover el robot está vacía, moverlo
    if (mapa[fila+deltaFila][columna+deltaColumna] == '.') {
        mapa[fila+deltaFila][columna+deltaColumna] = '@'
        mapa[fila][columna] = '.'
        //Actualizar la posición del robot
        posRobot.fila += deltaFila
        posRobot.columna += deltaColumna
    }

    //Si la casilla a la que se quiere mover el robot tiene una caja, buscar si se puede mover toda la secuencia de cajas
    var numCajas = 1
    while (mapa[fila+(deltaFila*numCajas)][columna+(deltaColumna*numCajas)] == 'O') {
        numCajas++
    }
    //Si al final hay una casilla vacía, mover las cajas, si no, no hacer nada
    if (numCajas > 1 && mapa[fila+(deltaFila*numCajas)][columna+(deltaColumna*numCajas)] == '.') {

        //Mover las cajas (la primera caja la llevo a la última posición)
        mapa[fila+(deltaFila*numCajas)][columna+(deltaColumna*numCajas)] = 'O'
        //Llevo el robot a la primera caja
        mapa[fila+deltaFila][columna+deltaColumna] = '@'
        //Libero la casilla del robot
        mapa[fila][columna] = '.'

        //Actualizar la posición del robot
        posRobot.fila += deltaFila
        posRobot.columna += deltaColumna
    }
}

fun imprimirMapa(mapa:MutableList<MutableList<Char>>) {
    println()
    for (fila in mapa) {
        println(fila.joinToString(""))
    }
    println()
}