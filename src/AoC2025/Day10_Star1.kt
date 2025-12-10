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

// Se desmadra muy rápido:  Validar que la combinación no haya sido tratada ya, en tal caso, ignorarla y no añadirla a la lista
// Sinó, tendremos un problema de solapamiento
// Examinando la entrada, se puede ver que el tamaño más grande es de 10 --> 2^10 = 1024 combinaciones posibles.
// Nuestra lista de estados no debería superar nunca esa cantidad.

data class Mapa (
    var mapa: MutableList<Boolean> = mutableListOf(),
    var movimientos:Int
)

data class Caso (
        var mapaEsperado: Mapa,
        var botones: MutableList<MutableList<Int>> = mutableListOf(),
        var joltage: MutableList<Int> = mutableListOf()
        ) {
    var mapas : MutableList<Mapa> = mutableListOf()

    init {
        var apagado : MutableList<Boolean> = MutableList<Boolean>(mapaEsperado.mapa.size) {false}
        mapas.add(Mapa(apagado,0))
    }
}

fun getInput_10(filename: String): MutableList<Caso> {
    return File(filename)
        .readLines()
        .map { line ->
            val parts = line.split(" ")
            val mapa : MutableList<Boolean> = crearMapa(parts[0])
            val botones : MutableList<MutableList<Int>> = crearBotones(parts)
            val joltage : MutableList<Int> = crearJoltage(parts[parts.lastIndex])
            Caso( Mapa(mapa,0), botones, joltage)
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

    var index = 1
    while (index < botones.size-1) {
        val l = mutableListOf<Int>()
        val s = botones[index].replace("(", "")
            .replace(")", "")
            .split(",")
        for (n in s) {
            l.add(n.toInt())
        }
        m.add(l)
        index++
    }
    return m
}

fun crearJoltage(joltage:String): MutableList<Int> {
    val m : MutableList<Int> = mutableListOf()
    // Nada que hacer por ahora
    return m
}

fun resolverCaso( caso : Caso) : Int {
    var numMovimientos : Int = 0

    while (caso.mapas.first().mapa.equals(caso.mapaEsperado.mapa) == false ) {

        for (boton in caso.botones) {
            var mapa = caso.mapas.first().copy()
            mapa.mapa = mapa.mapa.toList().toMutableList() // Hace un clon de la lista
            mapa.movimientos++

            aplicarBotones(mapa.mapa, boton)
            caso.mapas.add(mapa)
        }

        caso.mapas.removeAt(0)

        println("Intentando caso: ${caso.mapas.first().mapa.toString()}  Tamaño: ${caso.mapas.size} Movimientos: ${caso.mapas.first().movimientos} ")
    }
    numMovimientos = caso.mapas.first().movimientos

    return numMovimientos
}

fun aplicarBotones(mapa:MutableList<Boolean>, botones: MutableList<Int>) {
    for (b in botones) {
        mapa[b] = !mapa[b]
    }
}


fun solve_10_1(filename: String): Int {
    val casos = getInput_10(filename)
    var numMovimientos = 0

    var numCasos : Int = 0
    for (c in casos) {
        numMovimientos += resolverCaso(c)

        println("Resuelto el caso $numCasos con $numMovimientos")
        numCasos++
    }

    return numMovimientos

}

fun main() {
    println("--- Part 1 ---")
    println("Test: ${solve_10_1("entradas/2025_10_test.txt")}")
    println("Solution: ${solve_10_1("entradas/2025_10_input.txt")}")
//    println("--- Part 2 ---")
//    println("Test: ${sol2("entradas/2025_09_test.txt")}")
//    println("Solution: ${sol2("entradas/2025_09_input.txt")}")
}
