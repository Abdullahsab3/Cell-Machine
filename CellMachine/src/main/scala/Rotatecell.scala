case class Rotatecell(initrow: Int, initcol: Int) extends GameCell(initrow, initcol), Moveable, Rotateable {
  
  
  override def cellAction(getCells : (List[(Int, Int)]) => List[GameCell]): Unit = {
    var neighborscoords = List[(Int, Int)]((row - 1, col), (row + 1, col), (row, col - 1), (row, col + 1))
    val neighbors = getCells(neighborscoords)

    neighbors.foreach((cell: GameCell) =>
      cell.rotate())
  }
  
}
