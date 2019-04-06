let getJson;
let nodeArray;
let baseNodes;
let baseLinks;
//let nodes;
//let links;


/*=========================parse node===========================*/
function updateNode() {
    console.log("getstring: "+getJson);
    var jsonObjects = JSON.parse(getJson);
    console.log(jsonObjects);   // please check the console and you will get an idea about the JSON format
    baseNodes = new Array(jsonObjects.length);
    for (var i = 0; i < jsonObjects.length; i++){
        console.log(jsonObjects[i]);
        baseNodes[i] = {};
        baseNodes[i].id = jsonObjects[i].id;
        baseNodes[i].group = jsonObjects[i].group;
        baseNodes[i].label = jsonObjects[i].label;
        baseNodes[i].level = jsonObjects[i].level;
    }

    //baseLinks = new Array(baseNodes.length/2);

    // for(let i =0;i< baseLinks.length;i++){
    //     const link = {};
    //     link.target = baseNodes[i].id;
    //     link.source = baseNodes[i+1].id;
    //     link.strength = 0.1;
    //     baseLinks[i] = link;
    // }
    //nodes = [...baseNodes];
    //links = [...baseLinks]
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
            document.getElementById("myDiv").innerHTML = "button down";
            getJson = xmlResruest.responseText;
            document.getElementById("myDiv").innerHTML = getJson;
            console.log("I'm before updateNode()!")
            updateNode();
            console.log("I'm after updateNode()!")
            //updateSimulation();
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



/*============================fake data=================================*/
/*
*  just for testing
* */
var linkss = [
    { target: "mammal", source: "dog" , strength: 0.7, rela:"xxx" ,type: "resolved"},
    { target: "mammal", source: "cat" , strength: 0.7, rela:"xxx" ,type: "resolved"},
    { target: "mammal", source: "fox" , strength: 0.7, rela:"xxx" ,type: "resolved"},
    { target: "mammal", source: "elk" , strength: 0.7, rela:"xxx" ,type: "resolved"},
    { target: "insect", source: "ant" , strength: 0.7, rela:"xxx" ,type: "resolved"},
    { target: "insect", source: "bee" , strength: 0.7, rela:"xxx" ,type: "resolved"},
    { target: "fish"  , source: "carp", strength: 0.7, rela:"xxx" ,type: "resolved"},
    { target: "fish"  , source: "pike", strength: 0.7, rela:"xxx" ,type: "resolved"},
    { target: "cat"   , source: "elk" , strength: 0.1, rela:"xxx" ,type: "resolved"},
    { target: "carp"  , source: "ant" , strength: 0.1, rela:"xxx" ,type: "resolved"},
    { target: "elk"   , source: "bee" , strength: 0.1, rela:"xxx" ,type: "resolved"},
    { target: "dog"   , source: "cat" , strength: 0.1, rela:"xxx" ,type: "resolved"},
    { target: "fox"   , source: "ant" , strength: 0.1, rela:"xxx" ,type: "resolved"},
    { target: "pike"  , source: "cat" , strength: 0.1, rela:"xxx" ,type: "resolved"}
]

var extralinks = [
    { target: "mammal", source: "dog" , strength: 0.7, rela:"xxx" ,type: "resolved"},
    { target: "mammal", source: "cat" , strength: 0.7, rela:"xxx" ,type: "resolved"},
    { target: "mammal", source: "fox" , strength: 0.7, rela:"xxx" ,type: "resolved"},
    { target: "mammal", source: "elk" , strength: 0.7, rela:"xxx" ,type: "resolved"}

]



/*===========================parameters for currant node displaying===============================*/
//store the links
var links = [];
//store the nodes
var nodes = {};





/*====================================drawing graph========================================*/
function processLink(linkss) {

    for (var i = 0; i < linkss.length; i++) {
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


//build the graph, draw the existed request nodes from server
function buildGraph(graphics,graphicsid,linkss)
{

    processLink(linkss);

    var div = document.getElementById(graphics);
    var height = div.clientHeight;
    var width = div.clientWidth;
    var curPos_x, curPos_y, mousePos_x, mousePos_y;
    var isMouseDown=false, oldScale = 1;
    var viewBox_x = 0, viewBox_y = 0;
    var force = d3.layout.force()//layout 将json格式转化为力学图可用的格式
        .nodes(d3.values(nodes))//set array of nodes
        .links(links)//设定连线数组
        .size([width, height])//作用域的大小
        .linkDistance(120)//连接线长度
        .charge(-1500)//顶点的电荷数。该参数决定是排斥还是吸引，数值越小越互相排斥
        .on("tick", tick)//指时间间隔，隔一段时间刷新一次画面
        .start();//开始转换


    var drag = force.drag()
        .on("dragstart", dragstart)
        .on("dragend",dragend);


   //define the
    var svg = d3.select(graphicsid)
        .append('svg')
        .attr("preserveAspectRatio", "xMidYMid meet")
        .attr("viewBox", "0 0 1500 1500")


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
            svg.attr("viewBox", viewBox_x + " " + viewBox_y + " " + width / oldScale + " " + height / oldScale);
        }
    });



    //箭头
    var marker=
        svg.append("marker")
        //.attr("id", function(d) { return d; })
            .attr("id", "resolved")
            //.attr("markerUnits","strokeWidth")//设置为strokeWidth箭头会随着线的粗细发生变化
            .attr("markerUnits","userSpaceOnUse")
            .attr("viewBox", "0 -5 10 10")//坐标系的区域
            .attr("refX",38)//箭头坐标
            .attr("refY", -1)
            .attr("markerWidth", 10)//标识的大小
            .attr("markerHeight", 10)
            .attr("orient", "auto")//绘制方向，可设定为：auto（自动确认方向）和 角度值
            .attr("stroke-width",2)//箭头宽度
            .append("path")
            .attr("d", "M0,-5L10,0L0,5")//箭头的路径
            .attr('fill','#aaa');//箭头颜色

    //设置连接线
    var edges_line = svg.selectAll(".edgepath")
        .data(force.links())
        .enter()
        .append("path")
        .attr({
            'd': function(d) {return 'M '+d.source.x+' '+d.source.y+' L '+ d.target.x +' '+d.target.y},
            'class':'edgepath',
            'id':function(d,i) {return 'edgepath'+i;}})
        .style("stroke",function(d){
            return "#BBB";
        })
        .style("pointer-events", "none")
        .style("stroke-width",0.5)//线条粗细
        .attr("marker-end", "url(#resolved)" );//根据箭头标记的id号标记箭头

    var edges_text = svg.append("g").selectAll(".edgelabel")
        .data(force.links())
        .enter()
        .append("text")
        .style("pointer-events", "none")
        //.attr("class","linetext")
        .attr({  'class':'edgelabel',
            'id':function(d,i){return 'edgepath'+i;},
            'dx':80,
            'dy':0
            //'font-size':10,
            //'fill':'#aaa'
        });

    //设置线条上的文字
    edges_text.append('textPath')
        .attr('xlink:href',function(d,i) {return '#edgepath'+i})
        .style("pointer-events", "none")
        .text(function(d){return d.rela;});

    //圆圈
    var circle = svg.append("g").selectAll("circle")
        .data(force.nodes())//表示使用force.nodes数据
        .enter().append("circle")
        .style("fill",function(node){
            return "#68BDF6";
        })
        .style('stroke',function(node){
            return "#68AEDD";
        })
        .attr("r", 25)//设置圆圈半径
        .on("click",function(node){
            //单击时让连接线加粗

            sendRequenst(node);
            edges_line.style("stroke-width",function(line){

                console.log("click it");


                if(line.source.name==node.name || line.target.name==node.name){
                    return 4;
                }else{
                    return 0.5;
                }
            });
            //d3.select(this).style('stroke-width',2);
        })
        .call(force.drag);//

    //圆圈的提示文字
    circle.append("svg:title")
        .text(function(node) {
            return
        });

    var text = svg.append("g").selectAll("text")
        .data(force.nodes())
        //返回缺失元素的占位对象（placeholder），指向绑定的数据中比选定元素集多出的一部分元素。
        .enter()
        .append("text")
        .attr("dy", ".35em")
        .attr("text-anchor", "middle")//在圆圈中加上数据
        .style('fill',function(node){
            return "#FFFFFF";
        }).attr('x',function(d){
            // console.log(d.name+"---"+ d.name.length);
            var re_en = /[a-zA-Z]+/g;
            //如果是全英文，不换行
            if(d.name.match(re_en)){
                d3.select(this).append('tspan')
                    .attr('x',0)
                    .attr('y',2)
                    .text(function(){return d.name;});
            }
            //如果小于四个字符，不换行
            else if(d.name.length<=4){
                d3.select(this).append('tspan')
                    .attr('x',0)
                    .attr('y',2)
                    .text(function(){return d.name;});
            }else{
                var top=d.name.substring(0,4);
                var bot=d.name.substring(4,d.name.length);
                d3.select(this).text(function(){return '';});
                d3.select(this).append('tspan')
                    .attr('x',0)
                    .attr('y',-7)
                    .text(function(){return top;});
                d3.select(this).append('tspan')
                    .attr('x',0)
                    .attr('y',10)
                    .text(function(){return bot;});
            }
        });


    function tick() {
        circle.attr("transform", transform1);//圆圈
        text.attr("transform", transform2);//顶点文字
        edges_line.attr('d', function(d) {
            var path='M '+d.source.x+' '+d.source.y+' L '+ d.target.x +' '+d.target.y;
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

    //设置连接线的坐标,使用椭圆弧路径段双向编码
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
    //设置圆圈和文字的坐标
    function transform1(d) {
        return "translate(" + d.x + "," + d.y + ")";
    }
    function transform2(d) {
        return "translate(" + (d.x) + "," + d.y + ")";
    }
}


/*==================================execute the whole script=======================================*/
buildGraph('d3c','#d3c',linkss);