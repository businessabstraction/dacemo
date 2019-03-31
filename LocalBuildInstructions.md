### Local Build Instructions
Requires:
* At least Maven 2
* Tomcat 7
* StarDog 6.x.x

First-time Instructions:
* For installling Stardog (our triplestore/database), instruction are found [here](https://www.stardog.com/docs/#_quick_start_guide).
* Once Stardog has been installed, we start the database server by going to `$STARDOG_INSTALL/bin/` and executing `stardog-admin server start` (Windows) / `./stardog-admin server start` (Mac/Linux). 
* Since this is a first-time setup, we must add the databases we are working on, by executing `stardog-admin db create -n iteration0 $PROJECT_LOCATION/owl/iteration-0.ttl` (Windows) / `./stardog-admin db create -n iteration0 $PROJECT_LOCATION/owl/iteration-0.ttl` (Mac/Linux).
* When finished, don't forget to execute `stardog-admin server stop`(Windows)/`./stardog-admin server stop`(Mac/Linux).

* Once Tomcat is installed, add the following to `$TOMCAT_INSTALL/conf/tomcat-users.xml` file, inside the `<tomcat-users>...</tomcat-users>` tag:
```  
<role rolename="manager-script"/>
<user username="admin" password="admin" roles="manager-script" />
``` 

Running it all other times:
* In `$STARDOG_INSTALL/bin/` execute `stardog-admin server start`(Windows)/`./stardog-admin server start`(Mac/Linux).
* In `$TOMCAT_INSTALL/bin/` execute `startup.bat`(Windows)/`./startup`(Mac/Linux).
* In `$PROJECT_LOCATION` execute `mvn tomcat7:deploy`.
* In a web browser, navigate to `http://localhost:8080/DaCeMo_war_exploded`. 
* Once finished, don't forget to execute `shutdown.bat`/`./shutdown` in `$TOMCAT_INSTALL/bin/`, and `stardog-admin server stop`/`./stardog-admin server stop` in `$STARDOG_INSTALL/bin/` to close the server and triplestore server gracefully. 
