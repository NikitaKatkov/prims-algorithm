package org.bmstu.prim.impl.input

import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.input.InputReader
import java.io.File
import java.net.URI

class FileInputReader(private val path: URI) : InputReader {
    override fun parseInput(): Graph {
        val text = File(path).readText()
        return StringInputReader(text).parseInput()
    }
}
