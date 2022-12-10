package bekyiu

import kotlin.math.sqrt

/**
 * @Date 2022/12/10 5:49 PM
 * @Created by bekyiu
 */
data class Vector(
    var x: Double,
    var y: Double,
    var z: Double,
) {
    constructor(x: Int, y: Int, z: Int) : this(x.toDouble(), y.toDouble(), z.toDouble())

    companion object {
        fun zero() = Vector(0, 0, 0)
    }

    fun add(v: Vector): Vector {
        val x = this.x + v.x
        val y = this.y + v.y
        val z = this.z + v.z
        return Vector(x, y, z)
    }

    fun sub(v: Vector): Vector {
        val x = this.x - v.x
        val y = this.y - v.y
        val z = this.z - v.z
        return Vector(x, y, z)
    }

    fun mul(n: Double): Vector {
        val x = this.x * n
        val y = this.y * n
        val z = this.z * n
        return Vector(x, y, z)
    }

    fun len() = sqrt(x * x + y * y + z * z)

    fun normalize(): Vector {
        val l = len()
        if (l == 0.0) {
            return this
        }
        val factor = 1 / l

        return this * factor
    }

    fun dot(v: Vector) = x * v.x + y * v.y + z * v.z


    fun cross(v: Vector): Vector {
        val x = this.y * v.z - this.z * v.y
        val y = this.z * v.x - this.x * v.z
        val z = this.x * v.y - this.y * v.x
        return Vector(x, y, z)
    }

    fun interpolate(target: Vector, x: Double, dx: Double): Vector {
        val ix = interpolate(true, this.x, x, target.x - this.x, dx)
        val iy = interpolate(true, this.y, x, target.y - this.y, dx)
        val iz = interpolate(false, this.z, x, target.z - this.z, dx)
        return Vector(ix, iy, iz)
    }

    operator fun plus(v: Vector) = add(v)

    operator fun minus(v: Vector) = sub(v)

    operator fun times(n: Double) = mul(n)
}

