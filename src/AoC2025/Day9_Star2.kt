import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

typealias Point = Pair<Int, Int>

fun getInput(filename: String): List<Point> {
    return File(filename)
        .readLines()
        .map { line ->
            val parts = line.split(",")
            Point(parts[0].toInt(), parts[1].toInt())
        }
}

fun area(p: Point, q: Point): Int {
    return (abs(p.first - q.first) + 1) * (abs(p.second - q.second) + 1)
}

fun sol1(filename: String): Int {
    val tiles = getInput(filename)
    var maxArea = 0
    for (i in tiles.indices) {
        for (j in i + 1 until tiles.size) {
            val rectArea = area(tiles[i], tiles[j])
            if (rectArea > maxArea) maxArea = rectArea
        }
    }
    return maxArea
}

fun line(p: Point, q: Point): Set<Point> {
    val (a1, a2) = min(p.first, q.first) to max(p.first, q.first)
    val (b1, b2) = min(p.second, q.second) to max(p.second, q.second)
    val s = mutableSetOf<Point>()
    for (a in a1..a2) {
        for (b in b1..b2) {
            s.add(Point(a, b))
        }
    }
    return s
}

fun findPerimeter(tiles: List<Point>): Set<Point> {
    val perimeter = mutableSetOf<Point>()
    for (i in 1 until tiles.size) {
        perimeter += line(tiles[i - 1], tiles[i])
    }
    perimeter += line(tiles.last(), tiles.first())
    return perimeter
}

fun strictlyContainsPerimeter(p: Point, q: Point, perimeter: Set<Point>): Boolean {
    val a1 = min(p.first, q.first)
    val a2 = max(p.first, q.first)
    val b1 = min(p.second, q.second)
    val b2 = max(p.second, q.second)

    for (s in perimeter) {
        if (s.first > a1 && s.first < a2 &&
            s.second > b1 && s.second < b2) {
            return false
        }
    }
    return true
}

fun sol2(filename: String): Int {
    val tiles = getInput(filename)
    val perimeter = findPerimeter(tiles)

    val areas = mutableListOf<Pair<Pair<Point, Point>, Int>>()
    for (i in tiles.indices) {
        for (j in i + 1 until tiles.size) {
            val p = tiles[i]
            val q = tiles[j]
            areas.add(Pair(Pair(p, q), area(p, q)))
        }
    }

    areas.sortByDescending { it.second }

    for ((pair, a) in areas) {
        val (p, q) = pair
        if (strictlyContainsPerimeter(p, q, perimeter)) {
            return a
        }
    }
    return 0
}

fun main() {
    println("--- Part 1 ---")
    println("Test: ${sol1("entradas/2025_09_test.txt")}")
    println("Solution: ${sol1("entradas/2025_09_input.txt")}")
    println("--- Part 2 ---")
    println("Test: ${sol2("entradas/2025_09_test.txt")}")
    println("Solution: ${sol2("entradas/2025_09_input.txt")}")
}
