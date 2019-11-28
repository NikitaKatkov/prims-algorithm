package org.bmstu.prim.impl.input

import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.input.InputReader
import java.io.File

class ConsoleInputReader : InputReader {
    override fun parseInput(): Graph {
        println("Enter file path:")
        val path = readLine()!!
        val pathUri = File(path).toURI()
        return FileInputReader(pathUri).parseInput()
    }
}