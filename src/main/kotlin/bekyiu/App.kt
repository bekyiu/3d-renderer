package bekyiu

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.stage.Stage

/**
 * @Date 2022/12/10 4:00 PM
 * @Created by bekyiu
 */
class App : Application() {

    override fun start(stage: Stage) {
        val javaVersion = System.getProperty("java.version")
        val javafxVersion = System.getProperty("javafx.version")
        val l = Label("Hello, JavaFX $javafxVersion, running on Java $javaVersion.")
        val scene = Scene(StackPane(l), 640.0, 480.0)
        stage.scene = scene
        stage.show()
    }

}

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}