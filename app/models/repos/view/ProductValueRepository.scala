package models.repos.view

import com.byteslounge.slickrepo.meta.Keyed
import com.byteslounge.slickrepo.repository.Repository
import com.google.inject.Inject
import models.entities.view.ProductValue
import play.api.db.slick.DatabaseConfigProvider
import slick.ast.BaseTypedType
import slick.jdbc.JdbcProfile

class ProductValueRepository @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Repository[ProductValue, Int](dbConfigProvider.get[JdbcProfile].profile) {

  import driver.api._

  val pkType = implicitly[BaseTypedType[Int]]
  val tableQuery = TableQuery[ProductValues]
  type TableType = ProductValues

  class ProductValues(tag: slick.lifted.Tag) extends Table[ProductValue](tag, "product_values") with Keyed[Int] {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def sku = column[String]("sku")

    def name = column[String]("name")

    def availableStock = column[Int]("available_stock")

    def avegPurchasePrice = column[Int]("avg_purchase_price")

    def * = (id.?, name, sku, availableStock, avegPurchasePrice) <> ((ProductValue.apply _).tupled, ProductValue.unapply)
  }

}