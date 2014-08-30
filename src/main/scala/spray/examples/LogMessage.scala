package spray.examples

import org.joda.time.DateTime

import reactivemongo.bson.BSONObjectID


case class LogMessage(_id: BSONObjectID = BSONObjectID.generate, msg: String, date: DateTime)
