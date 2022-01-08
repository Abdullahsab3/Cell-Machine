/* cellen die niet geducpliceerd kunnen worden */
trait Uncopyable extends GameCell {
  
  def copy(newRow : Int = row, newCol: Int = col) = {this}
}
