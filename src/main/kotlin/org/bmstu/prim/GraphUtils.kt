package org.bmstu.prim

import org.bmstu.prim.api.graph.Graph

fun iterateMarkedEdgesWithCoordinates(graph: Graph, block: (Int, Int) -> Unit) {
    for (leftNode in 0 until graph.nodesNumber) {
        for (rightNode in 0 until graph.nodesNumber) {
            if (graph.isEdgeMarked(leftNode, rightNode)) {
                block(leftNode, rightNode)
            }
        }
    }
}