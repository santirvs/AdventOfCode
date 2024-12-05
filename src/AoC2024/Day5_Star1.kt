package AoC2024

import java.util.*
import kotlin.collections.HashMap

fun main() {
    val scan = Scanner(System.`in`)

    val precedencias: HashMap<Int, MutableList<Int>> = HashMap<Int, MutableList<Int>>()

    //Cargar matriz de datos
    while (scan.hasNextLine()) {
        val linea = scan.nextLine()
        if (linea == "") break  //Cambio de sección

        //Cargar la línea en el mapa de precedencias
        val partes = linea.split("|")
        val numAntes: Int = partes[0].toInt()
        val numDespues: Int = partes[1].toInt()
        if (precedencias.containsKey(numAntes)) {
            precedencias[numAntes]!!.add(numDespues)
        } else {
            precedencias[numAntes] = mutableListOf(numDespues)
        }
    }

    var sumaPaginasCentrales = 0

    while (scan.hasNextLine()) {
        val linea = scan.nextLine()
        if (linea == "-1") break  //Salida manual interactiva

        //Descomponer la línea en partes
        val partes = linea.split(",")

        //Comprobar que las partes respetan el mapa de precedencias
        var respetaPrecedencias = true
        for (index1 in 0 .. partes.lastIndex) {
            for (index2 in index1+1 .. partes.lastIndex) {
                if (precedencias.containsKey(partes[index2].toInt())) {
                    if (precedencias[partes[index2].toInt()]!!.contains(partes[index1].toInt())) {
                        respetaPrecedencias = false
                    }
                }
            }
        }

        if (respetaPrecedencias) {
            val paginaCentral = partes[partes.size/2]
            sumaPaginasCentrales += paginaCentral.toInt()
        }

    }

    println(sumaPaginasCentrales)

}