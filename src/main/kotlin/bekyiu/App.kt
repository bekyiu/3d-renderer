package bekyiu

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

/**
 * @Date 2022/12/10 4:00 PM
 * @Created by bekyiu
 */

fun m(num: Int) = num * Config.pixelSize.toDouble()

object Config {
    val w = m(500)
    val h = m(500)
    const val pixelSize = 1
}


class App : Application() {
    override fun start(stage: Stage) {
        val canvas = Canvas(Config.w, Config.h)
        val pane = AnchorPane(canvas)
        val scene = Scene(pane)

        stage.scene = scene
        stage.title = "Renderer"

        val r = Renderer(canvas)
        r.clearCanvas()
        r.render()

        stage.show()
    }

}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}