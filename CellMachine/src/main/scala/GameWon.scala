/* Tekst printen op het scherm op het einde van het spel*/
import java.awt.{Font, Graphics2D}

class GameWon extends Cell{
  val fontsize = 50
  val f: Font = new Font("Comic Sans MS", Font.BOLD, fontsize)
  override def draw(g: Graphics2D): Unit = {
    g.setFont(f)
    val textx = 100
    val texty = scrwidth / 2
    g.drawString("YOU WON!", textx, texty)
  }
}
