# ProxySellerTestTask
# Twitter API Clone

## Description

This project is a simple Twitter clone implemented using Groovy and Spring Boot. The project allows users to register, create posts, comment on posts, like posts, follow other users, and more.

## Technology Stack

- **Programming Language:** Groovy
- **Framework:** Spring Boot
- **Database:** MongoDB
- **Build Tool:** Gradle
- **Testing:** Spock
- **Containerization:** Docker
- **API Documentation:** Swagger

## How to Build and Run the Project

### Prerequisites

- Docker
- Docker Compose

### Steps

1. **Clone the repository:**
   ```
   git clone https://github.com/DenysPiven/ProxySellerTestTask
   cd ProxySellerTestTask
   ```
3. **Run the application using Docker Compose:**
   ```
   docker-compose up
   ```
3. **Access the application:**
   The application will be available at `http://localhost:8080`.

4. **Access Swagger API documentation:**
   Swagger UI is available at `http://localhost:8080/swagger-ui/index.html`.

## API Endpoints

Here are some example API requests you can use to test the application.

### Register a User
```
curl -X POST http://localhost:8080/api/auth/register \
-H "Content-Type: application/json" \
-d '{
"id": "1",
"username": "testuser",
"password": "password",
"email": "testuser@example.com"
}'
```

### Login a User
```
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{
"username": "testuser",
"password": "password"
}'
```

### Create a User
```
curl -X POST "http://localhost:8080/api/users/" -H "Content-Type: application/json" -d '{
"id": "1",
"username": "user1",
"password": "password",
"email": "user1@example.com"
}'
```
### Update a User
```
curl -X PUT "http://localhost:8080/api/users/1" -H "Content-Type: application/json" -d '{
"username": "user1_updated",
"password": "newpassword",
"email": "user1_updated@example.com"
}'
```
### Delete a User
```
curl -X DELETE "http://localhost:8080/api/users/1"
```
### Create a Post
```
curl -X POST "http://localhost:8080/api/posts/" -H "Content-Type: application/json" -d '{
"id": "1",
"userId": "1",
"content": "This is a new post"
}'
```
### Update a Post
```
curl -X PUT "http://localhost:8080/api/posts/1" -H "Content-Type: application/json" -d '{
"content": "This is an updated post"
}'
```
### Delete a Post
```
curl -X DELETE "http://localhost:8080/api/posts/1"
```
### Like a Post
```
curl -X POST "http://localhost:8080/api/posts/1/like?userId=1"
```
### Unlike a Post
```
curl -X POST "http://localhost:8080/api/posts/1/unlike?userId=1"
```
### Follow a User
```
curl -X POST "http://localhost:8080/api/users/1/follow?followUserId=2"
```
### Unfollow a User
```
curl -X POST "http://localhost:8080/api/users/1/unfollow?unfollowUserId=2"
```
### Comment on a Post
```
curl -X POST "http://localhost:8080/api/comments/" -H "Content-Type: application/json" -d '{
"id": "1",
"postId": "1",
"userId": "1",
"content": "This is a comment"
}'
```
### Update a Comment
```
curl -X PUT "http://localhost:8080/api/comments/1" -H "Content-Type: application/json" -d '{
"content": "Updated comment content"
}'
```
### Delete a Comment
```
curl -X DELETE "http://localhost:8080/api/comments/1"
```
### Get User Feed
```
curl -X GET "http://localhost:8080/api/posts/feed?userIds=1"
```
### Get User's Posts
```
curl -X GET "http://localhost:8080/api/posts/user/1"
```
### Get Comments for a Post
```
curl -X GET "http://localhost:8080/api/comments/post/1"
```
## Running Tests

To run tests, use the following command:
```
./gradlew test
```
This will execute all the Spock tests defined in the project.