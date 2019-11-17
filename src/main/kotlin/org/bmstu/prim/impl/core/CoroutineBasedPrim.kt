package org.bmstu.prim.impl.core

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.apache.log4j.LogManager
import org.bmstu.prim.api.core.Algorithm
import org.bmstu.prim.api.graph.EdgeCoordinates
import org.bmstu.prim.api.graph.EdgeData
import org.bmstu.prim.api.graph.Graph
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

class CoroutineBasedPrim : Algorithm {
    companion object {
        private val LOG = LogManager.getLogger("Prim's Algorithm")
        private const val NODE_NOT_FOUND = -1

        private const val THREADS = 4
    }

    private lateinit var graph: Graph
    private lateinit var indicesAlreadyInMst: BooleanArray

    @ObsoleteCoroutinesApi
    private val threadPool = newFixedThreadPoolContext(THREADS, "Custom Thread Pool")

    override fun calculateMinimumSpanningTreeFor(graph: Graph): Graph {
        initAlgorithm(graph)

        val measuredExecutionTime = measureTimeMillis {
            for (iteration in 0 until graph.nodesNumber) {
                val candidates = mutableListOf<EdgeCoordinates>()

                runBlocking {
                    for (alreadyAddedNodeIndex in indicesAlreadyInMst.indices) {
                        if (!indicesAlreadyInMst[alreadyAddedNodeIndex]) continue

                        val localMinWeightIndex = wrapIntoCoroutine(threadPool) {
                            findMinWeightIndexNotInMst(graph.getSiblings(alreadyAddedNodeIndex))
                        } ?: continue

                        candidates.add(EdgeCoordinates(alreadyAddedNodeIndex, localMinWeightIndex))
                    }
                }
                markEffectiveMinAmongSelection(candidates, graph)
            }
        }
        LOG.info("Calculation completed in $measuredExecutionTime ms")
        return graph
    }

    private suspend fun <T> wrapIntoCoroutine(context: CoroutineContext, heavyTask: () -> T?): T? =
        withContext(context) {
            LOG.debug("Performing task...")
            heavyTask()
        }.also {
            LOG.debug("Sub-task calculation completed")
        }

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