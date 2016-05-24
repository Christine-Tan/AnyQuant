<%--
  Created by IntelliJ IDEA.
  User: Seven
  Date: 16/5/12
  Time: 下午8:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>AnyQuant</title>
    <link href="/css/frontpage.css" rel="stylesheet">
    <%--整个页面的留白--%>
    <style>
        body {
            padding-top: 50px;
            padding-left: 50px;
        }
    </style>
    <!--引入ECharts文件-->
    <script src="/js/echarts.min.js"></script>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
</head>
<body class="front-page-default-theme">
<div class="container">
    <!--最上方的
    导航-->
    <div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#" style="font-family:Arial,Helvetica,sans-serif;font-size:100%">AnyQuant</a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#"><span class="glyphicon glyphicon-chevron-right"> 单股查询</span></a></li>
                    <li><a href="#"><span class="glyphicon glyphicon-chevron-right"> 热门行业</span></a></li>
                    <li><a href="#"><span class="glyphicon glyphicon-chevron-right"> 热门股票</span></a></li>
                    <li><a href="#"><span class="glyphicon glyphicon-chevron-right">关于我们</span></a></li>
                </ul>
            </div><!-- /.nav-collapse -->
        </div><!-- /.container -->
    </div>

    <div class="container-fluid text-center" style="padding-top: 60px">
        <img class="logo-img" src="/images/logo.png" alt="股票分析">
    </div>

    <%--搜索框--%>
    <form class="form-inline text-center" style="padding-top: 20px" action="search.stock" method="get">
        <div class="form-group">
            <div class="row-inline" class="thumbnail" style="border: none">
                <input type="text" name="number" class="form-control" placeholder="请输入股票代码"/>
                <input type="submit" value="搜索">
            </div>
        </div>
        <%--<button class="btn btn-primary" >搜 索</button>--%>

    </form>
</div>

</body>
</html>