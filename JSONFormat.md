## JSON Format For JavaScript
### JSON Format Description

When user clicks the node on web page, frontend will automatically send a query to backend. Database will get information
of the clicked node. The node's information is converted into a JSONObject and sent to server as a String by using `jsonObject.toString()` .

### Format Structure
``` 
{
   “index0”: {
         “subject”: {
		“id”: ..,
		“group”: ..,
		“label”: ..,
		“level”: ..
		     }
         “predicate”: {
		“id”: ..,
		“group”: ..,
		“label”: ..,
		“level”: ..
		     }
         “object”: {
		“id”: ..,
		“group”: ..,
		“label”: ..,
		“level”: ..
		     }
    },
    “index1”: {
         “subject”: {
		“id”: ..,
		“group”: ..,
		“label”: ..,
		“level”: ..
		     }
         “predicate”: {
		“id”: ..,
		“group”: ..,
		“label”: ..,
		“level”: ..
		     }
         “object”: {
		“id”: ..,
		“group”: ..,
		“label”: ..,
		“level”: ..
		     }
    },
...
}
``` 
### Using JSON in Java
To retrieve the id of the first object:
``` 
String id = jsonObject.getJSONObject("index0").getJSONObject("object").getString("id");
``` 
To retrieve the level of the second predicate:
``` 
int level = jsonObject.getJSONObject("index1").getJSONObject("predicate").getInt("level");
``` 

### Using JSON in JavaScript
To retrieve the label of the first subject:
``` 
var jsonObject = JSON.parse(getJson);
var label = jsonObject.index0.subject.label
``` 
`getJson` is the JSON object which stored in server.

### Top Level Concept JSON Format Structure
``` 
{
   “index0”: {
         “s”: {
            “id”: ..,
            “group”: ..,
            “label”: ..,
            “level”: ..
            }
    },
    “index1”: {
         “s”: {
            “id”: ..,
            “group”: ..,
            “label”: ..,
            “level”: ..
            }
    },
...
}
``` 
### Using Top Level Concept JSON in JavaScript
To retrieve the label of the first node:
``` 
var jsonObject = JSON.parse(getJson);
var label = jsonObject.index0.s.label
``` 
`getJson` is the JSON object which stored in server.