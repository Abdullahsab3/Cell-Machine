trait MoveableInOneDirection(dir: Direction) extends Moveable {
  direction = dir

  override def move(getCell: (row: Int, col: Int) => List[GameCell]): Boolean =
    direction = rotateDirection
    super.move(getCell)

  override def hitByACell(hitBy: GameCell): Unit = {
    if(this.direction == hitBy.direction) then
      this.moving = true
  }
}


