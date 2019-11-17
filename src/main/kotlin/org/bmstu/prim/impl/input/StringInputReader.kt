package org.bmstu.prim.impl.input

import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.input.InputReader

class StringInputReader(private val graphDescription: String): InputReader {
    companion object {
        private const val NEW_LINE = "\n"
        private const val SEPARATOR = " "
    }

    override fun parseInput(): org.bmstu.prim.api.graph.Graph {
        val edges = graphDescription.split(NEW_LINE)
        val nodesNumber = edges.first().toInt()

        val graph = Graph(nodesNumber, false)
        edges.asSequence().drop(1)
            .forEach {
                val (left, right, weight) = it.split(SEPARATOR).map(String::toInt)
                graph.addEdge(left, right, weight)
            }

        return graph
    }
}