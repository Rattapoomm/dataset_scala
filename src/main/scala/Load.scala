import java.io.PrintWriter
import scala.util.Try

object Load {

  // ลบข้อมูลซ้ำ
  def removeDuplicate(data: List[Array[String]]): List[Array[String]] = {
    data.distinct
  }

  // ฟังก์ชันเขียนข้อมูล JSON ลงไฟล์
  def writeJsonFile(path: String, jsonData: Iterable[String]): Unit = {

    // ใช้ Try กัน error ตอนเขียนไฟล์
    Try {

      val writer = new PrintWriter(path)

      writer.println("[")

      val jsonList = jsonData.toList

      // เขียน JSON ทีละ object
      for (i <- jsonList.indices) {

        if (i == jsonList.length - 1)
          writer.println(jsonList(i))
        else
          writer.println(jsonList(i) + ",")

      }

      writer.println("]")

      writer.close()

    }.recover {

      case e => println("Error writing JSON: " + e.getMessage)

    }

  }

}