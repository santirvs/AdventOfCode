package AoC2024.Day25_Star1

//Leer las entradas y apuntarlas en un TreeMap<String, Int>

import java.util.*


fun main() {
    val scan = Scanner(System.`in`)

    var keys : MutableList<MutableList<Int>> = mutableListOf()
    var locks : MutableList<MutableList<Int>> = mutableListOf()

    while (scan.hasNext()) {
        var linea=scan.nextLine()
        if (linea == "-1") break // Fin de la entrada interactiva
        if (linea == "") continue // Ignorar líneas en blanco
        var esLock : Boolean = false
        var clave : MutableList<Int> = mutableListOf(0, 0, 0, 0, 0)

        //Leer el bloque de 7 líneas
        for (l in 1..7) {
            if (l == 1 && linea == "#####") {
                // Es un cierre (lock)
                clave = mutableListOf(0, 0, 0, 0, 0)
                esLock = true
            } else if (l == 1 && linea == ".....") {
                // Es una llave (key)
                clave = mutableListOf(5, 5, 5, 5, 5)
                esLock = false
            } else {
                forma(esLock, linea, clave)
            }
            linea = scan.nextLine()
        }

        if (esLock) locks.add(clave)
        else keys.add(clave)
    }

    //Comprobar si las llaves y los cierres encajan
    var cantidad = 0
    for (k in keys) {
        for (l in locks) {
            var encajan = true
            for (i in 0 until 5) {
                var result = k.get(i) + l.get(i)
                if (result > 5) encajan = false
            }
            if (encajan) {
                println("Key: $k Lock: $l")
                cantidad++
            }
        }
    }

    println("Cantidad de llaves que encajan con cierres: $cantidad")


}

fun forma(esLock: Boolean, linea: String, clave:MutableList<Int> ) {
    var forma = ""
    var delta = if (esLock) 1 else -1
    for (i in 0 until 5) {
        if (linea.get(i) == '#' && esLock) clave[i] = clave[i] + delta
        else if (linea.get(i) == '.' && !esLock) clave[i] = clave[i] + delta
    }
}