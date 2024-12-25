package AoC2024.Day24_Star1

//Leer las entradas y apuntarlas en un TreeMap<String, Int>

import java.util.*

data class Operacion(val operador: String, val operando1: String, val operando2:String, val resultado:String)

fun solve (op:Operacion, entradas:TreeMap<String, Int>) : Int {
    var valOperando1 = -1
    var valOperando2 = -1
    var valResultado = -1

    if (entradas.containsKey(op.operando1)) {
        valOperando1 = entradas.get(op.operando1)!!
    }
    if (entradas.containsKey(op.operando2)) {
        valOperando2 = entradas.get(op.operando2)!!
    }

    if (op.operador == "AND" && (valOperando1 == 0 || valOperando2 == 0) ) {
        valResultado = 0
    }
    else if (op.operador == "OR" && (valOperando1 == 1 || valOperando2 == 1) ) {
        valResultado = 1
    }
    else if (valOperando1 != -1 && valOperando2 != -1) {
        when (op.operador) {
            "AND" -> valResultado = valOperando1 and valOperando2
            "OR" -> valResultado = valOperando1 or valOperando2
            "XOR" -> valResultado = valOperando1 xor valOperando2
        }
    }

    if (valResultado != -1) {
        entradas.put(op.resultado, valResultado)
    }

    return valResultado

}
fun main() {
    val scan = Scanner(System.`in`)

    var entradas = TreeMap<String, Int>()
    
    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "") break // Cambio de sección
        var partes = linea.split(": ")
        entradas.put(partes[0], partes[1].toInt())
    }
    
    //Empiezan las operaciones
    var operaciones = ArrayList<Operacion>()
    
    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "-1") break // Salida manual interactiva
        var partes = linea.split(" -> ")
        var operacion = partes[0].split(" ")    
        var resultado = partes[1]
        var operador = operacion[1]
        var operando1 = operacion[0]
        var operando2 = operacion[2]

        var op = Operacion(operador, operando1, operando2, resultado)
        if (solve(op, entradas) == -1) {
            operaciones.add(op)
        }

    }
    
    //Operaciones pendientes
    while (!operaciones.isEmpty()) {
        var operacion = operaciones.removeAt(0)
        if (solve(operacion, entradas) == -1) {
            operaciones.add(operacion)
        }
    }

    //Extraer la lista de "z"
    var zetas = TreeMap<String, Int>()
    entradas.filter { it.key.startsWith("z")}.forEach { zetas.put(it.key,it.value) }

    //Obtener el número codificado en la z
    var result = 0L
    var factor = 1L
    for (z in zetas) {
        result += z.value * factor
        factor *= 2
    }

    //Imprimir el resultado
    println(result)
}
