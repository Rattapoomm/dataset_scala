object Transform {

  def isValid(row: Array[String]): Boolean = {

    // ตรวจว่าข้อมูลครบหรือไม่
    if (row.length < 15) return false

    // ดึงค่าจาก column
    val firstName = row(1)
    val lastName = row(2)
    val age = row(4)

    // ตรวจว่าค่าว่างหรือไม่
    if (firstName.isEmpty || lastName.isEmpty || age.isEmpty)
      return false

    // age ต้องเป็นตัวเลข
    if (!age.forall(_.isDigit))
      return false

    true
  }

  def summarize(data: List[Array[String]]): Unit = {

  println("\n===== DATA SUMMARY =====")

  // จำนวนเที่ยวบินทั้งหมด
  val totalFlights = data.length
  println(s"Total Flights : $totalFlights")

  // สนามบินปลายทางที่มีคนไปเยอะสุด
  val popularAirport =
    data.groupBy(_(12))
        .mapValues(_.size)
        .maxBy(_._2)

  println(s"Most Popular Arrival Airport : ${popularAirport._1} (${popularAirport._2} flights)")

  // ประเทศที่มีผู้โดยสารมากสุด
  val popularCountry =
    data.groupBy(_(8))
        .mapValues(_.size)
        .maxBy(_._2)

  println(s"Most Passenger Country : ${popularCountry._1} (${popularCountry._2} passengers)")

  // ค่าอายุเฉลี่ย
  val avgAge =
    data.map(_(4).toInt).sum.toDouble / data.length

  println(f"Average Age : $avgAge%.2f")

  // สถานะเที่ยวบิน
  val statusCount =
    data.groupBy(_(14))
        .mapValues(_.size)

  println("\nFlight Status:")
  statusCount.foreach { case (status, count) =>
    println(s"$status : $count")
  }

}
  // แปลงเป็น JSON
  def toJson(row: Array[String]): String = {

  s"""{
  "PassengerID":"${row(0)}",
  "FirstName":"${row(1)}",
  "LastName":"${row(2)}",
  "Gender":"${row(3)}",
  "Age":"${row(4)}",
  "Nationality":"${row(5)}",
  "AirportName":"${row(6)}",
  "AirportCountryCode":"${row(7)}",
  "CountryName":"${row(8)}",
  "AirportContinent":"${row(9)}",
  "Continents":"${row(10)}",
  "DepartureDate":"${row(11)}",
  "ArrivalAirport":"${row(12)}",
  "PilotName":"${row(13)}",
  "FlightStatus":"${row(14)}"
  }"""
}
}