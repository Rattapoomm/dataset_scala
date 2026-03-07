import scala.util.Try
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Transform {

  // ตรวจสอบว่าข้อมูลใน row ถูกต้องหรือไม่
  def isValid(row: Array[String]): Boolean = {

    // ตรวจสอบว่ามี column ครบ
    if (row.length < 15) return false

    // ใช้ Option กันค่า null
    val firstName = Option(row(1)).getOrElse("")
    val lastName = Option(row(2)).getOrElse("")

    // ใช้ Try กัน error ตอนแปลง age เป็น Int
    val ageOpt = Try(row(4).toInt).toOption

    // ตรวจสอบค่าที่จำเป็น
    if (firstName.isEmpty || lastName.isEmpty || ageOpt.isEmpty)
      return false

    true
  }
  // ฟังก์ชันแปลง format วันที่ให้เป็น dd/MM/yy
  def formatDate(dateStr: String): String = {

    val inputFormats = List(
      DateTimeFormatter.ofPattern("yyyy/MM/dd"),
      DateTimeFormatter.ofPattern("yyyy-MM-dd"),
      DateTimeFormatter.ofPattern("yyyy/dd/MM"),
      DateTimeFormatter.ofPattern("dd/MM/yyyy"),
      DateTimeFormatter.ofPattern("dd-MM-yyyy")
    )

    val outputFormat =
      DateTimeFormatter.ofPattern("dd/MM/yy")

    inputFormats
      .view
      .flatMap { format =>
        Try(LocalDate.parse(dateStr, format)).toOption
      }
      .headOption
      .map(_.format(outputFormat))
      .getOrElse(dateStr)

  }

  def summarize(data: List[Array[String]]): Unit = {

    println("\n===== DATA SUMMARY =====")

    // จำนวนเที่ยวบินทั้งหมด
    val totalFlights = data.length
    println(s"Total Flights : $totalFlights")

    // สนามบินปลายทางที่มีเที่ยวบินมากที่สุด
    val popularAirport =
      data.groupBy(_(12))
        .view.mapValues(_.size)
        .maxBy(_._2)

    println(s"Most Popular Arrival Airport : ${popularAirport._1} (${popularAirport._2} flights)")

    // ประเทศของผู้โดยสารที่มีจำนวนมากที่สุด
    val popularCountry =
      data.groupBy(_(8))
        .view.mapValues(_.size)
        .maxBy(_._2)

    println(s"Most Passenger Country : ${popularCountry._1} (${popularCountry._2} passengers)")

    // คำนวณค่าอายุเฉลี่ย
    val ages =
      data.flatMap(row => Try(row(4).toInt).toOption)

    val avgAge =
      if (ages.nonEmpty) ages.sum.toDouble / ages.length
      else 0

    println(f"Average Age : $avgAge%.2f")

    // นับสถานะของเที่ยวบิน
    val statusCount =
      data.groupBy(_(14))
        .view.mapValues(_.size)

    println("\nFlight Status:")
    statusCount.foreach { case (status, count) =>
      println(s"$status : $count")
    }

    // นับจำนวนผู้โดยสารที่บินมากกว่า 1 ครั้ง
    val duplicatePassenger =
      data.groupBy(_(0)).count(_._2.size > 1)

    println(s"Passengers with multiple flights : $duplicatePassenger")

    // แบ่งกลุ่มอายุ
    var child = 0
    var teen = 0
    var adult = 0
    var old = 0

    data.foreach { row =>

      Try(row(4).toInt).toOption.foreach { age =>

        if (age <= 12) child += 1
        else if (age <= 20) teen += 1
        else if (age <= 60) adult += 1
        else old += 1

      }

    }

    println("\nAge Groups:")
    println(s"Children (0-12) : $child")
    println(s"Teen (13-20) : $teen")
    println(s"Adult (21-60) : $adult")
    println(s"Elderly (60+) : $old")

  }

  // ฟังก์ชันดึงค่าจาก array แบบ safe
  def safe(i: Int, row: Array[String]): String =
    if (row.length > i) row(i) else ""

  // แปลงข้อมูล 1 แถวเป็น JSON format
  def toJson(row: Array[String]): String = {

    s"""{
  "PassengerID":"${safe(0,row)}",
  "FirstName":"${safe(1,row)}",
  "LastName":"${safe(2,row)}",
  "Gender":"${safe(3,row)}",
  "Age":"${safe(4,row)}",
  "Nationality":"${safe(5,row)}",
  "AirportName":"${safe(6,row)}",
  "AirportCountryCode":"${safe(7,row)}",
  "CountryName":"${safe(8,row)}",
  "AirportContinent":"${safe(9,row)}",
  "Continents":"${safe(10,row)}",
  "DepartureDate":"${formatDate(safe(11,row))}",
  "ArrivalAirport":"${safe(12,row)}",
  "PilotName":"${safe(13,row)}",
  "FlightStatus":"${safe(14,row)}"
  }"""
  }

}