package bekyiu

import java.io.InputStreamReader
import java.util.Arrays

/**
 * @Date 2022/12/11 12:28 PM
 * @Created by bekyiu
 */
class Mesh(
    val path: String,
    val renderer: Renderer,
) {
    val vertexes: Array<Vertex>
    // indexes[i] 存储一个三角形三个顶点的索引
    val indexes: Array<Array<Int>>

    init {
        val inStream = Thread.currentThread().contextClassLoader.getResourceAsStream(path)!!
        val reader = InputStreamReader(inStream)
        val lines = reader.readLines()
        assert(lines[0] == "3d") { "error 3d format" }
        assert(lines[1] == "1.0") { "error 3d version" }

        vertexes = parseVertexes(lines)
        indexes = parseIndexes(lines)
    }

    private fun parseVertexes(lines: List<String>): Array<Vertex> {
        val numOfVtx = parseCount(lines[2], "vertices")
        val vertexes = arrayOfNulls<Vertex>(numOfVtx)

        for (i in 0 until numOfVtx) {
            val line = lines[i + 4]
            val vtx = line.split(" ")
            val pos = Vector(vtx[0].toDouble(), vtx[1].toDouble(), vtx[2].toDouble())
            // todo normal
            val normal = Vector(0, 0, 0)
            // todo uv
            val u = 0.0
            val v = 0.0
            // todo color
            val c = Color(vtx[3].toDouble(), vtx[4].toDouble(), vtx[5].toDouble(), vtx[6].toDouble() / 255)
            vertexes[i] = Vertex(pos, normal, u, v, c)
        }
        return vertexes.map { it!! }.toTypedArray()
    }

    private fun parseIndexes(lines: List<String>): Array<Array<Int>> {
        val numOfTri = parseCount(lines[3], "triangles")
        val numOfVtx = vertexes.size

        val indexes: Array<Array<Int>> = Array(numOfTri) { arrayOf(0, 0, 0) }
        for (i in 0 until numOfTri) {
            val line = lines[4 + numOfVtx + i]
            val idx = line.split(" ")
            indexes[i] = idx.map { it.toInt() }.toTypedArray()
        }
        return indexes
    }

    private fun parseCount(line: String, target: String): Int {
        val arr = line.split(" ")
        assert(arr.size == 2) { "error 3d $target count" }
        assert(arr[0] == target) { "error 3d $target format" }
        return arr[1].toInt()
    }

    fun draw() {
        for (tri in indexes) {
            val v1 = vertexes[tri[0]]
            val v2 = vertexes[tri[1]]
            val v3 = vertexes[tri[2]]

            renderer.drawTriangle(v1, v2, v3)
        }
    }
}
