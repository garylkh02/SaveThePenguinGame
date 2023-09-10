package flappy.penguin.game.view

import flappy.penguin.game.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafxml.core.macros.sfxml


@sfxml
class WelcomeController(private val quit: Button,
                        private val story: Button,
                        private val instruction: Button){


  def quitGame(): Unit = {
    MainApp.stage.close()
  }

  def showSto(event: ActionEvent): Unit = {
    MainApp.showStory()
  }

  def showInstructions(): Unit = {
    MainApp.showInstuction()
  }


}
