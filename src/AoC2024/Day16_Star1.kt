package AoC2024

import java.io.File
import java.util.*
import java.util.function.IntPredicate
import kotlin.collections.HashMap
import kotlin.collections.HashSet

//Cargar el mapa del almacén
//Identificar el inicio y el final
//Calcular el camino más corto entre el inicio y el final
//Devolver la longitud del camino más corto
//Para calcular el camino más corto, se puede usar un algoritmo de búsqueda en anchura
//Priorizando el camino con menor coste (no es necesario calcular la distancia de Manhattan)

data class Celda (var fila:Int, var columna:Int)
enum class Direccion {ARRIBA, ABAJO, IZQUIERDA, DERECHA}
class Alternativa {
    var fila:Int
    var columna:Int
    var celdasVisitadas:MutableList<Celda>
    var coste:Int
    var direccion:Direccion

    constructor() {
        fila = 0
        columna = 0
        celdasVisitadas = mutableListOf()
        coste = 0
        direccion = Direccion.DERECHA
    }
}
val compararAlternativas = Comparator<Alternativa> { a, b -> a.coste - b.coste }

fun main() {
    val scan = Scanner(System.`in`)

    var mapa : MutableList<MutableList<Char>> = mutableListOf()
    var posRobot:Celda = Celda(0,0)
    var caminoActual : Alternativa = Alternativa()
    var colaAlternativas : PriorityQueue<Alternativa> = PriorityQueue<Alternativa>(compararAlternativas)


    //Leer el mapa
    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "-1") break  //Final del mapa

        mapa.add(linea.toMutableList())

        //Buscar la casilla de salida
        if (linea.contains('S')) {
            posRobot.fila = mapa.size-1
            posRobot.columna = linea.indexOf('S')

            caminoActual.fila = posRobot.fila
            caminoActual.columna = posRobot.columna
            caminoActual.celdasVisitadas.add(Celda(caminoActual.fila, caminoActual.columna))
            caminoActual.coste = 0
            caminoActual.direccion = Direccion.DERECHA
            colaAlternativas.add(caminoActual)
        }
    }

    imprimirMapa16(mapa)

    //Calcular el camino más corto
    while (colaAlternativas.isNotEmpty()) {
        caminoActual = colaAlternativas.poll()
        println("Camino actual: ${caminoActual.fila}, ${caminoActual.columna}, ${caminoActual.coste}, ${caminoActual.direccion}, ${caminoActual.celdasVisitadas.size} , ${colaAlternativas.size}")

        //Ha encontrado el camino!!!
        if (mapa[caminoActual.fila][caminoActual.columna] == 'E') {
            println("Camino encontrado: ${caminoActual.coste}")
            break
        }

        //Explorar las alternativas
        //Arriba
        if (mapa[caminoActual.fila-1][caminoActual.columna] != '#' && !caminoActual.celdasVisitadas.contains(Celda(caminoActual.fila-1, caminoActual.columna))) {
            var nuevaAlternativa = Alternativa()
            nuevaAlternativa.fila = caminoActual.fila-1
            nuevaAlternativa.columna = caminoActual.columna
            nuevaAlternativa.celdasVisitadas=caminoActual.celdasVisitadas
            nuevaAlternativa.celdasVisitadas.add(Celda(nuevaAlternativa.fila, nuevaAlternativa.columna))
            nuevaAlternativa.coste = caminoActual.coste + 1 //Coste de moverse a la nueva casilla
            nuevaAlternativa.direccion = Direccion.ARRIBA
            if (caminoActual.direccion != Direccion.ARRIBA)
                nuevaAlternativa.coste += 1000 //Coste de girar
            colaAlternativas.add(nuevaAlternativa)
        }
        //Abajo
        if (mapa[caminoActual.fila+1][caminoActual.columna] != '#' && !caminoActual.celdasVisitadas.contains(Celda(caminoActual.fila+1, caminoActual.columna))) {
            var nuevaAlternativa = Alternativa()
            nuevaAlternativa.fila = caminoActual.fila+1
            nuevaAlternativa.columna = caminoActual.columna
            nuevaAlternativa.celdasVisitadas =caminoActual.celdasVisitadas
            nuevaAlternativa.celdasVisitadas.add(Celda(nuevaAlternativa.fila, nuevaAlternativa.columna))
            nuevaAlternativa.coste = caminoActual.coste + 1 //Coste de moverse a la nueva casilla
            nuevaAlternativa.direccion = Direccion.ABAJO
            if (caminoActual.direccion != Direccion.ABAJO)
                nuevaAlternativa.coste += 1000 //Coste de girar
            colaAlternativas.add(nuevaAlternativa)
        }
        //Izquierda
        if (mapa[caminoActual.fila][caminoActual.columna-1] != '#' && !caminoActual.celdasVisitadas.contains(Celda(caminoActual.fila, caminoActual.columna-1))) {
            var nuevaAlternativa = Alternativa()
            nuevaAlternativa.fila = caminoActual.fila
            nuevaAlternativa.columna = caminoActual.columna-1
            nuevaAlternativa.celdasVisitadas = caminoActual.celdasVisitadas
            nuevaAlternativa.celdasVisitadas.add(Celda(nuevaAlternativa.fila, nuevaAlternativa.columna))
            nuevaAlternativa.coste = caminoActual.coste + 1 //Coste de moverse a la nueva casilla
            nuevaAlternativa.direccion = Direccion.IZQUIERDA
            if (caminoActual.direccion != Direccion.IZQUIERDA)
                nuevaAlternativa.coste += 1000 //Coste de girar
            colaAlternativas.add(nuevaAlternativa)
        }
        //Derecha
        if (mapa[caminoActual.fila][caminoActual.columna+1] != '#' && !caminoActual.celdasVisitadas.contains(Celda(caminoActual.fila, caminoActual.columna+1))) {
            var nuevaAlternativa = Alternativa()
            nuevaAlternativa.fila = caminoActual.fila
            nuevaAlternativa.columna = caminoActual.columna+1
            nuevaAlternativa.celdasVisitadas = caminoActual.celdasVisitadas
            nuevaAlternativa.celdasVisitadas.add(Celda(nuevaAlternativa.fila, nuevaAlternativa.columna))
            nuevaAlternativa.coste = caminoActual.coste + 1 //Coste de moverse a la nueva casilla
            nuevaAlternativa.direccion = Direccion.DERECHA
            if (caminoActual.direccion != Direccion.DERECHA)
                nuevaAlternativa.coste += 1000 //Coste de girar
            colaAlternativas.add(nuevaAlternativa)
        }
    }

}

fun imprimirMapa16(mapa:MutableList<MutableList<Char>>) {
    println()
    for (fila in mapa) {
        println(fila.joinToString(""))
    }
    println()
}