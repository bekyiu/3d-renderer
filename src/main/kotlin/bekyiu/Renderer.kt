package bekyiu

import javafx.scene.canvas.Canvas
import kotlin.math.abs
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
        val v1 = Vertex(Vector(200, 50, 10), Vector.zero(), 0.0, 0.0, Color.red())
        val v2 = Vertex(Vector(50, 200, 10), Vector.zero(), 0.0, 0.0, Color.blue())
        val v3 = Vertex(Vector(400, 400, 10), Vector.zero(), 0.0, 0.0, Color.green())

        drawTriangle(v1, v2, v3)
    }

    private fun drawVertex(v: Vertex) {
        val p = v.position
        val x = p.x.toInt()
        val y = p.y.toInt()

        context.fill = v.color.toJavaFXColor()
        context.fillRect(m(x), m(y), m(1), m(1))
    }

    private fun drawLine(v1: Vertex, v2: Vertex) {
        val x1 = v1.position.x.toInt()
        val y1 = v1.position.y.toInt()

        val x2 = v2.position.x.toInt()
        val y2 = v2.position.y.toInt()

        val dx = x2 - x1
        val dy = y2 - y1

        if (dx == 0 && dy == 0) {
            drawVertex(v1)
            return
        }

        if (abs(dx) > abs(dy)) {
            // 从 x1 步进 到x2
            val steps = if (dx > 0) x1..x2 else x1 downTo x2
            for (x in steps) {
                val p = v1.interpolate(v2, (x - x1).toDouble(), dx.toDouble())
                drawVertex(p)
            }
        } else {
            // 从 y1 步进到 y2
            val steps = if (dy > 0) y1..y2 else y1 downTo y2
            for (y in steps) {
                val p = v1.interpolate(v2, (y - y1).toDouble(), dy.toDouble())
                drawVertex(p)
            }
        }
    }

    /*
        up:                         down:
                a                       a       b
            b       c                       c
    */
    private fun drawTriangle(v1: Vertex, v2: Vertex, v3: Vertex, forward: Forward) {
        // y值从小到大排序
        val vArr = arrayOf(v1, v2, v3).sortedBy { it.position.y }

        val y1 = vArr[0].position.y.toInt()
        val y3 = vArr[2].position.y.toInt()

        val a = vArr[0]
        val b = vArr[1]
        val c = vArr[2]


        for (y in y1 .. y3) {
            val stepped = (y - y1).toDouble()
            val total = (y3 - y1).toDouble()

            val (left, right) = if (forward == Forward.UP) {
                Pair(a.interpolate(b, stepped, total), a.interpolate(c, stepped, total))
            } else {
                Pair(a.interpolate(c, stepped, total), b.interpolate(c, stepped, total))
            }
            drawLine(left, right)
        }
    }

    private fun drawTriangle(v1: Vertex, v2: Vertex, v3: Vertex) {
        // y值从小到大排序
        val vArr = arrayOf(v1, v2, v3).sortedBy { it.position.y }

        val a = vArr[0]
        val b = vArr[1]
        val c = vArr[2]

        val pa = a.position
        val pb = b.position
        val pc = c.position

        // 倒三角
        if (pa.y.toInt() == pb.y.toInt()) {
            drawTriangle(a, b, c, Forward.DOWN)
            return
        }
        // 正的三角
        if (pb.y.toInt() == pc.y.toInt()) {
            drawTriangle(a, b, c, Forward.UP)
            return
        }

        // 对 a c 取一个点 M
        // 让 M.y == b.y
        // 三角形就被分成了2个: 正面三角形和倒立三角形
        // 计算M点
        val my = pb.y
        val m = a.interpolate(c, my - pa.y, pc.y - pa.y)

        drawTriangle(a, b, m, Forward.UP)
        drawTriangle(b, m, c, Forward.DOWN)
    }
}

enum class Forward {
    UP, DOWN
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