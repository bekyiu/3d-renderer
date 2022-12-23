package bekyiu

import kotlin.math.*

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

        fun rotationX(angle: Double): Matrix {
            val c = cos(angle)
            val s = sin(angle)
            val m = arrayOf(
                arrayOf(1.0, 0.0, 0.0, 0.0),
                arrayOf(0.0, c, s, 0.0),
                arrayOf(0.0, -s, c, 0.0),
                arrayOf(0.0, 0.0, 0.0, 1.0),
            )
            return Matrix(m)
        }

        fun rotationY(angle: Double): Matrix {
            val c = cos(angle)
            val s = sin(angle)
            val m = arrayOf(
                arrayOf(c, 0.0, -s, 0.0),
                arrayOf(0.0, 1.0, 0.0, 0.0),
                arrayOf(s, 0.0, c, 0.0),
                arrayOf(0.0, 0.0, 0.0, 1.0),
            )
            return Matrix(m)
        }

        fun rotationZ(angle: Double): Matrix {
            val c = cos(angle)
            val s = sin(angle)
            val m = arrayOf(
                arrayOf(c, s, 0.0, 0.0),
                arrayOf(-s, c, 0.0, 0.0),
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

        fun translation(vec: Vector): Matrix {
            val m = arrayOf(
                arrayOf(1.0, 0.0, 0.0, 0.0),
                arrayOf(0.0, 1.0, 0.0, 0.0),
                arrayOf(0.0, 0.0, 1.0, 0.0),
                arrayOf(vec.x, vec.y, vec.z, 1.0),
            )
            return Matrix(m)
        }

        fun scale(vec: Vector): Matrix {
            val m = arrayOf(
                arrayOf(vec.x, 0.0, 0.0, 0.0),
                arrayOf(0.0, vec.y, 0.0, 0.0),
                arrayOf(0.0, 0.0, vec.z, 0.0),
                arrayOf(0.0, 0.0, 0.0, 1.0),
            )
            return Matrix(m)
        }

        fun view(eye: Vector, target: Vector, up: Vector): Matrix {
            val zaxis = (target - eye).normalize()
            val xaxis = up.cross(zaxis).normalize()
            val yaxis = zaxis.cross(xaxis).normalize()

            val ex = -xaxis.dot(eye)
            val ey = -yaxis.dot(eye)
            val ez = -zaxis.dot(eye)

            val m = arrayOf(
                arrayOf(xaxis.x, yaxis.x, zaxis.x, 0.0),
                arrayOf(xaxis.y, yaxis.y, zaxis.y, 0.0),
                arrayOf(xaxis.z, yaxis.z, zaxis.z, 0.0),
                arrayOf(ex, ey, ez, 1.0),
            )
            return Matrix(m)
        }

        // aspect = w / h
        fun perspectiveProjection(fov: Double, aspect: Double, znear: Double, zfar: Double): Matrix {
            val h = 1 / tan(fov / 2)
            val w = h / aspect

            return Matrix(
                arrayOf(
                    arrayOf(w, 0.0, 0.0, 0.0),
                    arrayOf(0.0, h, 0.0, 0.0),
                    arrayOf(0.0, 0.0, zfar / (zfar - znear), 1.0),
                    arrayOf(0.0, 0.0, (znear * zfar) / (znear - zfar), 0.0),
                )
            )
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

    fun transform(vec: Vector): Vector {
        val x = vec.x * arr[0][0] + vec.y * arr[1][0] + vec.z * arr[2][0] + arr[3][0]
        val y = vec.x * arr[0][1] + vec.y * arr[1][1] + vec.z * arr[2][1] + arr[3][1]
        val z = vec.x * arr[0][2] + vec.y * arr[1][2] + vec.z * arr[2][2] + arr[3][2]
        val w = vec.x * arr[0][3] + vec.y * arr[1][3] + vec.z * arr[2][3] + arr[3][3]
        return Vector(x / w, y / w, z / w)
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

