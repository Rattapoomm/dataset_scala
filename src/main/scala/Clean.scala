object Clean {

  def cleanText(text: String): String = {
    text.trim
  }

  def cleanRow(line: String): Array[String] = {

    line.split(",").map(cleanText)

  }

}