package org.bmstu.mock

import org.bmstu.prim.api.graph.Graph
import org.bmstu.prim.api.view.ViewProvider

class GraphToStringConverter(resultCollector: (Graph) -> Unit): ViewProvider {
    override fun displayGraph(graph: Graph) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}