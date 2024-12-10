package AoC2024

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


fun main() {
    val scan = Scanner(System.`in`)

    var mapa : MutableList<MutableList<Char>> = mutableListOf()

    while (scan.hasNextLine()) {
        val linea = scan.nextLine()
        if (linea == "-1") break  //Final de la entrada manual

        mapa.add(linea.toMutableList())
    }

    //Recorrer el mapa buscando puntos de partida
    var sumaScores = 0

    for (fila in 0 until mapa.size) {
        for (columna in 0 until mapa[0].size) {
            if (mapa[fila][columna] == '0') {
                sumaScores += puntosMapa (fila, columna, mapa)
            }
        }
    }

    //Imprimir el resultado
    println(sumaScores)

}

fun puntosMapa(fila: Int, columna: Int, mapa: MutableList<MutableList<Char>>) : Int {
    //Si la casilla no es un punto de partida, devolver 0
    if (mapa[fila][columna] != '0') return 0


    println("Inicio la exploración desde $fila, $columna")

    //Inicializar la lista de puntos a explorar
    var puntosAExplorar = mutableListOf<Pair<Int, Int>>()
    puntosAExplorar.add(Pair(fila, columna))

    //Inicializar la lista de puntos explorados
    var puntosExplorados = HashSet<Pair<Int, Int>>()

    //Inicializar el score
    var score = 0

    //Recorrer los puntos a explorar
    while (puntosAExplorar.isNotEmpty()) {
        val punto = puntosAExplorar.removeAt(0)

        //Si el punto ya ha sido explorado, continuar
        if (puntosExplorados.contains(punto)) continue

        //Si el punto es un punto de llegada, sumar el score
        if (mapa[punto.first][punto.second] == '9') {
            score++
            println("Punto de llegada en ${punto.first}, ${punto.second}")
        }
        else {
            //Añadir los 4 puntos adyacentes a la lista de puntos a explorar
            var f: Int = 0
            var c: Int = 0
            for (i in 1..4) {
                when (i) {
                    1 -> {
                        f = -1; c = 0
                    } //Arriba
                    2 -> {
                        f = 1; c = 0
                    } //Abajo
                    3 -> {
                        f = 0; c = -1
                    } //Izquierda
                    4 -> {
                        f = 0; c = 1
                    } //Derecha
                }

                if (punto.first + f >= 0 && punto.first + f < mapa.size && punto.second + c >= 0 && punto.second + c < mapa[0].size) {
                    if (mapa[punto.first + f][punto.second + c] - mapa[punto.first][punto.second] == 1) {
                        puntosAExplorar.add(Pair(punto.first + f, punto.second + c))
                    }
                }
            }
        }

        //Marcar el punto como explorado
        puntosExplorados.add(punto)
    }

    println("Trailhead de $fila, $columna tiene un score final de $score")

    return score
}







