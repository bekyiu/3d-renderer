package bekyiu

import java.util.Arrays
import kotlin.math.cos
import kotlin.math.sin

/**
 * @Date 2022/12/22 4:20 PM
 * @Created by bekyiu
 */
class Matrix(
    var arr: Array<Array<Double>>
) {
    companion object {
        fun zero(): Matrix {
            val m = arrayOf(
                arrayOf(0.0, 0.0, 0.0, 0.0),
                arrayOf(0.0, 0.0, 0.0, 0.0),
                arrayOf(0.0, 0.0, 0.0, 0.0),
                arrayOf(0.0, 0.0, 0.0, 0.0),
            )
            return Matrix(m)
        }

        fun identity(): Matrix {
            val m = arrayOf(
                arrayOf(1.0, 0.0, 0.0, 0.0),
                arrayOf(0.0, 1.0, 0.0, 0.0),
                arrayOf(0.0, 0.0, 1.0, 0.0),
                arrayOf(0.0, 0.0, 0.0, 1.0),
            )
            return Matrix(m)
        }
    }

    fun mul(other: Matrix): Matrix {
        val a = this.arr
        val b = other.arr
        val c = zero().arr

        for (i in c.indices) {
            for (j in c[0].indices) {
                val target = a[i][0] * b[0][j] + a[i][1] * b[1][j] + a[i][2] * b[2][j] + a[i][3] * b[3][j]
                c[i][j] = target
            }
        }
        return Matrix(c)
    }

    fun rotationX(angle: Double): Matrix {
        val c = cos(angle)
        val s = sin(angle)
        val m = arrayOf(
            arrayOf(1.0, 0.0, 0.0, 0.0),
            arrayOf(0.0, c, -s, 0.0),
            arrayOf(0.0, s, c, 0.0),
            arrayOf(0.0, 0.0, 0.0, 1.0),
        )
        return Matrix(m)
    }

    fun rotationY(angle: Double): Matrix {
        val c = cos(angle)
        val s = sin(angle)
        val m = arrayOf(
            arrayOf(c, 0.0, s, 0.0),
            arrayOf(0.0, 1.0, 0.0, 0.0),
            arrayOf(-s, 0.0, c, 0.0),
            arrayOf(0.0, 0.0, 0.0, 1.0),
        )
        return Matrix(m)
    }

    fun rotationZ(angle: Double): Matrix {
        val c = cos(angle)
        val s = sin(angle)
        val m = arrayOf(
            arrayOf(c, -s, 0.0, 0.0),
            arrayOf(s, c, 0.0, 0.0),
            arrayOf(0.0, 0.0, 1.0, 0.0),
            arrayOf(0.0, 0.0, 0.0, 1.0),
        )
        return Matrix(m)
    }

    fun rotation(vec: Vector): Matrix {
        val x = rotationX(vec.x)
        val y = rotationX(vec.y)
        val z = rotationX(vec.z)
        return x * y * z
    }


    fun view(position: Vector, target: Vector, up: Vector): Matrix {
        // 把相机放到原点
        val translation = Matrix(
            arrayOf(
                arrayOf(1.0, 0.0, 0.0, -position.x),
                arrayOf(0.0, 1.0, 0.0, -position.y),
                arrayOf(0.0, 0.0, 1.0, -position.z),
                arrayOf(0.0, 0.0, 0.0, 1.0),
            )
        )
        // up 旋转到 y
        // target 旋转到-z
        // target cross up 旋转到x
        val cross = target.cross(up)
        val rotation = Matrix(
            arrayOf(
                arrayOf(cross.x, cross.y, cross.z, 0.0),
                arrayOf(up.x, up.y, up.z, 0.0),
                arrayOf(-target.x, -target.y, -target.z, 0.0),
                arrayOf(0.0, 0.0, 0.0, 1.0),
            )
        )

        return rotation * translation
    }

    // fov
    fun perspectiveProjection() {

    }


    operator fun times(other: Matrix) = mul(other)

    override fun toString(): String {
        val sb = StringBuilder()
        for (line in arr) {
            sb.append(line.contentToString())
                .append("\n")
        }
        return sb.toString()
    }
}

fun main() {
    val a1 = arrayOf(
        arrayOf(2.0, 3.0, 4.0, 1.0),
        arrayOf(6.0, -58.0, 2.0, 3.0),
        arrayOf(3.0, 3.0, 5.0, 76.0),
        arrayOf(4.0, 4.0, 2.0, 2.0),
    )
    val m1 = Matrix(a1)

    val a2 = arrayOf(
        arrayOf(3.0, 4.0, 5.0, 6.0),
        arrayOf(3.0, 4.0, 32.0, 43.0),
        arrayOf(32.0, 3.0, 2.0, 1.0),
        arrayOf(-2.3, 2.7, 5.5, -123.0),
    )
    val m2 = Matrix(a2)

    val m3 = m1 * m2
    println(m3)
}