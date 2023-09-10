package flappy.penguin.game.model

class GameObject(var x: Int, var y: Int) {
  def draw(gc: scalafx.scene.canvas.GraphicsContext, blockSize: Double): Unit = {
    gc.fillRect(x * blockSize, y * blockSize, blockSize, blockSize)
  }
}


