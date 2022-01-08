case class Enemy(initrow: Int, initcol: Int) extends GameCell(initrow, initcol), Unmoveable, Unrotateable {
  Enemy.nrOfEnemies += 1
  
  override def remove(): Boolean = {
    Enemy.nrOfEnemies -= 1
    super.remove()
  }

  override def hitByACell(hitBy: GameCell): Unit = {
    if(!this.removed)
      this.remove()
      hitBy.remove()
  }
  
}

// het aantal aanwezige enemies in het spel
object Enemy {
  var nrOfEnemies = 0
}