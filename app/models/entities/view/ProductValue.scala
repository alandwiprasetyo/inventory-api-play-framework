package models.entities.view

import com.byteslounge.slickrepo.meta.Entity

/**
  * Created by alandwiprasetyo on 1/29/18.
  */
case class ProductValue(override val id: Option[Int], productName: String, productSku: String, available: Int, avgPurchasePrice: Int) extends Entity[ProductValue, Int] {
  def withId(id: Int): ProductValue = this.copy(id = Some(id))
}
