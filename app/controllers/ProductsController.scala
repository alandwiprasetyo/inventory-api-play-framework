package controllers

import javax.inject._

import models.entities.table.Product
import models.repos.table.ProductRepository
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import util.DatabaseImplicits
import scala.concurrent.{ExecutionContext, Future, Promise}

@Singleton
class ProductsController @Inject()(productsDAO: ProductRepository, dbExecuter: DatabaseImplicits) extends Controller {

  import dbExecuter.executeOperation

  implicit val productWrites: Writes[Product] = new Writes[Product] {
    def writes(product: Product) = Json.obj(
      "id" -> product.id,
      "name" -> product.name,
      "sku" -> product.sku,
      "color" -> product.color,
      "size" -> product.size
    )
  }

  def index: Action[AnyContent] = Action.async { implicit request =>
    productsDAO.findAll().map { posts =>
      Ok(Json.toJson(posts))
    }
  }

  def show(id: Int) = Action.async {
    productsDAO.findOne(id) map { sup => sup.fold(NoContent)(sup => Ok(Json.toJson(sup))) }
  }

  def add() = Action.async(parse.json) {
    request => {
      for {
        name <- (request.body \ "name").asOpt[String]
        sku <- (request.body \ "sku").asOpt[String]
        color <- (request.body \ "color").asOpt[String]
        size <- (request.body \ "size").asOpt[String]
      } yield {
        productsDAO.save(Product(None, name, sku, color, size)).mapTo[Product] map {
          sup => Created("Id of Product Added : " + sup.id.getOrElse(0))
        } recoverWith {
          case e: Exception => Future {
            InternalServerError(e.getMessage)
          }
        }
      }
    }.getOrElse(Future {
      BadRequest("Wrong json format")
    })
  }

  def edit(id: Int) = Action.async(parse.json) {
    request => {
      for {
        name <- (request.body \ "name").asOpt[String]
        sku <- (request.body \ "sku").asOpt[String]
        color <- (request.body \ "color").asOpt[String]
        size <- (request.body \ "size").asOpt[String]
      } yield {
        productsDAO.update(Product(Option(id), name, sku, color, size)).mapTo[Product] map {
          sup => Created("Id of Product updated : " + sup.id.getOrElse(0))
        } recoverWith {
          case e: Exception => Future {
            InternalServerError(e.getMessage)
          }
        }
      }
    }.getOrElse(Future {
      BadRequest("Wrong json format")
    })
  }
}
