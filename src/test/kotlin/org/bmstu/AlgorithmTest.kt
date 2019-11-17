package org.bmstu

import org.bmstu.prim.api.core.Algorithm
import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.input.InputReader
import org.bmstu.prim.api.view.ViewProvider
import org.bmstu.prim.impl.core.CoroutineBasedPrim
import org.bmstu.prim.impl.core.Solver
import org.bmstu.prim.impl.input.StringInputReader
import org.bmstu.prim.impl.view.ConsoleViewer
import org.junit.Assert
import org.junit.Test

class AlgorithmTest {

    private fun doTestAlgorithm(
        inputReader: InputReader,
        algorithm: Algorithm,
        viewProvider: ViewProvider,
        expectedMst: Set<Pair<Int, Int>>? = null
    ): Graph {
        fun nodesRange(graph: Graph) = (0 until graph.nodesNumber)

        val result = Solver(inputReader, algorithm, viewProvider).solve()

        val actualMst = mutableListOf<Pair<Int, Int>>()
        for (leftNode in nodesRange(result)) {
            for (rightNode in nodesRange(result)) {
                if (result.isEdgeMarked(leftNode, rightNode)) {
                    actualMst.add(leftNode to rightNode)
                }
            }
        }

        if (expectedMst != null) {
            Assert.assertEquals(expectedMst.size, actualMst.size)
            for (actual in actualMst) {
                Assert.assertTrue(expectedMst.contains(actual))
            }
        }
        return result
    }

    @Test
    fun testPrimMstCalculation() {
        val stringInputReader = StringInputReader(
            """
                5
                0 1 5
                0 2 3
                0 3 7
                1 2 1
                1 3 5
                1 4 1
                2 3 8
                3 4 3
            """.trimIndent()
        )
        doTestAlgorithm(
            stringInputReader,
            CoroutineBasedPrim(4),
            ConsoleViewer(),
            setOf(
                0 to 2,
                1 to 4,
                2 to 1,
                4 to 3
            )
        )
    }
}
