Science Gateway Project (Fall 2016)
==============================
  
Team Aviato -
==============================
 * Amol Bhagwat (amolrbhagwat) 
 * Laxmi Bhavani Malkareddy (LaxmiBhavaniM)
 * Prajna Cheryl Peters (pcpeters)
 * Sneha Tilak (tilaks26)

Milestone 1 -
==============================

Build Status  : [![Build Status](https://travis-ci.org/airavata-courses/TeamAviato.svg?)](https://travis-ci.org/airavata-courses/TeamAviato)

CI/CD Instructions -
==============================

* Each one of the microservices (Registry, Data Ingestor, Storm Detector, Storm Clustering, Forecast Trigger, Run Weather Forecast) is created in a seperate branch under master in our repository (TeamAviato - https://github.com/airavata-courses/TeamAviato). 
* We have used Travis CI to continuously integrate and Amazon AWS to continuously deploy the changes made to the system. 
* Each feature branch has a .travis.yml file and appspec.yml file which provides build and deploy instructions.
* Each feature branch has a .travis.yml and appspec.yml file which provide build instructions along with deploy instructions.
* The artifacts will be uploaded to the S3 bucket and deployed to the EC2 instance (both of which are defined in .travis.yml file). 
* Once deployed, one can test the services by opening a browser and entering the IP address of the EC2 instance along with the port number 3000. After login, by entering the required Station Name, Date and Time, the user can run the Weather Forecast. After all the services are executed, the output will then be generated on the UI. The user may also check his or her log history by clicking on the appropriate tab on the UI.
