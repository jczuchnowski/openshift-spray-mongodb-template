package spray.examples

import akka.actor.{Props, ActorSystem}

import com.typesafe.config.ConfigFactory

import reactivemongo.api.{MongoConnection,MongoDriver}

import spray.servlet.WebBoot


class Boot extends WebBoot {

  override val system = ActorSystem("example")

  implicit val exeutionContext = system.dispatcher
  
  lazy val dbConnection = {
    
    val config = ConfigFactory.load()
    val openShiftAppName = System.getenv("OPENSHIFT_APP_NAME")
    val uri = config.getString("mongodb.uri")
    
    val driver = new MongoDriver(system)
    val connection = MongoConnection.parseURI(uri).map( parsedUri => driver.connection(parsedUri)).get
    
    // Gets a reference to the database
    connection(openShiftAppName)
  }
  
  val logMessageRepository = new LogMessageRepository(dbConnection)
  
  // the service actor replies to incoming HttpRequests
  override val serviceActor = system.actorOf(DemoServiceActor.props(logMessageRepository))

  system.registerOnTermination {
    // put additional cleanup code here
    system.log.info("Application shut down")
  }
}