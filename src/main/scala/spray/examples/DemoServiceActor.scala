package spray.examples

import akka.actor.Props

import java.net.URI

import org.joda.time.DateTime

import spray.examples.DemoJsonProtocol._

import spray.http.HttpHeaders._
import spray.http.{HttpResponse, StatusCodes}
import spray.httpx.SprayJsonSupport._
import spray.routing.HttpServiceActor


case class LogMessageCmd(msg: String)

case class LogMessageData(id: String, msg: String, date: DateTime)

object DemoServiceActor {

  def props(repository: LogMessageRepository): Props = Props(new DemoServiceActor(repository))
}

class DemoServiceActor(repository: LogMessageRepository) extends HttpServiceActor with ServletDirectives {

  implicit val executionContext = context.dispatcher
    
  def receive = runRoute {
    schemeName { sName =>
      hostName { hName =>
        servletContextPath { srvPath =>
        path("log" / Segment) { id =>
          get {
            complete {
              for {
                opt <- repository.get(id)
              } yield opt.map { log =>
                LogMessageData(log._id.stringify, log.msg, log.date)
              }
            }
          }
        } ~
        path("log") {
          get {
            complete {
              for {
                seq <- repository.all
              } yield seq.map { log =>
                LogMessageData(log._id.stringify, log.msg, log.date)
              }
            }
          } ~
          post {
            entity(as[LogMessageCmd]) { cmd =>
              complete {
                val newLogMsg = LogMessage(msg = cmd.msg, date = new DateTime)
                val result = repository.save(newLogMsg)

                for {
                  err <- result
                } yield {
                  if (err.ok) {
                    val baseHref = new URI(s"$sName://$hName$srvPath/log")
                    val id = newLogMsg._id.stringify

                    HttpResponse(status = StatusCodes.Created, headers = `Location`(s"$baseHref/$id") :: Nil)
                  } else {
                    val errMsg = err.errMsg.getOrElse("not ok")

                    HttpResponse(StatusCodes.BadRequest, errMsg)
                  }
                }
              }
            }
          }}
        }
      }
    }
  }
}