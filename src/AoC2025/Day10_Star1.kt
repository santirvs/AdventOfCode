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
// De hecho, se puede mapear esa combinación a un entero y mantener un Set de Int para verificar si ya se ha usado esa combinación o no

// Para el caso 2, en lugar de una codificación del estado, lo que hay que mirar es que no superemos ningún valor del contador
// En el caso que se supere, esa combinación debe descartarse. TLE!! Explora demasiadas combinaciones antes de llegar a la solución
// Debe resolverse mediante un sistema de ecuaciones lineales (copiado y adaptado desde Python en otro fichero)


data class Mapa (
    var mapa: MutableList<Boolean> = mutableListOf(),
    var movimientos:Int,
    var joltage: MutableList<Int> = mutableListOf()
)

data class Caso (
        var mapaEsperado: Mapa,
        var botones: MutableList<MutableList<Int>> = mutableListOf(),
        var joltage: MutableList<Int> = mutableListOf()
        ) {
    var mapas : MutableList<Mapa> = mutableListOf()

    init {
        var apagado : MutableList<Boolean> = MutableList<Boolean>(mapaEsperado.mapa.size) {false}
        var joltage0 : MutableList<Int> = MutableList<Int>(joltage.size) {0}
        mapas.add(Mapa(apagado,0, joltage0))
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
    val l = mutableListOf<Int>()
    val s = joltage.replace("{", "")
        .replace("}", "")
        .split(",")
    for (n in s) {
        l.add(n.toInt())
    }
    return l
}

fun resolverCaso_parte1( caso : Caso) : Int {
    var numMovimientos : Int = 0
    var mapasExplorados: HashSet<Int> = HashSet<Int>()

    while (caso.mapas.first().mapa.equals(caso.mapaEsperado.mapa) == false ) {

        for (boton in caso.botones) {
            var mapa = caso.mapas.first().copy()
            mapa.mapa = mapa.mapa.toList().toMutableList() // Hace un clon de la lista
            mapa.movimientos++
            mapa.joltage = mapa.joltage.toList().toMutableList()

            aplicarBotones(mapa.mapa, boton, mapa.joltage)
            //Verificar que la combinación no haya sido explorada ya (parte 1)
            var codigoMapa = codificarMapa(mapa.mapa)
            if (!mapasExplorados.contains(codigoMapa)) {
                caso.mapas.add(mapa)
                mapasExplorados.add(codigoMapa)
            }

        }

        caso.mapas.removeAt(0)

        println("Intentando caso: ${caso.mapas.first().mapa.toString()}  Tamaño: ${caso.mapas.size} Movimientos: ${caso.mapas.first().movimientos} ")
    }
    numMovimientos = caso.mapas.first().movimientos

    return numMovimientos
}


fun resolverCaso_parte2( caso : Caso) : Int {
    var numMovimientos : Int = 0

    while (caso.mapas.first().mapa.equals(caso.mapaEsperado.mapa) == false ||
           caso.mapas.first().joltage.equals(caso.joltage) == false) {

        for (boton in caso.botones) {
            var mapa = caso.mapas.first().copy()
            mapa.mapa = mapa.mapa.toList().toMutableList() // Hace un clon de la lista
            mapa.movimientos++
            mapa.joltage = mapa.joltage.toList().toMutableList()

            aplicarBotones(mapa.mapa, boton, mapa.joltage)
            //Verificar que la combinación no exceda el contador de Joltage (parte 2)

            if (checkJoltage(caso.joltage, mapa.joltage)) {
                caso.mapas.add(mapa)
            }

        }

        caso.mapas.removeAt(0)

        println("Intentando caso: ${caso.mapas.first().mapa.toString()}  Tamaño: ${caso.mapas.size} Movimientos: ${caso.mapas.first().movimientos} Joltage:${caso.mapas.first().joltage.toString()} ")
    }
    numMovimientos = caso.mapas.first().movimientos

    return numMovimientos
}



fun checkJoltage(objectiu: List<Int>, llista: List<Int>) : Boolean {
    var pos:Int = 0
    var resultat = true
    while (pos < objectiu.size && resultat) {
        if (objectiu[pos] < llista[pos]) {
            resultat=false
        }
        pos++
    }
    return resultat
}


fun codificarMapa(mapa:List<Boolean>):Int {
    var resultado : Int = 0
    var pos:Int = 0
    while (pos < mapa.size) {
        if (mapa[pos]) resultado = resultado +  (1 shl pos)
        pos++
    }
    return resultado
}

fun aplicarBotones(mapa:MutableList<Boolean>, botones: MutableList<Int>, joltage:MutableList<Int>) {
    for (b in botones) {
        mapa[b] = !mapa[b]
        joltage[b]++
    }
}


fun solve_10(filename: String, parte: Int): Int {
    val casos = getInput_10(filename)
    var numMovimientos = 0

    var numCasos : Int = 0
    for (c in casos) {
        if (parte == 1)
            numMovimientos += resolverCaso_parte1(c)
        else
            numMovimientos += resolverCaso_parte2(c)

        println("Resuelto el caso $numCasos con $numMovimientos")
        numCasos++
    }

    return numMovimientos

}

fun main() {
    println("--- Part 1 ---")
    println("Test: ${solve_10("entradas/2025_10_test.txt",1)}")
    //println("Solution: ${solve_10("entradas/2025_10_input.txt",1)}")
    println("--- Part 2 ---")
    println("Test: ${solve_10("entradas/2025_10_test.txt",2)}")
//    println("Solution: ${sol2("entradas/2025_10_input.txt")}")
}
