let getJson;
let linkss;
let tooltip = d3.select("body").append("div")
    .attr("class", "tooltip")
    .style("opacity", 0);
let expandedNodes = [];

/*=========================parse node===========================*/
/**
 * The function is used to parse the node when initialising the graph
 * @returns {returns a list of relationship between objects and subject}
 */
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
    }

    return linkss;
}

/**
 * get the numbers of objects in the json
 * @return {number}
 */
function JSONLength(obj) {
    let size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
}

/**
 * use to add extra nodes when expanding the graph
 * @returns the list that includes the added link information
 */
function updateAdditionalNode(){
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

        linkadded[i] = {};
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

/*============================receive and request=====================================*/
//update every time when have a request
//send the request to the server
/**
 * Sends a request to the backend with the name of the clicked node.
 * @param node the Node to send to the frontend.
 * @param clickType the type of operation to perform on the existing nodes:
 *          "expand" : expands the subnodes of the given node
 *          "dive" : removes all nodes except the given node and then expands it
 *          "init" : get the top-level concepts
 */
function sendNodeRequest(node, clickType) {
    let result;

    if(window.XMLHttpRequest){
        result = new XMLHttpRequest();
    }else if(window.ActiveXObject){
        result = new ActiveXObject("MICROSOFT.XMLHTTP");
    }

    result.onreadystatechange = function(){
        if(result.readyState === 4 && result.status === 200){
            getJson = result.responseText;

            if (clickType === "init"){
                buildGraph('d3c','#d3c', updateNode());

            } else if (clickType === "dive" && getJson !== "{}" ){ // if there are no more subnodes to add, do nothing.
                linkss = [{target:node.name, source:node.name, rela:"", type:"resolved"}]; //todo: refactor linkss to contain all details
                expandedNodes = [];
                links = [];
                d3.selectAll("svg").remove();
                buildGraph('d3c','#d3c', updateAdditionalNode());

            } else if (clickType === "expand" && getJson !== "{}"){
                d3.selectAll("svg").remove();
                buildGraph('d3c', '#d3c', updateAdditionalNode());
            }
        }
    };

    // if the function is initalizing the graph, there is no need to send a node parameter.
    if (clickType === "init") {
        result.open("POST", "/DaCeMo_war_exploded/servlet/GraphServlet", true);
    } else {
        const param = "nodename=https:/www./docemo.org/owl/examples/iteration-0/" + node.name; //todo: not generic enough.
        result.open("POST", "/DaCeMo_war_exploded/servlet/NodeExpandServlet?" + param, true);
    }
    result.setRequestHeader("req","req");
    result.send(null);
}

/*===========================parameters for currant node displaying==============================*/
//store the links
let links = [];
//store the nodes
let nodes = {};


/*====================================drawing graph========================================*/
/**
 * send the query name of node to the backend and update the lists.
 * @param node
 */
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
    let param = "nodename=https:/www./docemo.org/owl/examples/iteration-0/" + node.name; //todo: not generic enough.

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

    result.open("POST","/DaCeMo_war_exploded/servlet/NodeDescriptionServlet?"+param,true);
    result.setRequestHeader("req","req");
    result.send(null);
}

/**
 * build the graph, draw the existed request nodes from server
 * @param graphics
 * @param graphicsid
 * @param linkss
 */
function buildGraph(graphics,graphicsid,linkss){
    processLink(linkss);

    const div = document.getElementById(graphics);
    const height = div.clientHeight;
    const width = div.clientWidth;
    let curPos_x, curPos_y, mousePos_x, mousePos_y;
    let isMouseDown = false;
    let viewBox_x = 0, viewBox_y = 0;

    // force objects can be dragged and added links.
    const force = d3.layout.force()
        .nodes(d3.values(nodes))//set array of nodes
        .links(links)
        .size([width, height])
        .linkDistance(190)
        .charge(-1200)
        .on("tick", tick)
        .start();

    // define the shape of nodes and draw links
    const svg = d3.select(graphicsid)
        .append('svg')
        .attr("preserveAspectRatio", "xMidYMid meet")
        .attr("viewBox", "300 -300 600 600")
        .on("mousedown", function () {
            if (d3.event.defaultPrevented) {
                return;
            }
            isMouseDown = true;
            mousePos_x = d3.mouse(this)[0];
            mousePos_y = d3.mouse(this)[1]
        })
        .on("mouseup", function () {
            if (d3.event.defaultPrevented) {
                return;
            }
            isMouseDown = false;
        })
        .on("mousemove", function () {
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

    // Defines and adds the attributes of the arrow marker on each edge.
    svg.append("marker")
        .attr("id", "resolved")
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
        .attr('fill', '#5F5654');

    //set attributes of lines between nodes.
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
        .style("stroke", "#5F5654")
        .style("pointer-events", "none")
        .style("stroke-width", 0.5)//storke of lines
        .attr("marker-end", "url(#resolved)");//arrow

    // set attributes of text of lines between nodes.
    const edges_text = svg.append("g").selectAll(".edgelabel")
        .data(force.links())
        .enter()
        .append("text")
        .style("pointer-events", "none")
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
        .text(d => d.rela);

    // draw node and define it's attributes and mouse events.
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
            // if the node has already been expanded, don't send a request.
            if (!expandedNodes.includes(node.name)) {
                expandedNodes.push(node.name);
                sendNodeRequest(node, "expand");
            }

            edges_line.style("stroke-width", function (line) {
                if (line.source.name === node.name || line.target.name === node.name) return 4;
                else return 0.5;
            });
        })
        .call(force.drag);

    // set attributes of the text
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


    /**
     * get the position of the nodes and draw node with text.
     */
    function tick() {
        circle.attr("transform", transform);
        text.attr("transform", transform);
        edges_line.attr('d', function(d) {
            return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y;
        });
        //flip the text if the connection is inversed
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

    /**
     * get the coordiniation of the translate point
     * @param d
     * @returns {string}
     */
    function transform(d) {
        return "translate(" + d.x + "," + d.y + ")";
    }

}

/*==================================execute the whole script=======================================*/

// Menu Object
/**
 *
 * @param menu
 * @param openCallback
 * @returns {Function}
 */
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
            .html(d => d.title)
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

/**
 * Define the Menu
 * @type {*[]}
 */
const menu = [
    {
        title: 'Dive in',
        action: function(elm, d) {
            sendNodeRequest(d, "dive");
        },
        disabled: false // optional, defaults to false
    },
    {
        title: 'Add',
        action: function(elm, d) {}
    },
    {
        title: 'Delete',
        action: function(elm, d) {}
    }
];
