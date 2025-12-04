package AoC2025;

import java.math.BigInteger
import java.util.*

// Leer la matriz
// Para cada casilla, verificar las 8 posiciones adyacentes
// Contar aquellas que tengan menos de 4

fun main() {
    val scan = Scanner(System.`in`)

    var matriz = mutableListOf<Array<Char>>()

    var entrada = scan.nextLine()
    while (entrada!="0") {
        matriz.add (entrada.toCharArray().toTypedArray())
        entrada = scan.nextLine()
    }

    //Buscar posiciones adyacentes
    var adyacentes = buscarAdyacencias(matriz, 4)


    println(adyacentes)

}

fun buscarAdyacencias(matriz: List<Array<Char>>, limit: Int): Int {
    var result = 0

    for (fila in matriz.indices) {
        for (columna in  matriz[fila].indices) {
            if (adyacencias(matriz, fila, columna) < limit) {
                println("La posición $fila, $columna cumple")
                result++
            }
        }
    }

    return result
}

fun adyacencias(matriz: List<Array<Char>>, fila:Int, columna: Int): Int {
    var result = 0

    //Si no hay rollo en la posición no hace falta buscar nada más
    if (matriz[fila][columna] == '@') {

        // Comprobar fila superior
        if (fila > 0) {
            if (columna > 0 && matriz[fila - 1][columna - 1] == '@') result++
            if (matriz[fila - 1][columna] == '@') result++
            if (columna < matriz[fila].size - 1 && matriz[fila - 1][columna + 1] == '@') result++
        }
        // Comprobar fila media
        if (columna > 0 && matriz[fila][columna - 1] == '@') result++
        if (columna < matriz[fila].size - 1 && matriz[fila][columna + 1] == '@') result++
        // Comprobar fila inferior
        if (fila < matriz.size - 1) {
            if (columna > 0 && matriz[fila + 1][columna - 1] == '@') result++
            if (matriz[fila + 1][columna] == '@') result++
            if (columna < matriz[fila].size - 1 && matriz[fila + 1][columna + 1] == '@') result++
        }
    } else {
        result = Int.MAX_VALUE
    }

    return result
}