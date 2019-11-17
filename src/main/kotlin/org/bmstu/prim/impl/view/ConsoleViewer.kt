package org.bmstu.prim.impl.view

import org.bmstu.prim.INFRASTRUCTURE
import org.bmstu.prim.Telemetry
import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.view.ViewProvider
import org.bmstu.prim.iterateMarkedEdgesWithCoordinates

class ConsoleViewer : ViewProvider {
    override fun displayGraph(graph: Graph) {
        println()
        println("CALCULATION RESULT: (left node - right node | weight)")
        iterateMarkedEdgesWithCoordinates(graph) { leftNode, rightNode ->
            println("$leftNode - $rightNode | ${graph.getEdgeWeight(leftNode, rightNode)}")
        }

        println()
        Telemetry.getAllTimings().forEach { (groupId, duration) ->
            println("$groupId took $duration ms - ${Telemetry.getPercentage(groupId, INFRASTRUCTURE)}%")
        }
    }
}