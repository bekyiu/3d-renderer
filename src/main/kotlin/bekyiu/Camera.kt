package bekyiu

/**
 * @Date 2022/12/22 9:25 PM
 * @Created by bekyiu
 */
class Camera(
    // 摄像机的位置
    val position: Vector = Vector(0, 0, 15),
    // 摄像机看的地方
    val target: Vector = Vector(0, 0, 0),
    // 摄像机的向上方向
    val up: Vector = Vector(0, 1, 0)
) {
}