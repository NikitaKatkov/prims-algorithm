package org.bmstu.prim

import org.bmstu.prim.impl.core.CoroutineBasedPrim
import org.bmstu.prim.impl.core.Solver
import org.bmstu.prim.impl.input.FileInputReader
import org.bmstu.prim.impl.view.ConsoleViewer

fun main() {
    Solver(
        FileInputReader("resources/graph.txt"),
        CoroutineBasedPrim(),
        ConsoleViewer
    ).solve()
}

