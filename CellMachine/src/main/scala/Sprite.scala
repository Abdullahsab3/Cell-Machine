import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.Color
import java.awt.Image


class Sprite[T <: GameCell](cell: T) extends Cell {
  def isRemoved = cell.removed

  def getRotationstring(cell: T): String = {
    cell.rotateDirection match {
      case Direction.left => "left"
      case Direction.right => ""
      case Direction.up => "up"
      case Direction.down=> "down"
      case Direction.unspecified => ""
    }
  }

  def imagePath: String = {
    val prefix =
      cell match {
      case _: PushCell => "mover"
      case _: Enemy => "enemy"
      case _: PushedCell => "push"
      case _: RunCell => "play"
      case _: ImmobileCell => "immobile"
      case _: Rotatecell => "spinner"
      case _: Generatorcell => "generator"
      case _: RestartCell => "redo"
      case _: Slidecell => "slide"
      case _:PushcellDieNa5IteratiesStopt => "mover5"
      case _:UnrotateablyMovingCell => "unrotating"
    }
    prefix + getRotationstring(cell) + ".png"
  }

  override def draw(g: Graphics2D): Unit =
    if (!isRemoved) then
      val image = AssetsLoader.loadImage(imagePath)
      g.drawImage(image, (cell.col)*cellwidth + padding, (cell.row)*cellheight + padding, cellwidth, cellheight, Color.WHITE, null)

}
