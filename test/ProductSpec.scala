import java.time.Clock

import com.google.inject.{AbstractModule, Provides}
import models.entities.table.Product
import models.repos.table.ProductRepository
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test._
import org.specs2.execute.Results
import org.specs2.matcher.Matchers
import org.specs2.mock.Mockito
import play.api.libs.json.{JsObject, JsString}
import slick.dbio.DBIOAction
import play.api.libs.concurrent.Execution.Implicits._
import slick.SlickException

import scala.concurrent.{ExecutionContext, Future}

class ProductSpec extends PlaySpecification with Results with Matchers with Mockito {
  sequential

  val daoMock = mock[ProductRepository]

  val application = new GuiceApplicationBuilder().overrides(new AbstractModule {
    override def configure() = {
      bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    }

    @Provides
    def productsDAO: ProductRepository = daoMock
  }).build

  "Routes" should {

    "send 404 on a bad request" in {
      route(application, FakeRequest(GET, "/productsssss")).map(status(_)) shouldEqual Some(NOT_FOUND)
    }

    "send 204 when there isn't a /products/1" in {
      daoMock.findOne(0).returns(DBIOAction.from(Future {
        None
      }))
      route(application, FakeRequest(GET, "/products/0")).map(
        status(_)) shouldEqual Some(NO_CONTENT)
    }

    "send 200 when there is a /products/1" in {
      daoMock.findOne(1).returns(DBIOAction.from(Future {
        Some(Product(Some(1), "Product Example", "SKU", "SIZE", "M"))
      }))
      route(application, FakeRequest(GET, "/products/1")).map(
        status(_)) shouldEqual Some(OK)
    }

    "send 415 when post to create a product without json type" in {
      route(application, FakeRequest(POST, "/products/add")).map(
        status(_)) shouldEqual Some(UNSUPPORTED_MEDIA_TYPE)
    }

    "send 400 when post to create a product with empty json" in {
      route(application,
        FakeRequest(POST, "/products/add", FakeHeaders(("Content-type", "application/json") :: Nil), JsObject(Seq()))).map(
        status(_)) shouldEqual Some(BAD_REQUEST)
    }
    //
    "send 400 when post to create a product with wrong json" in {
      route(application,
        FakeRequest(POST, "/products/add", FakeHeaders(("Content-type", "application/json") :: Nil), JsObject(Seq("wrong" -> JsString("wrong"))))).map(
        status(_)) shouldEqual Some(BAD_REQUEST)
    }

    "send 500 when post to create a product with valid json" in {
      val (name, sku, size, color) = ("T shirt", "SKU-1231", "M", "Blue")
      val product = Product(None, name, sku, size, color)
      daoMock.save(product).returns(DBIOAction.failed(new SlickException("test")))
      route(application,
        FakeRequest(POST, "/products/add", FakeHeaders(("Content-type", "application/json") :: Nil),
          JsObject(Seq("name" -> JsString(name), "sku" -> JsString(sku), "color" -> JsString(color), "size" -> JsString(size))))).map(
        status(_)) shouldEqual Some(INTERNAL_SERVER_ERROR)
    }

  }

}

