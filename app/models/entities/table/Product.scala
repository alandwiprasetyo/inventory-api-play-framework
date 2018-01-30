package models.entities.table

import com.byteslounge.slickrepo.meta.Entity

case class Product(override val id: Option[Int], name: String, sku: String, size: String, color: String) extends Entity[Product, Int]{
  def withId(id: Int): Product = this.copy(id = Some(id))
}