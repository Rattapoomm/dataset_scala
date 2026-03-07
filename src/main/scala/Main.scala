import scala.collection.parallel.CollectionConverters._

object Main {

  def main(args: Array[String]): Unit = {

    val path = "src/main/scala/airplant.csv"

    // SEQUENTIAL

    val startSeq = System.nanoTime()

    val rawData = Extract.readCSV(path)

    val cleaned = rawData.map(Clean.cleanRow)

    val unique = Load.removeDuplicate(cleaned)

    val valid = unique.filter(Transform.isValid)

    Transform.summarize(valid)

    val jsonResult = valid.map(Transform.toJson)

    // สร้างไฟล์ JSON
    Load.writeJsonFile("output_sequential.json", jsonResult)

    val endSeq = System.nanoTime()

    val seqTime = (endSeq - startSeq) / 1e9

    println("Sequential JSON created: output_sequential.json")
    println(s"Sequential Time: $seqTime seconds")

    // PARALLEL

    val startPar = System.nanoTime()

    val parallelResult =
      rawData.toVector.par
        .par
        .map(Clean.cleanRow)
        .distinct
        .filter(Transform.isValid)
        .map(Transform.toJson)

    // สร้างไฟล์ JSON
    Load.writeJsonFile("output_parallel.json", parallelResult.seq)

    val endPar = System.nanoTime()

    val parTime = (endPar - startPar) / 1e9

    println("\nParallel JSON created: output_parallel.json")
    println(s"Parallel Time: $parTime seconds")

  }

}