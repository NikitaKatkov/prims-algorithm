package org.bmstu

import org.bmstu.prim.impl.input.StringInputReader
import org.bmstu.prim.api.core.Algorithm
import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.impl.core.CoroutineBasedPrim
import org.bmstu.prim.impl.core.Solver
import org.bmstu.prim.impl.view.ConsoleViewer
import org.junit.Assert
import org.junit.Test
import java.util.logging.LogManager

class AlgorithmTest {
    companion object {
        private val LOG = LogManager.getLogManager().getLogger(AlgorithmTest::javaClass.name)
    }

    private fun doTestAlgorithm(inputData: String, algorithm: Algorithm, expectedMst: Set<Pair<Int, Int>>) {
        fun nodesRange(graph: Graph) = (0 until graph.nodesNumber)

        val result = Solver(StringInputReader(inputData), algorithm, ConsoleViewer).solve()

        val actualMst = mutableListOf<Pair<Int, Int>>()
        for (leftNode in nodesRange(result)) {
            for (rightNode in nodesRange(result)) {
                if (result.isEdgeMarked(leftNode, rightNode)) {
                    actualMst.add(leftNode to rightNode)
                }
            }
        }

        Assert.assertEquals(expectedMst.size, actualMst.size)
        for (actual in actualMst) {
            Assert.assertTrue(expectedMst.contains(actual))
        }
    }

    @Test
    fun testPrimMstCalculation() {
        doTestAlgorithm(
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
            """.trimIndent(),
            CoroutineBasedPrim(4),
            setOf(
                0 to 2,
                1 to 4,
                2 to 1,
                4 to 3
            )
        )
    }
}
