package org.bmstu.prim

import org.bmstu.prim.impl.core.CoroutineBasedPrim
import org.bmstu.prim.impl.core.Solver
import org.bmstu.prim.impl.input.ConsoleInputReader
import org.bmstu.prim.impl.view.FileWriter

fun main() {
    Solver(
        ConsoleInputReader(),
        CoroutineBasedPrim(4),
        FileWriter("result.txt")
    ).solve()
}

