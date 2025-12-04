package AoC2025;

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
    var totalAdyacentes = 0;
    do {
        var adyacentes = buscarAdyacencias2(matriz, 4)
        totalAdyacentes += adyacentes
    }
    while (adyacentes > 0) ;

    println(totalAdyacentes)

}

fun buscarAdyacencias2(matriz: List<Array<Char>>, limit: Int): Int {
    var result = 0

    for (fila in matriz.indices) {
        for (columna in  matriz[fila].indices) {
            if (adyacencias(matriz, fila, columna) < limit) {
                println("La posición $fila, $columna cumple")
                result++
                matriz[fila][columna] = '.' //Eliminar el rollo
            }
        }
    }

    return result
}

