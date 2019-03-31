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

# DaCeMo Visualisation - Landing Page
A website that displays a navigable, query-able, and understandable graph representation of a Data-Centric Model (DaCeMo) of an Enterprise.

A complex domain made understandable to enterprise users from middle management, to IT, to CEOs, through varying layers of data abstraction. 

DaCeMo is an approach to modelling Enterprise as a set of OWL (Web Ontology Language) Ontologies. 

## SIGNED ACCEPTANCE CRITERIA RECORD
[DaCeMo Visualisation Delivery/Acceptance Plan] (https://docs.google.com/document/d/1SHdLQG55HGzwzkL80NOvoNet_rAlMJIhJWk7JNr0DqY/edit)


## ROLE AND ACTIVITYIES 
Task ID	|Task Name|	Task Details|Owners|	Deadline
---|---|---|---|---
1	|Setup Server|	Setup the  server so that other can coding on it|	Lifi|	15/3
2	|Display	|Display the Images on the website	|Yu|	16/3
3	|Test D3.js|	draw some simple tables using d3.js	|Wenrui|	16/3
4	|Personal development environment|	Make sure everyone setup the development enviroment.	|Tommy|	17/3
5	|Config Service|	Config server and make connection with frontend	|Lifu|	21/3
6	|Config Service|	Connects the frontend to the server via API	|Wenrui|	21/3
7	|Draggable|	Make the table draggable	|Yu|	23/3
8	|Joint adjustment|	Make sure the data stream of the backend and the frontend is valid	|Lifu, Wenrui|	24/3
9	|Queries|	Write more advanced queries (reasoning)	|Tommy|	24/3
10	|Data Format|	translate the data from queries to json file	|Min| 	28/3
11	|Build multipage website|	split landing page into index and graph visualisation page	|Wenrui|	29/3
12	|Refactor the structure of the frontend|	arrange the website resource into different folder, using css, js and images etc.	|Wenrui|29/3
13	|Responsive layout|	Update the webpage responsive when the size of window is changed	|Yu|	31/3

## QUANTITIVE PROGRESS
The procject progress recorded in Asana
（https://github.com/businessabstraction/dacemo/blob/master/Screenshot/ASANA.jpg）


## DELIVERING VALUE
### For Client
* Exploratory research into different ontology and graph presentation formats.
* Develop user-friendly, presentable Data Centric Modelling graphs. 
* A domain in which the information gathered by the modeller is traditionally explained to the end user in subsets (or not at all) is now clearly understandable to an untrained user. 
* With graphs that integrate Business and Technical knowledge, the Business/IT misalignment in traditional enterprise is narrowed. 


## DECISION MAKING PROCESS
Decision making log: (https://drive.google.com/drive/folders/1m4mbyVoh4ibqYACCxjURbvI8BEEMAGKK)

## FEEDBACK FROM CLIENT/TUTOR
Feedback folder: (https://drive.google.com/drive/folders/18DwHCNWPbn7vS9Wgz2ojq_kGW4YRXe8o)

## TEAM WORK
The team members are divided into two subteams to work on frontend and backend. 
However, these subteams will communicate regularly, as the format of data passed between these layers may change. 

## TECHNICAL ARTIFACTS

### SOURCE CODE
Frontend Code: (https://github.com/businessabstraction/dacemo/tree/master/src/main/java)
#### Sub-models are included:
 * css
 * js
 * html
Backend Code: (https://github.com/businessabstraction/dacemo/tree/master/src/main/webapp)
#### Sub-models are included:
 * Bean
 * DAO
 * Servlet
 * Stardog
### PROTOTYPE
The ScreenShot of the DaCeMo Visualisation Site

* The landing page of the DaCeMo Visualisation

![image](https://github.com/businessabstraction/dacemo/blob/master/Screenshot/2045255427.jpg)

* The D3 graph demo  
![image](https://github.com/businessabstraction/dacemo/blob/master/Screenshot/637981016.jpg)

### TEST DATA
The sample data for OWL (https://github.com/businessabstraction/dacemo/blob/master/owl/dacemo-core.owl)


## PROJECT SCHEDULE
### Iterations
* Iteration Zero
  * Build Tomcat server.
  * Website contains landing page, graph view page, can present Top-Level Concepts graphically.
* Iteration One
  * Build graphs of Top-Level Concepts (Nodes and Edges from Top Level Concepts). 

### Timeline
Week | Goal
--- | --- 
3 | Finalize roles, Implement and build server structure  
4 | Frontend must have the landing page, Open graph views
5 | Present top level concepts graphically (Nodes only)(Iteration Zero)
6 | Build graphs of top level concepts (Nodes and Edges from Top Level Concepts)
7 | Make graphs searchable and navigable 

Iterations/Timelines are subject to change. 

## CLIENT VISION
Present Data-Centric, Ontology-based, integrated, qualitative models of enterprise in a way that can be understood and navigated by untrained enterprise users. The model will contain:

* Explicitly modelled
  - Business Motivations
  - Business Processes
  - Business Capabilities
* Imported IT data
* Imported personnel data
* Imported Requirements, User Stories

## TEAM
Name | Role(s) 
--- | ---
Tommy Gatti - u6044453 | Project Manager, Backend/Triples Engineer
Min Liu - u6339307 | Supporting Project Manager, Backend/Business Logic Programmer
Lifu Zhao - u6534756 | Backend/Server Build Engineer
Yu Yang - u6412985 | Frontend/Presentation Logic Programmer
Wenrui Li - u6361099 | Frontend/D3.js Specialist
Taizhou Wang - u6273306 | Frontend/JS build & lifecycle

## GENERAL TOOLS

Communication: 
* [Slack](https://businessabstraction.slack.com/messages/CGMDE8ZCH/)
  * Day-to-day communication regarding development and issues that may arise.
  * People outside our team including our stakeholder, shadows, and anyone who is interested in our project can also reach us easily.
* Email
  * Meeting invitations, and communications that are especially important.
* Skype
  * Members that cannot make it to our weekly meetings can appear digitially. 
* Meetings
  * Weekly meetings (Sundays) to communicate with our client and present deliverables and issues. 

Documentation: [Google Drive](https://drive.google.com/open?id=1WjYbFm8Bo-LiOVIUo5V1L1T3OA2NqrcU)
* We use Google Drive to host our documentation

Management: [Asana](https://app.asana.com/0/inbox/1112380319431612?du=1112380319431612&invite=8a394c166f17d335c2ffbb8a719bc864)
* Assignment and management of tasks, deadlines, and task specification.

## DEVELOPING TOOLS
* [IntelliJ](https://www.jetbrains.com/idea/) (IDE)
* [Jena](https://jena.apache.org/index.html) (Open Source Semantic Web framework)
* [Maven](https://maven.apache.org) (build management tool)
* [D3](https://d3js.org) (graph presentation software)
* [Tomcat](https://tomcat.apache.org) (server)

## PROJECT ARTEFACTS
* Group Meeting Notes (https://drive.google.com/drive/folders/1_aV8nk1iTIEDQqXfpiLfJIt98wacqaNN)
* Tutorial Meeting Notes (https://drive.google.com/drive/folders/1EVf0rtFta_mMU0YTYTKv01-1qXnByU2B)
* Client Meeting Notes (https://drive.google.com/drive/folders/1itF2R-CSuwl4ndsbM9IxND6fQgra1L-S)

## KEY STAKEHOLDERS
### Alex Jornalev (Business Abstraction)
Alex is the principal consultant of Business Abstraction. He is a specialist in Semantic Data, 
Knowledge and Semantic Data Engineering, combining solid academic background 
with two decades in Enterprise IT. Highly experienced Architect and Visual Modelling mentor who trained dozens of respected Enterprise Architecture, Information Architecture and Application Architecture professionals.

## CLIENT EXPECTATIONS
* To deliver at least Iteration Zero.
* Honesty
* Communication Transparency
* Teamwork

## CONSTRAINTS
* Team members are inexperienced with Web application development process
* Some members are inexperienced with this domain.

## RISKS
* One of our team members is working remotely until 14th April (Severity: 2). 
  * Mitigation strategies include clear meeting minutes and documentation, as well frequent (multiple times a week) virtual meetings.
* Client is a consultant for projects - time between communications can be longer than anticipated (Severity: 2). 
  * Mitigation strategies are still being researched by the team.
* The demand is not clearly defined (Severity: 2).
  * Mitigation strategies include keeping contact with the client.
* The client's demand may change during development (Severity: 3).
  * Mitigation strategies could be to apply the agile development methods to reduce the influence of demand changes.

## NONDISCLOSURE AGREEMENTS
* The project is Open Source and is governed by AGPL3. 
* An NDA is at this stage not nessecary.

## COSTS
* The project is open source and the potential costs (such as evaluating StarDog (a propietry knowledge graph software) and eventual hosting on an AWS server) will be covered by the client. 

Signed:
