package org.bmstu.prim.impl.input

import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.input.InputReader
import java.io.File

class FileInputReader(private val path: String) : InputReader {
    override fun parseInput(): Graph {
        val text = File(path).readText()
        return StringInputReader(text).parseInput()
    }
}
