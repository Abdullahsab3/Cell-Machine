case class Slidecell(initrow: Int, initcol: Int) extends GameCell(initrow, initcol), MoveableInOneDirection(Direction.right), Rotateable