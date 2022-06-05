# nace
These URLs are active for the application

# GET

http://localhost:8081/api/orders/{naceID}

Returns data for an existing Nace entity

# POST

http://localhost:8081/api/putNaceDetails

This will allow the user to upload a file with Nace data to the system.


http://localhost:8081/api/order

Create a new Nace entity

The body would contain Nace data e.g.

{ "orderId": "99999", "level": 1, "code": "A", "parent": null, "description": "AGRICULTURE, FORESTRY AND FISHING", "itemIncludes": "This section includes the exploitation of vegetal and animal natural resources, comprising the activities of growing of crops, raising and breeding of animals, harvesting of timber and other plants, animals or animal products from a farm or their natural habitats.", "alsoIncludes": null, "rulings": null, "excludes": null, "refToISICRev4": "A" }

# Swagger

http://localhost:8081/swagger-ui/
