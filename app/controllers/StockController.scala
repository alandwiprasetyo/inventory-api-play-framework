package controllers

import java.sql.Timestamp
import java.util.Date;
import java.time.LocalDateTime
import javax.inject._

import models.entities.table.Stock
import models.repos.table.StockRepository
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import util.DatabaseImplicits

import scala.concurrent.Future
import scala.util.Try


@Singleton
class StockController @Inject()(stocksDAO: StockRepository, dbExecuter: DatabaseImplicits) extends Controller {

  import dbExecuter.executeOperation

  implicit val productWrites: Writes[Stock] = new Writes[Stock] {
    def writes(stock: Stock) = Json.obj(
      "id" -> stock.id,
      "quantity_hand" -> stock.quantityHand,
      "quantity_progress" -> stock.quantityProgress,
      "purchase_price" -> stock.purchasePrice,
      "selling_price" -> stock.sellingPrice,
      "receipt" -> stock.receipt,
      "note" -> stock.note
//      "created" -> stock.created.toStringa
    )
  }

  def index: Action[AnyContent] = Action.async { implicit request =>
    stocksDAO.findAll().map { posts =>
      Ok(Json.toJson(posts))
    }
  }

  def show(id: Int) = Action.async {
    stocksDAO.findOne(id) map { sup => sup.fold(NoContent)(sup => Ok(Json.toJson(sup))) }
  }

  def add() = Action.async(parse.json) {
    request => {
      for {
        productId <- (request.body \ "product_id").asOpt[Int]
        quantityHand <- (request.body \ "quantity_hand").asOpt[Int]
        quantityProgress <- (request.body \ "quantity_progress").asOpt[Int]
        purchasePrice <- (request.body \ "purchase_price").asOpt[Int]
        sellingPrice <- (request.body \ "selling_price").asOpt[Int]
        receipt <- (request.body \ "receipt").asOpt[String]
        note <- (request.body \ "note").asOpt[String]
      } yield {
        stocksDAO.save(Stock(None, productId, quantityHand, quantityProgress, purchasePrice, sellingPrice, receipt, note)).mapTo[Stock] map {
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
        quantityHand <- (request.body \ "quantity_hand").asOpt[Int]
        quantityProgress <- (request.body \ "quantity_progress").asOpt[Int]
        purchasePrice <- (request.body \ "purchase_price").asOpt[Int]
        sellingPrice <- (request.body \ "selling_price").asOpt[Int]
        receipt <- (request.body \ "receipt").asOpt[String]
        note <- (request.body \ "note").asOpt[String]
      } yield {
        stocksDAO.update(Stock(Option(id), productId, quantityHand, quantityProgress, purchasePrice, sellingPrice, receipt, note)).mapTo[Stock] map {
          sup => Created("Id of Stock updated : " + sup.id.getOrElse(0))
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