package AoC2025;

import java.util.*

// Leer cada entrada (mientras no sea 0)
// Para cada rango...
// Buscar el máximo entre 0 y n-1
// Una vez encontrado, buscar el máximo entre la pos y n
// Construir la salida y sumar
// mostrar la suma de todas las salidas

fun main() {
    val scan = Scanner(System.`in`)

    var entrada = scan.nextLine()
    var suma : Long = 0
    while (entrada != "0") {

        suma += tratarCaso(entrada)

        entrada = scan.nextLine()
    }

    println(suma)

}

fun tratarCaso(valor: String) : Long {
    var resultado : Long = 0

    println("Tratar caso: $valor")

    var pos1 : Int = buscarMaximo(valor, 0, valor.length-1)
    var pos2 : Int = buscarMaximo(valor, pos1+1, valor.length)

    resultado = valor.get(pos1).digitToInt()*10L + valor.get(pos2).digitToInt()

    println("Resultado: $resultado")

    return resultado

    }

fun buscarMaximo(valor: String, pos1: Int, pos2: Int): Int {
    println("Buscar maximo de $valor entre $pos1 y $pos2")
    var max = 1
    var posMax = -1
    for( i in pos1 until pos2) {
        if (valor[i].digitToInt() > max) {
            max = valor[i].digitToInt()
            posMax = i
        }
    }
    println("El resultado es $max en la posicion $posMax")
    return posMax
}

