# Crossref REST API

Technology:

* Play framework (Scala)
* SQLite

#Running

        $ sbt run

#Testing

To run all tests :

        $ sbt test

#API Resources

  - [Products](#products)
  - [Orders](#orders)
  - [Stocks](#stocks)
  - [Reports](#reports)

## Products

### List Product

### GET /products

`GET localhost:9000/products`

Using :

    curl --request GET localhost:9000/products

### List Product Available Stock

### GET /products/available

`GET localhost:9000/products/available`

Using :

    curl --request GET localhost:9000/products/available

### Add Product

### POST /products/add

`POST localhost:9000/products/add`

Using :

    curl --request POST localhost:9000/products/add -H "Content-type: application/json" --data "{\"name\" : \"sup1\",\"sku\" : \"sku-12312\",\"color\" : \"blue\",\"size\" : \"M\"}"


Response body:

    {
        "name": "1234",
        "sku": "magazine",
        "color": "Blue",
        "size": "M",
    }

### Edit Product

### PUT /products/{id}/edit

`PUT localhost:9000/products/{id}/edit`

Using :

    curl --request PUT localhost:9000/products/1/edit -H "Content-type: application/json" --data "{\"name\" : \"sup1\",\"sku\" : \"sku-12312\",\"color\" : \"blue\",\"size\" : \"M\"}"


Response body:

    {
        "name": "1234",
        "sku": "magazine",
        "color": "Blue",
        "size": "M",
    }



## Stocks

### List Stock

### GET /stocks

`GET localhost:9000/stocks`

Using :

    curl --request GET localhost:9000/stocks

### Add Stock

### POST /stocks/add

`POST localhost:9000/stocks/add`

Using :

    curl --request POST localhost:9000/stocks/add -H "Content-type: application/json" --data "{\"product_id\" : 1,\"quantity_hand\" : 2,\"quantity_progress\" : 2,\"purchase_price\" : 10000,\"selling_price\" : 10000,\"receipt\" : \"sku-12312\",\"note\" : \"just note\"}"



Response body:

    {
        "product_id": 1,
        "quantity_hand": 10,
        "quantity_progress": 10,
        "purchase_price": 10000,
        "selling_price": 15000,
        "receipt": "Kwitansi 12313",
        "note": "Note 1"
    }

### Edit Stock

### PUT /stocks/{id}/edit

`PUT localhost:9000/stocks/{id}/edit`

Using :

    curl --request POST localhost:9000/stocks/1/edit -H "Content-type: application/json" --data "{\"product_id\" : 1,\"quantity_hand\" : 2,\"quantity_progress\" : 2,\"purchase_price\" : 10000,\"selling_price\" : 10000,\"receipt\" : \"sku-12312\",\"note\" : \"just note\"}"


Response body:


    {
        "product_id": 1,
        "quantity_hand": 10,
        "quantity_progress": 10,
        "purchase_price": 10000,
        "selling_price": 15000,
        "receipt": "Kwitansi 12313",
        "note": "Note 1"
    }



## Orders

### List Order

### GET /orders

`GET localhost:9000/orders`

Using :

    curl --request GET localhost:9000/orders

### Add Orders

### POST /orders/add

`POST localhost:9000/orders/add`

Using :

    curl --request POST localhost:9000/orders/add -H "Content-type: application/json" --data "{\"product_id\" : 1,\"stock_id\" : 1,\"quantity\" : 2,\"selling_price\" : 10000}"





Response body:

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


Response body:



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

    curl --request GET localhost:9000/products


### Export CSV Product Reports

### GET /sellings/report/export

`GET localhost:9000/sellings/report/export`



## Product Reports

### List products report

### GET /products/report

`GET localhost:9000/products/report`

Using :

    curl --request GET localhost:9000/products


### Export CSV Products Report

### GET /products/report/export

`GET localhost:9000/products/report/export`
