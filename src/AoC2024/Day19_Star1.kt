package AoC2024.Day19_Star1

import java.io.File
import java.util.*
import java.util.function.IntPredicate
import javax.swing.plaf.metal.MetalToolBarUI
import kotlin.collections.HashMap
import kotlin.collections.HashSet

//Para cada patron, ir cogiendo las diferentes toallas y ver si lo que forman es prefijo del patrón
//  Si es prefijo, añadir la toalla al patrón y añadir la posibilidad
//  Si no es prefijo, eliminar esa posibilidad
//  Si no hay posibilidades, salir --> Ese patrón no es posible
//  Si algún patrón es igual al buscado --> Ese patrón es posible. Contar y pasar al siguiente

//Demasiado lento con una lista simple de posibilidades
//  Cambiar a una lista de prioridad basado en el tamaño de la cadena
//  Y añadir una lista de descartes para evitar repeticiones
// Sigue siendo lento, pero al menos no se cuelga
// Para acelerarlo, ordenar las toallas alfabéticamente y buscar sólo por aquellas que empiezan por la siguiente letra del patrón

val compareByLength: Comparator<String> = compareBy { it.length }

fun main() {
    val scan = Scanner(System.`in`)

    var toallas: MutableList<String> = mutableListOf()

    //Cargar las toallas
    toallas = scan.nextLine().split(", ").toMutableList()

    Collections.sort(toallas)

    //Saltar la línia en blanco
    scan.nextLine()

    var patronesPosibles = 0

    //Analizar los patrones
    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "-1") break // Salida manual interactiva
        var posibilidades : PriorityQueue<String> = PriorityQueue<String>(Collections.reverseOrder(compareByLength))
        var descartes : HashSet<String> = HashSet<String>()
        posibilidades.add("")

        //Cargar las posibilidades
        while (!posibilidades.isEmpty())  {
            var posibilidad = posibilidades.poll()
            if (posibilidad.toString() == linea) {
                println("Patrón posible")
                patronesPosibles++
                break
            }
            //Comprobar las posibilidades al prefijo existente
            // y añadir aquellas que siguen siendo prefijo
            // evitando las que ya se han descartado
            var descartar = true
            for (toalla in toallas.filter { it.startsWith(linea[posibilidad.length].toString()) }) {
                if (linea.startsWith(posibilidad.toString() + toalla) && !descartes.contains(posibilidad + toalla)) {
                    posibilidades.add(posibilidad + toalla)
                    descartar = false
                }
            }
            if (descartar) {
                descartes.add(posibilidad)
            }
        }
    }

    //Imprimir la cantidad de patrones posibles
    println(patronesPosibles)
}
