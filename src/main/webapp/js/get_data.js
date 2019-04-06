let getJson;
let linkss;


/*=========================parse node===========================*/
//get the node from json file
function updateNode() {

    const jsonObjects = JSON.parse(getJson);
    console.log(jsonObjects[0][0].label);
    linkss = new Array(jsonObjects[1].length);

    //parse the json to array
    for(let i =0; i<jsonObjects[1].length; i++){

        linkss[i] = {};
        linkss[i].target = jsonObjects[2][i].label;
        linkss[i].targerid = jsonObjects[2][i].id;
        linkss[i].source = jsonObjects[0][0].label;
        linkss[i].sourceid = jsonObjects[0][0].id;
        linkss[i].rela = jsonObjects[1][i].label;
        linkss[i].relaid = jsonObjects[1][i].id;
        linkss[i].type = "resolved";

    }

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

    //let params = "comment=" + "value";
    if(methodType === "GET"){
        xmlResruest.open("GET","/DaCeMo_war_exploded/Servlet/GraphServlet?"+params,true);
        xmlResruest.send();

    }else if(methodType === "POST"){
        xmlResruest.open("POST","/DaCeMo_war_exploded/Servlet/GraphServlet",true);
        xmlResruest.setRequestHeader("req","req");
        xmlResruest.send();

    }

}

/*============================receive and request=====================================*/
//update every time when have a request
function updateGraph() {
    //todo: this function is for diving, adding the node
}


//send the request to the server
function sendRequenst(node) {
    console.log(node.name);
    //todo: to transfer the node id to the server and return a json format
    return
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
        .style("fill", function (node) {
            return "#68BDF6";
        })
        .style('stroke', function (node) {
            return "#68AEDD";
        })
        .attr("r", 20)
        .on("click", function (node) {

            sendRequenst(node);
            edges_line.style("stroke-width", function (line) {

                console.log("click it");
                //todo leave this part waiting for tranfering avaliable

                if (line.source.name == node.name || line.target.name == node.name) {
                    return 4;
                } else {
                    return 0.5;
                }
            });
            //d3.select(this).style('stroke-width',2);
        })
        .call(force.drag);//

    circle.append("svg:title")
        .text(function(node) {
            return
        });

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
                    .text(function () {
                        return d.name;
                    });
            }
            //less than 4
            else if (d.name.length <= 4) {
                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', 2)
                    .text(function () {
                        return d.name;
                    });
            } else {
                const top = d.name.substring(0, 4);
                const bot = d.name.substring(4, d.name.length);
                d3.select(this).text(function () {
                    return '';
                });
                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', -7)
                    .text(function () {
                        return top;
                    });
                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', 10)
                    .text(function () {
                        return bot;
                    });
            }
        });


    function tick() {
        circle.attr("transform", transform1);
        text.attr("transform", transform2);
        edges_line.attr('d', function(d) {
            const path = 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y;
            return path;
        });
        edges_text.attr('transform',function(d,i){
            if (d.target.x<d.source.x){
                bbox = this.getBBox();
                rx = bbox.x+bbox.width/2;
                ry = bbox.y+bbox.height/2;
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
