package org.bmstu.prim.api.core

import org.bmstu.prim.api.input.InputReader
import org.bmstu.prim.api.view.ViewProvider

class Solver {
    private lateinit var inputReader: InputReader
    private lateinit var algorithm: Algorithm
    private lateinit var viewProvider: ViewProvider

    fun withInputReader(inputReader: InputReader) = apply { this.inputReader = inputReader }
    fun withAlgorithm(algorithm: Algorithm) = apply { this.algorithm = algorithm }
    fun withViewProvider(viewProvider: ViewProvider) = apply { this.viewProvider = viewProvider }

    fun solve() {
        val graph = inputReader.parseInput()
        val result = algorithm.calculateMinimumSpanningTreeFor(graph)
        viewProvider.displayGraph(result)
    }
}