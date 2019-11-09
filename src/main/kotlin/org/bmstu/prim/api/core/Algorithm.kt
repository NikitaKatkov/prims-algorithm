package org.bmstu.prim.api.core

import org.bmstu.prim.api.graph.Graph

interface Algorithm {
    fun calculateMinimumSpanningTreeFor(graph: Graph): Graph
}