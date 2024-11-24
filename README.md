# KNITTER API

Aron, Elías, and Snæfríður proudly present the **KNITTER API**:  
A Java Spring REST API developed for the course *Hugbúnaðarverkefni 1* at the University of Iceland.

### ⚙️Prerequisites
To use the `create` or `edit` pattern features, the following environment variables must be set:
- `CLOUDINARY_CLOUD_NAME`
- `CLOUDINARY_API_KEY`
- `CLOUDINARY_API_SECRET`

You can set these variables in a `.env` file at the project root.

The server runs on **port 8081** by default.

---

## API Endpoints

| HTTP Method | Endpoint                           | Description                                          | Parameters/Headers                                                                                   |
|-------------|------------------------------------|------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| `GET`       | `/users/{id}`                     | Get a user by ID                                    | `id` (Path Variable)                                                                                 |
| `GET`       | `/users/username/{username}`      | Get a user by username                              | `username` (Path Variable)                                                                           |
| `GET`       | `/users/notifications`            | Get user notifications                              | `Authorization` (Header)                                                                             |
| `POST`      | `/users/notifications/{id}/accept`| Accept a notification                               | `id` (Path Variable), `Authorization` (Header)                                                       |
| `POST`      | `/users/notifications/{id}/decline`| Decline a notification                              | `id` (Path Variable), `Authorization` (Header)                                                       |
| `GET`       | `/users`                          | Get all users                                       | `username` (Query Param, Optional), Paging and Sorting (`Pageable`)                                   |
| `GET`       | `/users/likedPatterns`            | Get liked knitting patterns by user                 | `Authorization` (Header), `title`, `username`, `sortBy`, `direction` (Query Params), Paging (`Pageable`) |
| `GET`       | `/users/sharedPatterns`           | Get shared knitting patterns with user              | `Authorization` (Header), `title`, `username`, `sortBy`, `direction` (Query Params), Paging (`Pageable`) |
| `POST`      | `/users`                          | Create a new user                                   | Request Body (JSON: `User`)                                                                           |
| `DELETE`    | `/users`                          | Delete the authenticated user                       | `Authorization` (Header)                                                                             |
| `PATCH`     | `/users`                          | Update fields of the authenticated user             | `Authorization` (Header), Request Body (JSON: Fields to update like `username`, `password`)           |
| `GET`       | `/patterns/public`                | Get public patterns                                 | Query Params: `title`, `username`, `sortBy`, `direction`; Paging (`Pageable`)                         |
| `GET`       | `/patterns/private`               | Get private patterns of the authenticated user      | Header: `Authorization`; Query Params: `title`, `sortBy`, `direction`; Paging (`Pageable`)            |
| `GET`       | `/patterns/{id}`                  | Get a pattern by ID                                 | Path Variable: `id`; Header (optional): `Authorization`                                              |
| `POST`      | `/patterns`                       | Create a new pattern                                | Header: `Authorization`; Request Body: JSON (`KnittingPattern`)                                      |
| `POST`      | `/patterns/share/{id}`            | Share a pattern with another user                  | Path Variable: `id`; Header: `Authorization`; Request Body: JSON (`username`)                        |
| `POST`      | `/patterns/save/{id}`             | Save a pattern by ID for the authenticated user     | Path Variable: `id`; Header: `Authorization`                                                        |
| `POST`      | `/patterns/url`                   | Generate a pattern from an image URL               | Request Body: JSON (`url`, `width`, `numColors`)                                                     |
| `POST`      | `/patterns/file`                  | Generate a pattern from an uploaded file           | Form Data: File (`file`), `width`, `numColors`                                                       |
| `POST`      | `/patterns/like/{id}`             | Like or unlike a pattern                           | Path Variable: `id`; Header: `Authorization`                                                        |
| `PATCH`     | `/patterns/{id}`                  | Update specific fields of a pattern                | Path Variable: `id`; Request Body: JSON (`title`, `isPublic`, `colorCodes`, `patternMatrix`)          |
| `DELETE`    | `/patterns/{id}`                  | Delete a pattern                                   | Path Variable: `id`; Header: `Authorization`                                                        |
| `GET`       | `/profile`                        | Get the profile of the authenticated user          | Header: `Authorization` (Bearer token)                                                              |
| `POST`      | `/profile/uploadPicture`          | Upload a profile picture                           | Header: `Authorization` (Bearer token); Form Data: `file` (MultipartFile)                            |
| `POST`      | `/auth/login`                     | Authenticate and log in a user                     | Request Body: JSON (`username`, `password`)                                                          |

---

### 🔑Additional Notes:
- **Paging and Sorting**: Supported by many endpoints, use query parameters like `sortBy` and `direction` to customize responses.
- **Authorization**: Most endpoints require a Bearer token in the `Authorization` header.
- **File Uploads**: Use `MultipartFile` for file uploads in `/profile/uploadPicture` and `/patterns/file`.
