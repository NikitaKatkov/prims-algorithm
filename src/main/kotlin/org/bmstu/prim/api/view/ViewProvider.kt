package org.bmstu.prim.api.view

import org.bmstu.prim.api.graph.Graph

interface ViewProvider {
    fun displayGraph(graph: Graph)
}