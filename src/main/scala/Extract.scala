import scala.io.Source
import scala.util.{Try, Success, Failure}

object Extract {

  // อ่านไฟล์ CSV แล้วคืนค่าเป็น List
  def readCSV(path: String): List[String] = {

    // ใช้ Try เพื่อป้องกัน error กรณีไฟล์ไม่พบ
    Try {

      val file = Source.fromFile(path)

      // อ่านทุกบรรทัดของไฟล์
      val lines = file.getLines().toList

      file.close()

      // ตัดหัวข้อออก
      lines.tail

    } match {

      case Success(data) => data

      case Failure(e) =>
        println("Error reading file: " + e.getMessage)
        List.empty

    }

  }

}