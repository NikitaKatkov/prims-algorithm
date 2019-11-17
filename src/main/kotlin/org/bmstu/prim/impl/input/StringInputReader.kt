package org.bmstu.prim.impl.input

import org.bmstu.prim.NEW_LINE
import org.bmstu.prim.SEPARATOR
import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.input.InputReader

class StringInputReader(private val graphDescription: String) : InputReader {
    override fun parseInput(): Graph {
        val edges = graphDescription.split(NEW_LINE)
        val nodesNumber = edges.first().toInt()

        val graph = Graph(nodesNumber, false)
        edges.asSequence().drop(1)
            .forEach {
                if (it.isNotEmpty()) {
                    val (left, right, weight) = it.split(SEPARATOR).map(String::toInt)
                    graph.addEdge(left, right, weight)
                }
            }

        return graph
    }
}