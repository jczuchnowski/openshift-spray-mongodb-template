package spray.examples

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

import spray.json._


object DemoJsonProtocol extends DefaultJsonProtocol {
  
  val isoFormat = ISODateTimeFormat.dateTime;
  
  /**
   * Serializes and deserializes ISO8601 String to DateTime.
   */
  implicit object DateFormat extends RootJsonFormat[DateTime] {
    def write(date: DateTime) = date match {
      case null => JsNull 
      case _ => JsString(isoFormat.print(date))
    }
    def read(value: JsValue) = value match {
      case JsString(dateString) => isoFormat.parseDateTime(dateString)
      case _ => null
    }
  }
  
  implicit val logMessageCmd = jsonFormat1(LogMessageCmd)
  
  implicit val logMessageData = jsonFormat3(LogMessageData)
}
