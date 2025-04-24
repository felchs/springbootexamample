# 📘 Hello STEFANINI API — Spring Boot + Maven + OAuth2 JWT

This project is a secure Spring Boot REST API that demonstrates role-based authorization using OAuth2 and JWT tokens. It integrates with a Keycloak realm and protects endpoints based on user roles.

---

## 🧰 Prerequisites

Make sure you have the following installed before running the project:

- Java 17+
- Maven 3.0.1+
- Keycloak or any OAuth2-compliant provider
- Optional: IDE like IntelliJ IDEA or VS Code

---

## 🚀 Building the Project

Clone the repository and navigate into the project directory:

```bash
git clone git@github.com:felchs/stefanini.git
cd stefanini
```

Then build the project:
```
mvn clean install
```

## ▶️ Running the Application
You can run the application in two ways:

1. Via Maven:
```
mvn spring-boot:run
```

2. Via the generated JAR:

```
java -jar target/hello-api-0.0.1-SNAPSHOT.jar
```
The API will be available at: http://localhost:8081

We are using port 8081 because Keyclock will be running under 8080

---

## 📑 Swagger Documentation
Once the application is running, you can access the Swagger UI for API documentation at the following URL:

Swagger UI -> http://localhost:8081/swagger-ui/index.html

This will provide an interactive interface where you can explore and test the API endpoints.

---

## 🔐 Securing the API
This API is secured using JWT tokens issued by a Keycloak realm (Felipe). Access to endpoints is restricted by roles present in the token.

---

## 🔑 Endpoints & Access

Endpoint	HTTP Method	Required Role
```
+-------------------+-------------+-------------------+
|     Endpoint      | HTTP Method |    Required Role  |
+-------------------+-------------+-------------------+
|   /hello          |     GET     |   client_user     |
|   /hello/admin    |     GET     |   client_admin    |
+-------------------+-------------+-------------------+

```

---

## ⚙️ Configuration (application.properties)

🔍 Property Explanation

```
issuer-uri: URL to the OAuth2 provider's realm (in this case, Keycloak)
jwk-set-uri: URI from which Spring fetches public keys to verify token signatures
server.port: Sets the API port to 8081
jwt.auth.converter.principle-attribute: Uses the preferred_username claim as the logged-in user's name
jwt.auth.converter.resource-id: Ensures token audience (aud) contains "felipe-rest-api"
```
---
## 🧪 Testing
You can test the API using Postman, Insomnia, or curl:****

```
curl -H "Authorization: Bearer <your-token>" http://localhost:8081/hello

```

Replace <your-token> with a valid JWT that includes:

The role (client_user or client_admin) under realm_access.roles

preferred_username claim

aud claim containing felipe-rest-api

---

## 📚 Resources
Spring Security OAuth2 Resource Server Docs

Keycloak Documentation

JWT Debugger

---

---

## 🚀 How to Download Docker, Pull the felipe-stefanini Image, and Run Keycloak

## 👉 1. Download Docker

If you don't already have Docker installed, follow these steps:

For Windows and Mac: https://www.docker.com/products/docker-desktop/

For Linux: You can follow the official Docker installation instructions for your specific Linux distribution on Docker's website.
https://www.docker.com/products/docker-desktop/

Verify Docker is Installed

Once Docker is installed, open a terminal (or command prompt) and run this command to verify that Docker is working properly:

```
docker --version
```

---
## 🏗️ 2. Pull the Keycloak Docker Image
To download the Keycloak image from Docker Hub, run this command:

```
docker pull felipeforexmj/keycloak:latest
```
- This will fetch the Keycloak image you need from Docker Hub.

- Make sure you're connected to the internet, as it will need to download the image.

🏃‍♂️ 3. Run the Keycloak Container

```
docker run -d -p 8080:8080 --name keycloake-felipe -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin felipeforexmj/keycloack start-dev
```
👉 DONT FORGET to add KEYCLOAK_ADMIN and KEYCLOAK_ADMIN_PASSWORD environment variables

Explanation of the command:
docker run: This tells Docker to create and start a new container.

```
-d: Runs the container in detached mode (in the background).
-p 8080:8080: Maps port 8080 on your local machine to port 8080 on the container. This is the port that Keycloak will run on, so you can access it via http://localhost:8080.
--name keycloak-felipe: Names the container keycloak-felipe (you can use any name here).
-e KEYCLOAK_ADMIN=admin: Sets the Keycloak admin username to admin.
-e KEYCLOAK_ADMIN_PASSWORD=admin: Sets the Keycloak admin password to admin.
felipeforexmj/keycloak: Specifies the Docker image to use, which is the image you just pulled.
start-dev: Starts Keycloak in development mode.
```

## 🌐 5. Access Keycloak

Once the container is running, you can access the Keycloak Admin Console by opening a web browser and going to:

```
http://localhost:8080
```

## 🎉 That's it!
You now have Keycloak running locally in a Docker container.

You can manage users, realms, and configure authentication settings directly through the Keycloak Admin Console.
 