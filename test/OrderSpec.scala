import java.time.Clock

import com.google.inject.{AbstractModule, Provides}
import models.entities.table.Order
import models.repos.table.OrderRepository
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test._
import org.specs2.execute.Results
import org.specs2.matcher.Matchers
import org.specs2.mock.Mockito
import play.api.libs.json.{JsNumber, JsObject, JsString}
import slick.dbio.DBIOAction
import play.api.libs.concurrent.Execution.Implicits._
import slick.SlickException

import scala.concurrent.{ExecutionContext, Future}

class OrderSpec extends PlaySpecification with Results with Matchers with Mockito {
  sequential

  val daoMock = mock[OrderRepository]

  val application = new GuiceApplicationBuilder().overrides(new AbstractModule {
    override def configure() = {
      bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    }

    @Provides
    def ordersDAO: OrderRepository = daoMock
  }).build

  "Routes" should {

    "send 404 on a bad request" in {
      route(application, FakeRequest(GET, "/ordersssss")).map(status(_)) shouldEqual Some(NOT_FOUND)
    }

    "send 204 when there isn't a /orders/1" in {
      daoMock.findOne(0).returns(DBIOAction.from(Future {
        None
      }))
      route(application, FakeRequest(GET, "/orders/0")).map(
        status(_)) shouldEqual Some(NO_CONTENT)
    }

    "send 200 when there is a /orders/1" in {
      daoMock.findOne(1).returns(DBIOAction.from(Future {
        Some(Order(Some(1), 1, 1, 1, 10000, 10000))
      }))
      route(application, FakeRequest(GET, "/orders/1")).map(
        status(_)) shouldEqual Some(OK)
    }

    "send 415 when post to create a order without json type" in {
      route(application, FakeRequest(POST, "/orders/add")).map(
        status(_)) shouldEqual Some(UNSUPPORTED_MEDIA_TYPE)
    }

    "send 400 when post to create a order with empty json" in {
      route(application,
        FakeRequest(POST, "/orders/add", FakeHeaders(("Content-type", "application/json") :: Nil), JsObject(Seq()))).map(
        status(_)) shouldEqual Some(BAD_REQUEST)
    }
    //
    "send 400 when post to create a order with wrong json" in {
      route(application,
        FakeRequest(POST, "/orders/add", FakeHeaders(("Content-type", "application/json") :: Nil), JsObject(Seq("wrong" -> JsString("wrong"))))).map(
        status(_)) shouldEqual Some(BAD_REQUEST)
    }

    "send 500 when post to create a order with valid json" in {
      val (productId, stockId, quantity, sellingPrice, total) = (1, 1, 1, 10000, 10000)
      val product = Order(None, productId, stockId, quantity, sellingPrice, total)
      daoMock.save(product).returns(DBIOAction.failed(new SlickException("test")))
      route(application,
        FakeRequest(POST, "/orders/add", FakeHeaders(("Content-type", "application/json") :: Nil),
          JsObject(Seq("product_id" -> JsNumber(productId), "stock_id" -> JsNumber(stockId), "quantity" -> JsNumber(quantity), "selling_price" -> JsNumber(sellingPrice))))).map(
        status(_)) shouldEqual Some(INTERNAL_SERVER_ERROR)
    }

  }

}

