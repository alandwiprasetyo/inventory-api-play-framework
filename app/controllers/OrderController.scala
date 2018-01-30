package controllers

import java.io.File
import java.text.SimpleDateFormat
import javax.inject._

import com.google.gson.Gson
import models.repos.table.ProductRepository
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{JsArray, Json, Writes}
import play.api.mvc._
import util.DatabaseImplicits
import play.api.libs.iteratee.Enumerator
import com.github.tototoshi.csv._
import models.entities.table.Order
import models.repos.table.{OrderRepository, ProductRepository}

import scala.concurrent.Future


@Singleton
class OrderController @Inject()(ordersDAO: OrderRepository, productsDAO: ProductRepository, dbExecuter: DatabaseImplicits) extends Controller {

  import dbExecuter.executeOperation

  val gson: Gson = new Gson()

  implicit val orderWrites: Writes[Order] = new Writes[Order] {
    def writes(order: Order) = Json.obj(
      "id" -> order.id,
      "quantity" -> order.quantity,
      "product_id" -> order.productId,
      "selling_price" -> order.sellingPrice,
      "total" -> order.total,
      "note" -> order.note
      //      "created" -> order.created
    )
  }

  def index: Action[AnyContent] = Action.async { implicit request =>
    ordersDAO.findAll().map { posts =>
      Ok(Json.toJson(posts))
    }
  }

  def show(id: Int) = Action.async {
    ordersDAO.findOne(id) map { sup => sup.fold(NoContent)(sup => Ok(Json.toJson(sup))) }
  }

  def add() = Action.async(parse.json) {
    request => {
      for {
        productId <- (request.body \ "product_id").asOpt[Int]
        stockId <- (request.body \ "stock_id").asOpt[Int]
        quantity <- (request.body \ "quantity").asOpt[Int]
        sellingPrice <- (request.body \ "selling_price").asOpt[Int]
        total = sellingPrice * quantity
      } yield {
        ordersDAO.save(Order(None, productId, stockId, quantity, sellingPrice, total)).mapTo[Order] map {
          sup => Created("Id of Stock Added : " + sup.id.getOrElse(0))
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
        productId <- (request.body \ "product_id").asOpt[Int]
        stockId <- (request.body \ "stock_id").asOpt[Int]
        quantity <- (request.body \ "quantity").asOpt[Int]
        sellingPrice <- (request.body \ "selling_price").asOpt[Int]
        total = sellingPrice * quantity
      } yield {
        ordersDAO.save(Order(Option(id), productId, stockId, quantity, sellingPrice, total)).mapTo[Order] map {
          sup => Created("Id of Order updated : " + sup.id.getOrElse(0))
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
