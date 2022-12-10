package bekyiu


/**
 * @Date 2022/12/10 5:24 PM
 * @Created by bekyiu
 */

class Color(
    var r: Double,
    var g: Double,
    var b: Double,
    var a: Double = 1.0,
) {
    constructor(r: Int, g: Int, b: Int) : this(r.toDouble(), g.toDouble(), b.toDouble())

    companion object {
        fun white() = Color(255, 255, 255)
    }

    fun addLightIntensity(li: Double) {
        this.r *= li
        this.g *= li
        this.b *= li
    }

    fun interpolate(target: Color, x: Double, dx: Double): Color {
        val r = interpolate(false, this.r, x, target.r - this.r, dx)
        val g = interpolate(false, this.g, x, target.g - this.g, dx)
        val b = interpolate(false, this.b, x, target.b - this.b, dx)
        return Color(r, g, b)
    }

    fun toJavaFXColor() = javafx.scene.paint.Color.rgb(r.toInt(), g.toInt(), b.toInt(), a)
}

