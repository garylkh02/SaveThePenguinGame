package flappy.penguin.game

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.application.JFXApp.PrimaryStage
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}

object MainApp extends JFXApp {

  def showWelcome(): Unit = {
    val rootResource = getClass.getResource("view/Welcome.fxml")
    val loader = new FXMLLoader(rootResource, NoDependencyResolver)
    val roots: jfxs.layout.AnchorPane = loader.load()
    stage = new PrimaryStage {
      title = "FlappyPenguin"
      scene = new Scene {
        root = roots
      }
    }
  }

  def chooseDifficulty(): Unit = {
    val resource = getClass.getResource("view/Difficulty.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    val roots: jfxs.layout.AnchorPane = loader.load()
    stage.scene = new Scene {
      root = roots
    }
  }

  def showStory(): Unit = {
    val resource = getClass.getResource("view/Story.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    val roots: jfxs.layout.AnchorPane = loader.load()
    stage.scene = new Scene {
      root = roots
    }
  }

  def showStory2(): Unit = {
    val resource = getClass.getResource("view/Story2.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    val roots: jfxs.layout.AnchorPane = loader.load()
    stage.scene = new Scene {
      root = roots
    }
  }

  def showInstuction(): Unit = {
    val resource = getClass.getResource("view/Instruction.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    val roots: jfxs.layout.AnchorPane = loader.load()
    stage.scene = new Scene {
      root = roots
    }
  }

  showWelcome()
}
