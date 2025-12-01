package AoC2025;

import java.util.*

// Leer cada entrada
// Rotar a la izquierda o derecha
// Comprobar el módulo 100
// Contar las veces que queda a 0

fun main() {
    val scan = Scanner(System.`in`)

    var position = 50
    var numZeros = 0

    var entrada = scan.nextLine()
    while (entrada != "0") {
        var numero = Integer.parseInt(entrada.subSequence(1..entrada.length-1).toString())
        if (entrada.get(0) == 'L') {
            //rotación a la izquierda
            position -=numero
        }
        else {
            //rotación a la derecha
            position+=numero
        }

        position = position %100
        if (position<0) position+=100

        if (position == 0) numZeros++

        println("The dial is rotated $entrada to point at $position")

        entrada = scan.nextLine()
    }

    println("Total veces en 0: $numZeros")
}
