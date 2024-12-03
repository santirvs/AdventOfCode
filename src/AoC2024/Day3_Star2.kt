package AoC2024;

import java.util.*

var enabled = true

fun main() {
    val scan = Scanner(System.`in`)

    var sumaTotal :Long = 0

    //Cargar las listas
    while (scan.hasNext()) {
        var line:String = scan.nextLine()
        if (line == "-1") break

        sumaTotal += sumaMults2(line)

    }

    println(sumaTotal)

}


fun sumaMults2(line:String):Long {
    var suma:Long = 0
    var regEx = Regex("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)")

    var matches = regEx.findAll(line)
    for (match in matches) {
        if (enabled) {
            if (match.value == "don't()") {
                enabled = false
            }
            else if (match.value == "do()") {
                enabled = true
            }
            else {
                var partes = match.value.split(",", "(", ")").toTypedArray()
                var num1 = partes[1].toLong()
                var num2 = partes[2].toLong()
                suma += num1 * num2
            }
        }
        else {
            //Si está inhabilitado solo hacemos caso al comando "do" de habilitación
            if (match.value == "do()") {
                enabled = true
            }

        }
    }


    return suma
}

