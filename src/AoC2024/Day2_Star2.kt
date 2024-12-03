package AoC2024;

import java.util.*

fun main() {
    val scan = Scanner(System.`in`)

    var safeNumber :Int = 0

    //Cargar las listas
    while (scan.hasNext()) {
        var line:String = scan.nextLine()
        if (line == "-1") break
        val lista = line.split(" ").map { it.toInt() }.toMutableList()

        var safe:Boolean = true
        var creciente:Boolean = true

        if (lista[0] > lista[1]) creciente = true
        if (lista[0] < lista[1]) creciente = false
        else safe = false;

        safe = isSafe(lista)
        var i:Int = 0
        while (!safe && i < lista.size) {
            var lista2 = lista.toMutableList()
            lista2.removeAt(i)
            safe = isSafe(lista2)
            i++
        }

        if (safe) {
            safeNumber++
        }


    }

    println(safeNumber)


}


fun isSafe(lista:MutableList<Int>):Boolean {
    var safe:Boolean = true
    var creciente:Boolean = true
    for (i in 1 until lista.size) {
        if (i==1) {
            creciente = lista[1] > lista[0]
        }
        if (creciente && (lista[i] <= lista[i-1] || !(lista[i] - lista[i-1] in 1..3))) {
            safe = false
        } else if (!creciente && (lista[i] >= lista[i-1] || !(lista[i-1] - lista[i] in 1..3))){
            safe = false
        }
    }
    return safe
}