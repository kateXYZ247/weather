# weather
swagger: '2.0'
info:
  description: This API allows user to get weather details
  version: 1.0.0
  title: Search Weather Detail API
  # put the contact info for your development or API team
  contact:
    email: starkatelyn247@gmail.com

  license:
    name: Apache 2.0
    url: http://www.apache.org/licenses/LICENSE-2.0.html

# tags are used for organizing operations
tags:
- name: developers
  description: Operations available to regular developers

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
