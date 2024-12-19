package aoc2024.Day19_Star2

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

object Day19 {
    private var head: Node? = null
    private val cache = HashMap<String, Long>()

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val startTime = System.currentTimeMillis()
        head = Node(null)
        val br = BufferedReader(FileReader("entradas\\2024_19.in"))
        val towels = br.readLine().split(", ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (towel in towels) {
            head!!.addPossible(towel)
        }

        //        head.print(0);
        val patterns: MutableList<String> = ArrayList()
        var s: String
        while ((br.readLine().also { s = it }) != null) {
            patterns.add(s)
        }

        var p1: Long = 0
        var p2: Long = 0
        for (pattern in patterns) {
            val numPoss = head!!.canMatch(pattern)
            if (numPoss > 0) {
                p1++
            }
            //            System.out.printf("%s: %d%n", pattern, numPoss);
            p2 += numPoss
        }
        println(p1)
        println(p2)
        val endTime = System.currentTimeMillis()
        System.out.printf("%d ms%n", endTime - startTime)
    }

    //  0 white (w), 1 blue (u), 2 black (b), 3 red (r), or 4 green (g)
    internal class Node(private val c: Char?) {
        private val out = arrayOfNulls<Node>(5)
        private var possible = false

        fun addPossible(towel: String) {
            if (towel.isEmpty()) {
                possible = true
            } else {
                val first = towel[0]
                val index = convert(first)
                if (out[index] == null) {
                    out[index] = Node(first)
                }
                out[index]!!.addPossible(towel.substring(1))
            }
        }

        private fun convert(c: Char): Int {
            return when (c) {
                'w' -> 0
                'u' -> 1
                'b' -> 2
                'r' -> 3
                'g' -> 4
                else -> throw IllegalArgumentException()
            }
        }

        fun canMatch(pattern: String): Long {
            if (c == null) {
                if (cache.containsKey(pattern)) {
                    return cache[pattern]!!
                }
            }
            var result: Long = 0
            if (pattern.isEmpty()) {
                result = (if (possible) 1 else 0).toLong()
            } else {
                if (possible) {
                    result += head!!.canMatch(pattern)
                }
                val index = convert(pattern[0])
                if (out[index] != null) {
                    result += out[index]!!.canMatch(pattern.substring(1))
                }
            }
            if (c == null) {
                cache[pattern] = result
            }
            return result
        }

        fun print(indent: Int) {
            print(" ".repeat(indent))
            System.out.printf("%s %s%n", c, if (possible) "+" else "-")
            for (node in out) {
                node?.print(indent + 2)
            }
        }
    }
}