import java.io.PrintWriter

object Load {

  def removeDuplicate(data: List[Array[String]]): List[Array[String]] = {

    data.distinct

  }

  // เขียน JSON ลงไฟล์
  def writeJsonFile(path: String, jsonData: Iterable[String]): Unit = {

    val writer = new PrintWriter(path)

    writer.println("[")

    val jsonList = jsonData.toList

    for (i <- jsonList.indices) {

      if (i == jsonList.length - 1)
        writer.println(jsonList(i))
      else
        writer.println(jsonList(i) + ",")

    }

    writer.println("]")

    writer.close()

  }

}