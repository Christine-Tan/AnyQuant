<%--
  Created by IntelliJ IDEA.
  User: Seven
  Date: 16/5/13
  Time: 下午7:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>BarChart</title>
    <script src="../../jsp/js/echarts.min.js"></script>
</head>
<body>
<form action="/TestServlet" method="GET">
    <input type="submit" value="提交"/>
</form>
<%
    ArrayList rawData= (ArrayList) request.getAttribute("barchart");
%>
<div id="volume_barchart" style=" width:100%;height:600px;"></div>
<script type="text/javascript" src="../../jsp/js/barchart.js"></script>
</body>
</html>