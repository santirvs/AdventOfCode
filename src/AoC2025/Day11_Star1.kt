import AoC2024.buscar
import java.io.File
import java.util.Scanner
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

// Leer los datos en un mapa String --> Lista<String>
// Quedarme con el nodo de nombre "you" para empezar por él
// Apuntarme una lista de nodos visitados (para no entrar en bucle)
// Para cada nodo, añadirlo en visitados
// Y añadir cada uno de los destinos (si no se ha visitado ya)
// Si el destino es "out", no se añade y se incrementa el contador

// parte 2: parece una pequeña derivación de la parte 1,
// con la restricción de que sólo debe tenerse en cuenta que pase por los nodos (que ya me he apuntado)
// pero crece muy rápidamente debido al solapamiento de casos
// Ojo al planteamiento recursivo de Day11_Star2_fromKt !!! Olé, olé y olé


data class Camino(
    var nodosVisitados : MutableSet<String>,
    var nodoActual : String
)


fun main() {
    var scan : Scanner = Scanner(System.`in`)

    //Leer las entradas y guardarlas en un HashMap<String, List<String>>
    var conexiones : HashMap<String, List<String>> = HashMap<String, List<String>>()
    while (scan.hasNext()) {
        //Hacer Ctrl+D para simular el final de la entrada
        var linea = scan.nextLine().split(":")
        var nodo = linea[0]
        var destinos = linea[1].trim().split(" ")
        var listaDestinos = mutableSetOf<String>()
        listaDestinos.addAll(destinos)
        conexiones.put(nodo, listaDestinos.toList())
    }

    //var totalCaminos = buscarCaminos("you","out", listOf<String>(), conexiones)
    //println("Solución Parte 1: $totalCaminos")

    var totalCaminos = buscarCaminos("svr","out", listOf<String>("fft","dac"), conexiones)
    println("Solución Parte 2: $totalCaminos")

}

fun buscarCaminos(desde:String, hasta:String, puntosPaso: List<String>, conexiones:HashMap<String, List<String>> ): Int {
    //Recuperar el nodo "desde" y empezar a buscar
    var actual = Camino( mutableSetOf(), desde )
    var totalCaminos = 0
    var listaCaminos : MutableList<Camino> = mutableListOf()
    listaCaminos.add(actual)
    while (!listaCaminos.isEmpty()) {
        actual = listaCaminos.removeAt(0)
        println("Analizando ${actual.nodoActual} Visitados: ${actual.nodosVisitados.toString()}  TotalCaminos:${listaCaminos.size}")
        var destinos = conexiones.get(actual.nodoActual)
        for (d in destinos!!) {
            if (d == hasta) {
                if  (actual.nodosVisitados.containsAll(puntosPaso)) {
                    totalCaminos++
                }
                else {
                    //ignorar este destino
                }
            }
            else {
                if (actual.nodosVisitados.contains(d)) {
                    //Nodo ya visitado. Ignorar
                }
                else {
                    //Añadir nueva opción
                    var c : Camino
                    var nv = actual.nodosVisitados.toList().toMutableSet()
                    nv.add(actual.nodoActual)
                    c = Camino(nv, d)
                    listaCaminos.add(c)
                }
            }
        }
    }
    return totalCaminos
}