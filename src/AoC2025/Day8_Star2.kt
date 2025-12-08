package AoC2025

import java.util.Scanner

//Leer todos los puntos
//Calcular la distancia euclidiana entre ellos
//Conectar los dos puntos más cercanos

/*
data class Punto (
    val x:Int,
    val y:Int,
    val z:Int,
    var puntoReferencia:Int,
    var conexiones: MutableSet<Int>
)
*/

fun main() {

    val scan = Scanner(System.`in`)

    //Leer los puntos
    var linea:String = scan.nextLine()
    val listaPuntos = mutableListOf<Punto>()
    var numLinea = 0
    while (linea!="") {
        var partes = linea.split(",")
        listaPuntos.add(Punto(partes[0].toInt(), partes[1].toInt(), partes[2].toInt(), -1,mutableSetOf(numLinea)))
        numLinea++
        linea = scan.nextLine()
    }

    //Calcular la distancia entre los puntos
    val distancias = mutableListOf<MutableList<Double>>()
    for (p in listaPuntos) {
        var distanciasP = mutableListOf<Double>()
        for (p2 in listaPuntos) {
            distanciasP.add( distanciaEuclidiana(p,p2))
        }
        distancias.add(distanciasP)
    }

    //Ya tengo la matriz de todas las distancias

    var encontrado: Boolean = false
    var resultadoStar2 : Long = 0L
    var it : Int = 0
    while  (!encontrado) {
        //Buscar la distancia mínima (que no sea 0)
        var dMin = Double.MAX_VALUE
        var posP: Int = 0
        var posP2: Int = 0
        for (p in distancias.indices) {
            for (p2 in distancias[p].indices) {
                var d: Double = distancias[p][p2]
                if (d > 0 && d < dMin) {
                    dMin = d
                    posP = p
                    posP2 = p2
                }
            }
        }
        //Conectar los puntos entre posP y posP2
        distancias[posP][posP2] = 0.0
        distancias[posP2][posP] = 0.0

        var posPOriginal = posP
        var posP2Original = posP2

        println("Iteración $it -- Unir los puntos $posP y $posP2")

        var puntosRecorrido = mutableListOf<Int>()
        //Buscar el punto de referencia de posP y posP2
        while (listaPuntos[posP].puntoReferencia != -1) {
            puntosRecorrido.add(posP)
            posP = listaPuntos[posP].puntoReferencia
        }
        while (listaPuntos[posP2].puntoReferencia != -1) {
            puntosRecorrido.add(posP2)
            posP2 = listaPuntos[posP2].puntoReferencia
        }
        listaPuntos[posP].conexiones.addAll(puntosRecorrido)
        listaPuntos[posP].conexiones.addAll(listaPuntos[posP2].conexiones)
        if (posP != posP2)
            listaPuntos[posP2].puntoReferencia = posP

        if (listaPuntos[posP].conexiones.size == listaPuntos.size) {
            encontrado = true
            resultadoStar2 = listaPuntos[posPOriginal].x.toLong() * listaPuntos[posP2Original].x.toLong()
        }
        println("La última conexión ha creado una longitud de ${listaPuntos[posP].conexiones.size}")

        it++

    }
    println("Resultado Star2:: $resultadoStar2")

}