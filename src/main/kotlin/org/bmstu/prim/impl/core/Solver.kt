package org.bmstu.prim.impl.core

import org.bmstu.prim.INFRASTRUCTURE
import org.bmstu.prim.Telemetry
import org.bmstu.prim.api.core.Algorithm
import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.input.InputReader
import org.bmstu.prim.api.view.ViewProvider

class Solver(
    private val inputReader: InputReader,
    private val algorithm: Algorithm,
    private vararg val viewers: ViewProvider
) {

    fun solve(): Graph {
        Telemetry.reset()
        Telemetry.registerGroup(INFRASTRUCTURE)
        Telemetry.startPeriodForGroup(INFRASTRUCTURE)

        val graph = inputReader.parseInput()
        val result = algorithm.calculateMinimumSpanningTreeFor(graph)

        Telemetry.finishPeriodForGroup(INFRASTRUCTURE)
        viewers.forEach { it.displayGraph(result) }

        return result
    }
}