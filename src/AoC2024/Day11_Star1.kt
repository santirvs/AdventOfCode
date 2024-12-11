package AoC2024

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


fun main() {
    val scan = Scanner(System.`in`)

    var listaPiedras = mutableListOf<Long>()

    //Leer la lista de piedras
    while (scan.hasNext()) {
        val num = scan.nextLong()
        if (num == -1L) break  //Final de la entrada manual

        listaPiedras.add(num)
    }

    //Repetir 25 veces  (Star 1)
    repeat(25) {
        var nuevaLista = mutableListOf<Long>()

        for (p in listaPiedras) {
            if (p == 0L) nuevaLista.add(1)
            else {
                var numDigits = { n: Long -> n.toString().length }
                if (numDigits(p) % 2 == 0) {
                    var numLeft = p.toString().substring(0, numDigits(p) / 2).toLong()
                    var numRight = p.toString().substring(numDigits(p) / 2).toLong()
                    nuevaLista.add(numLeft)
                    nuevaLista.add(numRight)
                }
                else {
                    nuevaLista.add(p*2024)
                }
            }

        }

        listaPiedras = nuevaLista

        println("Iteración ${it+1}: ${listaPiedras.size}")
    }

    //Mostrar el resultado
    println("Resultado: ${listaPiedras.size}")

}



