📦 Fulfillment Service (Spring Boot + PostgreSQL + Docker)

🚀 Quick Start

    1. Clone the repository
       git clone https://github.com/AbhishekBuildz/fulfillment-service.git
       cd fulfillment-service
    2. Run with Docker
       docker compose up --build
    3. Base API URL
       http://localhost:8080
    4. Import Postman Collection
       Open Postman → Import: postman/FulfillmentService.postman_collection.json


How to import postman collection

    Open Postman
    
    Click on Collections in the left menu
    
    Click Import (top‑left)
    
    Select the file: postman/Fulfillment-Service.postman_collection.json

    Click Import

🗄 Database Configuration

       Field	      Value
        Host	    localhost
        Port	    5432
        DB	        fulfillment
        User	    fulfillment_user
        Password	postgres123

💡 Features

    ✔ Place Order
    ✔ FEFO Allocation
    ✔ Skip Expired Lots
    ✔ 1‑Day Expiry Buffer
    ✔ Optimistic Locking
    ✔ Lot Usage Returned in Response

🐳 Docker Commands

    Build:
        docker build -t fulfillment-service .
    Run:
        docker compose up --build
    Stop:
        docker compose down
    Reset (delete DB volume):
        docker compose down -v

📦 Tech Stack

    Java 21

    Spring Boot

    Hibernate / JPA

    PostgreSQL

    Flyway

    Docker