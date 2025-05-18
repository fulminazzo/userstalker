**Pending proper documentation**

```yaml
POST /api/v1/userlogins # Stores a new user login
GET /api/v1/userlogins/top?count=<size> # Returns the names of the users with most accesses to the service. If size is specified, the list will be sliced
GET /api/v1/userlogins/month?count=<size> # Returns the names of the users with most accesses during the current month to the service. If size is specified, the list will be sliced
GET /api/v1/userlogins/newest?count=<size> # Returns the latest accesses information. If size is specified, the list will be sliced
GET /api/v1/userlogins/usernames # Gets all the names of the users who visited the service
GET /api/v1/userlogins/<username> # Gets the information of all the accesses of the given user
```