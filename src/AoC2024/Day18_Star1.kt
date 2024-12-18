package AoC2024.Day18_Star1

import java.io.File
import java.util.*
import java.util.function.IntPredicate
import javax.swing.plaf.metal.MetalToolBarUI
import kotlin.collections.HashMap
import kotlin.collections.HashSet

//Cargar el mapa del almacén
//Identificar el inicio y el final
//Calcular el camino más corto entre el inicio y el final
//Devolver la longitud del camino más corto
//Para calcular el camino más corto, se puede usar un algoritmo de búsqueda en anchura
//Priorizando el camino con menor coste (no es necesario calcular la distancia de Manhattan)

data class Celda (var fila:Int, var columna:Int)
class Alternativa {
    var fila:Int
    var columna:Int
    var celdasVisitadas:MutableList<Celda>
    var coste:Int

    constructor() {
        fila = 0
        columna = 0
        celdasVisitadas = mutableListOf()
        coste = 0
    }
}
val compararAlternativas = Comparator<Alternativa> { a, b -> a.coste - b.coste }

fun main() {
    val scan = Scanner(System.`in`)

    val TOTAL_PUNTOS = 1000
    val ANCHO = 70
    val ALTO = 70

    var mapa : MutableList<MutableList<Char>> = mutableListOf()
    //Crear el mapa relleno de puntos (zonas libres)
    for (fila in 0..ALTO) {
        mapa.add(mutableListOf())
        for (columna in 0..ANCHO) {
            mapa[fila].add('.')
        }
    }
    var posInicial:Celda = Celda(0,0)
    var posFinal:Celda = Celda(ALTO,ANCHO)
    var colaAlternativas : PriorityQueue<Alternativa> = PriorityQueue<Alternativa>(compararAlternativas)

    //Leer los puntos
    for (punto in 1..TOTAL_PUNTOS) {
        var linea = scan.nextLine()
        var partes = linea.split(",")
        var columna = partes[0].toInt()
        var fila = partes[1].toInt()
        mapa[fila][columna] = '#'
    }

    var caminoActual = Alternativa()
    caminoActual.fila = 0
    caminoActual.columna = 0
    caminoActual.coste = 0
    caminoActual.celdasVisitadas = mutableListOf()
    caminoActual.celdasVisitadas.add(Celda(0,0))

    colaAlternativas.add(caminoActual)

    imprimirMapa(mapa)


    //Calcular el camino más corto
    while (colaAlternativas.isNotEmpty()) {
        caminoActual = colaAlternativas.poll()
        println("Camino actual: fila:${caminoActual.fila}, columna:${caminoActual.columna}, coste:${caminoActual.coste}, visitadas:${caminoActual.celdasVisitadas.size}, por visitar:${colaAlternativas.size}")

        //Ha encontrado el camino!!!
        if (caminoActual.fila==ALTO && caminoActual.columna==ANCHO) {
            println("Camino encontrado: ${caminoActual.coste}")
            break
        }

        //Explorar las alternativas
        //Arriba
        if (caminoActual.fila-1 >=0 && mapa[caminoActual.fila-1][caminoActual.columna] != '#' && !caminoActual.celdasVisitadas.contains(Celda(caminoActual.fila-1, caminoActual.columna))) {
            var nuevaAlternativa = Alternativa()
            nuevaAlternativa.fila = caminoActual.fila-1
            nuevaAlternativa.columna = caminoActual.columna
            nuevaAlternativa.celdasVisitadas=caminoActual.celdasVisitadas
            nuevaAlternativa.celdasVisitadas.add(Celda(nuevaAlternativa.fila, nuevaAlternativa.columna))
            nuevaAlternativa.coste = caminoActual.coste + 1 //Coste de moverse a la nueva casilla
            colaAlternativas.add(nuevaAlternativa)
        }
        //Abajo
        if (caminoActual.fila+1 <= ALTO && mapa[caminoActual.fila+1][caminoActual.columna] != '#' && !caminoActual.celdasVisitadas.contains(Celda(caminoActual.fila+1, caminoActual.columna))) {
            var nuevaAlternativa = Alternativa()
            nuevaAlternativa.fila = caminoActual.fila+1
            nuevaAlternativa.columna = caminoActual.columna
            nuevaAlternativa.celdasVisitadas =caminoActual.celdasVisitadas
            nuevaAlternativa.celdasVisitadas.add(Celda(nuevaAlternativa.fila, nuevaAlternativa.columna))
            nuevaAlternativa.coste = caminoActual.coste + 1 //Coste de moverse a la nueva casilla
            colaAlternativas.add(nuevaAlternativa)
        }
        //Izquierda
        if (caminoActual.columna-1 >=0 &&mapa[caminoActual.fila][caminoActual.columna-1] != '#' && !caminoActual.celdasVisitadas.contains(Celda(caminoActual.fila, caminoActual.columna-1))) {
            var nuevaAlternativa = Alternativa()
            nuevaAlternativa.fila = caminoActual.fila
            nuevaAlternativa.columna = caminoActual.columna-1
            nuevaAlternativa.celdasVisitadas = caminoActual.celdasVisitadas
            nuevaAlternativa.celdasVisitadas.add(Celda(nuevaAlternativa.fila, nuevaAlternativa.columna))
            nuevaAlternativa.coste = caminoActual.coste + 1 //Coste de moverse a la nueva casilla
            colaAlternativas.add(nuevaAlternativa)
        }
        //Derecha
        if (caminoActual.columna+1 <= ANCHO && mapa[caminoActual.fila][caminoActual.columna+1] != '#' && !caminoActual.celdasVisitadas.contains(Celda(caminoActual.fila, caminoActual.columna+1))) {
            var nuevaAlternativa = Alternativa()
            nuevaAlternativa.fila = caminoActual.fila
            nuevaAlternativa.columna = caminoActual.columna+1
            nuevaAlternativa.celdasVisitadas = caminoActual.celdasVisitadas
            nuevaAlternativa.celdasVisitadas.add(Celda(nuevaAlternativa.fila, nuevaAlternativa.columna))
            nuevaAlternativa.coste = caminoActual.coste + 1 //Coste de moverse a la nueva casilla
            colaAlternativas.add(nuevaAlternativa)
        }
    }

}

fun imprimirMapa(mapa:MutableList<MutableList<Char>>) {
    println()
    for (fila in mapa) {
        println(fila.joinToString(""))
    }
    println()
}