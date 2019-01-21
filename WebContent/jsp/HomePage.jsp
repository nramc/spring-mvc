<!doctype html>
<html>
<head>
  <title>Welcome</title>
  
  <link href="js/vis.min.css" rel="stylesheet" type="text/css" />
  <link href="js/vis-network.min.css" rel="stylesheet" type="text/css" />
  
  <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="js/vis.min.js"></script>
  <script type="text/javascript" src="js/vis-network.min.js"></script>
  
  <style type="text/css">
    .graph-panel {
      width: 600px;
      height: 400px;
      border: 1px solid lightgray;
    }
  </style>
  
  <script type="text/javascript">
  var nodes,resultNodes, edges,resultEdges, network,resultNetwork;
  function drawNetwork() {
	  var algorithType = $('#idGraph').val();
	  var graph = getNetwork(algorithType);
	  drawSourceGraph(graph);
	  
  }
  function changeAlgorithm() {
	  	var algorithm = $('#selectAlgm').val();
	  	var edgeOpt= {
			edges : {
				arrows : {
					to: {
						enabled : ( algorithm.toUpperCase().indexOf('BELLMANFORD') >= 0 ) ? true : false,
						type : "arrow"
					}
				}
			}
		};
	  network.setOptions(edgeOpt);
  }
  function getNetwork(graphID) {
	  var graph = {};
	  switch(graphID) {
	  	case "1":
			graph.nodes = [
				{id: "Node 1", label: 'Node 1'},
				{id: "Node 2", label: 'Node 2'},
				{id: "Node 3", label: 'Node 3'},
				{id: "Node 4", label: 'Node 4'},
				{id: "Node 5", label: 'Node 5'},
				{id: "Node 6", label: 'Node 6'},
				{id: "Node 7", label: 'Node 7'},
				{id: "Node 8", label: 'Node 8'}
			];
			graph.edges = [
				{from: "Node 1", to: "Node 3", label: "4"},
				{from: "Node 1", to: "Node 2", label: "5"},
				{from: "Node 2", to: "Node 4", label: "6"},
				{from: "Node 2", to: "Node 5", label: "7"},
				{from: "Node 3", to: "Node 6", label: "3"},
				{from: "Node 3", to: "Node 7", label: "6"},
				{from: "Node 5", to: "Node 7", label: "9"},
				{from: "Node 5", to: "Node 4", label: "5"},
				{from: "Node 6", to: "Node 7", label: "6"},
				{from: "Node 1", to: "Node 7", label: "2"},
				{from: "Node 8", to: "Node 5", label: "7"},
				{from: "Node 8", to: "Node 6", label: "8"}
			];
			graph.arrowType = "circle";
			break;
	  	case "2":
	  		graph.nodes = [
				{id: "Node 1", label: 'Node 1'},
				{id: "Node 2", label: 'Node 2'},
				{id: "Node 3", label: 'Node 3'},
				{id: "Node 4", label: 'Node 4'},
				{id: "Node 5", label: 'Node 5'}
			];
			graph.edges = [
				{from: "Node 1", to: "Node 2", label: "-1"},
				{from: "Node 1", to: "Node 3", label: "4"},
				{from: "Node 2", to: "Node 3", label: "3"},
				{from: "Node 2", to: "Node 4", label: "2"},
				{from: "Node 2", to: "Node 5", label: "3"},
				{from: "Node 4", to: "Node 3", label: "5"},
				{from: "Node 4", to: "Node 2", label: "1"},
				{from: "Node 5", to: "Node 4", label: "-3"}
			];
			graph.arrowType = "arrow";
	  		break;
	  		
	 	default:
		  graph = null;
	  }
	  
	  return graph;
  }
	function drawResultGraph(graph) {
		resultNodes = new vis.DataSet();
		if( graph.nodes ) {
			resultNodes.add(graph.nodes);
		}
		resultEdges = new vis.DataSet();
		if(graph.edges) {
			resultEdges.add(graph.edges);
		}
		// create a network
		var resultContainer = document.getElementById('resultNetwork');
		var resultData = {
			nodes: resultNodes,
			edges: resultEdges
		};
		var options = {
			configure: {
				enabled: false,
				filter: true,
				showButton: false
			},
			interaction:{
				dragNodes:false,
				dragView: true,
				hideEdgesOnDrag: true,
				hideNodesOnDrag: true,
				hover: true,
				hoverConnectedEdges: true,
				keyboard: {
					enabled: false,
					speed: {x: 10, y: 10, zoom: 0.02},
					bindToWindow: true
				},
				multiselect: false,
				navigationButtons: false,
				selectable: true,
				selectConnectedEdges: true,
				tooltipDelay: 300,
				zoomView: true
			},
			manipulation: {
				enabled: false,
				initiallyActive: false,
				controlNodeStyle:{
					// all node options are valid.
				}
			},
			edges:{
				arrows : {
					to: {
						//enabled : ((graph && graph.arrowType && graph.arrowType === 'arrow'  ) ? true : false),
						enabled : true,
						//type : (graph ? graph.arrowType : "circle")
						type : 'arrow'
					}
				},
				width: 2,
				color: {
				  color:'#008000',
				  highlight:'#008000',
				  hover: '#008000',
				  inherit: 'from',
				  opacity:1.0
				},
				font: {
				  color: '#000000',
				  size: 14, // px
				  face: 'arial',
				  background: 'none',
				  strokeWidth: 2, // px
				  strokeColor: '#ffffff',
				  align: 'horizontal',
				  multi: false,
				  vadjust: 0,
				  bold: {
					color: '#000000',
					size: 14, // px
					face: 'arial',
					vadjust: 0,
					mod: 'bold'
				  },
				  ital: {
					color: '#000000',
					size: 14, // px
					face: 'arial',
					vadjust: 0,
					mod: 'italic',
				  },
				  boldital: {
					color: '#000000',
					size: 14, // px
					face: 'arial',
					vadjust: 0,
					mod: 'bold italic'
				  },
				  mono: {
					color: '#000000',
					size: 15, // px
					face: 'courier new',
					vadjust: 2,
					mod: ''
				  }
				}
			}
		}
		resultNetwork = new vis.Network(resultContainer, resultData, options);
		resultNetwork.redraw();
	}
	function drawSourceGraph(graph) {
		nodes = new vis.DataSet();
		if( graph && graph.nodes ) {
			nodes.add( graph.nodes );
		}
		edges = new vis.DataSet();
		if( graph && graph.edges ) {
			edges.add( graph.edges );
		}
		var container = document.getElementById('sourceNetwork');
		var data = {
			nodes: nodes,
			edges: edges
		};
		var options = {
			edges: {
				arrows : {
					to: {
						enabled : ((graph && graph.arrowType && graph.arrowType === 'arrow'  ) ? true : false),
						type : (graph ? graph.arrowType : "circle")
					}
				}
			},
			configure: {
				enabled: false,
				filter: true,
				showButton: true
			},
			interaction:{
				dragNodes:true,
				dragView: true,
				hideEdgesOnDrag: false,
				hideNodesOnDrag: false,
				hover: true,
				hoverConnectedEdges: true,
				keyboard: {
					enabled: false,
					speed: {x: 10, y: 10, zoom: 0.02},
					bindToWindow: true
				},
				multiselect: false,
				navigationButtons: true,
				selectable: true,
				selectConnectedEdges: true,
				tooltipDelay: 300,
				zoomView: true
			},
			manipulation: {
				enabled: true,
				initiallyActive: true,
				addNode: function(nodeData,callback){
					console.log("Hello from addNode()...");
					
					nodeData.label = prompt("Please enter Name for new node: ", "New");
					nodeData.id = nodeData.label;
					console.log( JSON.stringify(nodeData, null, 4) );
					callback(nodeData);
					console.log( JSON.stringify(nodes.get(), null, 4) );
				},
				
				editNode: function(nodeData,callback){
					console.log("Hello from editNode()...");
					
					nodeData.label = prompt("Please enter Node Name : ", nodeData.label);
					console.log( JSON.stringify(nodeData, null, 4) );
					callback(nodeData);
					console.log( JSON.stringify(nodes.get(), null, 4) );
				},
				deleteNode: function(nodeData,callback){
					console.log("Hello from deleteNode()...");
					
					console.log( JSON.stringify(nodeData, null, 4) );
					callback(nodeData);
					console.log( JSON.stringify(nodes.get(), null, 4) );
				},
				addEdge: function(edgeData,callback){
					console.log("Hello from addEdge()...");
					
					edgeData.label = prompt("Please enter Distance for Edge : ", 1);
					console.log( JSON.stringify(edgeData, null, 4) );
					callback(edgeData);
					console.log( JSON.stringify(edges.get(), null, 4) );
				},
				editEdge: function(edgeData,callback){
					console.log("Hello from editEdge()...");
					
					edgeData.label = prompt("Please enter Distance for Edge : ", edgeData.label);
					console.log( JSON.stringify(edgeData, null, 4) );
					callback(edgeData);
					console.log( JSON.stringify(edges.get(), null, 4) );
				},
				deleteEdge: function(edgeData,callback){
					console.log("Hello from deleteEdge()...");
					
					console.log( JSON.stringify(edgeData, null, 4) );
					callback(edgeData);
					console.log( JSON.stringify(edges.get(), null, 4) );
				},
				controlNodeStyle:{
					// all node options are valid.
				}
			}
		}
		network = new vis.Network(container, data, options);
	}
	
	function calculateEffectivePath() {
		var inputJSON = {};
		inputJSON.nodes = nodes.get();
		inputJSON.edges = edges.get();
		inputJSON.algorithm = $('#selectAlgm').val();
		inputJSON.sourceNode = $('#txtSrcNode').val();
		
		alert( JSON.stringify(inputJSON) );
		
		$.ajax({
			type: "POST",
			dataType: "json",
			contentType : 'application/json; charset=utf-8',
			url: "calcEffectivePath.json",
			data: JSON.stringify(inputJSON),
			success: function(result) {
				alert( JSON.stringify(result) );
				
				$('#divResult').css('display','');
				$('#tabResult tbody').children().remove();
				$('#lblAlgorithm').html( result.algorithm );
				var listOfNodes = [];
				$.each(result.nodes,function(idx, objNode){
					var newNode = {};
					newNode.id = objNode.id;
					newNode.label = objNode.label;
					if( objNode.isSrcFlg === true ) {
						newNode.shape = 'box';
					}
					listOfNodes.push(newNode);
					$('#tabResult tbody').append(
	                    $('<tr>')
	                        .append($('<td>').append( result.srcNode ))
	                        .append($('<td>').append( objNode.label ))
	                        .append($('<td>').append( objNode.distFromSrc ))
	                        .append($('<td>').append( objNode.pathFromSrc ))
	                );
				});
				result.arrowType = ( result.algorithm.toUpperCase().indexOf('BELLMANFORD') >= 0 ) ? "arrow" : "circle";
				drawResultGraph(result);
				
			}
		});
		
	}
	
	function addNode(nodeID, nodeVal) {
		try {
			nodes.add({
				id: nodeID,
				label: nodeVal
			});
        }
		catch (err) {
			alert(err);
		}
	}
	function updateNode(nodeID, nodeVal) {
		
       try {
		nodes.update({
			id: nodeID,
			label: nodeVal
         });
      }
		catch (err) {
	    	alert(err);
	}
	}
	function removeNode(nodeID) {
		try {
			nodes.remove({id: nodeID});
		}
		catch (err) {
			alert(err);
		}
	}
	
	$(document).ready(function(){
		drawSourceGraph(null,null,null);
	});
	
	
  </script>
  
</head>
<body style="vertical-align: middle;font-family: Calibri,Arial,sans-serif;font-size: 14px;">

<h2>Welcome <b>${userName}</b></h2>
<hr />

<div style="width:100%;display:inline-block;">
	
	<div style="width: 48%;display:inline-block;">
		<div id="sourceNetwork" class="graph-panel"></div>
	</div>
	
	<div style="width: 45%;display:inline-block;vertical-align: top;">
		<div style="">
			<div>
				<br />
				<label>Saved Network</label>
				<select id="idGraph" style="width: 35%;" onchange="drawNetwork()">
					<option value="0">select</option>
					<option value="1"> 8 Node - Dijkstra's Network</option>
					<option value="2"> x Node - BellmanFord Algorithm</option>
				</select>
				<br />
				<br />
			</div>
			<div>
				<label>Algorithm : </label>
				<select id="selectAlgm" style="width: 35%;" onchange="changeAlgorithm()">
					<option value="Dijkstra">Dijkstra's algorithm</option>
					<option value="BellmanFord">BellmanFord Algorithm</option>
				</select>
			</div>
			<div>
				<br />
				<label>Source Node : </label>
				<input type="text" id="txtSrcNode" style="width:20%"/>
			</div>
		</div>
		<br />
		
		<div>
			<input type="button" onclick="calculateEffectivePath()" value="Calculate Effective PAth"/>
		</div>
	</div>
	
</div>
<hr />
<div id="divResult" style="display: none;">
	<center><b><label id="lblAlgorithm"></label> Algorithm Results</b></center>
	
	<hr />
	<div style="width:100%;display:inline-block;">
		
		<div style="width: 48%;display:inline-block;">
			<div id="resultNetwork" class="graph-panel"></div>
		</div>
		
		<div style="width: 45%;display:inline-block;vertical-align: top;">
			<label><h5>Effective Path Table</h5></label>
			<div>
				<table id="tabResult" style="width:100%;border:1px solid blue;">
					<thead style="background: #0000ff;color: #ffffff;font-weight: bold;">
						<tr>
							<th style="width:20%;"> Source Node </th>
							<th style="width:20%;"> Target Node </th>
							<th style="width:10%;"> Cost </th>
							<th style="width:35%;"> Path </th>
						</tr>
					</thead>
					<tbody style="">
					
					</tbody>
				
				</table>
			</div>
		</div>
		
	</div>
</div>

<hr />

</body>
</html>
