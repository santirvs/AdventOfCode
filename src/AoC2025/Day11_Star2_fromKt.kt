import java.util.Scanner

class Reactor(val instructions: List<String>) {
    fun numberOfPaths(): Long {
        return recurseThroughInstruction("you", emptyList<String>())
    }

    fun numberOfSvrPaths(): Long {
        return recurseThroughInstruction("svr", listOf("fft", "dac"))
    }

    private val knownPaths = mutableMapOf<String, Long>()

    private fun recurseThroughInstruction(node: String, mustVisit: List<String>): Long {
        if (node == "out") return if (mustVisit.isEmpty()) 1 else 0
        val next = instructions.first { it.startsWith("$node: ") }.substring(5).split(" ")
        if (knownPaths[node + mustVisit] != null) return knownPaths[node + mustVisit]!!
        val pathLength = next.fold(0L) { acc, s -> acc + recurseThroughInstruction(s, mustVisit - s) }
        knownPaths[node + mustVisit] = pathLength
        return pathLength
    }
}

fun main() {
    var scan : Scanner = Scanner(System.`in`)

    //Leer las entradas y guardarlas List<String>
    var instrucciones : MutableList<String>  = mutableListOf()
    while (scan.hasNext()) {
        instrucciones.add(scan.nextLine())
    }

    var r = Reactor(instrucciones)

    println("Solucion 1 :: ${r.numberOfPaths()}")
    println("Solución 2 :: ${r.numberOfSvrPaths()}")
}