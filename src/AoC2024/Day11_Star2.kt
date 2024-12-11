package AoC2024

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


fun main() {
    val scan = Scanner(System.`in`)

    var listaPiedras = HashMap<Long,Long>()   //  Cantidad de dígitos --> Cantidad de piedras con esos dígitos

    //Leer la lista de piedras
    while (scan.hasNext()) {
        val num = scan.nextLong()
        if (num == -1L) break  //Final de la entrada manual

        if (listaPiedras.containsKey(num)) {
            listaPiedras[num] = listaPiedras[num]!! + 1
        }
        else {
            listaPiedras[num] = 1
        }
    }

    //Repetir 75 veces  (Star 2)
    repeat(75) {
        var nuevaLista = HashMap<Long,Long>()

        for ((valor, numPiedras) in listaPiedras) {
            var numDigits = valor.toString().length
            if (valor == 0L) {
                nuevaLista[1] = numPiedras
            }
            else {
                if (numDigits % 2 == 0) {
                    var numLeft = valor.toString().substring(0, numDigits / 2).toLong()
                    var numRight = valor.toString().substring(numDigits / 2).toLong()
                    if (nuevaLista.containsKey(numLeft)) {
                        nuevaLista[numLeft] = nuevaLista[numLeft]!! + numPiedras
                    }
                    else {
                        nuevaLista[numLeft] = numPiedras
                    }
                    if (nuevaLista.containsKey(numRight)) {
                        nuevaLista[numRight] = nuevaLista[numRight]!! + numPiedras
                    }
                    else {
                        nuevaLista[numRight] = numPiedras
                    }
                }
                else {
                    if (nuevaLista.containsKey(valor*2024)) {
                        nuevaLista[valor*2024] = nuevaLista[valor*2024]!! + numPiedras
                    }
                    else {
                        nuevaLista[valor*2024] = numPiedras
                    }
                }
            }
        }

        println("Iteración ${it+1}: Cantidad valores diferentes: ${listaPiedras.size}")
        println("Valores: ${nuevaLista.keys} Cantidad: ${nuevaLista.values}")
        println("Total piedras: ${nuevaLista.values.sum()}")

        listaPiedras = nuevaLista
    }

    //Mostrar el resultado
    println("Resultado: ${listaPiedras.values.sum()}")

}



