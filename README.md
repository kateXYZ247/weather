weather
swagger: '2.0'
info:
  description: This API allows user to get weather details
  version: 1.0.0
  title: Search Weather Detail API
  contact:
    email: starXXX@gmail.com
paths:
  /weather/search:
    get:
      description: |
        By passing in the list of country names, user can search for
        weather info of these countries
      produces:
      - application/json
      parameters:
      - in: query
        name: countryList
        description: a list of country names that the user is searching for
        required: true
        type: string
  
      responses:
        200:
          description: successful returned search 
          schema:
              type: array (of maps)
              items: 
                $ref: '#/definitions'
            
        400:
          description: bad input parameter

definitions:
  City:
    type: object
    required:
    - woeid
    properties:
      woeid:
        type: integer
  
# Added by API Auto Mocking Plugin
host: virtserver.swaggerhub.com
# basePath: /ipAddress:port/weather/search
schemes:
 - https
# Added by API Auto Mocking Plugin
basePath: /kazhang7/getDetails/1.0.0
