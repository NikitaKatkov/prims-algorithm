package org.bmstu.prim.impl.input

import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.input.InputReader
import java.net.URI

class ConsoleInputReader : InputReader {
    override fun parseInput(): Graph {
        println("Enter file path:")
        val path = URI(readLine()!!)
        return FileInputReader(path).parseInput()
    }
}