import scala.io.Source

object Extract {

  // อ่าน CSV แล้วคืนค่าเป็น List
  def readCSV(path: String): List[String] = {

    val file = Source.fromFile(path)

    val lines = file.getLines().toList

    file.close()

    lines.tail
  }

}