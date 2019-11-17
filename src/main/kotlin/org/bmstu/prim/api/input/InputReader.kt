package org.bmstu.prim.api.input

import org.bmstu.prim.api.graph.Graph

interface InputReader {
    fun parseInput(): Graph
}