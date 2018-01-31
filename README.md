# INVENTORY REST API

Technology:

* Play framework (Scala)
* SQLite
# Requirements
  - Scala
  - sbt (https://www.scala-sbt.org/download.html)
  
# Running

        $ sbt run

# Testing

To run all tests :

        $ sbt test

# API Resources

  - [Products](#products)
  - [Orders](#orders)
  - [Stocks](#stocks)
  - [Selling Reports](#selling-reports)
  - [Product Reports](#product-reports)

## Products

### List Product

### GET /products

`GET localhost:9000/products`

Using :

    curl --request GET localhost:9000/products

Response :

        [
          {
            "id": 1,
            "name": "Zalekia Plain Casual Blouse (L,Broken White)",
            "sku": "SSI-D00791015-LL-BWH",
            "color": "L",
            "size": "Broken White"
          },
          {
            "id": 2,
            "name": "Deklia Plain Casual Blouse (L,Navy)",
            "sku": "SSI-D00864612-LL-NAV",
            "color": "Navy",
            "size": "L"
          },
          {
            "id": 3,
            "name": "Deklia Plain Casual Blouse (L,Navy)",
            "sku": "SSI-D00864612-LL-NAV",
            "color": "M",
            "size": "blue"
          }
        ]

### List Product Available Stock

### GET /products/available

`GET localhost:9000/products/available`

Using :

    curl --request GET localhost:9000/products/available

Response :

    [
      {
        "id": 1,
        "name": "Zalekia Plain Casual Blouse (L,Broken White)",
        "sku": "SSI-D00791015-LL-BWH",
        "available": 13
      },
      {
        "id": 2,
        "name": "Deklia Plain Casual Blouse (L,Navy)",
        "sku": "SSI-D00864612-LL-NAV",
        "available": 9
      },
      {
        "id": 3,
        "name": "Deklia Plain Casual Blouse (L,Navy)",
        "sku": "SSI-D00864612-LL-NAV",
        "available": 0
      },
      {
        "id": 6,
        "name": "Zalekia Plain Casual Blouse (L,Broken White)",
        "sku": "SSI-D00791015-LL-BWH",
        "available": 0
      }
    ]

### Add Product

### POST /products/add

`POST localhost:9000/products/add`

Using :

    curl --request POST localhost:9000/products/add -H "Content-type: application/json" --data  "{\"name\" : \"Zalekia Plain Casual Blouse (L,Broken White)\",\"sku\" : \"SSI-D00791015-LL-BWH\",\"color\" : \"Broken White\",\"size\" : \"M\"}"


Request Example :

    {
            "name": "Zalekia Plain Casual Blouse (L,Broken White)",
            "sku": "SSI-D00791015-LL-BWH",
            "color": "Broken White",
            "size": "M",
    }

### Edit Product

### PUT /products/{id}/edit

`PUT localhost:9000/products/{id}/edit`

Using :

    curl --request PUT localhost:9000/products/1/edit -H "Content-type: application/json" --data "{\"name\" : \"Zalekia Plain Casual Blouse (L,Broken White)\",\"sku\" : \"SSI-D00791015-LL-BWH\",\"color\" : \"Broken White\",\"size\" : \"L\"}"


Request example:

    {
        "name": "Zalekia Plain Casual Blouse (L,Broken White)",
        "sku": "SSI-D00791015-LL-BWH",
        "color": "Broken White",
        "size": "L",
    }

## Stocks

### List Stock

### GET /stocks

`GET localhost:9000/stocks`

Using :

    curl --request GET localhost:9000/stocks

Response :

    [
      {
        "id": 1,
        "quantity_hand": 2,
        "quantity_progress": 2,
        "purchase_price": 10000,
        "selling_price": 10000,
        "receipt": "20180102-69458",
        "note": "2017/12/15 terima 2"
      },
      {
        "id": 2,
        "quantity_hand": 10,
        "quantity_progress": 10,
        "purchase_price": 20000,
        "selling_price": 25000,
        "receipt": "20180102-69458",
        "note": "2017/12/15 terima 2"
      },
      {
        "id": 3,
        "quantity_hand": 2,
        "quantity_progress": 2,
        "purchase_price": 10000,
        "selling_price": 10000,
        "receipt": "20180102-69458",
        "note": "2017/12/15 terima 2"
      },
      {
        "id": 4,
        "quantity_hand": 2,
        "quantity_progress": 2,
        "purchase_price": 10000,
        "selling_price": 10000,
        "receipt": "20180102-69458",
        "note": "2017/12/15 terima 2"
      },
      {
        "id": 5,
        "quantity_hand": 2,
        "quantity_progress": 2,
        "purchase_price": 10000,
        "selling_price": 10000,
        "receipt": "20180102-69458",
        "note": "2017/12/15 terima 2"
      },
      {
        "id": 6,
        "quantity_hand": 2,
        "quantity_progress": 2,
        "purchase_price": 10000,
        "selling_price": 10000,
        "receipt": "20180102-69458",
        "note": "2017/12/15 terima 2"
      },
      {
        "id": 7,
        "quantity_hand": 2,
        "quantity_progress": 2,
        "purchase_price": 10000,
        "selling_price": 10000,
        "receipt": "20180102-69458",
        "note": "2017/12/15 terima 2"
      }
    ]

### Add Stock

### POST /stocks/add

`POST localhost:9000/stocks/add`

Using :

    curl --request POST localhost:9000/stocks/add -H "Content-type: application/json" --data "{\"product_id\" : 1,\"quantity_hand\" : 2,\"quantity_progress\" : 2,\"purchase_price\" : 10000,\"selling_price\" : 10000,\"receipt\" : \"20180102-69539\",\"note\" : \"2018/01/06 terima 47; Masih Menunggu\"}"

Request example:

    {
        "product_id": 1,
        "quantity_hand": 10,
        "quantity_progress": 10,
        "purchase_price": 10000,
        "selling_price": 15000,
        "receipt": "20180102-69539",
        "note": "2018/01/06 terima 47; Masih Menunggu"
    }

### Edit Stock

### PUT /stocks/{id}/edit

`PUT localhost:9000/stocks/{id}/edit`

Using :

    curl --request POST localhost:9000/stocks/1/edit -H "Content-type: application/json" --data "{\"product_id\" : 1,\"quantity_hand\" : 2,\"quantity_progress\" : 2,\"purchase_price\" : 10000,\"selling_price\" : 10000,\"receipt\" : \"sku-12312\",\"note\" : \"just note\"}"

Request example:
   
    {
        "product_id": 1,
        "quantity_hand": 10,
        "quantity_progress": 10,
        "purchase_price": 10000,
        "selling_price": 15000,
        "receipt": "20180102-69539",
        "note": "2018/01/06 terima 47; Masih Menunggu"
    }

## Orders

### List Order

### GET /orders

`GET localhost:9000/orders`

    [
      {
        "id": 1,
        "quantity": 1,
        "product_id": 1,
        "selling_price": 15000,
        "total": 15000,
        "note": "Pesanan ID-20180108-548167"
      },
      {
        "id": 2,
        "quantity": 1,
        "product_id": 2,
        "selling_price": 25000,
        "total": 25000,
        "note": "Pesanan ID-20180108-170723"
      }
    ]

Using :

    curl --request GET localhost:9000/orders

### Add Orders

### POST /orders/add

`POST localhost:9000/orders/add`

Using :

    curl --request POST localhost:9000/orders/add -H "Content-type: application/json" --data "{\"product_id\" : 1,\"stock_id\" : 1,\"quantity\" : 2,\"selling_price\" : 10000}"



Request example:

    {
        "product_id": 1,
        "stock_id": 1,
        "quantity": 1,
        "selling_price": 14000,
    }

### Edit Orders

### PUT /orders/{id}/edit

`PUT localhost:9000/orders/{id}/edit`

Using :

    curl --request POST localhost:9000/orders/1/edit -H "Content-type: application/json" --data "{\"product_id\" : 1,\"stock_id\" : 1,\"quantity\" : 2,\"selling_price\" : 10000}"


Request example:

    {
        "product_id": 1,
        "stock_id": 1,
        "quantity": 1,
        "selling_price": 14000,
    }



## Selling Reports

### List Selling Reports

### GET /sellings/report

`GET localhost:9000/sellings/report`

Using :

    curl --request GET localhost:9000/sellings/report

Response :

    [
      {
        "id": 1,
        "quantity": 1,
        "selling_price": 15000,
        "purchase_price": 10000,
        "total": 15000,
        "profit": 5000,
        "product_name": "Zalekia Plain Casual Blouse (L,Broken White)",
        "product_sku": "SSI-D00791015-LL-BWH"
      },
      {
        "id": 2,
        "quantity": 1,
        "selling_price": 25000,
        "purchase_price": 20000,
        "total": 25000,
        "profit": 5000,
        "product_name": "Deklia Plain Casual Blouse (L,Navy)",
        "product_sku": "SSI-D00864612-LL-NAV"
      }
    ]

### Export CSV Product Reports

### GET /sellings/report/export

`GET localhost:9000/sellings/report/export`


## Product Reports

### List products report

### GET /products/report

`GET localhost:9000/products/report`

Using :

    curl --request GET localhost:9000/products/report

Response :

    [
      {
        "id": 1,
        "name": "Zalekia Plain Casual Blouse (L,Broken White)",
        "sku": "SSI-D00791015-LL-BWH",
        "available": 13,
        "avg_purchase_price": 10000,
        "total": 130000
      },
      {
        "id": 2,
        "name": "Deklia Plain Casual Blouse (L,Navy)",
        "sku": "SSI-D00864612-LL-NAV",
        "available": 9,
        "avg_purchase_price": 20000,
        "total": 180000
      },
      {
        "id": 3,
        "name": "Deklia Plain Casual Blouse (L,Navy)",
        "sku": "SSI-D00864612-LL-NAV",
        "available": 0,
        "avg_purchase_price": 0,
        "total": 0
      },
      {
        "id": 6,
        "name": "Zalekia Plain Casual Blouse (L,Broken White)",
        "sku": "SSI-D00791015-LL-BWH",
        "available": 0,
        "avg_purchase_price": 0,
        "total": 0
      }
    ]

### Export CSV Products Report

### GET /products/report/export

`GET localhost:9000/products/report/export`
