package org.bmstu.prim.impl.core

import kotlinx.coroutines.*
import org.apache.log4j.LogManager
import org.bmstu.prim.api.core.Algorithm
import org.bmstu.prim.api.graph.EdgeCoordinates
import org.bmstu.prim.api.graph.Graph
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

@ObsoleteCoroutinesApi
class CoroutineBasedPrim(threadsCount: Int) : Algorithm {
    companion object {
        private val LOG = LogManager.getLogger("Prim's Algorithm")
        private const val NODE_NOT_FOUND = -1
    }

    private lateinit var graph: Graph
    private lateinit var indicesAlreadyInMst: BooleanArray

    private val threadPool = newFixedThreadPoolContext(threadsCount, "Prim's Algorithm")

    private fun addYetAnotherEdge() {
        val candidates = runBlocking {
            indicesAlreadyInMst.indices
                .filter { indicesAlreadyInMst[it] }
                .parallelMap(threadPool) {
                    findMinWeightIndexNotInMst(it)
                }
        }
        markEffectiveMinAmongSelection(candidates.filterNotNull(), graph)
    }

    override fun calculateMinimumSpanningTreeFor(graph: Graph): Graph {
        initAlgorithm(graph)

        val measuredExecutionTime = measureTimeMillis {
            for (iteration in 0 until graph.nodesNumber) addYetAnotherEdge()
        }
        LOG.info("Calculation completed in $measuredExecutionTime ms")
        return graph
    }

    private suspend fun <T, R> Iterable<T>.parallelMap(
        context: CoroutineContext,
        heavyMappingOperation: suspend (T) -> R
    ): List<R> = withContext(context) {
        map {
            async {
                LOG.debug("Submitting task")
                heavyMappingOperation(it)
            }
        }.awaitAll()
    }

    private fun findMinWeightIndexNotInMst(currentNodeIndex: Int): EdgeCoordinates? {
        Thread.sleep(1000)
        val siblings = graph.getSiblings(currentNodeIndex)

        var minWeightIndex = NODE_NOT_FOUND
        var minWeight = Int.MAX_VALUE

        for (index in siblings.indices) {
            if (!indicesAlreadyInMst[index] && siblings[index].weight < minWeight) {
                minWeight = siblings[index].weight
                minWeightIndex = index
            }
        }
        return if (minWeightIndex == NODE_NOT_FOUND) null else EdgeCoordinates(currentNodeIndex, minWeightIndex)
    }

    private fun markEffectiveMinAmongSelection(candidates: Collection<EdgeCoordinates>, graph: Graph) {
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