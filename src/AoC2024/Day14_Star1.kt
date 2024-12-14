package AoC2024

import java.util.*
import java.util.function.IntPredicate
import kotlin.collections.HashMap
import kotlin.collections.HashSet

//Leer cada uno de los robots
//Simular el movimiento de cada uno de los robots después de 100 segundos
//Identificar el cuadrante en el que se queda y acumularlo en un contador
//Calcular el resultado

fun main() {
    val scan = Scanner(System.`in`)

    val ANCHO =101
    val ALTO = 103
    val CENTRO_ANCHO = (ANCHO / 2)
    val CENTRO_ALTO = (ALTO / 2)
    val TIEMPO = 100

    var robots : Array<Long> = Array (4, {0})

    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "-1") break  //Final de la entrada manual

        //Parsear la entrada
        //Posicion del robot
        var posicion = linea.split(" ")[0].split(",")
        var x : Int = posicion[0].substring(2).toInt()
        var y : Int = posicion[1].toInt()

        //Velocidad del robot
        var velocidad = linea.split(" ")[1].split(",")
        var velX = velocidad[0].substring(2).toInt()
        var velY = velocidad[1].toInt()

        //Posicion final del robot
        var posX = ((x + velX * TIEMPO) % ANCHO + ANCHO ) % ANCHO
        var posY = ((y + velY * TIEMPO) % ALTO + ALTO ) % ALTO


        //Determinar el cuadrante en el que queda el robot
        if (posX > CENTRO_ANCHO && posY > CENTRO_ALTO)
            robots[3]++
        else if (posX < CENTRO_ANCHO && posY > CENTRO_ALTO)
            robots[2]++
        else if (posX < CENTRO_ANCHO && posY < CENTRO_ALTO)
            robots[0]++
        else if (posX > CENTRO_ANCHO && posY < CENTRO_ALTO)
            robots[1]++

        println("Robot en ($posX, $posY)  --  Q1:${robots[0]} Q2:${robots[1]} Q3:${robots[2]} Q4:${robots[3]}")


    }
    println("Total robots: ${robots[0]} * ${robots[1]} * ${robots[2]} * ${robots[3]} = ${robots[0] * robots[1] * robots[2] * robots[3]}")

}
