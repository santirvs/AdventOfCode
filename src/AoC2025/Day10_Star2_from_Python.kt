import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.abs
import kotlin.math.min

private const val INF = 999_999_999

/* ===================== UTILIDADES ===================== */

fun gcd(a: Int, b: Int): Int {
    var x = abs(a)
    var y = abs(b)
    while (y != 0) {
        val t = x % y
        x = y
        y = t
    }
    return x
}

/*
 Read an input line and return a Triple(A, b, c) where:
 A is the coefficient matrix
 b is the target vector
 c is the bounds vector
*/
fun readLineParsed(l: String): Triple<MutableList<MutableList<Int>>, MutableList<Int>, MutableList<Int>> {

    val b = l.substring(l.indexOf('{') + 1, l.indexOf('}'))
        .split(",")
        .map { it.trim().toInt() }
        .toMutableList()

    val A = MutableList(b.size) { mutableListOf<Int>() }
    val c = mutableListOf<Int>()

    var end = l.indexOf(']')
    while (l.substring(end).contains("(")) {
        val begin = l.indexOf('(', end) + 1
        end = l.indexOf(')', begin)

        val bu = l.substring(begin, end)
            .split(",")
            .map { it.trim().toInt() }

        c.add(bu.minOf { b[it] })

        for (i in b.indices) {
            A[i].add(if (i in bu) 1 else 0)
        }
    }

    return Triple(A, b, c)
}

/* ===================== MATRIZ: OPERACIONES ===================== */

fun swapRow(A: MutableList<MutableList<Int>>, b: MutableList<Int>, i: Int, j: Int) {
    if (i != j) {
        val tmpRow = A[i]
        A[i] = A[j]
        A[j] = tmpRow

        val tmp = b[i]
        b[i] = b[j]
        b[j] = tmp
    }
}

fun swapCol(A: MutableList<MutableList<Int>>, c: MutableList<Int>, i: Int, j: Int) {
    if (i != j) {
        for (k in A.indices) {
            val tmp = A[k][i]
            A[k][i] = A[k][j]
            A[k][j] = tmp
        }
        val tmp = c[i]
        c[i] = c[j]
        c[j] = tmp
    }
}

fun reduceRow(A: MutableList<MutableList<Int>>, b: MutableList<Int>, i: Int, j: Int) {
    if (A[i][i] != 0) {
        val x = A[i][i]
        val y = -A[j][i]
        val d = gcd(x, y)

        for (k in A[i].indices) {
            A[j][k] = (y * A[i][k] + x * A[j][k]) / d
        }
        b[j] = (y * b[i] + x * b[j]) / d
    }
}

/* ===================== REDUCCIÓN DE MATRIZ ===================== */

fun reduce(
    A: MutableList<MutableList<Int>>,
    b: MutableList<Int>,
    c: MutableList<Int>
): Triple<MutableList<MutableList<Int>>, MutableList<Int>, MutableList<Int>> {

    var col = 0
    while (col < A[0].size) {
        var rowsWithPivot: List<Int> = emptyList()
        var k = col

        while (rowsWithPivot.isEmpty() && k < A[0].size) {
            swapCol(A, c, col, k)
            rowsWithPivot = (col until A.size).filter { A[it][col] != 0 }
            k++
        }

        if (rowsWithPivot.isEmpty()) break

        swapRow(A, b, col, rowsWithPivot[0])

        for (j in col + 1 until A.size) {
            reduceRow(A, b, col, j)
        }

        col++
    }

    // Remove zero rows
    val nonZeroRows = A.indices.filter { row -> A[row].any { it != 0 } }

    val A2 = nonZeroRows.map { A[it] }.toMutableList()
    val b2 = nonZeroRows.map { b[it] }.toMutableList()

    // Back substitution
    for (i in A2.size - 1 downTo 0) {
        for (j in 0 until i) {
            reduceRow(A2, b2, i, j)
        }
    }

    return Triple(A2, b2, c)
}

/* ===================== PARÁMETROS LIBRES ===================== */

fun paramComb(nParam: Int, c: List<Int>): List<List<Int>> {
    if (nParam == 0) return listOf(emptyList())

    val res = mutableListOf<List<Int>>()
    val bound = c[c.size - nParam]

    for (i in 0..bound) {
        for (tail in paramComb(nParam - 1, c)) {
            res.add(listOf(i) + tail)
        }
    }
    return res
}

/* ===================== RESOLUCIÓN ===================== */

fun solveSystemMinSum(
    A: List<List<Int>>,
    b: List<Int>,
    c: List<Int>
): Int {

    val k = A[0].size - A.size
    var minSum = INF

    for (params in paramComb(k, c)) {
        var sol = params.sum()

        for (i in A.indices) {
            var cc = 0
            for (j in params.indices) {
                cc += params[j] * A[i][A[0].size - k + j]
            }

            val s = b[i] - cc
            val a = s / A[i][i]

            if (a < 0 || s % A[i][i] != 0) {
                sol = INF
                break
            }
            sol += a
        }
        minSum = min(minSum, sol)
    }
    return minSum
}

/* ===================== MAIN ===================== */

fun main() {
    // Ctrl+D para simular el final del fichero
    val reader = BufferedReader(InputStreamReader(System.`in`))
    var total = 0

    reader.lineSequence().forEach { line ->
        val (A, b, c) = readLineParsed(line)
        val (A2, b2, c2) = reduce(A, b, c)
        total += solveSystemMinSum(A2, b2, c2)
    }

    println(total)
}
