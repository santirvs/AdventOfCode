package AoC2024

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


fun main() {
    val scan = Scanner(System.`in`)
    var entrada : String

    //Leer la entrada (una única línea!
    entrada = scan.nextLine()

    //Convertir la entrada al formato interno
    var pos = 0
    var index = 0
    var fileId = 0
    var diskUsed = TreeMap<Int, Int>()      // DiscoUsado position -> fileId
    var diskFree : MutableList<Int> = mutableListOf()
    while (index < entrada.length) {
        var espacioOcupado = Integer.parseInt(entrada[index].toString())
        var espacioLibre = 0
        if (index+1 < entrada.length)
            espacioLibre = Integer.parseInt(entrada[index + 1].toString())
        index +=2

        for (i in 0 until espacioOcupado) {
            diskUsed[pos++] = fileId
        }

        for (i in 0 until espacioLibre) {
            diskFree.add(pos++)
        }
        fileId++
    }

    //Ordenar los bloques de disco libres
    diskFree.sort()
    //Mover los bloques de disco al primer espacio libre
    var posUsed = diskUsed.lastKey()
    var posFree = diskFree.first()

    while (posFree != null && posFree < posUsed) {
        //Sacar el bloque de disco usado
        var fileId : Int = diskUsed[posUsed]!!
        diskUsed.remove(posUsed)
        //Moverlo al primer espacio libre y añadir el espacio usado a la lista de libres
        diskUsed[posFree] = fileId
        diskFree.add(posUsed)
        //Eliminar el espacio libre
        diskFree.removeAt(0)
        posFree = diskFree.first()
        posUsed = diskUsed.lastKey()
    }

    //Una vez reorganizado el disco, calcular el checksum  (position * fileId)

    var checksum : Long = 0
    for (key in diskUsed.keys) {
        checksum += key * diskUsed[key]!!.toLong()
    }

    println(checksum)

}







