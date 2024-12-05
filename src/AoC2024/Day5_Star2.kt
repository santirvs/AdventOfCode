package AoC2024;

import java.util.*
import kotlin.collections.HashMap

fun main() {
    val scan = Scanner(System.`in`)

    var precedencias: HashMap<Int, MutableList<Int>> = HashMap<Int, MutableList<Int>>()

    //Cargar matriz de datos
    while (scan.hasNextLine()) {
        var linea = scan.nextLine()
        if (linea == "") break  //Cambio de sección

        //Cargar la línea en el mapa de precedencias
        var partes = linea.split("|")
        var numAntes: Int = partes[0].toInt()
        var numDespues: Int = partes[1].toInt()
        if (precedencias.containsKey(numAntes)) {
            precedencias[numAntes]!!.add(numDespues)
        } else {
            precedencias[numAntes] = mutableListOf(numDespues)
        }
    }

    var sumaPaginasCentrales = 0

    while (scan.hasNextLine()) {
        var linea = scan.nextLine()
        if (linea == "-1") break  //Salida manual interactiva

        //Descomponer la línea en partes
        var partes = linea.split(",")

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

        if (!respetaPrecedencias) {
            //Ordenar las partes
            var listaOrdenada = ordenarPartes(partes, precedencias)

            sumaPaginasCentrales += listaOrdenada[listaOrdenada.size/2].toInt()


        }

    }

    println(sumaPaginasCentrales)

}

fun ordenarPartes(partes: List<String>, precedencias: HashMap<Int, MutableList<Int>>): List<String> {
    var partesOrdenadas = mutableListOf<String>()
    var partesRestantes = partes.toMutableList()

    while (partesRestantes.isNotEmpty()) {
        var parte = partesRestantes.removeAt(0)
        if (partesOrdenadas.isEmpty()) {
            partesOrdenadas.add(parte)
        } else {
            var index = 0
            while (index < partesOrdenadas.size) {
                if (precedencias.containsKey(partesOrdenadas[index].toInt())) {
                    if (precedencias[partesOrdenadas[index].toInt()]!!.contains(parte.toInt())) {
                        index++
                    } else {
                        partesOrdenadas.add(index, parte)
                        break
                    }
                } else {
                    partesOrdenadas.add(index, parte)
                    break
                }
            }
            if (index == partesOrdenadas.size) {
                partesOrdenadas.add(parte)
            }
        }
    }

    return partesOrdenadas
}