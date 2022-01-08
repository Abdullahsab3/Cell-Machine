trait Moveable extends GameCell {

  def move(getCell: (row: Int, col: Int) => List[GameCell]): Boolean = {
    // als moving true is, mag de cel bewegen
    // moving kan true zijn wanneer een cel bvb geduwd wordt, of wanneer een cel vanzelf beweegt
    if (moving)
      moving = false
      // kijken of de cel waarin deze cel gaat terechtkomen leeg is
      val neighbor = getCell(row + direction.nextRow, col + direction.nextCol)
      // als die leeg is, mag je bewegen
      if (neighbor.isEmpty) then
        row += direction.nextRow
        col += direction.nextCol
        true
      // als het niet leeg is, dan zit er iets in die cel
      // maw: er is een collision gebeurd
      else
      // als collisionaction true teruggeeft, dan kan je die laten moven
        if (collisionAction(neighbor.head))
          if (neighbor.head.move(getCell)) // als move van de andere cel true teruggeeft, dan heeft die bewogen
          // en kan deze cel dus nu ook bewegen
            this.move(getCell)
          else
            false
        else
          false
    else
      false
  }

  // de actie die moet gebeuren wanneer een collision is gebeurd
  override def collisionAction(hitCel: GameCell): Boolean =
    hitCel.hitByACell(this)
    hitCel.moving


  override def hitByACell(hitBy: GameCell): Unit = {
    this.moving = true
    this.direction = hitBy.direction
  }
}