//package bekyiu
//
//import kotlin.math.*
//
///**
// * @Date 2022/12/22 4:20 PM
// * @Created by bekyiu
// *
// * for test
// */
//class _Matrix(
//    var arr: Array<Array<Double>>
//) {
//    companion object {
//        fun zero(): _Matrix {
//            val m = arrayOf(
//                arrayOf(0.0, 0.0, 0.0, 0.0),
//                arrayOf(0.0, 0.0, 0.0, 0.0),
//                arrayOf(0.0, 0.0, 0.0, 0.0),
//                arrayOf(0.0, 0.0, 0.0, 0.0),
//            )
//            return _Matrix(m)
//        }
//
//        fun identity(): _Matrix {
//            val m = arrayOf(
//                arrayOf(1.0, 0.0, 0.0, 0.0),
//                arrayOf(0.0, 1.0, 0.0, 0.0),
//                arrayOf(0.0, 0.0, 1.0, 0.0),
//                arrayOf(0.0, 0.0, 0.0, 1.0),
//            )
//            return _Matrix(m)
//        }
//
//        fun rotationX(angle: Double): _Matrix {
//            val c = cos(angle)
//            val s = sin(angle)
//            val m = arrayOf(
//                arrayOf(1.0, 0.0, 0.0, 0.0),
//                arrayOf(0.0, c, -s, 0.0),
//                arrayOf(0.0, s, c, 0.0),
//                arrayOf(0.0, 0.0, 0.0, 1.0),
//            )
//            return _Matrix(m)
//        }
//
//        fun rotationY(angle: Double): _Matrix {
//            val c = cos(angle)
//            val s = sin(angle)
//            val m = arrayOf(
//                arrayOf(c, 0.0, s, 0.0),
//                arrayOf(0.0, 1.0, 0.0, 0.0),
//                arrayOf(-s, 0.0, c, 0.0),
//                arrayOf(0.0, 0.0, 0.0, 1.0),
//            )
//            return _Matrix(m)
//        }
//
//        fun rotationZ(angle: Double): _Matrix {
//            val c = cos(angle)
//            val s = sin(angle)
//            val m = arrayOf(
//                arrayOf(c, -s, 0.0, 0.0),
//                arrayOf(s, c, 0.0, 0.0),
//                arrayOf(0.0, 0.0, 1.0, 0.0),
//                arrayOf(0.0, 0.0, 0.0, 1.0),
//            )
//            return _Matrix(m)
//        }
//
//        fun rotation(vec: Vector): _Matrix {
//            val x = rotationX(vec.x)
//            val y = rotationX(vec.y)
//            val z = rotationX(vec.z)
//            return x * y * z
//        }
//
//
//        fun view(position: Vector, target: Vector, up: Vector): _Matrix {
//            // 把相机放到原点
//            val translation = _Matrix(
//                arrayOf(
//                    arrayOf(1.0, 0.0, 0.0, -position.x),
//                    arrayOf(0.0, 1.0, 0.0, -position.y),
//                    arrayOf(0.0, 0.0, 1.0, -position.z),
//                    arrayOf(0.0, 0.0, 0.0, 1.0),
//                )
//            )
//            // up 旋转到 y
//            // target 旋转到-z
//            // target cross up 旋转到x
//            val cross = target.cross(up)
//            val rotation = _Matrix(
//                arrayOf(
//                    arrayOf(cross.x, cross.y, cross.z, 0.0),
//                    arrayOf(up.x, up.y, up.z, 0.0),
//                    arrayOf(-target.x, -target.y, -target.z, 0.0),
//                    arrayOf(0.0, 0.0, 0.0, 1.0),
//                )
//            )
//
//            return rotation * translation
//        }
//
//        // aspect = w / h
//        fun perspectiveProjection(fov: Double, aspect: Double, znear: Double, zfar: Double): _Matrix {
//            val n = -znear
//            val f = -zfar
//            val t = tan(fov / 2) * abs(n)
//            val r = t * aspect
//            val b = -t
//            val l = -r
//
//            return _Matrix(
//                arrayOf(
//                    arrayOf((2 * n) / (r - l), 0.0, (l + r) / (l - r), 0.0),
//                    arrayOf(0.0, (2 * n) / (t - b), (b + t) / (b - t), 0.0),
//                    arrayOf(0.0, 0.0, (f + n) / (n - f), (2 * f * n) / (f - n)),
//                    arrayOf(0.0, 0.0, 1.0, 0.0),
//                )
//            )
//        }
//
//        fun viewport(): _Matrix {
//            return _Matrix(
//                arrayOf(
//                    arrayOf(Config.w / 2, 0.0, 0.0, Config.w / 2),
//                    arrayOf(0.0, Config.h / 2, 0.0, Config.h / 2),
//                    arrayOf(0.0, 0.0, 1.0, 0.0),
//                    arrayOf(0.0, 0.0, 0.0, 1.0),
//                )
//            )
//        }
//    }
//
//    fun mul(other: _Matrix): _Matrix {
//        val a = this.arr
//        val b = other.arr
//        val c = zero().arr
//
//        for (i in c.indices) {
//            for (j in c[0].indices) {
//                val target = a[i][0] * b[0][j] + a[i][1] * b[1][j] + a[i][2] * b[2][j] + a[i][3] * b[3][j]
//                c[i][j] = target
//            }
//        }
//        return _Matrix(c)
//    }
//
//    fun transform(vec: Vector): Vector {
//        val x = vec.x * arr[0][0] + vec.y * arr[0][1] + vec.z * arr[0][2] + arr[0][3]
//        val y = vec.x * arr[1][0] + vec.y * arr[1][1] + vec.z * arr[1][2] + arr[1][3]
//        val z = vec.x * arr[2][0] + vec.y * arr[2][1] + vec.z * arr[2][2] + arr[2][3]
//        val w = vec.x * arr[3][0] + vec.y * arr[3][1] + vec.z * arr[3][2] + arr[3][3]
//        // / w
//        return Vector(x, y, z)
//    }
//
//    operator fun times(other: _Matrix) = mul(other)
//
//    override fun toString(): String {
//        val sb = StringBuilder()
//        for (line in arr) {
//            sb.append(line.contentToString())
//                .append("\n")
//        }
//        return sb.toString()
//    }
//}
//
