package AoC2024;

import java.util.*

fun main() {
    val scan = Scanner(System.`in`)

    var matriu : MutableList<List<Char>> = mutableListOf()

    //Cargar matriz de datos
    while (scan.hasNextLine()) {
        var linea = scan.nextLine()
        if (linea == "-1") break  //Salida para pruebas interactivas

        matriu.add(linea.toCharArray().toList())
        }

    //Recorrer matriz de datos
    val cantidad : Int = buscar(matriu, "XMAS")

    println(cantidad)
}

fun buscar(matriz: List<List<Char>>, palabra: String): Int {
    var cantidad:Int = 0
/*
    //Buscar la palabra en todas las direcciones posibles
    cantidad += buscarPalabraHorizontal(matriz, palabra)
    cantidad += buscarPalabraVertical(matriz, palabra)

    cantidad += buscarPalabraDiagonalDescendente(matriz, palabra)
*/
//    cantidad += buscarPalabraDiagonalAscendenteAdelante(matriz, palabra)
    cantidad += buscarPalabraDiagonalAscendenteAtras(matriz, palabra)


    return cantidad
}

fun buscarPalabraHorizontal(matriz: List<List<Char>>, palabra: String): Int {
    var cantidad = 0
    for (fila in matriz.indices) {
        for (columna in 0 until matriz[0].lastIndex - palabra.lastIndex) {
            var nuevaPalabra:String = ""
            for (pos in 0 .. palabra.lastIndex) {
                nuevaPalabra += matriz[fila][columna + pos]
            }
            if (nuevaPalabra == palabra || nuevaPalabra == palabra.reversed()) {
                cantidad++
            }
        }
    }
    return cantidad
}

fun buscarPalabraVertical(matriz: List<List<Char>>, palabra: String): Int {
    var cantidad = 0
    for (columna in matriz[0].indices) {
        for (fila in 0 .. matriz.lastIndex - palabra.lastIndex) {
            var nuevaPalabra:String = ""
            for (pos in 0 .. palabra.lastIndex) {
                nuevaPalabra += matriz[fila + pos][columna]
            }

            if (nuevaPalabra == palabra || nuevaPalabra == palabra.reversed()) {
                cantidad++
            }

        }
    }
    return cantidad
}

fun buscarPalabraDiagonalDescendente(matriz: List<List<Char>>, palabra: String): Int {
    var cantidad = 0
    for (fila in 0 .. matriz.lastIndex - palabra.lastIndex) {
        for (columna in 0 .. matriz.lastIndex - palabra.lastIndex) {
            var nuevaPalabra:String = ""
            for (pos in 0 .. palabra.lastIndex) {
                nuevaPalabra += matriz[fila+pos][columna+pos]
            }
            if (nuevaPalabra == palabra || nuevaPalabra == palabra.reversed()) {
                cantidad++
            }
        }
    }
    return cantidad
}

fun buscarPalabraDiagonalAscendenteAdelante(matriz: List<List<Char>>, palabra: String): Int {
    var cantidad = 0
    for (fila in matriz.lastIndex downTo palabra.lastIndex ) {
        for (columna in 0 until matriz.lastIndex - palabra.lastIndex) {
            var nuevaPalabra:String = ""
            for (pos in 0 .. palabra.lastIndex) {
                nuevaPalabra += matriz[fila-pos][columna+pos]
            }
            if (nuevaPalabra == palabra) {
                cantidad++
            }
        }
    }
    return cantidad
}

fun buscarPalabraDiagonalAscendenteAtras(matriz: List<List<Char>>, palabra: String): Int {
    var cantidad = 0
    for (fila in matriz.lastIndex downTo palabra.lastIndex ) {
        for (columna in matriz[fila].lastIndex downTo palabra.lastIndex) {
            var nuevaPalabra:String = ""
            for (pos in 0 .. palabra.lastIndex) {
                nuevaPalabra += matriz[fila-pos][columna-pos]
            }
            if (nuevaPalabra == palabra) {
                cantidad++
            }
        }
    }
    return cantidad
}