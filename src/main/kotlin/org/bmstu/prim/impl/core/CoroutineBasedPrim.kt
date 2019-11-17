package org.bmstu.prim.impl.core

import kotlinx.coroutines.*
import org.apache.log4j.LogManager
import org.bmstu.prim.ALGORITHM_GROUP
import org.bmstu.prim.CRITICAL_SECTION_GROUP
import org.bmstu.prim.PARALLEL_TASKS_GROUP
import org.bmstu.prim.Telemetry
import org.bmstu.prim.api.core.Algorithm
import org.bmstu.prim.api.graph.EdgeCoordinates
import org.bmstu.prim.api.graph.Graph
import kotlin.coroutines.CoroutineContext

@ObsoleteCoroutinesApi
class CoroutineBasedPrim(threadsCount: Int) : Algorithm {
    companion object {
        private val LOG = LogManager.getLogger("Prim's Algorithm")
        private const val NODE_NOT_FOUND = -1
    }

    private lateinit var graph: Graph
    private lateinit var indicesAlreadyInMst: BooleanArray

    private val threadPool = newFixedThreadPoolContext(threadsCount, "Prim's Algorithm")

    override fun calculateMinimumSpanningTreeFor(graph: Graph): Graph {
        initAlgorithm(graph)

        Telemetry.startPeriodForGroup(ALGORITHM_GROUP)
        for (iteration in 0 until graph.nodesNumber) addYetAnotherEdge()
        Telemetry.finishPeriodForGroup(ALGORITHM_GROUP)
        return graph
    }

    private fun addYetAnotherEdge() {
        Telemetry.startPeriodForGroup(PARALLEL_TASKS_GROUP)
        val candidates = runBlocking {
            indicesAlreadyInMst.indices
                .filter { indicesAlreadyInMst[it] }
                .parallelMap(threadPool) { findMinWeightIndexNotInMst(it) }
        }
        Telemetry.finishPeriodForGroup(PARALLEL_TASKS_GROUP)
        markEffectiveMinAmongSelection(candidates.filterNotNull(), graph)
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

    private fun markEffectiveMinAmongSelection(candidates: Iterable<EdgeCoordinates>, graph: Graph) {
        Telemetry.startPeriodForGroup(CRITICAL_SECTION_GROUP)
        with(candidates.minBy { graph.getEdgeWeight(it) }) {
            if (this == null) return

            indicesAlreadyInMst[newNode] = true
            addEdgeToMst(existingNode, newNode)
        }
        Telemetry.finishPeriodForGroup(CRITICAL_SECTION_GROUP)
    }

    private fun addEdgeToMst(existingNode: Int, newNode: Int) {
        //mark only one direction edges even for non-oriented graph - enough for result check
        graph.markEdge(existingNode, newNode)
    }

    private fun initAlgorithm(initialGraph: Graph) {
        graph = initialGraph
        indicesAlreadyInMst = BooleanArray(graph.nodesNumber)
        indicesAlreadyInMst[0] = true

        Telemetry.initWith(ALGORITHM_GROUP, PARALLEL_TASKS_GROUP, CRITICAL_SECTION_GROUP)
    }
}