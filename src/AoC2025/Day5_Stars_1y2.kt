package AoC2025;

import java.util.*

data class Rango(
    val desde:Long,
    val hasta:Long
    );

// Leer los rangos y almacenar en una lista
// Leer los números y determinar si entran en algun rango
// Contar los elementos que entran en algún rango

fun main() {
    val scan = Scanner(System.`in`)
// Leer los rangos y almacenar en una lista
    var listaRangos = mutableListOf<Rango>()
    var linea = scan.nextLine()
    while (linea!=""){
        var partes = linea.split("-")
        var desde:Long = partes[0].toLong()
        var hasta:Long = partes[1].toLong()
        listaRangos.add(Rango(desde, hasta))
        linea = scan.nextLine()
    }

    // Leer los números y determinar si entran en algún rango
    // Contar los elementos que entran en algún rango
    var num:Long = 0L
    var cantidadEnRango:Int = 0
    while (num != -1L) {
        num = scan.nextLine().toLong()
        if (enAlgunRango(num, listaRangos)) {
            cantidadEnRango++
        }
    }

    //Contar la cantidad de IDs que hay en los rangos solapados
    var totalIDs = rangosSolapados(listaRangos)

    println("Parte1: $cantidadEnRango")
    println("Parte2: $totalIDs")

}

fun enAlgunRango(num:Long, lista:List<Rango>) : Boolean {
    var resultat = false
    for (r in lista) {
        if (num >= r.desde && num <= r.hasta) {
            resultat = true
        }
    }
    return resultat
}

fun rangosSolapados(lista:MutableList<Rango>) : Long {
    //Ordenar los rangos
    lista.sortBy{ it.desde }
    var resultado : Long = 0
    var inicio : Long = lista[0].desde
    var final : Long = lista[0].hasta
    for ( r in lista) {
        if (r.desde > final) {
            // Rango no solapado. Calcular
            resultado+=final-inicio+1
            inicio = r.desde
            final = r.hasta
        }
        else if (r.hasta > final) {
            final = r.hasta
        }
    }

    //Último rango
    resultado+=final-inicio+1

    return resultado
}


