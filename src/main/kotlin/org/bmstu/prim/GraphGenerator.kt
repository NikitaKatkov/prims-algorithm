package org.bmstu.prim

import java.io.File
import java.io.PrintWriter
import kotlin.random.Random

fun generateFullGraph(nodesCount: Int, filePath: String): File {
    val random = Random
    val edges = mutableMapOf<Int, Set<Int>>()

    fun generateWeight(): Int = random.nextInt(LOWER_WEIGHT_BOUND, UPPER_WEIGHT_BOUND)

    fun addNode(leftNode: Int, rightNode: Int, printWriter: PrintWriter) {
        if (edges[leftNode]?.contains(rightNode) == true || edges[rightNode]?.contains(leftNode) == true) return

        if (edges[leftNode] == null) {
            edges[leftNode] = mutableSetOf()
        }

        (edges[leftNode] as MutableSet).add(rightNode)
        printWriter.println("$leftNode $rightNode ${generateWeight()}")
    }

    return File(filePath).also {
        it.printWriter().use {printWriter ->
            printWriter.println(nodesCount)

            for (leftNode in 0 until nodesCount) {
                for (rightNode in 0 until nodesCount) {
                    addNode(leftNode, rightNode, printWriter)
                }
            }
        }
    }
}

