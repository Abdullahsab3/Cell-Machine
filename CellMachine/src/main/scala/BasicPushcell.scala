// het basiskenmerk voor een cel die zichzelf duwt
trait BasicPushcell extends Moveable {

  // moving blijft op true staan, en op die manier blijft de cel "zichzelf" pushen
  // maw: de cel beweegt automatisch
  // de beweegrichting van zo een cel is de rotatiericthing van die cel
  override def move(getCell: (row: Int, col: Int) => List[GameCell]): Boolean = {
    moving = true
    direction = rotateDirection
    val moved = super.move(getCell)
    moving = true
    moved
  }

  // de actie die moet gebeuren wanneer een andere cel tegen een basicpushcell botst

  override def hitByACell(hitBy: GameCell): Unit = {
    if(this.direction != hitBy.direction)
      this.direction = hitBy.direction
  }
}
