# vehicle-management
Docker/file-compose, Mongo/MongoExpress, SpringBoot, Mockito

#Instructions
Commands below have to run in root directory of project. (.../.../vehicles-manager)
- After clonning repository try to create network for docker containers with particular name
    * command: docker network create mongonetwork
- Make the all services up in root directory
    * command: docker-compose up

#There are 3 services working
- api-service (http://localhost:8088)
- mongo-express(http://localhost:8081)
- mongo

#Endpoints of api-service
- GET: http://localhost:8088/api/v1/vehicles
- GET: http://localhost:8088/api/v1/vehicles/{id}
- POST: http://localhost:8088/api/v1/vehicles
- PUT: http://localhost:8088/api/v1/vehicles/{id}
- DELETE: http://localhost:8088/api/v1/vehicles/{id}

P.S: You can change Content-Type & Accept in Headers to application/xml or application/json for to 
get/post/put/delete xml or json formats.
Additionally, if you change id in request which is not exists you will faced client friendly 
errormessage like "Vehicle not found with id : worng_id"