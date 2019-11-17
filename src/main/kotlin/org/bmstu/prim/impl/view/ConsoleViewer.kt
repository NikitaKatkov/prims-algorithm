package org.bmstu.prim.impl.view

import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.view.ViewProvider

object ConsoleViewer : ViewProvider {
    override fun displayGraph(graph: Graph) {
        println("CALCULATION RESULT: (left node - right node | weight)")
        for (leftNode in 0 until graph.nodesNumber) {
            for (rightNode in 0 until graph.nodesNumber) {
                if (graph.isEdgeMarked(leftNode, rightNode)) {
                    println("$leftNode - $rightNode | ${graph.getEdgeWeight(leftNode, rightNode)}")
                }
            }
        }
    }
}