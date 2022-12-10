package bekyiu

/**
 * @Date 2022/12/10 6:09 PM
 * @Created by bekyiu
 */
data class Vertex(
    var position: Vector,
    var normal: Vector,
    var u: Double,
    var v: Double,
    var color: Color = Color.white(),
) {

    fun interpolate(target: Vertex, x: Double, dx: Double): Vertex {
        val p = position.interpolate(target.position, x, dx)
        val n = normal.interpolate(target.normal, x, dx)
        val u = interpolate(false, this.u, x, target.u - this.u, dx)
        val v = interpolate(false, this.v, x, target.v - this.v, dx)
        val c = color.interpolate(target.color, x, dx)
        return Vertex(p, n, u, v, c)
    }
}