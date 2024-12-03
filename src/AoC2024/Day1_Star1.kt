package AoC2024;

import java.util.*


fun main() {
    val scan = Scanner(System.`in`)

    val leftList = mutableListOf<Int>()
    val rightList = mutableListOf<Int>()

    //Cargar las listas
    while (scan.hasNext()) {
        val left = scan.nextInt()
        if (left==-1) break             //Salida del bucle interactiva
        val right = scan.nextInt()

        leftList.add(left)
        rightList.add(right)
    }

    //Ordenar las listas
    leftList.sort()
    rightList.sort()

    //Calcular las distancias entre los elementos de ambas listas
    var distance = 0
    for (i in 0 until leftList.size) {
        distance += Math.abs(leftList[i] - rightList[i])
    }

    println(distance)
}
