package AoC2025

import java.util.Scanner

/*
class Teleport(val schematic: List<String>) {
    fun splitAndCount(): Int {
        val shortSchematic = schematic.filter { it.contains('^') }
        var beamLocations = setOf(schematic[0].indexOf('S'))
        var totalSplits = 0
        shortSchematic.forEach {line ->
            val nextLocations = mutableSetOf<Int>()
            beamLocations.forEach { beam ->
                if (line[beam] == '^') {
                    nextLocations.addAll(listOf(beam - 1, beam + 1))
                    totalSplits += 1
                } else {
                    nextLocations.add(beam)
                }
            }
            beamLocations = nextLocations
        }
        return totalSplits
    }
    */

    fun quantumSplitAndCount(lineas: List<String>): Long {
        //Usar un mapa <Int, Long>  donde la key es la posición y el value es la cantidad de caminos que llegan a él
        val lineasConSplits = lineas.filter { it.contains('^') }
        var rutasHastaPunto = mapOf(lineas[0].indexOf('S') to 1L)
        lineasConSplits.forEach { linea ->
            val siguientesRutas = mutableMapOf<Int, Long>()
            rutasHastaPunto.forEach { punto ->
                if (linea[punto.key] == '^') {
                    //Dividimos el punto actual en dos, añadiendo la cantidad de caminos que llegan al punto actual a cada una de las dos rutas
                    siguientesRutas[punto.key - 1] = siguientesRutas.getOrDefault(punto.key - 1, 0) + punto.value
                    siguientesRutas[punto.key + 1] = siguientesRutas.getOrDefault(punto.key + 1, 0) + punto.value
                } else {
                    // añadir la cantidad de caminos que llegan al punto actual al punto siguiente
                    siguientesRutas[punto.key] = siguientesRutas.getOrDefault(punto.key, 0) + punto.value
                }
            }
            rutasHastaPunto = siguientesRutas
        }
        return rutasHastaPunto.values.sum()
    }



//Leer cada linea
//Mirar la S en la primera línea y apuntar la posición
//Mirar cada una de las posiciones apuntadas en la siguiente línea
//Si hay split ^ añadir la posición anterior y siguiente. Sumar split
//Si hay hueco, añadir la posición
//Con las posiciones de la línea, tratar la siguiente línea

//Segunda *
//Igual que en la primera estrella, pero sumar todos los caminos que hacen llegar al mismo punto

fun main() {

    val scan = Scanner(System.`in`)

    //Lectura del puzzle
    var lineas = mutableListOf<String>()
    var linea = scan.nextLine()
    while (linea!="") {
        lineas.add(linea)
        linea= scan.nextLine()
    }

    //Resolver la 1a *

    var posiciones : HashSet<Int> = HashSet<Int>()
    posiciones.add (lineas[0].indexOf('S'))
    var numSplits:Int = 0
    var numLineas = 1
    while (numLineas < lineas.size) {
        var posicionesSiguientes : HashSet<Int> = HashSet<Int>()
        linea = lineas[numLineas]
        for (p in posiciones) {
            if (linea.length > p && linea.get(p) == '^') {
                posicionesSiguientes.add(p-1)
                posicionesSiguientes.add(p+1)
                numSplits++
            } else {
                posicionesSiguientes.add(p)
            }
        }
        posiciones = posicionesSiguientes
        numLineas++
    }

    println("Star1:: Num Splits: $numSplits")

    // Star 2
    var numQuantumSplits : Long = quantumSplitAndCount(lineas)

    println("Star2:: Num Rutas: ${numQuantumSplits}")

}