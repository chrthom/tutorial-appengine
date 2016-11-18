# Tech Survey

### Dependencies to run it

* Java JDK 1.7 or newer
* Apache Maven version 3.1.0 or newer
* A Google account

### Run the app locally

```
mvn clean appengine:devserver
```

It will start the Cloud server instance, database server, services, message queue and all other services you want to use in the cloud locally within memory. Database persistence only lasts as long as you keep the server running.

### Access the app

You can access the app via http://localhost:8080

You can access the app's webservice explorer via http://localhost:8080/_ah/api/explorer

You can access the app's datastore admin via http://localhost:8080/_ah/admin

### Reload the project

```
mvn package
```

It will reload the project if you did any changes to the code as long as the server is still running (see above).

### Deploy the app to the cloud

```
mvn clean appengine:update
```

At the first deployment a browser window will appear to let you log in via your Google account. After this, the deployment will run through automatically. Please tell the others before starting a deployment to the cloud.

## What to look at?

### 1.) Mobile & responsive design

What you need to know:

* HTML
* CSS

Important Links:

* Components of the Ionic mobile framework: http://ionicframework.com/docs/components
* Native icons in the Ionic mobile framework: http://ionicons.com
* AngularJS MVC framework: https://angularjs.org

Relevant files and paths of the project structure:

* HTML templates: src/main/webapp/index.html
* HTML templates: src/main/webapp/templates/*.html
* CSS files: src/main/webapp/style/*

### 2.) Frontend MVC

What you need to know to work in this team:

* JavaScript

Important Links:

* AngularJS MVC framework: https://angularjs.org

Relevant files and paths of the project structure:

* JavaScript files: src/main/webapp/js/*.js

### 3.) Cloud service

What you need to know to work in this team:

* Java

Important Links:

* Walkthrough for REST services in the Google cloud: https://cloud.google.com/appengine/docs/java/endpoints/getstarted/backend/code_walkthrough
* Local webservice explorer: http://localhost:8080/_ah/api/explorer

Relevant files and paths of the project structure:

* REST API classes: src/main/java/de/oc/cloud/techsurvey/api/*.java
* Container classes for entities: src/main/java/de/oc/cloud/techsurvey/container/*.java

### 4.) NoSQL Datastore

What you need to know to work in this team:

* Java

Important Links:

* API howto for Google Datastore: https://cloud.google.com/appengine/docs/java/datastore
* Local datastore admin: http://localhost:8080/_ah/admin

Relevant files and paths of the project structure:

* DAO classes: src/main/java/de/oc/cloud/techsurvey/persistence/*.java
* DAO tests: src/test/java/de/oc/cloud/techsurvey/persistence/*.java
* Container classes for entities: src/main/java/de/oc/cloud/techsurvey/container/*.java
