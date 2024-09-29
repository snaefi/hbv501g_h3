Aron, Elías og Snæfríður kynna með stolti
# PATTERN API

Java spring REST api for the class hugbúnaðarverkefni 1 at the University of Iceland. 

The following Endpoints have been implemented along with a example usage at the root '/'.

This program runs a host that is open at port 8081.

# API Refrence

# Users
- **GET /users**: Returns all users with support for filters with queries.

        href: '/users',
        method: ['GET'],
        filtering: {
            description: 'GET query filters',
            parameters: {
                id: {
                    description: 'Returns a specifc user based on id.'
                    href: '/users?id=1
                      }
                }
        }


# Patterns
- **GET /patterns**: Returns all patterns with support for filters with queries.

        href: '/patterns',
        method: ['GET'],
        filtering: {
            description: 'GET query filters',
            parameters: {
                id: {
                    description: 'Returns a specific pattern od id.'
                    href: '/patterns?id=1
                      }
                }
        }
