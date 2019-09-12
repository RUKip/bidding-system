# 2019_group_25_s2934833_s2756781_s3856046

# Bidding system
An auction system for various products. Using a 3-tier architecture.


## Installation


## Help

### Front-end

#### Running locally
To run your front-end locally run in your main Angular app folder: ``ng serve``
This will run your front-end on localhost:4200, auto updating if any changes are applied.
To only build once use: ``ng build``

### Back-end

### Databases

### Docker

#### Testing environment
The production environment should use image from the docker repo. However to build your local image for testing purposes you can use the command:
 `` docker build -t bidding-frontend -f Docker/Dockerfile . `` 
Short explanation:  -t is the name (tag), -f the location of the docker folder your creating an image from and the final '.' is your current build environment. 

To run the container simply use: `` docker run -p 80:80 bidding-frontend``
Here -p is the port (80 is the default)
