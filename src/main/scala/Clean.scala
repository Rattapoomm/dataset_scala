object Clean {

  // ลบ space หน้า-หลัง
  def cleanText(text: String): String = {
    text.trim
  }

  // แปลง line เป็น array
  def cleanRow(line: String): Array[String] = {

    line.split(",").map(cleanText)

  }

}