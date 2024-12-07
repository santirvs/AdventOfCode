package AoC2024

import java.util.*
import kotlin.collections.HashMap


//Ahora existe un tercer operador, la concatenación
//Seguiremos la misma estrategia, pero hay que cambiar los booleanos por un entero que puede ser 0, 1 o 2


fun main() {
    val scan = Scanner(System.`in`)

    //Cada caso es independiente
    var totalSumaCorrectos : Long = 0

    //Cargar valores
    while (scan.hasNextLine()) {
        val linea = scan.nextLine()
        if (linea == "-1") break  //Final de la entrada manual

        //Descomponer la línea en partes
        val partes = linea.split(":")
        val resultado = partes[0].trim().toLong()
        val numeros = partes[1].trim().split(" ")
        val combinaciones : Int = Math.pow(3.0, (numeros.size-1).toDouble()).toInt()

        //Probar todas las combinaciones posibles hasta que se encuentre una que dé el resultado
        var encontrado = false
        var combinacion : Long = 0
        while (combinacion < combinaciones && !encontrado) {
            //Descomponer la combinación en partes
            var combinacionActual = combinacion
            var operadores = mutableListOf<Char>()
            for (index in 0 until numeros.size - 1) {
                if (combinacionActual % 3 == 0L) {
                    operadores.add('+')
                }else if (combinacionActual % 3 == 1L) {
                        operadores.add('*')
                } else {
                    operadores.add('|')
                }
                combinacionActual /= 3
            }
            operadores.reverse()

            //Calcular el resultado de la combinación
            var resultadoActual = numeros[0].toLong()
            for (index in 0 until operadores.size) {
                if (operadores[index] == '+') {
                    resultadoActual += numeros[index + 1].toLong()
                } else if (operadores[index] == '*') {
                    resultadoActual *= numeros[index + 1].toLong()
                }
                else {
                    resultadoActual = (resultadoActual.toString() + numeros[index + 1]).toLong()
                }
            }

            //Comprobar si el resultado es el deseado
            if (resultadoActual == resultado) {
                encontrado = true
            }

            combinacion++
        }

        if (encontrado) {
            totalSumaCorrectos += resultado
        }

    }

    println(totalSumaCorrectos)

}




