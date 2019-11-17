package org.bmstu.prim.impl.core

import org.bmstu.prim.api.core.Algorithm
import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.input.InputReader
import org.bmstu.prim.api.view.ViewProvider

class Solver(
    private val inputReader: InputReader,
    private val algorithm: Algorithm,
    private val viewer: ViewProvider
) {

    fun solve(): Graph {
        val graph = inputReader.parseInput()
        val result = algorithm.calculateMinimumSpanningTreeFor(graph)
        viewer.displayGraph(result)

        return result
    }
}