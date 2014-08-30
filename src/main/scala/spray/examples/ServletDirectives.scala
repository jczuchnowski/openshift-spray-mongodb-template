package spray.examples

import javax.servlet.ServletContext

import spray.http.StatusCodes
import spray.routing.Directive1
import spray.routing.directives.BasicDirectives._
import spray.routing.directives.HeaderDirectives._
import spray.routing.directives.RouteDirectives._
import spray.servlet.ServletRequestInfoHeader

trait ServletDirectives {

  def servletContext: Directive1[ServletContext] = ServletDirectives._servletContext
  
  def servletContextPath: Directive1[String] = ServletDirectives._servletContextPath
}

object ServletDirectives extends ServletDirectives {
  
  val _servletContext: Directive1[ServletContext] =
    optionalHeaderValuePF { case ServletRequestInfoHeader(hsReq) => hsReq }.flatMap {
      case Some(hsReq) =>
        provide(hsReq.getServletContext)

      case None =>
        complete(StatusCodes.InternalServerError)
    }
    
  val _servletContextPath: Directive1[String] = _servletContext.map { sc => sc.getContextPath() }
}