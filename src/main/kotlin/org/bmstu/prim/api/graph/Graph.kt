package org.bmstu.prim.api.graph

class Graph(val nodesNumber: Int, private val isOriented: Boolean) {
    companion object {
        private const val UNREACHABLE = Int.MAX_VALUE
    }

    private val matrix = Array(nodesNumber) { Array(nodesNumber) { EdgeData(UNREACHABLE) } }

    fun addEdge(leftNode: Int, rightNode: Int, weight: Int) {
        matrix[leftNode][rightNode].weight = weight
        if (!isOriented) matrix[rightNode][leftNode].weight = weight
    }

    fun getEdgeWeight(leftNode: Int, rightNode: Int): Int = matrix[leftNode][rightNode].weight

    fun getEdgeWeight(edgeCoordinates: EdgeCoordinates): Int = getEdgeWeight(edgeCoordinates.existingNode, edgeCoordinates.newNode)

    fun getSiblings(nodesIndex: Int) = matrix[nodesIndex]

    fun markEdge(leftNode: Int, rightNode: Int) = with(matrix[leftNode][rightNode]) { marker = true }

    fun isEdgeMarked(leftNode: Int, rightNode: Int) = matrix[leftNode][rightNode].marker

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Graph

        if (nodesNumber != other.nodesNumber) return false
        if (isOriented != other.isOriented) return false
        if (!matrix.contentDeepEquals(other.matrix)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nodesNumber
        result = 31 * result + isOriented.hashCode()
        result = 31 * result + matrix.contentDeepHashCode()
        return result
    }
}

data class EdgeCoordinates(val existingNode: Int, val newNode: Int)

data class EdgeData(var weight: Int, var marker: Boolean = false)