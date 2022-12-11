package bekyiu

import java.io.InputStreamReader
import kotlin.math.min

/**
 * @Date 2022/12/11 11:29 AM
 * @Created by bekyiu
 */
class Image(
    val path: String,
    val renderer: Renderer,
) {
    val w: Int
    val h: Int

    // pixels[y][x] 表示 坐标为(x, y)的像素点   y行x列
    val pixels: Array<Array<Color>>

    init {
        val inStream = Thread.currentThread().contextClassLoader.getResourceAsStream(path)!!
        val reader = InputStreamReader(inStream)
        val lines = reader.readLines()
        assert(lines[0] == "image") { "error image format" }
        assert(lines[1] == "1.0") { "error image version" }
        w = lines[2].toInt()
        h = lines[3].toInt()
        pixels = parsePixels(lines)
    }

    private fun parsePixels(lines: List<String>): Array<Array<Color>> {
        val pixels: Array<Array<Color?>> = Array(h) { arrayOfNulls(w) }
        for (i in 0 until h) {
            val line = lines[i + 4]
            val cols = line.split(" ")
            for (j in 0 until w) {
                val c = parsePixel(cols[j])
                pixels[i][j] = c
            }
        }

        return pixels.map { row ->
            row.map { col -> col!! }.toTypedArray()
        }.toTypedArray()
    }

    // pixel 是一个 4 字节 的数
    // 四个字节依次代表 r g b a
    private fun parsePixel(pixel: String): Color {
        val p = pixel.toUInt()
        val a = p and 0x0000_00FF_U
        val b = (p shr 8) and 0x0000_00FF_U
        val g = (p shr 16) and 0x0000_00FF_U
        val r = (p shr 24) and 0x0000_00FF_U
        return Color(r.toDouble(), g.toDouble(), b.toDouble(), a.toInt() / 255.0)
    }

    fun getColorByUV(u: Double, v: Double): Color {
        var x = (u * w).toInt()
        var y = (v * h).toInt()

        x = min(x, w - 1)
        y = min(y, h - 1)

        return pixels[y][x]
    }

    fun getNormalByUV(u: Double, v: Double): Vector {
        val c = getColorByUV(u, v)
        val x = (c.r - 127) / 128
        val y = (c.g - 127) / 128
        val z = (c.b - 127) / 128
        return Vector(x, y, z)
    }

    fun draw() {
        for (y in 0 until h) {
            for (x in 0 until w) {
                val c = pixels[y][x]
                renderer.drawPixel(x, y, c)
            }
        }
    }
}
