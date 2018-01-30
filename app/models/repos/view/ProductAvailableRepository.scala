package models.repos.view

import com.byteslounge.slickrepo.meta.Keyed
import com.byteslounge.slickrepo.repository.Repository
import com.google.inject.Inject
import models.entities.view.ProductAvailable
import play.api.db.slick.DatabaseConfigProvider
import slick.ast.BaseTypedType
import slick.jdbc.JdbcProfile

class ProductAvailableRepository @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Repository[ProductAvailable, Int](dbConfigProvider.get[JdbcProfile].profile) {

  import driver.api._

  val pkType = implicitly[BaseTypedType[Int]]
  val tableQuery = TableQuery[ProductAvailables]
  type TableType = ProductAvailables

  class ProductAvailables(tag: slick.lifted.Tag) extends Table[ProductAvailable](tag, "product_available") with Keyed[Int] {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def sku = column[String]("sku")

    def name = column[String]("name")

    def availableStock = column[Int]("available_stock")

    def * = (id.?, name, sku, availableStock) <> ((ProductAvailable.apply _).tupled, ProductAvailable.unapply)
  }
}