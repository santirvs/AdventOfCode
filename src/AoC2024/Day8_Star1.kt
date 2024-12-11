package AoC2024

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


//En la carga de datos, almacenar las frecuencias de las antenas
//Una vez realizada la carga, para cada antena, emparejarla con el resto de antenas de la misma frecuencia
//A medida que se leen las antenas, nos guardamos la lista de antenas de la misma frecuencia

data class Coordenadas (val fila : Int, val columna : Int)

fun main() {
    val scan = Scanner(System.`in`)
    val antenas: HashMap<Char, MutableList<Coordenadas>> = HashMap()

    //Cargar valores
    var filas = 0
    var columnas = 0
    while (scan.hasNextLine()) {
        val linea = scan.nextLine()
        if (linea == "-1") break  //Final de la entrada manual

        //Añadir las diferentes antenas
        columnas = 0
        for (c in linea) {
            if (c != '.') {
                if (antenas.containsKey(c)) {
                    antenas[c]!!.add(Coordenadas(filas, columnas))
                } else {
                    antenas[c] = mutableListOf(Coordenadas(filas, columnas))
                }
            }
            columnas++
        }
        filas++
        columnas = linea.length
    }

    //Crear la matriz de antinodos, inicializada a false
    val antinodos: MutableList<MutableList<Boolean>> = MutableList(filas) { MutableList(columnas) { false } }
    var totalAntinodos = 0

    //Recorrer el set de antenas
    for (antena in antenas) {
        //Recorrer la matriz de antenas
        for (antena1 in antena.value) {
            for (antena2 in antena.value) {

                //Si las antenas son diferentes, buscar sus antinodos y marcarlos
                if (antena1 != antena2) {
                    val fila1 = antena1.fila
                    val columna1 = antena1.columna
                    val fila2 = antena2.fila
                    val columna2 = antena2.columna

                    //Calcular la pendiente
                    val distanciaFila = fila2 - fila1
                    val distanciaColumna = columna2 - columna1

                    //Antinodo 1
                    var filaA1 = fila2 - 2 * distanciaFila
                    var columnaA1 = columna2 - 2 * distanciaColumna
                    try {
                        if (antinodos[filaA1][columnaA1] == false) {
                            antinodos[filaA1][columnaA1] = true
                            totalAntinodos++
                        }
                    }
                    catch (e: IndexOutOfBoundsException) {
                        //Fuera de límites, no hacer nada
                    }

                    //Antinodo 2
                    var filaA2 = fila1 + 2 * distanciaFila
                    var columnaA2 = columna1 + 2 * distanciaColumna
                    try {
                        if (antinodos[filaA2][columnaA2] == false) {
                            antinodos[filaA2][columnaA2] = true
                            totalAntinodos++
                        }
                    }
                    catch (e: IndexOutOfBoundsException) {
                        //Fuera de límites, no hacer nada
                    }
                }
            }
        }
    }

    println(totalAntinodos)
}






