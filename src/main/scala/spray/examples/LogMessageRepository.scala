package spray.examples

import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.api.DefaultDB
import reactivemongo.bson._
import reactivemongo.bson.Macros.Options._
import reactivemongo.core.commands.LastError

import akka.actor.ActorSystem

import scala.concurrent.{ExecutionContext, Future}

import spray.examples.BsonHandlers._


class LogMessageRepository(db: DefaultDB)(implicit val executionContext: ExecutionContext) extends MongoRepository[LogMessage] {

  override val collection = db("logs")

  override implicit val format = Macros.handler[LogMessage]
}

trait MongoRepository[Entity] {

  implicit def executionContext: ExecutionContext

  implicit def format: BSONDocumentReader[Entity] with BSONDocumentWriter[Entity]

  def collection: BSONCollection

  def save(e: Entity): Future[LastError] = collection.insert(e)

  def update(e: Entity): Future[LastError] = collection.save(e)

  def get(id: String): Future[Option[Entity]] =
    collection.find(BSONDocument("_id" -> BSONObjectID(id))).cursor[Entity].headOption

  def delete(id: String): Future[LastError] =
    collection.remove(BSONDocument("_id" -> BSONObjectID(id)))
    
  def all: Future[Seq[Entity]] =
    collection.find(BSONDocument.empty).cursor[Entity].collect[List]()

}