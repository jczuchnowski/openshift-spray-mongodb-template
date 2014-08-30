package spray.examples

import org.joda.time.DateTime

import reactivemongo.bson.{BSONDateTime,BSONHandler}


object BsonHandlers {

  implicit object BSONDateTimeHandler extends BSONHandler[BSONDateTime, DateTime] {
    def read(time: BSONDateTime) = new DateTime(time.value)
    def write(jdtime: DateTime) = BSONDateTime(jdtime.getMillis)
  }
}