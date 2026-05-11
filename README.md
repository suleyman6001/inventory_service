# Inventory Service

Inventory Service is part of the Shopping Platform Microservices project.

It is responsible for managing inventory items, reserving stock atomically, consuming order creation events from Kafka, and publishing inventory reservation results back to Kafka.

## Responsibilities

- Create inventory items
- Retrieve inventory items by product code
- Store inventory data in PostgreSQL
- Consume `OrderCreatedEvent` from Kafka
- Attempt atomic stock reservation
- Publish `InventoryReservationResultEvent` to Kafka
- Return reservation success or rejection result

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Kafka
- PostgreSQL
- Apache Kafka
- Docker / Docker Compose
- Maven

## Project Structure

```text
inventory_service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── infrastructure/
│       └── docker-compose.yml   # Kafka and Kafka UI compose file, if present
├── docker-compose.yml           # Inventory Service / database compose file
├── Dockerfile
├── pom.xml
├── .env.example
├── application-local.example.properties
└── README.md
```

## Kafka Event Flow

```text
Inventory Service
  ├── consumes OrderCreatedEvent
  │       ↑
  │     Kafka topic: order-created
  │
  └── publishes InventoryReservationResultEvent
          ↓
        Kafka topic: inventory-reservation-result
```

## Kafka Topics

| Topic | Direction | Event |
|---|---|---|
| `order-created` | Consumed by Inventory Service | `OrderCreatedEvent` |
| `inventory-reservation-result` | Produced by Inventory Service | `InventoryReservationResultEvent` |

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/inventory/items` | Create a new inventory item |
| `GET` | `/inventory/{productCode}` | Get inventory item by product code |
| `POST` | `/inventory/reserve` | Reserve stock manually |

### Create inventory item

```http
POST http://localhost:8082/inventory/items
Content-Type: application/json
```

```json
{
  "productCode": "ps5",
  "productName": "Sony Playstation 5",
  "price" : 400,
  "availableQuantity": 24
}
```

### Get inventory item

```http
GET http://localhost:8082/inventory/RTX-4090
```

### Reserve stock manually

```http
POST http://localhost:8082/inventory/reserve
Content-Type: application/json
```

```json
{
  "productCode": "RTX-4090",
  "requestedQuantity": 2
}
```

## Atomic Reservation Logic

Inventory reservation is handled atomically at the database level.

The service updates available and reserved quantity only if enough stock exists at the moment of the update.

Conceptually:

```text
availableQuantity = availableQuantity - requestedQuantity
reservedQuantity  = reservedQuantity + requestedQuantity

only if availableQuantity >= requestedQuantity
```

This prevents overselling when multiple reservation requests happen at the same time.

## Running the Service Locally

This is useful when you want to run the Spring Boot application from IntelliJ or Maven.

### 1. Create local application properties

Copy the example local properties file:

```bash
cp src/main/resources/application-local.example.properties src/main/resources/application-local.properties
```

On Windows PowerShell:

```powershell
Copy-Item src/main/resources/application-local.example.properties src/main/resources/application-local.properties
```

Then edit:

```text
src/main/resources/application-local.properties
```

Set your local database and Kafka values.

Typical local Kafka setting:

```properties
spring.kafka.bootstrap-servers=localhost:9094
```

The `application-local.properties` file should not be committed to Git. It is meant for your local machine only.

### 2. Start the Inventory Service database

From the root of this repository:

```bash
docker compose up -d
```

This starts the PostgreSQL database used by Inventory Service.

If the service-specific `docker-compose.yml` also starts the Inventory Service container and you only want the database, run the database service by name instead:

```bash
docker compose up -d inventory_db
```

Use the actual database service name from your `docker-compose.yml` if it is different.

### 3. Start Kafka and Kafka UI

Kafka and Kafka UI are located in:

```text
src/infrastructure/docker-compose.yml
```

Start them with:

```bash
docker compose -f src/infrastructure/docker-compose.yml up -d
```

Kafka UI should then be available at:

```text
http://localhost:8090
```

### 4. Run the Spring Boot application locally

Using Maven:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
```

Or run the application from IntelliJ with the `local` profile enabled.

The service should start on:

```text
http://localhost:8082
```

## Running with Docker Compose

The standard Docker Compose setup can be started from the root of this repository:

```bash
docker compose up --build
```

This uses the repository's `docker-compose.yml`.

To stop it:

```bash
docker compose down
```

To stop it and remove volumes:

```bash
docker compose down -v
```

## Running as Part of the Full Shopping Platform

For running the complete system, use the root Shopping Platform repository.

The full system includes:

- API Gateway
- Order Service
- Inventory Service
- PostgreSQL databases
- Kafka
- Kafka UI

When running inside the full Docker Compose setup, this service should connect to Kafka using the Docker network hostname:

```properties
spring.kafka.bootstrap-servers=kafka:9092
```

When running locally from IntelliJ, it should usually connect to Kafka through:

```properties
spring.kafka.bootstrap-servers=localhost:9094
```

## Environment Variables

This repository includes:

```text
.env.example
```

Create a local `.env` file from it:

```bash
cp .env.example .env
```

On Windows PowerShell:

```powershell
Copy-Item .env.example .env
```

Then adjust the database credentials if needed.

The `.env` file should not be committed.

## Reservation Result Statuses

The inventory reservation result event can produce statuses such as:

```text
RESERVED
REJECTED
```

Possible rejection reasons may include:

```text
INSUFFICIENT_STOCK
PRODUCT_NOT_FOUND
INVALID_QUANTITY
```

Adjust these names based on the actual constants/enums used in the project.

## Notes

- Inventory Service owns its own database.
- It should not directly access the Order Service database.
- Communication with Order Service happens asynchronously through Kafka.
- Kafka topics can be created manually or through Spring Kafka `NewTopic` beans.
- For full-system execution, prefer the root Shopping Platform Docker Compose file.
