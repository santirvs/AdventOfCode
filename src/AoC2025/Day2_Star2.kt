package AoC2025

import java.util.Scanner

// Cambio planteamiento a fuerza bruta pura y dura
// Desde el inicio del rango hasta el final del rango
// comprobar si los números se repiten por la mitad
// o si se repiten múltiples veces

//Comprueba si s es una repetición
// *** IDEA FELIZ ***
// Duplica s, quita el primer y último caracter
// y comprueba si contiene s.  Si lo contiene es que s es una repetición
fun repetidoMultiple(s: String): Boolean {
    return s in (s + s).substring(1, s.length * 2 - 1)
}

// Comprueba si la mitad de s es igual a la otra mitad
fun repetidoMitad(s: String): Boolean {
    val half = s.length / 2
    return s.substring(0, half) == s.substring(half)
}

fun solution(entrada: String) {
    var sumInvalidP1 : Long = 0
    var sumInvalidP2 : Long = 0

    var input = entrada.split(",")

    for (rango in input) {
        //Obtener los dos límites del rango
        val (d1, d2) = rango.split("-").map { it.toLong() }

        //Recorrer el rango
        for (i in d1..d2) {
            //Convertir a String
            val strI = i.toString()
            //Resolver parte 1
            if (repetidoMitad(strI)) {
                sumInvalidP1 += i
            }
            //Resolver parte 2
            if (repetidoMultiple(strI)) {
                sumInvalidP2 += i
            }
        }
    }

    println("Parte 1: $sumInvalidP1")
    println("Parte 2: $sumInvalidP2")
}

fun main2() {
    var scan: Scanner =  Scanner(System.`in`)
    val input = scan.nextLine()
    solution(input)
}

fun main() {
    var s= "123123"
    var s1 = (s + s).substring(1, s.length * 2 - 1)
    println(s1)
}
