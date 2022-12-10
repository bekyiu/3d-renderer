package bekyiu

import javafx.scene.canvas.Canvas
import kotlin.math.floor

/**
 * @Date 2022/12/10 5:06 PM
 * @Created by bekyiu
 */
class Renderer(
    private val canvas: Canvas
) {
    private val context = canvas.graphicsContext2D

    fun render() {
        val v = Vertex(Vector(10, 10, 10), Vector.zero(), 0.0, 0.0)
        drawVertex(v)
    }

    private fun drawVertex(v: Vertex) {
        val p = v.position
        val x = p.x.toInt()
        val y = p.y.toInt()

        context.fill = v.color.toJavaFXColor()
        context.fillRect(m(x), m(y), m(1), m(1))
    }
}

/*
x 每次步进1, 对应的y 和 z
var y = y1 + (x - x1) * k
var z = z1 + (x - x1) * ((z2 - z1) / (x2 - x1))

所以
b: 被插值元素的起始值
x: 步进元素已经步进了多长了
dy: 被插值元素终点减去起点
dx: 步进元素终点减去起点
k: dy / dx, 斜率
*/
fun interpolate(round: Boolean, b: Double, x: Double, dy: Double, dx: Double, k: Double? = null): Double {
    var k = k
    if (k == null) {
        k = dy / dx
    }
    var ret = b + k * x
    if (round) {
        ret = floor(ret)
    }
    return ret
}