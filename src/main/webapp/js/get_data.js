let getJson;
let linkss;
let tooltip = d3.select("body").append("div")
    .attr("class", "tooltip")
    .style("opacity", 0);

/*=========================parse node===========================*/
//get the node from json file
function updateNode() {
    const jsonObjects = JSON.parse(getJson);
    const indexchar = "index";

    linkss = new Array(jsonObjects.length);

    //parse the json to array
    const lengthJSON = JSONLength(jsonObjects);
    for(let i = 0; i < lengthJSON; i++){
        const name = indexchar + i;


        linkss[i] = {};
        linkss[i].target = jsonObjects[name]["s"].label;
        linkss[i].targetid = jsonObjects[name]["s"].id;
        linkss[i].source = jsonObjects[name]["s"].label;
        linkss[i].sourceid = jsonObjects[name]["s"].id;
        linkss[i].rela = "";
        linkss[i].relaid = name;
        linkss[i].type = "resolved";
        console.log(linkss[i])
    }
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

    const totalLength = JSONLength(jsonObjects) + previousLinks.length;
    for(let i = previousLinks.length; i < totalLength; i++){

        const name = indexchar + (i - previousLinks.length);
        console.log(name);

        linkadded[i] = {};
        console.log(jsonObjects[name][object].label);

        linkadded[i].target = jsonObjects[name][object].label;
        linkadded[i].targetid = jsonObjects[name][object].id;
        linkadded[i].source = jsonObjects[name][subject].label;
        linkadded[i].sourceid = jsonObjects[name][subject].id;

        linkadded[i].rela = jsonObjects[name][predicate].label;
        linkadded[i].relaid = jsonObjects[name][predicate].id;
        linkadded[i].type = "resolved";
    }

    linkss = linkadded;
    return linkadded;
}

/*=========================calling server=============================*/

function callServer(methodType) {
    let result;

    if(window.XMLHttpRequest){
        result = new XMLHttpRequest();
    }else if(window.ActiveXObject){
        result = new ActiveXObject("MICROSOFT.XMLHTTP");
    }

    result.onreadystatechange = function(){
        if(result.readyState === 4 && result.status === 200){
            getJson = result.responseText;
            updateNode();
            buildGraph('d3c','#d3c',linkss);
        }

    };

    let params = "comment=" + "value";
    if(methodType === "GET"){
        result.open("GET","/DaCeMo_war_exploded/Servlet/GraphServlet?"+params,true);
        result.send();

    }else if(methodType === "POST"){
        result.open("POST","/DaCeMo_war_exploded/Servlet/GraphServlet",true);
        result.setRequestHeader("req","req");
        result.send(null);
    }

}

/*============================receive and request=====================================*/
//update every time when have a request



//send the request to the server
/**
 * Sends a request to the backend with the name of the clicked node.
 * @param node the Node to send to the frontend.
 * @param clickType the type of operation to perform on the existing nodes:
 *          "expand" : expands the subnodes of the given node
 *          "dive" : removes all nodes except the given node and then expands it
 */
function sendNodeRequest(node, clickType) {
    console.log(node.name);
    //todo: to transfer the node id to the server and return a json format

    // todo: not generic enough.
    const param = "nodename=https:/www./docemo.org/owl/examples/iteration-0/" + node.name;

    let result;

    if(window.XMLHttpRequest){
        result = new XMLHttpRequest();
    }else if(window.ActiveXObject){
        result = new ActiveXObject("MICROSOFT.XMLHTTP");
    }

    result.onreadystatechange = function(){
        if(result.readyState === 4 && result.status === 200){
            getJson = result.responseText;
            if (getJson !== "{}"){ // if there are no more subnodes to add, do nothing.
                d3.selectAll("svg").remove();
                if (clickType === "dive"){
                    //todo: refactor linkss to contain all details
                    linkss = [{target:node.name, source:node.name, type:"resolved"}];
                }
                buildGraph('d3c','#d3c',updateAdditionalNode());
            }
        }

    };

    result.open("POST","/DaCeMo_war_exploded/Servlet/NodeExpandServlet?"+param,true);
    result.setRequestHeader("req","req");
    result.send(null);
}

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


/**
 *
 * @param node
 * @returns {string}
 */
function sendDescriptionRequest(node) {
    let result;
    let param = "nodename=https:/www./docemo.org/owl/examples/iteration-0/" + node.name;

    if (window.XMLHttpRequest) result = new XMLHttpRequest();
    else if (window.ActiveXObject) result = new ActiveXObject("MICROSOFT.XMLHTTP");

    result.onreadystatechange = function() {
        if (result.readyState === 4 && result.status === 200 && result.responseText !== "") {
            tooltip.transition().duration(5).style("opacity", 1);
            tooltip.html(result.responseText)
                .style("left", (d3.event.pageX + 10) + "px")
                .style("top", (d3.event.pageY - 15) + "px");
        } else if (result.readyState === 4 && result.status === 200){
            tooltip.transition().duration(5).style("opacity", 0)
        }
    };

    result.open("POST","/DaCeMo_war_exploded/Servlet/NodeDescriptionServlet?"+param,true);
    result.setRequestHeader("req","req");
    result.send(null);
}

//build the graph, draw the existed request nodes from server
function buildGraph(graphics,graphicsid,linkss){
    processLink(linkss);

    const div = document.getElementById(graphics);
    const height = div.clientHeight;
    const width = div.clientWidth;
    let curPos_x, curPos_y, mousePos_x, mousePos_y;
    let isMouseDown = false;
    let viewBox_x = 0, viewBox_y = 0;

    const force = d3.layout.force()
        .nodes(d3.values(nodes))//set array of nodes
        .links(links)
        .size([width, height])
        .linkDistance(180)
        .charge(-1500)
        .on("tick", tick)
        .start();

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
        }
    });

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
        .style("stroke", "#BBB")
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
        .style("fill", "#68BDF6")
        .style('stroke', "#68AEDD")
        .attr("r", 20)
        .on('contextmenu', d3.contextMenu(menu))
        .on('mouseover', function (node){
            tooltip.transition()
                .duration(5)
                .style("opacity", 1);
            tooltip.html("...")
                .style("left", (d3.event.pageX + 10) + "px")
                .style("top", (d3.event.pageY - 15) + "px");

            sendDescriptionRequest(node);
        })
        .on('mouseout', function(){
            tooltip.transition()
                .style("opacity", 0);
        })
        .on("click", function (node) {
            console.log("On left click, node is: " + node.name);
            sendNodeRequest(node, "expand");
            edges_line.style("stroke-width", function (line) {
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
        .style('fill', "#000")
        .attr('x', function (d) {
            const re_en = /[a-zA-Z]+/g;
            //if english
            if (d.name.match(re_en)) {
                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', 2)
                    .text(d.name);
            }
            //less than 4
            else if (d.name.length <= 4) {
                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', 2)
                    .text(d.name);
            } else {
                const top = d.name.substring(0, 4);
                const bot = d.name.substring(4, d.name.length);
                d3.select(this).text(() => '');
                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', -7)
                    .text(top);
                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', 10)
                    .text(bot);
            }
        });
    d3.select('#saveButton').on('click', function(){
        console.log("Print button clicked!");
        const svgString = getSVGString(d3.select('svg').node());
        svgString2Image( svgString, div.clientWidth, div.clientHeight, 'png', save ); // passes Blob and filesize String to the callback

        function save(dataBlob){
            saveAs( dataBlob, 'D3 Graph.png' ); // FileSaver.js function
        }
    });

    function tick() {
        circle.attr("transform", transform1);
        text.attr("transform", transform2);
        edges_line.attr('d', function(d) {
            return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y;
        });
        edges_text.attr('transform',function(d){
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
            .on('click', d => {
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
// Define the Menu
const menu = [
    {
        title: 'Dive in',
        action: function(elm, d) {
            console.log('Clicked \'Dive in\'');
            console.log('The data for this circle is: ' + d.name);
            sendNodeRequest(d, "dive");
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
