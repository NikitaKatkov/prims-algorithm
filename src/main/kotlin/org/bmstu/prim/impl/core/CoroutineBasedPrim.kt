package org.bmstu.prim.impl.core

import org.bmstu.prim.api.core.Algorithm
import org.bmstu.prim.api.graph.EdgeCoordinates
import org.bmstu.prim.api.graph.EdgeData
import org.bmstu.prim.api.graph.Graph

class CoroutineBasedPrim(val threadsCount: Int) : Algorithm {
    companion object {
        private const val NODE_NOT_FOUND = -1
    }

    private lateinit var graph: Graph
    private lateinit var indicesAlreadyInMst: BooleanArray

    override fun calculateMinimumSpanningTreeFor(graph: Graph): Graph {
        initAlgorithm(graph)

        for (iteration in 0 until graph.nodesNumber) {
            val candidates = mutableListOf<EdgeCoordinates>()
            for (alreadyAddedNodeIndex in indicesAlreadyInMst.indices) {
                if (!indicesAlreadyInMst[alreadyAddedNodeIndex]) continue

                val localMinWeightIndex =
                    findMinWeightIndexNotInMst(graph.getSiblings(alreadyAddedNodeIndex)) ?: continue
                candidates.add(EdgeCoordinates(alreadyAddedNodeIndex, localMinWeightIndex))
            }

            markEffectiveMinAmongSelection(candidates, graph)
        }
        return graph
    }

    //parallel this
    private fun findMinWeightIndexNotInMst(siblings: Array<EdgeData>): Int? {
        var minWeightIndex = NODE_NOT_FOUND
        var minWeight = Int.MAX_VALUE

        for (index in siblings.indices) {
            if (!indicesAlreadyInMst[index] && siblings[index].weight < minWeight) {
                minWeight = siblings[index].weight
                minWeightIndex = index
            }
        }
        return if (minWeightIndex == NODE_NOT_FOUND) null else minWeightIndex
    }

    // critical section
    private fun markEffectiveMinAmongSelection(candidates: List<EdgeCoordinates>, graph: Graph) {
        with(candidates.minBy { graph.getEdgeWeight(it) }) {
            if (this == null) return

            indicesAlreadyInMst[newNode] = true
            addEdgeToMst(existingNode, newNode)
        }
    }

    private fun addEdgeToMst(existingNode: Int, newNode: Int) {
        //mark only one direction edges even for non-oriented graph - enough for result check
        graph.markEdge(existingNode, newNode)
    }

    private fun initAlgorithm(initialGraph: Graph) {
        graph = initialGraph
        indicesAlreadyInMst = BooleanArray(graph.nodesNumber)
        indicesAlreadyInMst[0] = true
    }
}