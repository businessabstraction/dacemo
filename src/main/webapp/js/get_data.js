let getJson;
let linkss;
let linkinit;

/*=========================parse node===========================*/
//get the node from json file
function updateNode() {

    const jsonObjects = JSON.parse(getJson);
    console.log(jsonObjects);
    console.log(jsonObjects["index0"]);
    console.log(JSONLength(jsonObjects));

    const indexchar = "index";

    linkss = new Array(jsonObjects.length);


    //parse the json to array
    for(let i =0; i<JSONLength(jsonObjects); i++){

        const name = indexchar + i;
        console.log(name);
        linkss[i] = {};
        console.log(jsonObjects[name]["s"].label);
        linkss[i].target = jsonObjects[name]["s"].label;
        linkss[i].targerid = jsonObjects[name]["s"].id;
        linkss[i].source = jsonObjects[name]["s"].label;
        linkss[i].sourceid = jsonObjects[name]["s"].id;
        linkss[i].rela = "";
        linkss[i].relaid = name;
        linkss[i].type = "resolved";
        console.log(linkss[i])
    }

    //linkss = new Array(JSONLength(jsonObjects));


    // //parse the json to array
    // var object = "object";
    // var predicate = "predicate";
    // var subject = "subject";
    //
    //
    // for(var i =0; i<JSONLength(jsonObjects);i++){
    //
    //     var name = indexchar + i;
    //     console.log(name);
    //
    //     linkss[i] = new Object();
    //     console.log(jsonObjects[name][object].label)
    //
    //     linkss[i].target = jsonObjects[name][object].label;
    //     linkss[i].targerid = jsonObjects[name][object].id;
    //     linkss[i].source = jsonObjects[name][subject].label;
    //     linkss[i].sourceid = jsonObjects[name][subject].id;
    //
    //     linkss[i].rela = jsonObjects[name][predicate].label;
    //     linkss[i].relaid = jsonObjects[name][predicate].id;
    //     linkss[i].type = "resolved";
    //
    //
    //     console.log(linkss[i]);
    //
    // }


}

/**
 * @return {number}
 */
function JSONLength(obj) {
    let size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
}


function updateAdditionalNode(){
    console.log("updateAddtional");
    console.log(linkss);

    const previousLinks = linkss;

    const jsonObjects = JSON.parse(getJson);
    console.log(jsonObjects);
    console.log(jsonObjects["index0"]);
    console.log(JSONLength(jsonObjects));

    const indexchar = "index";


    //parse the json to array
    const linkadded = new Array(JSONLength(jsonObjects) + previousLinks.length);


    //parse the json to array
    const object = "object";
    const predicate = "predicate";
    const subject = "subject";

    for(let l = 0; l < previousLinks.length; l++){
        linkadded[l] = {};
        linkadded[l] = previousLinks[l];
    }


    for(let i = previousLinks.length; i<JSONLength(jsonObjects) + previousLinks.length; i++){

        const name = indexchar + (i - previousLinks.length);
        console.log(name);

        linkadded[i] = {};
        console.log(jsonObjects[name][object].label);

        linkadded[i].target = jsonObjects[name][object].label;
        linkadded[i].targerid = jsonObjects[name][object].id;
        linkadded[i].source = jsonObjects[name][subject].label;
        linkadded[i].sourceid = jsonObjects[name][subject].id;

        linkadded[i].rela = jsonObjects[name][predicate].label;
        linkadded[i].relaid = jsonObjects[name][predicate].id;
        linkadded[i].type = "resolved";


        console.log(linkadded[i]);

    }



    return linkadded;
}

/*=========================calling server=============================*/

function callServer(methodType) {
    let xmlResruest;

    if(window.XMLHttpRequest){
        xmlResruest = new XMLHttpRequest();
    }else if(window.ActiveXObject){
        xmlResruest = new ActiveXObject("MICROSOFT.XMLHTTP");
    }

    xmlResruest.onreadystatechange = function(){
        if(xmlResruest.readyState === 4 && xmlResruest.status === 200){
            getJson = xmlResruest.responseText;
            updateNode();
            buildGraph('d3c','#d3c',linkss);
        }

    };

    let params = "comment=" + "value";

    const param = "nodename=https:/www./docemo.org/owl/examples/iteration-0/MoM";
    if(methodType === "GET"){
        xmlResruest.open("GET","/DaCeMo_war_exploded/Servlet/GraphServlet?"+params,true);
        xmlResruest.send();

    }else if(methodType === "POST"){
        //xmlResruest.open("POST","/DaCeMo_war_exploded/Servlet/NodeExpandServlet?"+param,true);
        xmlResruest.open("POST","/DaCeMo_war_exploded/Servlet/GraphServlet",true);
        xmlResruest.setRequestHeader("req","req");
        xmlResruest.send(null);
    }

}

/*============================receive and request=====================================*/
//update every time when have a request



//send the request to the server
function sendRequenst(node) {
    console.log(node.name);
    //todo: to transfer the node id to the server and return a json format

    const param = "nodename=https:/www./docemo.org/owl/examples/iteration-0/" + node.name;

    let xmlResruest;

    if(window.XMLHttpRequest){
        xmlResruest = new XMLHttpRequest();
    }else if(window.ActiveXObject){
        xmlResruest = new ActiveXObject("MICROSOFT.XMLHTTP");
    }

    xmlResruest.onreadystatechange = function(){
        if(xmlResruest.readyState === 4 && xmlResruest.status === 200){
            getJson = xmlResruest.responseText;

            console.log(getJson);
            d3.selectAll("svg").remove();
            buildGraph('d3c','#d3c',updateAdditionalNode());
        }

    };

    //xmlResruest.open("POST","/DaCeMo_war_exploded/Servlet/NodeExpandServlet?"+param,true);
    xmlResruest.open("POST","/DaCeMo_war_exploded/Servlet/NodeExpandServlet?"+param,true);
    xmlResruest.setRequestHeader("req","req");
    xmlResruest.send(null);

    return node.id;
}


/*============================fake data=================================*/
/*
*  just for testing
* */
const linksss = [
    {target: "mammal", source: "dog", strength: 0.7, rela: "xxx", type: "resolved"},
    {target: "mammal", source: "cat", strength: 0.7, rela: "xxx", type: "resolved"},
    {target: "mammal", source: "fox", strength: 0.7, rela: "xxx", type: "resolved"},
    {target: "mammal", source: "elk", strength: 0.7, rela: "xxx", type: "resolved"},
    {target: "insect", source: "ant", strength: 0.7, rela: "xxx", type: "resolved"},
    {target: "insect", source: "bee", strength: 0.7, rela: "xxx", type: "resolved"},
    {target: "fish", source: "carp", strength: 0.7, rela: "xxx", type: "resolved"},
    {target: "fish", source: "pike", strength: 0.7, rela: "xxx", type: "resolved"},
    {target: "cat", source: "elk", strength: 0.1, rela: "xxx", type: "resolved"},
    {target: "carp", source: "ant", strength: 0.1, rela: "xxx", type: "resolved"},
    {target: "elk", source: "bee", strength: 0.1, rela: "xxx", type: "resolved"},
    {target: "dog", source: "cat", strength: 0.1, rela: "xxx", type: "resolved"},
    {target: "fox", source: "ant", strength: 0.1, rela: "xxx", type: "resolved"},
    {target: "pike", source: "cat", strength: 0.1, rela: "xxx", type: "resolved"}
];


const extralinks = [
    {target: "mammal", source: "dog", strength: 0.7, rela: "xxx", type: "resolved"},
    {target: "mammal", source: "cat", strength: 0.7, rela: "xxx", type: "resolved"},
    {target: "mammal", source: "fox", strength: 0.7, rela: "xxx", type: "resolved"},
    {target: "mammal", source: "elk", strength: 0.7, rela: "xxx", type: "resolved"}

];


/*===========================parameters for currant node displaying==============================*/
//store the links
const links = [];
//store the nodes
let nodes = {};


/*====================================drawing graph========================================*/
//parse the node and link into node and links
function processLink(linkss) {

    for (let i = 0; i < linkss.length; i++) {
        links[i] = {
            source: linkss[i].source,
            target: linkss[i].target,
            rela:linkss[i].rela
        };
    }
    nodes = {};
    links.forEach(function(link) {
        link.source = nodes[link.source] || (nodes[link.source] = {name: link.source});
        link.target = nodes[link.target] || (nodes[link.target] = {name: link.target});
    });
}




//build the graph, draw the existed request nodes from server
function buildGraph(graphics,graphicsid,linkss)
{

    processLink(linkss);

    const div = document.getElementById(graphics);
    const height = div.clientHeight;
    const width = div.clientWidth;
    let curPos_x, curPos_y, mousePos_x, mousePos_y;
    let isMouseDown = false;
    const oldScale = 1;
    let viewBox_x = 0, viewBox_y = 0;

    const force = d3.layout.force()
        .nodes(d3.values(nodes))//set array of nodes
        .links(links)
        .size([width, height])
        .linkDistance(180)
        .charge(-1500)
        .on("tick", tick)
        .start();


    const drag = force.drag()
        .on("dragstart", dragstart)
        .on("dragend", dragend);


    const rect = document.getElementById("d3-container").getBoundingClientRect();
    rect.height = 1030;

    console.log(rect.width);
    console.log(rect.height);
    console.log(this.width);
    console.log(this.height);
    //define the
    const svg = d3.select(graphicsid)
        .append('svg')
        .attr("preserveAspectRatio", "xMidYMid meet")
        //.attr("viewBox",rect.x + " " +rect.x +" "+ rect.width +" "+ 500)
        .attr("viewBox", "130 -250 600 600");

    svg.on("mousedown", function () {
        if (d3.event.defaultPrevented) {
            return;
        }
        isMouseDown = true;
        mousePos_x = d3.mouse(this)[0];
        mousePos_y = d3.mouse(this)[1];
    });

    svg.on("mouseup", function () {
        if (d3.event.defaultPrevented) {
            return;
        }
        isMouseDown = false;
    });

    svg.on("mousemove", function () {
        if (d3.event.defaultPrevented) {
            return;
        }
        curPos_x = d3.mouse(this)[0];
        curPos_y = d3.mouse(this)[1];
        if (isMouseDown) {
            viewBox_x = viewBox_x - d3.mouse(this)[0] + mousePos_x;
            viewBox_y = viewBox_y - d3.mouse(this)[1] + mousePos_y;
            //svg.attr("viewBox", viewBox_x + " " + viewBox_y + " " + width / oldScale + " " + height / oldScale);
        }
    });


    const marker =
        svg.append("marker")
        //.attr("id", function(d) { return d; })
            .attr("id", "resolved")
            //.attr("markerUnits","strokeWidth")
            .attr("markerUnits", "userSpaceOnUse")
            .attr("viewBox", "0 -5 10 10")
            .attr("refX", 38)
            .attr("refY", -1)
            .attr("markerWidth", 10)
            .attr("markerHeight", 10)
            .attr("orient", "auto")
            .attr("stroke-width", 2)
            .append("path")
            .attr("d", "M0,-5L10,0L0,5")
            .attr('fill', '#aaa');

    //set link
    const edges_line = svg.selectAll(".edgepath")
        .data(force.links())
        .enter()
        .append("path")
        .attr({
            'd': function (d) {
                return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y
            },
            'class': 'edgepath',
            'id': function (d, i) {
                return 'edgepath' + i;
            }
        })
        .style("stroke", function (d) {
            return "#BBB";
        })
        .style("pointer-events", "none")
        .style("stroke-width", 0.5)//storke of lines
        .attr("marker-end", "url(#resolved)");//arrow

    const edges_text = svg.append("g").selectAll(".edgelabel")
        .data(force.links())
        .enter()
        .append("text")
        .style("pointer-events", "none")
        //.attr("class","linetext")
        .attr({
            'class': 'edgelabel',
            'id': function (d, i) {
                return 'edgepath' + i;
            },
            'dx': 80,
            'dy': 0
            //'font-size':10,
            //'fill':'#aaa'
        });

    //set text on link
    edges_text.append('textPath')
        .attr('xlink:href',function(d,i) {return '#edgepath'+i})
        .style("pointer-events", "none")
        .text(function(d){return d.rela;});

    //draw node
    const circle = svg.append("g").selectAll("circle")
        .data(force.nodes())
        .enter().append("circle")
        .style("fill", () => "#68BDF6")
        .style('stroke', () => "#68AEDD")
        .attr("r", 20)
        .on('contextmenu', d3.contextMenu(menu))
        .on("click", function (node) {
            console.log("On left click, node is: " + node.name);
            sendRequenst(node);
            edges_line.style("stroke-width", line => {
                if (line.source.name === node.name || line.target.name === node.name) {
                    return 4;
                } else {
                    return 0.5;
                }
            });
        })
        .call(force.drag);

    circle.append("svg:title")
        .text(node => {});

    const text = svg.append("g").selectAll("text")
        .data(force.nodes())
        .enter()
        .append("text")
        .attr("dy", ".35em")
        .attr("text-anchor", "middle")
        .style('fill', function (node) {
            return "#000";
        }).attr('x', function (d) {
            // console.log(d.name+"---"+ d.name.length);
            const re_en = /[a-zA-Z]+/g;
            //if english
            if (d.name.match(re_en)) {
                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', 2)
                    .text(() => d.name);
            }
            //less than 4
            else if (d.name.length <= 4) {
                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', 2)
                    .text(() => d.name);
            } else {
                const top = d.name.substring(0, 4);
                const bot = d.name.substring(4, d.name.length);
                d3.select(this).text(() => '');
                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', -7)
                    .text(() => top);
                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', 10)
                    .text(() => bot);
            }
        });
    d3.select('#saveButton').on('click', function(){
        console.log("Print button clicked!");
        const svgString = getSVGString(d3.select('svg').node());
        svgString2Image( svgString, div.clientWidth, div.clientHeight, 'png', save ); // passes Blob and filesize String to the callback

        function save( dataBlob, filesize ){
            saveAs( dataBlob, 'D3 Graph.png' ); // FileSaver.js function
        }
    });

    function tick() {
        circle.attr("transform", transform1);
        text.attr("transform", transform2);
        edges_line.attr('d', function(d) {
            return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y;
        });
        edges_text.attr('transform',function(d,i){
            if (d.target.x<d.source.x){
                let bbox = this.getBBox();
                let rx = bbox.x+bbox.width/2;
                let ry = bbox.y+bbox.height/2;
                return 'rotate(180 '+rx+' '+ry+')';
            }
            else {
                return 'rotate(0)';
            }
        });
    }


    function linkArc(d) {
        return 'M '+d.source.x+' '+d.source.y+' L '+ d.target.x +' '+d.target.y
    }
    function dragstart(d) {
        d3.event.sourceEvent.stopPropagation();
        d3.select(this).classed("fixed", d.fixed = true);
    }
    function dragend(d) {
        d3.event.sourceEvent.stopPropagation();
        d3.select(this).classed("fixed", d.fixed = true);
    }

    function transform1(d) {
        return "translate(" + d.x + "," + d.y + ")";
    }
    function transform2(d) {
        return "translate(" + (d.x) + "," + d.y + ")";
    }
}

/*==================================execute the whole script=======================================*/

// Menu Object
d3.contextMenu = function (menu, openCallback) {
    // create the div element that will hold the context menu
    d3.selectAll('.d3-context-menu').data([1])
        .enter()
        .append('div')
        .attr('class', 'd3-context-menu');

    // close menu
    d3.select('body').on('click.d3-context-menu', () => {
        d3.select('.d3-context-menu').style('display', 'none');
    });

    // this gets executed when a contextmenu event occurs
    return function(data, index) {
        const elm = this;

        d3.selectAll('.d3-context-menu').html('');
        const list = d3.selectAll('.d3-context-menu').append('ul');
        list.selectAll('li').data(menu).enter()
            .append('li')
            .html(function(d) {
                return d.title;
            })
            .on('click', function(d, i) {
                d.action(elm, data, index);
                d3.select('.d3-context-menu').style('display', 'none');
            });

        // the openCallback allows an action to fire before the menu is displayed
        // an example usage would be closing a tooltip
        if (openCallback) openCallback(data, index);

        // display context menu
        d3.select('.d3-context-menu')
            .style('left', (d3.event.pageX - 2) + 'px')
            .style('top', (d3.event.pageY - 2) + 'px')
            .style('display', 'block');

        d3.event.preventDefault();
    };
};

function sendDive(node) {
    console.log(node.name);
    //todo: to transfer the node id to the server and return a json format

    const param = "nodename=https:/www./docemo.org/owl/examples/iteration-0/" + node.name;

    let xmlResruest;

    if(window.XMLHttpRequest){
        xmlResruest = new XMLHttpRequest();
    }else if(window.ActiveXObject){
        xmlResruest = new ActiveXObject("MICROSOFT.XMLHTTP");
    }

    xmlResruest.onreadystatechange = function(){
        if(xmlResruest.readyState === 4 && xmlResruest.status === 200){
            getJson = xmlResruest.responseText;

            console.log("In sendDive got " + getJson);
            d3.selectAll("svg").remove();
            buildGraph('d3c','#d3c',addDiveNode());
        }

    };

    xmlResruest.open("POST","/DaCeMo_war_exploded/Servlet/NodeExpandServlet?"+param,true);
    xmlResruest.setRequestHeader("req","req");
    xmlResruest.send(null);

    return node.id;
}

function addDiveNode(){

    console.log("addDiveNode");
    console.log(linkss);


    const indexchar = "index";

    //parse the json to array
    const linkadded = new Array(JSONLength(jsonObjects));


    //parse the json to array
    const object = "object";
    const predicate = "predicate";
    const subject = "subject";



    for(let i = 0; i<JSONLength(jsonObjects); i++) {

        const name = indexchar + i;
        console.log(name);

        linkadded[i] = {};
        console.log(jsonObjects[name][object].label);

        linkadded[i].target = jsonObjects[name][object].label;
        linkadded[i].targerid = jsonObjects[name][object].id;
        linkadded[i].source = jsonObjects[name][subject].label;
        linkadded[i].sourceid = jsonObjects[name][subject].id;

        linkadded[i].rela = jsonObjects[name][predicate].label;
        linkadded[i].relaid = jsonObjects[name][predicate].id;
        linkadded[i].type = "resolved";

        console.log(linkadded[i]);
    }
    return linkadded;
}


// Define the Menu
var menu = [
    {
        title: 'Dive in',
        action: function(elm, d) {
            console.log('Clicked \'Dive in\'');
            console.log('The data for this circle is: ' + d.name);
            sendDive(d);
        },
        disabled: false // optional, defaults to false
    },
    {
        title: 'Add',
        action: function(elm, d) {
            console.log('Clicked \'Add\'!');
            console.log('The data for this circle is: ' + d);

        }
    },
    {
        title: 'Delete',
        action: function(elm, d) {
            console.log('Clicked \'Delete\'!');
            console.log('The data for this circle is: ' + d);
        }
    }
];
