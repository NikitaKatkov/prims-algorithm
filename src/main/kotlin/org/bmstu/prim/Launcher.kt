package org.bmstu.prim

import org.bmstu.prim.impl.core.CoroutineBasedPrim
import org.bmstu.prim.impl.core.Solver
import org.bmstu.prim.impl.input.FileInputReader
import org.bmstu.prim.impl.view.ConsoleViewer
import org.bmstu.prim.impl.view.FileWriter
import java.io.File

fun main() {
    Solver(
        FileInputReader(setupWithPredefinedInput().toURI()),
        CoroutineBasedPrim(4),
        FileWriter("result.txt"),
        ConsoleViewer()
    ).solve()
}

fun setupWithPredefinedInput(): File {
    println("Type graph size (100, 1000, 3000): ")
    val size = readLine()!!.toInt()
    return File("/Users/nikita_katkov/IdeaProjects/bmstu/primsalgorithm/src/test/resources/largeGraphs/$size.txt")
}

