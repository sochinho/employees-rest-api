# employees-rest-api

Rest api to manage list of employees from fict company.

Service allows to:
1) Add a new employer to list - POST ([url:port]/employees/post -d "firstName":"First","lastName":"Last","email": "mail.com", "positionId": 1)
2) Get all employees from list - GET ([url:port]/employees/all)
3) Filter of employees list by first name, last name or email - GET url:post/search?parameters
    i.e. [url:port]/employees/search/?email=first.employee@mail.com&firstName=First
4) Delete employee with specific id - DELETE ([url:port]/employees/delete/{id})
5) Show all positions with number of employees - GET ([url:port]/positions/all)

Swagger documentation is available on [url:port]/swagger-ui.html 