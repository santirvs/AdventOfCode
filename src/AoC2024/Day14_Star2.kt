package AoC2024

import java.io.File
import java.util.*
import java.util.function.IntPredicate
import kotlin.collections.HashMap
import kotlin.collections.HashSet

//Iterar cada robot 1 segundo
//Imprimir la matriz de robots
//Comprobar visualmente si aparece un árbol de navidad
//Alternativa----> 10000 iteraciones, enviar a un fichero cada iteración y buscar "XXXXXXXXXXXXXX" en el fichero

data class Robot(var x: Int, var y: Int, var velX: Int, var velY: Int)

fun main() {
    val scan = Scanner(System.`in`)

    val ANCHO =101
    val ALTO = 103
    val CENTRO_ANCHO = (ANCHO / 2)
    val CENTRO_ALTO = (ALTO / 2)
    val TIEMPO = 1

    var robots : Array<Robot> = arrayOf()

    var out = File("salidas\\output_Day14.txt")
    var fichero = out.bufferedWriter()

    while (scan.hasNext()) {
        var linea = scan.nextLine()
        if (linea == "-1") break  //Final de la entrada manual

        //Parsear la entrada
        //Posicion del robot
        var posicion = linea.split(" ")[0].split(",")
        var x: Int = posicion[0].substring(2).toInt()
        var y: Int = posicion[1].toInt()

        //Velocidad del robot
        var velocidad = linea.split(" ")[1].split(",")
        var velX = velocidad[0].substring(2).toInt()
        var velY = velocidad[1].toInt()

        robots += Robot(x, y, velX, velY)
    }

    repeat (10000) {
        var matriz = Array(ALTO) {CharArray(ANCHO) {' '} }
        for (robot in robots) {
            robot.x = ((robot.x + robot.velX * TIEMPO) % ANCHO + ANCHO ) % ANCHO
            robot.y = ((robot.y + robot.velY * TIEMPO) % ALTO + ALTO ) % ALTO

            matriz[robot.y][robot.x] = 'X'
        }

        fichero.write("********** ITERACION  ${it+1} *********\n")
        for (fila in matriz) {
            fichero.write(fila)
            fichero.newLine()
        }
    }

    fichero.close()

}
