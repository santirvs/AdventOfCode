package AoC2025;

import java.util.*

// Leer todos la entrada
// Separar por rangos (,)
// Para cada rango...
//  Separar el inicio y el final
//  Tratar rango
//      Inicio tiene longitud impar? --> buscar la siguiente longitud par
//      Final tiene longitud impar? --> buscar la anterior longitud par
//      Partir por la mitad el inicio
//      Coger la 1a mitad y duplicarla
//      Comprobar que se encuentra en el rango
//      Coger la 2a mitad y duplicarla
//      Comprobar que se encuentra en el rango
//      Restar la 2a mitad a la 1a mitad
//  Acumular el resultado del rango

fun main() {
    val scan = Scanner(System.`in`)

    var rangos = scan.nextLine().split(",")
    var suma : Long = 0
    repeat(rangos.size) { it ->
        suma += tratarRango(rangos[it])
    }

    println("La suma total es: $suma")

}

fun tratarRango(rango: String) : Long {
    var suma : Long = 0

    println("Rango original: $rango")

    var inicio = rango.split("-")[0]
    var final = rango.split("-")[1]

    println("Rango a tratar: $inicio - $final")

    //El inicio del rango tiene longitud impar?  Buscar el siguiente par -> 10000..000
    if (inicio.length %2 == 1) {
        inicio = "1" + "0".repeat(inicio.length)
    }
    //El final del rango tiene longitud impar? Buscar el anterior par -> 999...999
    if (final.length %2 == 1) {
        final = "9".repeat(final.length-1)
    }

    println("Rango adaptado: $inicio - $final")

    //Límites del rango
    var limInferior = inicio.toLong()
    var limSuperior = final.toLong()

    //Inicio duplicado queda dentro del rango?
    var medioInicio = inicio.substring(0, inicio.length/2).toLong()

    //EL primer falso ID queda dentro del rango?
    var falsoID = medioInicio.toString().repeat(2).toLong()
    if (falsoID >= limInferior && falsoID <= limSuperior) {
        println("El ID: $falsoID es FALSO")
        suma+=falsoID
    }

    while (falsoID <= limSuperior) {
        medioInicio++
        falsoID = medioInicio.toString().repeat(2).toLong()
        if (falsoID <= limSuperior) {
            println("El ID: $falsoID es FALSO")
            suma+=falsoID
        }
    }

    println("Devuelvo la suma: $suma")
    return suma
}
