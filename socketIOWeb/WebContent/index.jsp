<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>netty-socketio-case</title>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<script src="socket.io.js"></script>
    <script src="jquery-1.7.2.min.js"></script>
	<script>
		$(function(){
		   //pushUrl = "http://"+ip+":"+port;
			var socket =  io.connect("http://192.168.16.211:6677");
			 socket.on("pushpoint", function(data){
			 	//监听pushpoint事件
				/* $('#x').text(data.x);
				$('#y').text(data.y); */
				console.log("接收到数据 :"+data);
			});
			    socket.on('connect', function() {
					console.log("连接成功");		
					//首次进入页面连接成功后从服务器端主动拉取一次数据。			
					//socket.emit('getmsg', "事件名称");					
				});
				socket.on("connecting", function() {
					console.log("正在连接");
				});
				socket.on('disconnect', function() {
					console.log("断开连接");
				});
				socket.on('connect_failed', function() {
					console.log("连接失败");
				});
				socket.on('error', function(data) {
					console.log("连接错误" + data);
				});
				socket.on('message', function(data) {
					console.log("信息" + data);
				});
				socket.on('anything', function(data) {
					console.log("连接信息" + data);
				});
				socket.on('reconnect_failed', function() {
					console.log("重连失败");
				});
				socket.on('reconnect', function() {
					console.log("成功重连");
				});
				socket.on('reconnecting', function(data) {
					console.log("正在重连" + data);
				});
		});
	</script>
</head>
<body>
	<div id="display" style="height:50px;background-color:gray;">
		<font size=50 >x=<span id="x">0</span>, y=<span id="y">0</span></font>
	</div>
</body>
</html>
