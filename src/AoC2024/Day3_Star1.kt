package AoC2024;

import java.util.*

fun main() {
    val scan = Scanner(System.`in`)

    var sumaTotal :Long = 0

    //Cargar las listas
    while (scan.hasNext()) {
        var line:String = scan.nextLine()
        if (line == "-1") break

        sumaTotal += sumaMults(line)

    }

    println(sumaTotal)

}


fun sumaMults(line:String):Long {
    var suma:Long = 0
    var regEx = Regex("mul\\((\\d+),(\\d+)\\)")

    var matches = regEx.findAll(line)
    for (match in matches) {
        var partes = match.value.split(",","(",")").toTypedArray()
        var num1 = partes[1].toLong()
        var num2 = partes[2].toLong()
        suma += num1 * num2
    }


    return suma
}

