# Library Management Application
***

### Brief Introduction of APIs
***

* There are 5 APIs , which contains CRUD for each.

This project consists of 5 APIs, each providing CRUD operations for a specific resource.

### API

- **Endpoint Structure:**
    - `GET /resource1`: Get all items.
    - `GET /resource1/{id}`: Get a specific item by ID.
    - `POST /resource1`: Create a new item.
    - `PUT /resource1/{id}`: Update a specific item by ID.
    - `DELETE /resource1/{id}`: Delete a specific item by ID.

***

Project Architecture
***
1. MVC pattern is used to seggregate the functionality,and the view part is not being used for now.
2. A dedicated Repository folder is used to change the datasource with minimal changes.
3. Outline of Core logic folder is as follows :

```
-- LibraryManagementApp
|-- LibraryManagementApp.java
|-- controller
|   |-- AuthorController.java
|   `-- BookController.java
|   `-- PublisherController.java
|   `-- ReviewController.java
|-- model
|   |-- Author.java
|   `-- Book.java
|   `-- Publisher.java
|   `-- Review.java
|-- repo
|   |-- AuthorRepo.java
|   `-- BookRepo.java
|   `-- PublisherRepo.java
|   `-- ReviewRepo.java
|-- request
|   |-- AuthorRequest.java
|   `-- BookRequest.java
|   `-- PublisherRequest.java
|   `-- ReviewRequest.java
`-- service
|   |-- AuthorService.java
|   `-- BookService.java
|   `-- PublisherService.java
|   `-- ReviewService.java

```



Entities are as follows
***

1. Author(id , name , surname)
2. Book(id , title , publicationDate , description)
3. Publisher(id , year , publisherName)
4. Review(id , reviewText , rating)
***