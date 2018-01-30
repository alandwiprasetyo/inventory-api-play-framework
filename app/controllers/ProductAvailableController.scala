package controllers

import javax.inject._

import models.entities.view.ProductAvailable
import models.repos.view.ProductAvailableRepository
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import util.DatabaseImplicits

@Singleton
class ProductAvailableController @Inject()(stockAvailableDAO: ProductAvailableRepository, dbExecuter: DatabaseImplicits) extends Controller {

  import dbExecuter.executeOperation

  implicit val orderWrites: Writes[ProductAvailable] = new Writes[ProductAvailable] {
    def writes(stock: ProductAvailable) = Json.obj(
      "id" -> stock.id,
      "name" -> stock.productName,
      "sku" -> stock.productSku,
      "available" -> stock.available
    )
  }

  def index: Action[AnyContent] = Action.async { implicit request =>
    stockAvailableDAO.findAll().map { posts =>
      Ok(Json.toJson(posts))
    }
  }
}
