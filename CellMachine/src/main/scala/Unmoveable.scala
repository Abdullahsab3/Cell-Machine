trait Unmoveable extends GameCell {
  def move(getCell: (row: Int, col: Int) => List[GameCell]): Boolean = {false}
  override def collisionAction(hitCel: GameCell): Boolean = {false}
  override def hitByACell(hitBy: GameCell): Unit = {}
}
