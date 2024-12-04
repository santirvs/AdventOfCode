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
    val cantidad : Int = buscarMAS(matriu)

    println(cantidad)
}

fun buscarMAS(matriz: List<List<Char>>): Int {
    var cantidad = 0
    for (fila in 0 .. matriz.lastIndex - 2) {
        for (columna in 0 .. matriz[fila].lastIndex - 2) {
            var nuevaPalabra1:String = ""
            var nuevaPalabra2:String = ""
            for (pos in 0..2) {
                nuevaPalabra1 += matriz[fila+pos][columna+pos]
                nuevaPalabra2 += matriz[fila+2-pos][columna+pos]
            }
            if ((nuevaPalabra1 == "MAS" || nuevaPalabra1 == "MAS".reversed()) &&
                (nuevaPalabra2 == "MAS" || nuevaPalabra2 == "MAS".reversed())) {
                cantidad++
            }
        }
    }
    return cantidad
}