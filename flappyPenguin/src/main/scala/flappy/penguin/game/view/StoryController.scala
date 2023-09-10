package flappy.penguin.game.view

import flappy.penguin.game.MainApp
import scalafx.scene.control.Button
import scalafxml.core.macros.sfxml

@sfxml
class StoryController(private val prev: Button,
                      private val next: Button){

  def backHome(): Unit = {
    MainApp.showWelcome()
  }

  def showStory2(): Unit = {
    MainApp.showStory2()
  }

}
