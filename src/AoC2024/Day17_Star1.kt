package AoC2024.Day17_Star1

import java.io.File
import java.util.*
import java.util.function.IntPredicate
import kotlin.collections.HashMap
import kotlin.collections.HashSet

//Nada en especial para esta parte.
//Parsear la entrada e ir ejecutando cada comando
//Largo y tedioso, pero no parece complicado

const val AX = 0
const val BX = 1
const val CX = 2
const val CMB_AX = 4
const val CMB_BX = 5
const val CMB_CX = 6

//Array para almacenar el valor de los registros
var registros = Array(3) {0}

fun main() {
    val scan = Scanner(System.`in`)

    //Instruction Pointer
    var ip = 0
    // Programa
    var programa : List<Int>

    //Leer los registros
    for (i in 0..2) {
        var linea = scan.nextLine()
        registros[i] = linea.split(":")[1].trim().toInt()
    }

    //Leer el programa
    scan.nextLine()
    var instrucciones = scan.nextLine().split(":")[1].trim()
    programa = instrucciones.split(",").map { it.toInt() }

    //Ejecutar el programa
    var primerOutput = true
    while (ip < programa.size) {
        var instruccion = programa[ip]
        var operador = programa[ip+1]

        when (instruccion) {
            0 -> // ADV : AX = AX / 2^operador
                registros[AX] = registros[AX] / Math.pow(2.0, comboOperand(operador).toDouble()).toInt()
            1 -> // BXL : BX = BX XOR operador
                registros[BX] = registros[BX] xor operador
            2 -> //BST : BX = comboOperand % 8
                registros[BX] = comboOperand(operador) % 8
            3 -> //JNZ
                if (registros[AX] != 0) {
                    ip = operador
                    continue
                }
            4 -> //BXC : BX = BX XOR CX
                registros[BX] = registros[BX] xor registros[CX]
            5 -> //OUT : print comboOperand % 8
                {
                if (!primerOutput) print(",")
                else primerOutput = false
                print(comboOperand(operador) % 8)
                }
            6 -> // BDV : BX = AX / 2^operador
                registros[BX] = registros[AX] / Math.pow(2.0, comboOperand(operador).toDouble()).toInt()
            7 -> // CDV : CX = AX / 2^operador
                registros[CX] = registros[AX] / Math.pow(2.0, comboOperand(operador).toDouble()).toInt()
        }
        //Incrementar el IP
        ip += 2
    }
}


fun comboOperand(operador:Int) : Int {
    return when (operador) {
        0,1,2,3 -> operador
        CMB_AX -> registros[AX]
        CMB_BX -> registros[BX]
        CMB_CX -> registros[CX]
        else -> 0
    }
}