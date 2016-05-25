<%--
  Created by IntelliJ IDEA.
  User: Lin
  Date: 2016/5/23
  Time: 23:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>chinaz</title>
    <!-- Bootstrap Styles-->
    <link href="/assets/css/bootstrap.css" rel="stylesheet"/>
    <!-- FontAwesome Styles-->
    <link href="/assets/css/font-awesome.css" rel="stylesheet"/>
    <!-- Morris Chart Styles-->
    <link href="/assets/js/morris/morris-0.4.3.min.css" rel="stylesheet"/>
    <!-- Custom Styles-->
    <link href="/assets/css/custom-styles.css" rel="stylesheet"/>
    <!-- Google Fonts-->
    <link href='http://fonts.useso.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
    <link rel="stylesheet" href="/assets/js/Lightweight-Chart/cssCharts.css">
    <link rel="stylesheet" href="/css/stock.css"/>
    <script src="/js/jquery-2.2.3.min.js"></script>
    <script src="/js/echarts.min.js"></script>
    <script src="/js/stock.js"></script>
</head>

<body>
<div id="wrapper">
    <nav class="navbar navbar-default top-navbar" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="single.stock"><strong>Marvel</strong></a>

            <div id="sideNav" href=""><i class="fa fa-caret-right"></i></div>
        </div>

        <ul class="nav navbar-top-links">
            <form class="form-inline text-left" style="padding-top: 15px" action="single.search" method="get">
                <div class="form-group">
                    <div class="row-inline" class="thumbnail" style="border: none">
                        <input type="text" id="search" name="number" class="form-control" placeholder="请输入股票代码"
                               style="margin-left: 40px;"/>
                        <input type="text" id="viewID" name="view" style="display:none;"/>
                        <input type="submit" value="搜索" style="margin-left: 30px;"/>
                    </div>
                </div>
            </form>
            <%
                String number = (String) request.getSession().getAttribute("number");
            %>
            <script type="text/javascript">
                var text = <%=number%>;
                document.getElementById("search").value = text.number;
                document.getElementById("viewID").value = "history";
            </script>
            <!--在此添加横向导航-->

        </ul>
    </nav>
    <!--/. NAV TOP  -->
    <nav class="navbar-default navbar-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="main-menu">

                <li>
                    <a href="single.stock"><i class="fa fa-dashboard"></i> SingleStock</a>
                </li>
                <li>
                    <a href="analysis.industry"><i class="fa fa-desktop"></i> Industry</a>
                </li>
                <li>
                    <a href="single.analysis"><i class="fa fa-bar-chart-o"></i> Analysis</a>
                </li>
                <%--<li>--%>
                <%--<a href="all.markets"><i class="fa fa-qrcode"></i> Markets</a>--%>
                <%--</li>--%>

                <li>
                    <a class="active-menu" href="single.history"><i class="fa fa-table"></i> history</a>
                </li>
            </ul>

        </div>

    </nav>
    <!-- /. NAV SIDE  -->

    <div id="page-wrapper">
        <%--索引--%>
        <div class="header">
            <h1 class="page-header">
                Dashboard
                <small>Welcome John Doe</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#">Home</a></li>
                <li><a href="#">Dashboard</a></li>
                <li class="active">Data</li>
            </ol>

        </div>

        <div id="page-inner">

            <!-- /. ROW  -->

            <div class="row">
                <div class="col-xs-6 col-md-12">
                    <div class="panel panel-default">
                        <%--放东西--%>


                        <div class="panel-body easypiechart-panel">
                            <h4>历史</h4>
                        </div>
                    </div>
                </div>

            </div><!--/.row-->


            <div class="row">
                <div class="col-md-12">

                </div>
            </div>
            <!-- /. ROW  -->

            <!-- /. ROW  -->


            <footer><p>Copyright &copy; 2016.Company name All rights reserved.<a target="_blank"
                                                                                 href="http://sc.chinaz.com/moban/">
                &#x7F51;&#x9875;&#x6A21;&#x677F;</a></p>


            </footer>
        </div>
        <!-- /. PAGE INNER  -->
    </div>
    <!-- /. PAGE WRAPPER  -->
</div>
<!-- /. WRAPPER  -->
<!-- JS Scripts-->
<!-- jQuery Js -->
<script src="/assets/js/jquery-1.10.2.js"></script>
<!-- Bootstrap Js -->
<script src="/assets/js/bootstrap.min.js"></script>

<!-- Metis Menu Js -->
<script src="/assets/js/jquery.metisMenu.js"></script>
<!-- Morris Chart Js -->
<script src="/assets/js/morris/raphael-2.1.0.min.js"></script>
<script src="/assets/js/morris/morris.js"></script>


<script src="/assets/js/easypiechart.js"></script>
<script src="/assets/js/easypiechart-data.js"></script>

<script src="/assets/js/Lightweight-Chart/jquery.chart.js"></script>

<!-- Custom Js -->
<script src="/assets/js/custom-scripts.js"></script>

<script>

</script>

</body>

</html>
