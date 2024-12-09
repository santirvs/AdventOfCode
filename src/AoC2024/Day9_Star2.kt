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
    var diskUsed = TreeMap<Int, MutableList<Int>>()      // FileId -> List of positions
    var diskFree = TreeMap<Int, Int> ()                  // position -> size
    while (index < entrada.length) {
        var espacioOcupado = Integer.parseInt(entrada[index].toString())
        var espacioLibre = 0
        if (index + 1 < entrada.length)
            espacioLibre = Integer.parseInt(entrada[index + 1].toString())
        index += 2

        //Guardar el espacio usado
        var listaPosiciones = mutableListOf<Int>()
        for (i in 0 until espacioOcupado) {
            listaPosiciones.add(pos++)
        }
        diskUsed[fileId] = listaPosiciones

        //Guardar el espacio libre, si lo hay
        if (espacioLibre > 0) {
            diskFree[pos] = espacioLibre
        }
        pos+=espacioLibre

        fileId++
    }


    //Mover los espacios de disco al primer espacio libre
    var ficheroExplorado = fileId - 1
    while (ficheroExplorado >= 0) {
        var posiciones = diskUsed[ficheroExplorado]!!
        var tamanyo = posiciones.size

        for (pos in diskFree.keys) {
            //Si se busca un espacio más a la derecha de donde se encuentra el fichero, se pasa al siguiente fichero
            if (pos > posiciones[0] ) {
                break
            }

            //Si hay espacio suficiente, mover el fichero
            if (diskFree[pos]!! >= tamanyo) {
                var posicionesALiberar : MutableList<Int> = mutableListOf()
                posicionesALiberar.addAll(diskUsed[ficheroExplorado]!!)
                diskUsed[ficheroExplorado]!!.clear()
                //Mover el fichero
                for (i in 0 until tamanyo) {
                    diskUsed[ficheroExplorado]!!.add(pos+i)
                    diskFree[pos] = diskFree[pos]!! - 1
                }
                //No se elimina el espacio libre, sino que se reduce (incluso hasta cero)
                //Pero ahora el espacio libre ya no empieza en pos sino en pos+tamanyo
                var espacioLibre = diskFree[pos]!!
                diskFree.remove(pos)
                if (espacioLibre > 0)
                    diskFree[pos+tamanyo] =  espacioLibre

                //Un espacio liberado nunca se reutilizará por un fichero anterior, por lo tanto no es necesario añadir el espacio libre que deja
                // el fichero que ha sido movido

                //El fichero se ha movido, por lo tanto se pasa al siguiente fichero
                break
                }
            }
        ficheroExplorado--
    }

    //Calcular el checksum
    var checksum: Long = 0
    for (fileId in diskUsed.keys) {
        for (pos in diskUsed[fileId]!!) {
            checksum += fileId * pos
        }
    }

    //Mostar el resultado
    println("Checksum: $checksum")

}







