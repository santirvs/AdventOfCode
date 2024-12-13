package AoC2024

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


//Plantearlo como una búsqueda en un espacio de soluciones ya que cada botón, como máximo, se podrá pulsar hasta 100 veces

fun main() {
    val scan = Scanner(System.`in`)

    var totalTokens : Int = 0

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
        var destX = destino[0].substring(3).toInt()
        var destY = destino[1].substring(3).toInt()

        //Buscar la solución en un espacio de soluciones de 0 a 100 pulsaciones de cada botón
        //No hay que quedarse con la primera, ya que buscamos la solución óptima
        var pulsA = 0
        var pulsB = 0
        var encontrado = false
        var solA = 1000
        var solB = 1000
        while (pulsA in 0..100 && desplazAX * pulsA <= destX && desplazAY * pulsA <= destY) {
            pulsB = 0
            while (pulsB in 0..100 && (desplazAX * pulsA + desplazBX * pulsB <= destX) && (desplazAY * pulsA + desplazBY * pulsB <= destY)) {
                if (desplazAX * pulsA + desplazBX * pulsB == destX && desplazAY * pulsA + desplazBY * pulsB == destY) {
                    encontrado = true
                    if (pulsA * 3 + pulsB < solA * 3 + solB) {
                        solA = pulsA
                        solB = pulsB
                    }
                }
                pulsB++
            }
            pulsA++
        }

        if (encontrado)
            totalTokens += solA * 3 + solB

    }

    println("Total tokens: $totalTokens")


}



