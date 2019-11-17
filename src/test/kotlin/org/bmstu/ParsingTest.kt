package org.bmstu

import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.impl.input.FileInputReader
import org.bmstu.prim.impl.input.StringInputReader
import org.junit.Assert
import org.junit.Test

class ParsingTest {
    @Test
    fun testParseGraphFromString() {
        val graph = StringInputReader(
            """
                6
                0 3 1
                1 2 3
                2 3 4
                3 4 2
                1 3 4
                1 4 5
                3 5 3
            """.trimIndent()
        ).parseInput()

        val expected = Graph(6, false).apply {
            addEdge(0, 3, 1)
            addEdge(1, 2, 3)
            addEdge(2, 3, 4)
            addEdge(3, 4, 2)
            addEdge(1, 3, 4)
            addEdge(1, 4, 5)
            addEdge(3, 5, 3)
        }
        Assert.assertEquals(expected, graph)
    }

    @Test
    fun testParseGraphFromFile() {
        val graph = FileInputReader("src/test/resources/smallGraph.txt").parseInput()
        val expected = Graph(6, false).apply {
            addEdge(0, 3, 1)
            addEdge(1, 2, 3)
            addEdge(2, 3, 4)
            addEdge(3, 4, 2)
            addEdge(1, 3, 4)
            addEdge(1, 4, 5)
            addEdge(3, 5, 3)
        }
        Assert.assertEquals(expected, graph)
    }
}