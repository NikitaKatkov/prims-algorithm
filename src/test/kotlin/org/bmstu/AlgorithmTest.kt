package org.bmstu

import org.bmstu.prim.impl.input.StringInputReader
import org.bmstu.prim.api.core.Algorithm
import org.bmstu.prim.impl.core.LightPrim
import org.bmstu.prim.impl.core.Solver
import org.bmstu.prim.impl.view.ConsoleViewer
import org.junit.Test
import java.util.logging.LogManager

class AlgorithmTest {
    companion object {
        private val LOG = LogManager.getLogManager().getLogger(AlgorithmTest::javaClass.name)
    }

    private fun doTestAlgorithm(inputData: String, algorithm: Algorithm) =
        Solver(StringInputReader(inputData), algorithm, ConsoleViewer).solve()

    @Test
    fun testSingleCorePrim() {
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
            LightPrim()
        )
    }
}
