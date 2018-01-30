package models.entities.view

import com.byteslounge.slickrepo.meta.Entity

/**
  * Created by alandwiprasetyo on 1/29/18.
  */
case class ProductAvailable(override val id:  Option[Int], productName: String, productSku: String, available: Int) extends Entity[ProductAvailable, Int] {
  def withId(id: Int): ProductAvailable = this.copy(id = Some(id))
}