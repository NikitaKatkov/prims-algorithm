package org.bmstu

import org.bmstu.mock.GraphToStringConverter
import org.bmstu.mock.StringInputReader
import org.bmstu.prim.api.core.Algorithm
import org.bmstu.prim.api.core.Solver
import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.impl.core.SingleCorePrim
import org.junit.Assert
import org.junit.Test
import java.util.logging.Level
import java.util.logging.LogManager

class AlgorithmTest {
    companion object {
        private val LOG = LogManager.getLogManager().getLogger(AlgorithmTest::javaClass.name)
    }

    private fun doTestAlgorithm(algorithm: Algorithm) {
        val graphDescription = "todo: some test graph description"
        val solver = Solver().withInputReader(StringInputReader(graphDescription))
            .withAlgorithm(algorithm)

        lateinit var calculatedGraph: String
        val viewProvider = GraphToStringConverter {
            calculatedGraph = it.toString() //fixme create good toString implementation or collect graph object itself
        }
        solver.withViewProvider(viewProvider)

        LOG.log(Level.INFO, calculatedGraph)
        val expected = Graph() //fixme create good test graph object or use string representation
        Assert.assertEquals("Graph string representations must be equal", expected, calculatedGraph)
    }

    @Test
    fun testSingleCorePrim() {
        doTestAlgorithm(SingleCorePrim)
    }
}
