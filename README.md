Aron, Elías og Snæfríður kynna með stolti
# PATTERN API

Java spring REST api for the class Hugbúnaðarverkefni 1 at the University of Iceland. 

The following Endpoints have been implemented along with a example usage at the root '/'.

This program runs a host that is open at port 8081.

---

# API Reference

### Users Endpoints

| Endpoint          | Method | Description                                              |
|-------------------|--------|----------------------------------------------------------|
| `/users`          | GET    | Get a list of users or search by username.               |
| `/users/{id}`     | GET    | Get a specific user by ID.                               |
| `/users`          | POST   | Create a new user.                                       |
| `/users/{id}`     | PATCH  | Update a user by ID (patching specific fields).          |
| `/users/{id}`     | DELETE | Delete a user by ID.                                     |

### Patterns Endpoints

| Endpoint          | Method | Description                                              |
|-------------------|--------|----------------------------------------------------------|
| `/patterns`       | GET    | Get a list of patterns or search/filter by public status, title, or username. |
| `/patterns/{id}`  | GET    | Get a specific pattern by ID.                            |
| `/patterns`       | POST   | Create a new pattern.                                    |
| `/patterns/{id}`  | PATCH  | Update a pattern by ID (patching specific fields).       |
| `/patterns/{id}`  | DELETE | Delete a pattern by ID.                                  |

---
