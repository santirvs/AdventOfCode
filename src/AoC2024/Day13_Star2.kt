package AoC2024

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


//Ahora ya no sirve la búsqueda en un espacio de soluciones, ya que el número es demasiado grande
//Se puede plantear como un sistema de ecuaciones lineales

fun main() {
    val scan = Scanner(System.`in`)

    var totalTokens : Long = 0L

    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "-1") break  //Final de la entrada manual

        //Separador de casos
        if (linea == "")
            linea = scan.nextLine()

        //Parsear la entrada
        //Button A
        var movsA = linea.split(":")[1].split(",")
        var desplazAX = movsA[0].substring(3).toInt()
        var desplazAY = movsA[1].substring(3).toInt()

        //Button B
        var movsB = scan.nextLine().split(":")[1].split(",")
        var desplazBX = movsB[0].substring(3).toInt()
        var desplazBY = movsB[1].substring(3).toInt()

        //Destino
        var destino = scan.nextLine().split(":")[1].split(",")
        var destX = destino[0].substring(3).toLong() // + 10000000000000
        var destY = destino[1].substring(3).toLong() // + 10000000000000


        // Resuelve el sistema de ecuaciones mediante el métode de Cramer
        //  a.x + b.x = target_x
        //  a.y + b.y = target_y

        val det_norm = desplazAX * desplazBY - desplazBX * desplazAY
        val det_x = destX * desplazBY - destY * desplazBX
        val x = det_x.toDouble() / det_norm
        val det_y = desplazAX * destY - destX * desplazAY
        val y = det_y.toDouble() / det_norm

        var encontrado = true

        val EPSILON = Math.pow(10.0, -8.0)
        if (Math.abs(x - Math.round(x)) > EPSILON || Math.abs(y - Math.round(y)) > EPSILON)
            encontrado = false
        if (x < 0 || y < 0)
            encontrado = false

        if (encontrado)
            totalTokens += x.toLong() * 3 + y.toLong()

    }
    println("Total tokens: $totalTokens")

}
