import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

// Leer los datos y dividir entre mapa, combinaciones, joltage (ignorar)
// Para cada caso
// Inicializar el mapa a off
// A partir del mapa, probar con cada combinación de interruptores
// El resultado de cada combinación se guarda en una Lista (al final)
// Iterar la lista, cogiendo el primero y añadiendo los N resultados hasta
// encontrar un resultado que coincida con el mapa esperado

data class Mapa (
    var mapa: MutableList<Boolean> = mutableListOf()
)

data class Caso (
        var mapas: MutableList<Mapa> = mutableListOf(),
        var botones: MutableList<MutableList<Int>> = mutableListOf(),
        var joltage: MutableList<Int> = mutableListOf()
        ) {
    var mapaEsperado= mapas.get(0)
    var mapaActual = MutableList(mapaEsperado.mapa.size) {false}
}

fun getInput_10(filename: String): MutableList<Caso> {
    return File(filename)
        .readLines()
        .map { line ->
            val parts = line.split(" ")
            val mapa : MutableList<Boolean> = crearMapa(parts[0])
            val botones : MutableList<MutableList<Int>> = crearBotones(parts)
            val joltage : MutableList<Int> = crearJoltage(parts[parts.lastIndex])
            val m: MutableList<Mapa> = mutableListOf()
            m.add(Mapa(mapa))
            Caso( m, botones, joltage)
        }.toMutableList()
}

fun crearMapa(mapa:String): MutableList<Boolean> {
    val m : MutableList<Boolean> = mutableListOf()
    for (c in mapa) {
        if (c=='.') m.add(false)
        if (c=='#') m.add(true)
    }
    return m
}

fun crearBotones(botones:List<String>): MutableList<MutableList<Int>> {
    val m : MutableList<MutableList<Int>> = mutableListOf()

    val index = 1
    while (index < botones.size-1) {
        val l = mutableListOf<Int>()
        val s = botones[index].replace("(", "")
            .replace(")", "")
            .split(",")
        for (n in s) {
            l.add(n.toInt())
        }
        m.add(l)
    }
    return m
}

fun crearJoltage(joltage:String): MutableList<Int> {
    val m : MutableList<Int> = mutableListOf()
    // Nada que hacer por ahora
    return m
}


fun solve_10_1(filename: String): Int {
    val casos = getInput_10(filename)
    var numMovimientos = 0

    return numMovimientos

}

fun main() {
    println("--- Part 1 ---")
    println("Test: ${solve_10_1("entradas/2025_10_test.txt")}")
    //println("Solution: ${sol1("entradas/2025_10_input.txt")}")
//    println("--- Part 2 ---")
//    println("Test: ${sol2("entradas/2025_09_test.txt")}")
//    println("Solution: ${sol2("entradas/2025_09_input.txt")}")
}
