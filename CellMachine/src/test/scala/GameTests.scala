import org.scalatest.flatspec.AnyFlatSpec

class GameTests extends AnyFlatSpec {
  trait Griddata {
    val g = new Grid[GameCell](8, 8)
  }

  trait GridWithMoveable extends Griddata {
    val p = PushCell(2, 2)
    g.addCell(p)
  }

  trait GridWithMoveables extends GridWithMoveable {
    val pd = PushedCell(2, 3)
    g.addCell(pd)
  }

  trait GridWithMoveablesAndRotate extends GridWithMoveables {
    pd.setCoord(5, 5)
    val r = Rotatecell(2, 4)
    g.addCell(r)
  }
  trait GridWithMoveablesAndGenerate extends GridWithMoveables {
    val gc = Generatorcell(3, 3, Direction.down, g.addCell)
    g.addCell(gc)

  }

  trait GridWithMoveableAndEnemy extends GridWithMoveable {
    val e = Enemy(2, 4)
    g.addCell(e)
  }

  trait GridWithImmobile extends Griddata {
    val i = ImmobileCell(1, 1)
    g.addCell(i)

  }

  trait GridWithSpecialPushCell extends Griddata {
    val p = PushcellDieNa5IteratiesStopt(0, 0)
    g.addCell(p)
  }

  "An empty grid" should "have no cells " in new Griddata {
    assert(g.activeCells.length == 0, s"Number of elements was ${g.activeCells.length == 0}")
  }

  "A grid with 2 cells " should "have 2 cells" in new GridWithMoveables {
    assert(g.activeCells.length == 2, s"Number of elements was ${g.activeCells.length == 0}")
  }

  "A push cell" should "be moved" in new GridWithMoveable {
    for(i <- 0 to 2)
      g.activateAll()
    assert(p.row == 2 && p.col == 5)
  }

  "A Moveable cells in a grid" should "Move and push each other" in new GridWithMoveables {
    g.activateAll()
    assert(g.getCell(2, 2).isEmpty)
    assert(g.getCell(2, 3).head == p)
    assert(g.getCell(2, 4).head == pd)
    assert(p.row == 2 && p.col == 3)
    assert(pd.row == 2 && pd.col == 4)
  }

  "A Rotatecells" should "rotate Rotateable cells" in new GridWithMoveablesAndRotate {
    for(i <- 0 to 2)
      g.activateAll()
    assert(p.rotateDirection == Direction.down)
  }

  "A Generatorcell" should "generate cells" in new GridWithMoveablesAndGenerate {
    g.activateAll()
    assert(!(g.getCell(3, 3).isEmpty))
  }

  "An Enemy" should "die when getting hit by a moveable" in new GridWithMoveableAndEnemy {
    for(i <- 0 to 2)
      g.activateAll()
    assert(Enemy.nrOfEnemies == 0)
    assert(g.activeCells.isEmpty)
  }
  "An ImmobileCell" should "not be able to move" in new GridWithImmobile {
    g.activateAll()
    assert(g.getCell(1, 1).head == i)
  }

  "An PushcellDieNa5IteratiesStops" should "stop after 5 iterations"  in new GridWithSpecialPushCell {
    for(i <- 0 to 7) {
      g.activateAll()
    }
    assert(g.getCell(0, 0).isEmpty)
    assert(g.getCell(0, 5).head == p)
  }

}

