package AoC2025

import java.util.Scanner
import java.util.TreeMap

//Leer todos los puntos
//Ordenar por filas (coord Y)
//Quedarme con el máximo y el mínimo de cada fila  (usar un Y -> List<X> )
//Una vez procesados todos los puntos, comprobar cada fila con el resto de filas
// guardar la cuadrícula mayor



fun main() {

    val scan = Scanner(System.`in`)

    //Leer los puntos
    var linea: String = scan.nextLine()
    var mapa : TreeMap<Int, MutableList<Int>> = TreeMap<Int, MutableList<Int>>()
    while (linea != "") {
        var X = linea.split(",")[0].toInt()
        var Y = linea.split(",")[1].toInt()

        if (mapa.containsKey(Y)) {
            var lista = mapa.get(Y)
            println("El rango para $Y es ${lista!![0]} - ${lista!![1]}")
            if (lista!![0]>X)
                lista!![0] = X
            if (lista!![1]<X)
                lista!![1] = X
            println("El nuevo rango para $Y es ${lista!![0]} - ${lista!![1]}")
        } else {
            var lista = mutableListOf<Int>(X,X)
            mapa.put(Y, lista)
        }
        linea = scan.nextLine()
    }

    //Buscar las combinaciones
    //Dadas dos filas, buscar el tamaño de los rectángulos
    //  p1.fila, max(p1) <-> p2.fila, min(p2)
    //  p2.fila, max(p2) <-> p1.fila1, min(p1)
    var tamanyo : Long = 0L
    for (fila1 in mapa.keys ) {
        var lista1 = mapa.get(fila1)
        for (fila2 in mapa.keys) {
            var lista2 = mapa.get(fila2)

            var tamanyo1 : Long = (Math.abs(fila1-fila2)+1L) * (Math.abs(lista1!![0]-lista2!![1])+1L)
            var tamanyo2 : Long = (Math.abs(fila1-fila2)+1L) * (Math.abs(lista1!![1]-lista2!![0])+1L)

            tamanyo = Math.max(tamanyo, tamanyo1)
            tamanyo = Math.max(tamanyo, tamanyo2)

        }
    }

    println("Star 1:  El tamanyo máximo es: $tamanyo")

}