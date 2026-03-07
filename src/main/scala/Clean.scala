object Clean {

  // ใช้ Option กันค่า null และ trim เพื่อตัดช่องว่างหน้า-หลัง
  def cleanText(text: String): String = {
    Option(text).getOrElse("").trim
  }

  // split ด้วย comma แล้ว clean ทุก column
  def cleanRow(line: String): Array[String] = {
    line.split(",").map(cleanText)
  }

}