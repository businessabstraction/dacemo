# DaCeMo Visualisation - Landing Page (Audit 2)
A website that displays a navigable, query-able, and understandable graph representation of a Data-Centric Model (DaCeMo) of an Enterprise.

A complex domain made understandable to enterprise users from middle management, to IT, to CEOs, through varying layers of data abstraction. 

DaCeMo is an approach to modelling Enterprise as a set of OWL (Web Ontology Language) Ontologies. 

## DOCUMENTATION
[DaCeMo Visualisation Delivery/Acceptance Plan](https://docs.google.com/document/d/1SHdLQG55HGzwzkL80NOvoNet_rAlMJIhJWk7JNr0DqY/edit)

[DaCeMo Visualisation Requirements](https://docs.google.com/spreadsheets/d/1ORTDyarfT0Tznf53bk4ienKKjSjThpoBEyUlCMrRNPA/edit#gid=0)
This file contains all the requirements from the client for this semester. We marked the tasks we have done. Further explanation of tasks is found in the delivery/acceptance plan. 

## ROLE AND ACTIVITYIES 
Task ID	|Task Name|	Task Details|Owners|	Deadline
---|---|---|---|---
1	|Setup Server|	Setup the Tomcat server so that other members can begin deploying code |	LifU|	15/3
2	|Display	|Display images on the website	|Yu|	16/3
3	|Test D3.js|	draw some simple graphs using d3.js	|Wenrui|	16/3
4	|Setup Dev Environment|	Make sure everyone has setup the development enviroment.	|Tommy|	17/3
5	|Config Server|	Configure server to make connection with frontend	|Lifu|	21/3
6	|Config Server|	Connects the frontend to the server via API	|Wenrui|	21/3
7	|Draggable|	Make the graph draggable, responsive to user input	|Yu|	23/3
8	|Joint adjustment|	Make sure the data stream of the backend and the frontend is valid	|Lifu, Wenrui|	24/3
9	|Queries|	Writing more advanced queries (Ex. Expand subnodes of a given node)	|Tommy|	24/3
10	|Data Format|	translate the data from queries to json file	|Min| 	28/3
11	|Build multipage website|	split landing page into index and graph visualisation page	|Wenrui|	29/3
12	|Refactor the structure of the frontend|	arrange the website resource into different folder, using css, js and images etc.	|Wenrui|29/3
13	|Responsive layout|	Update the webpage responsive when the size of window is changed	|Yu|	31/3

## QUANTITIVE PROGRESS
The project progress as recorded in Asana ![image](https://github.com/businessabstraction/dacemo/blob/master/Screenshot/asana.png)

## DELIVERING VALUE - INDIVIDUAL PROGRESS
#### Min Liu (Business Logic Programmer)
I have built a Node class and D3Object class for constructing the format of the JSON file we need for the frontend. Then with these classes, I am able to create nodes and d3objects based
 on the information from dataset. The JSON file requires id, group, label and level of the entity, therefore I add this variables to Node class. When the data is translated into JSON 
 format, I send it to server, frontend can easily retrieve and resolve it into a frontend usable format.
 #### Lifu Zhao (Backend Programmer)
 I have created a data object classes and servlet classes for handle the requets form the frontend. Those classes mainly deal with the basic requesting data transfering between backend and frontend. The servlet can get the requets and translate to SPARQL language, which will also communicate with the database. The connection between frontend and backend have been finished at this stage. I also fixed server configuration for application deployment.
 
 #### Yu Yang (Frontend Programmer)
The webpages are responsive now, which means it matches multi-platforms with different window sizes. In addition, the draggable plugin has been completed which can be added to the project when the images are ready. The progress is following the time schedule now.

 #### Tommy Gatti (Triplestore Engineer)
I have set up the triplestore server (Stardog) and can run predefined queries over a given ontology (such as getting all top-level concepts, and getting a nodes description). 

 #### Wenrui Li (D3 and Frontend Developer)
I have investigated the D3 force graph plugin, which is used to demostrate the ontology graph. I used javascript to implement a demo for presenting the data structure from the Stardog via Servlet. I also wrote code for processing the topological logic. I designed the style of webpage of the site by using CSS and HTML. which includes an index and a visuualisation page.

## DECISION MAKING PROCESS
Decision making log: (https://drive.google.com/drive/folders/1m4mbyVoh4ibqYACCxjURbvI8BEEMAGKK)

## FEEDBACK FROM CLIENT/TUTOR
Feedback folder: (https://drive.google.com/drive/folders/18DwHCNWPbn7vS9Wgz2ojq_kGW4YRXe8o)

## TEAM WORK
The team members are divided into two subteams to work on frontend and backend. 
However, these subteams will communicate regularly, as the format of data passed between these layers may change. 

## TECHNICAL ARTIFACTS
### SOURCE CODE
Frontend Code: (https://github.com/businessabstraction/dacemo/tree/master/src/main/webapp)
#### Sub-models are included:
 * css
 * js
 * html

Backend Code: (https://github.com/businessabstraction/dacemo/tree/master/src/main/java)
#### Sub-models are included:
 * Bean
 * DAO
 * Servlet
 * Stardog
 
### PROTOTYPE
The ScreenShot of the DaCeMo Visualisation Site

* The landing page of DaCeMo Visualisation

<div align=center><img src="https://github.com/businessabstraction/dacemo/blob/master/Screenshot/2045255427.jpg"/></div>

* The D3 graph demo  
<div align=center><img width = "400" height ="400"  src=https://github.com/businessabstraction/dacemo/blob/master/Screenshot/637981016.jpg/></div>

### TEST DATA
The sample data for OWL (https://github.com/businessabstraction/dacemo/blob/master/owl/iteration-0.ttl)


## PROJECT SCHEDULE
### Iterations
* Iteration 0 (Weeks 3, 4)
  * Build Tomcat server, connect it to Stardog.
  * Website contains landing page and graph view page.
  * Can present Top-Level Concepts as Nodes, graphically.
* Iteration 1 (Weeks 5, 6)
  * Nodes can handle named relationship links.
  * Nodes are clickable and will, depending on the click, expand the nodes children onto the graph, or create a new graph with just the selected node and it's children. 
* Iteration 2 (Weeks 7, 8)
  * Hovering over a Node will give it's description
  * Exporting/Printing the graph (MVP ends here)
  * Undo function that reverts a graph to it's previous state
  * Various levels of data abstraction depending on the experience of the Enterprise User. 
  * Graph structure can be driven by a paradigm (such as Use Case, Process model)
  * Nodes can have images associated with them (through properties of the ontology)

### Timeline
Week(s) | Goal
--- | --- 
3, 4 | Finalize roles, Iteration 0
5, 6 | Iteration 1
Bk1, Bk2 | Investigation into other open-source triplestores, semantic web frameworks, and Backlog tasks
7, 8 | Iteration 2
9 | Low-priority UI, Auxillary/Backlog tasks, Buffer Week
10, 11, 12 | System Testing, QA, UAT of website

Iterations/Timelines are subject to change. 

## TEAM
Name | Role(s) | Backup Roles(s)
--- | --- | --- |
Tommy Gatti - u6044453 | Project Manager, Backend/Triples Engineer | Tester
Min Liu - u6339307 | Supporting Project Manager, Backend/Business Logic Programmer | Tester, Frontend Developer
Lifu Zhao - u6534756 | Backend/Server Build Engineer | Tester, Docker
Yu Yang - u6412985 | Frontend/Presentation Logic Programmer | Backend Development
Wenrui Li - u6361099 | Frontend/D3.js Specialist | Backend Developer
Taizhou Wang - u6273306 | Frontend/JS build & lifecycle | Tester

Documentation: [Google Drive](https://drive.google.com/open?id=1WjYbFm8Bo-LiOVIUo5V1L1T3OA2NqrcU)
* We use Google Drive to host our documentation

## OTHER PROJECT ARTEFACTS
* Group Meeting Notes (https://drive.google.com/drive/folders/1_aV8nk1iTIEDQqXfpiLfJIt98wacqaNN)
* Tutorial Meeting Notes (https://drive.google.com/drive/folders/1EVf0rtFta_mMU0YTYTKv01-1qXnByU2B)
* Client Meeting Notes (https://drive.google.com/drive/folders/1itF2R-CSuwl4ndsbM9IxND6fQgra1L-S)
