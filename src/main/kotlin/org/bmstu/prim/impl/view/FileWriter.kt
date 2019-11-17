package org.bmstu.prim.impl.view

import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.view.ViewProvider
import org.bmstu.prim.iterateMarkedEdgesWithCoordinates
import java.io.File

class FileWriter(private val fileName: String): ViewProvider {
    override fun displayGraph(graph: Graph) {
        File(fileName).printWriter().use {
            iterateMarkedEdgesWithCoordinates(graph) { leftNode, rightNode ->
                it.println("$leftNode - $rightNode | ${graph.getEdgeWeight(leftNode, rightNode)}")
            }
        }
    }
}