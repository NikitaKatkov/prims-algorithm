package org.bmstu.prim

import org.bmstu.prim.api.core.Solver
import org.bmstu.prim.impl.core.SingleCorePrim
import org.bmstu.prim.impl.input.ConsoleInputReader
import org.bmstu.prim.impl.view.ConsoleViewer

fun main() {
    Solver().withInputReader(ConsoleInputReader())
            .withAlgorithm(SingleCorePrim)
            .withViewProvider(ConsoleViewer)
            .solve()
}

