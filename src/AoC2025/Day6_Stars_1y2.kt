package AoC2025;

import java.util.*

// Leer cada línia y almacenarla en una lista
// La última línea es la que contiene las operaciones a realizar en cada columna
// Realizar las operaciones y acumular

// Parte II
// Los espacios son significativos, así que no podemos separar por espacios
// y habrá que tratar una lista de Strings
// Crear una segunda lista

fun main() {
    val scan = Scanner(System.`in`)

    // Leer cada línia y almacenarla en una lista
    var listaDatos = mutableListOf<List<String>>()
    var listaDatos2 = mutableListOf<String>()
    var linea = scan.nextLine()
    while (linea != "") {
        var partes = linea.trim().split("[ ]+".toRegex())  // Cualquier repetición de espacios es un separador
        listaDatos.add(partes)
        listaDatos2.add(linea)
        linea = scan.nextLine()
    }

    //Recorrer la última lista y operar las columnas
    var resultadoTotal = 0L
    for (columna in listaDatos[listaDatos.lastIndex].indices) {
        val operador = listaDatos[listaDatos.lastIndex][columna]
        var resultadoOperacion: Long = 0
        if (operador == "*") resultadoOperacion = 1L

        for (fila in listaDatos.indices) {
            if (fila != listaDatos.lastIndex) {
                if (operador == "+")
                    resultadoOperacion += listaDatos[fila][columna].toInt()
                else
                    resultadoOperacion *= listaDatos[fila][columna].toInt()
            }
        }
        resultadoTotal +=resultadoOperacion
    }

    println("Parte1 :: $resultadoTotal")

    //Resolver 2a parte
    var columna = listaDatos2[0].length -1
    resultadoTotal = 0L


    var numeros = mutableListOf<Long>()
    while (columna >= 0) {
        var numero = 0L
        var operador :Char = ' '
        repeat (listaDatos2.size) { fila ->
            if (fila!=listaDatos2.size-1) {
                if (listaDatos2[fila].length > columna && listaDatos2[fila][columna].isDigit())
                    numero = numero * 10 + (listaDatos2[fila][columna] - '0')
            }
            else if (listaDatos2[fila].length > columna)
                    operador = listaDatos2[fila][columna]
        }
        println("El número es: $numero" )
        numeros.add(numero)
        if (operador!=' ')  {
            var total:Long = 0
            //Hacer la operación suma o multiplicacion
            if (operador == '+') total = numeros.sum()
            if (operador == '*') {
                total = 1L
                for (n in numeros) {
                    total *= n
                }
            }
            println("El resultado de la columna és: $total")
            resultadoTotal += total
            columna--
            numeros.clear()
        }
        columna--
    }

    println("Parte2 :: $resultadoTotal")

}


