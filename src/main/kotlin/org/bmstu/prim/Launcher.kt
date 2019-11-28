package org.bmstu.prim

import org.bmstu.prim.impl.core.SingleCorePrim
import org.bmstu.prim.impl.core.Solver
import org.bmstu.prim.impl.input.ConsoleInputReader
import org.bmstu.prim.impl.view.ConsoleViewer
import org.bmstu.prim.impl.view.FileWriter

fun main() {
    Solver(
        ConsoleInputReader(),
        SingleCorePrim(1),
        FileWriter("result.txt"),
        ConsoleViewer()
    ).solve()
}

