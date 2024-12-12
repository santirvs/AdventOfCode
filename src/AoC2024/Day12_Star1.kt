package AoC2024

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


data class GardenPlot(val x:Int, val y:Int)

data class Valla(val tipo:Char, var area:Int, var perimetro:Int)

fun main() {
    val scan = Scanner(System.`in`)

    var celdasExploradas = HashSet<GardenPlot>()
    var celdasAExplorar = mutableListOf<GardenPlot>()
    var regiones = mutableListOf<Valla>()
    var mapa : MutableList<MutableList<Char>> = mutableListOf()

    //Leer las celdas
    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "-1") break  //Final de la entrada manual

        mapa.add(linea.toMutableList())
    }

    //Empezar por la posición 0,0
    celdasAExplorar.add(GardenPlot(0,0))
    var vallaActual : Valla = Valla(mapa[0][0], 0, 0)

    var celdasMismoTipo = mutableListOf<GardenPlot>()

    //Explorar las celdas
    while (!celdasAExplorar.isEmpty() || !celdasMismoTipo.isEmpty()) {

        //Toma la celda siguiente a explorar, dando prioridad a las del mismo tipo
        var celdaActual : GardenPlot
        if (!celdasMismoTipo.isEmpty())
            celdaActual = celdasMismoTipo.removeAt(0)
        else {
            //Cambio de región. Nueva valla
            if (vallaActual.area!=0) regiones.add(vallaActual)
            celdaActual = celdasAExplorar.removeAt(0)
            vallaActual = Valla(mapa[celdaActual.x][celdaActual.y], 0, 0)
        }

        //Si ya se ha visitado esta celda, se ignora
        if (celdasExploradas.contains(celdaActual))
            continue

        //Se añade la celda a las exploradas
        celdasExploradas.add(celdaActual)

        //Se incrementa en una unidad el area de la region para la valla actual
        //y se asume que no tiene vecinos de la misma región -> se incrementa el perímetro en 4 unidades
        vallaActual.area++
        vallaActual.perimetro += 4

        //Explorar las celdas adyacentes
        for (dx in -1..1) {
            for (dy in -1..1) {
                //Ignorar la celda actual y las diagonales
                if ((dx == 0 && dy == 0) || (dx!=0 && dy!=0)) continue

                try {
                    if (mapa[celdaActual.x + dx][celdaActual.y + dy] != vallaActual.tipo) {
                        //Límite de la región
                        celdasAExplorar.add(GardenPlot(celdaActual.x + dx, celdaActual.y + dy))
                    }
                    else {
                        //Celda del mismo tipo, se necesita una valla menos
                        celdasMismoTipo.add(GardenPlot(celdaActual.x + dx, celdaActual.y + dy))
                        vallaActual.perimetro--
                    }
                }
                catch (e: IndexOutOfBoundsException) {
                    //Fuera de los límites del mapa --> No hacer nada
                }
            }
        }
    }

    //Añadir la última región
    regiones.add(vallaActual)

    //Mostrar el resultado
    for (r in regiones) {
        println("Región de tipo ${r.tipo}: Area: ${r.area} Perímetro: ${r.perimetro}")
    }

    //Calcular el resultado
    var resultado = regiones.map { r -> r.area * r.perimetro }.sum()
    println("Resultado: $resultado")



}



