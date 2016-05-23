<%@ page import="model.stock.StockVO" %>
<%@ page import="model.stock.StockAttribute" %>
<%@ page import="java.util.List" %>
<%@ page import="util.enums.AttributeEnum" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>stock</title>


    <link rel="stylesheet" type="text/css" href="/css/stock.css"/>
    <script src="/js/echarts.min.js"></script>
    <script src="/js/jquery-2.2.3.min.js"></script>
    <%--<script src="/html/js/test.js"></script>--%>
</head>


<body>
<!-- <center> -->
<button id="test">跳转</button>
<script>
    $(document).ready(function(){
        $('#test').click(function(){
           top.window.location.href = "test.jsp";
        });
    });
</script>
<h1 name="top">单股界面</h1>
<hr>
<div>

    <%--<script>document.write("pages/js/test.js")</script>--%>
        <script type="text/javascript">
            var top;
            var down;
        </script>
    <%--//LineChart--%>
    <script>
        function createLineChart(id, rawData, title, legend) {
//            alert(title + " " + "length: " + rawData.length);
            var LineChart = echarts.init(document.getElementById(id));

            var allData = [];
            for (var i = 0; i < rawData.length; i++) {
                allData.push(splitLineData(rawData[i]));
            }
//            var data0 = splitLineData(rawData[0]);

            function splitLineData(rawData) {
                var categoryData = [];
                var values = [];

                for (var i = 0; i < rawData.length; i++) {
                    categoryData.push(rawData[i].splice(0, 1)[0]);
                    values.push(rawData[i].splice(0, 1)[0]);

                }
                return {
                    categoryData: categoryData,
                    values: values
                };
            }

            function getSeries() {
                var series = [];
                for (var i = 0; i < legend.length; i++) {
                    var item = {
                        name: legend[i],
                        type: 'line',
                        data: allData[i].values
                    };
                    series.push(item);
                }
                ;
                return series;

            }

            var mySeries = getSeries();

            var option = {
                title: {
                    text: title
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: legend
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    <!--feature: {-->
                    <!--saveAsImage: {}-->
                    <!--}-->
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: allData[0].categoryData
                },
                yAxis: {
                    type: 'value',
                    scale: true
                },
//                series:[
//                    {
//                        name:legend[0],
//                        type:'line',
//                        impl:allData[0].values
//                    }
//                ]
                series: mySeries


            };
            LineChart.setOption(option);

            return LineChart;
        }

    </script>

    <%--//BarChart--%>
    <script type="text/javascript">
        function createBarChart(id, rawData, title, legend) {

            var data0 = splitBarData(rawData);

            function splitBarData(rawData) {
                var categoryData = [];
                var values0 = [];
//                var values1 = [];
//                var values2 = [];
                for (var i = 0; i < rawData.length; i++) {
                    categoryData.push(rawData[i].splice(0, 1)[0]);
                    values0.push(rawData[i].splice(0, 1)[0]);
//                    values1.push(rawData[i].splice(0, 1)[0]);
//                    values2.push(rawData[i].splice(0, 1)[0]);
                }
                return {
                    categoryData: categoryData,
                    values0: values0
//                    values1: values1,
//                    values2: values2,
                };
            }

            var BarChart = echarts.init(document.getElementById(id));

            var option = {
                title: {
                    text: title
                },

                tooltip: {},
                legend: {
                    data: legend
                },

                xAxis: {data: data0.categoryData},
                yAxis: {scale: true},
                series: [{
                    name: legend[0],
                    type: 'bar',
                    data: data0.values0
                }]
            };

            BarChart.setOption(option);

            return BarChart;
        }
    </script>

    <%--//KLine--%>
    <script type="text/javascript">
        function createKLine(id) {
            var KLineChart = echarts.init(document.getElementById(id));
            var kLineData = <%=request.getAttribute("kLine")%>;
            var data0 = splitData(kLineData);


            function splitData(rawData) {
                var categoryData = [];
                var values = [];
                for (var i = 0; i < rawData.length; i++) {
                    categoryData.push(rawData[i].splice(0, 1)[0]);
                    values.push(rawData[i])
                }
                return {
                    categoryData: categoryData,
                    values: values
                };
            }

            function calculateMA(dayCount) {
                var result = [];
                for (var i = 0, len = data0.values.length; i < len; i++) {
                    if (i < dayCount) {
                        result.push('-');
                        continue;
                    }
                    var sum = 0;
                    for (var j = 0; j < dayCount; j++) {
                        sum += data0.values[i - j][1];
                    }
                    result.push(sum / dayCount);
                }
                return result;
            }


            option = {
                title: {
                    text: 'K线图',
                    left: 0
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'line'
                    }
                },
                legend: {
                    data: ['日K', 'MA5', 'MA10', 'MA20', 'MA30']
                },
                grid: {
                    left: '10%',
                    right: '10%',
                    bottom: '15%'
                },
                xAxis: {
                    type: 'category',
                    data: data0.categoryData,
                    scale: true,
                    boundaryGap: false,
                    axisLine: {onZero: false},
                    splitLine: {show: false},
                    splitNumber: 20,
                    min: 'dataMin',
                    max: 'dataMax'
                },
                yAxis: {
                    scale: true,
                    boundaryGap: false,
                    splitArea: {
                        show: true
                    }
                },
                dataZoom: [
                    {
                        type: 'inside',
                        start: 50,
                        end: 100
                    },
                    {
                        show: true,
                        type: 'slider',
                        y: '90%',
                        start: 50,
                        end: 100
                    }
                ],
                series: [
                    {
                        name: '日K',
                        type: 'candlestick',
                        data: data0.values,
                        markLine: {
                            symbol: ['none', 'none'],
                            data: [
                                [
                                    {
                                        <!--name: 'from lowest to highest',-->
                                        <!--type: 'min',-->
                                        <!--valueDim: 'lowest',-->
                                        <!--symbol: 'circle',-->
                                        <!--symbolSize: 10,-->
                                        <!--label: {-->
                                        <!--normal: {show: false},-->
                                        <!--emphasis: {show: false}-->
                                        <!--}-->
                                    },
                                    {
//                                        type: 'max',
//                                        valueDim: 'highest',
//                                        symbol: 'circle',
//                                        symbolSize: 10,
//                                        label: {
//                                            normal: {show: false},
//                                            emphasis: {show: false}
//                                        }
                                    }
                                ]

                            ]
                        }
                    },
                    {
                        name: 'MA5',
                        type: 'line',
                        data: calculateMA(5),
                        smooth: true,
                        lineStyle: {
                            normal: {opacity: 0.5}
                        }
                    },
                    {
                        name: 'MA10',
                        type: 'line',
                        data: calculateMA(10),
                        smooth: true,
                        lineStyle: {
                            normal: {opacity: 0.5}
                        }
                    },
                    {
                        name: 'MA20',
                        type: 'line',
                        data: calculateMA(20),
                        smooth: true,
                        lineStyle: {
                            normal: {opacity: 0.5}
                        }
                    },
                    {
                        name: 'MA30',
                        type: 'line',
                        data: calculateMA(30),
                        smooth: true,
                        lineStyle: {
                            normal: {opacity: 0.5}
                        }
                    }

                ]
            };

            KLineChart.setOption(option);
            return KLineChart;
        }


    </script>

    <%--PieChart--%>

    <script type="text/javascript">
        function createPieChart(id, title, legend, values) {
            function getData() {
                var data = [];
                for (var i = 0; i < values.length; i++) {
                    var item = {
                        value: values[i],
                        name: legend[i]
                    };

                    data.push(item);
                }

                return data;
            }

            var displayData = getData();
            var pieChart = echarts.init(document.getElementById(id));
            option = {
                title: {
                    text: title,
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    x: 'center',
                    y: 'bottom',
                    data: legend
                },
                calculable: true,
                series: [
                    {
                        name: '半径模式',
                        type: 'pie',
                        radius: [50, 100],
                        roseType: 'radius',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        lableLine: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: displayData
                    }
                ]
            };
            pieChart.setOption(option);
        }
    </script>



    <h3>
        <a name="single">基本行情</a>
    </h3>
    <hr width="80%">
    <div class="single_label">
        <div class="allTabs">
            <div class="tabs">
                <div class="on" id="tab1_kline">k线图</div>
                <div class="tab" id="tab1_line">分时</div>

            </div>
        </div>

        <div class="k_line" id="single_k_line">K线图</div>
        <div class="bar" id="single_bar">bar</div>


        <script type="text/javascript">



            $(document).ready(function () {

                $('.single_label .tabs .tab,.on').css('cursor', 'pointer');

                $('.single_label .tabs .tab[id^="tab1"],.on[id^="tab1"]').on('click', function () {
                    $('.single_label .tabs .on[id^="tab1"]').attr('class', 'tab');
                    $(this).attr('class', 'on');


                    var type = $(this).attr('id');
                    if(type=='tab1_kline'){
                        top=createKLine("single_k_line");
                        down = createBarChart('single_bar',<%=request.getAttribute("singleVolumeLine")%>, '成交量柱状图', ['volume']);
                        top.connect(down);
                        down.connect(top);
                    }else if(type == "tab1_line"){
                        var dailyLineData = [];
                        var dailyLegend = [];
                        <%
                            List<String> dailyLineList=(List<String>)request.getAttribute("dailyLine");
                            for(String s:dailyLineList){
                        %>
                        dailyLineData.push(<%=s%>);
                        <%}%>
                        dailyLegend.push('line1');
                        dailyLegend.push('line2');
                        top = createLineChart('single_k_line',dailyLineData,'股价分时图',dailyLegend);
                        down = createBarChart('single_bar',<%=request.getAttribute("volumeLine")%>, '成交量分时柱状图', ['volume'])
                        top.connect(down);
                        down.connect(top);
                    }

                });

            });


        </script>




        <script>
            <%--window.onload = createKLine("single_k_line");--%>
            <%--window.onload = createBarChart('single_bar',<%=request.getAttribute("singleVolumeLine")%>, '成交量柱状图', ['volume']);--%>
            top=createKLine("single_k_line");
            down = createBarChart('single_bar',<%=request.getAttribute("singleVolumeLine")%>, '成交量柱状图', ['volume']);
            top.connect(down);
            down.connect(top);
        </script>


        </div>
    <div>一些基本信息还有相关系数</div>

</div>



<br>

<div>
    <h3>
        <a name="industry">行业分析</a>
    </h3>
    <hr width="80%">

    <div class="single_label">
        <div class="industry_line" id="avgPrice_line">平均股价</div>
        <div class="industry_pie" id="vsMarket_line">与大盘对比</div>
        <div class="blank"></div>
        <div class="industry_bar" id="industryVolume">柱状图</div>

    </div>

    <div>一些基本信息</div>

</div>


<%--行业分析--%>
<%
    String industryVolume = (String) request.getAttribute("industryVolume");
    List<String> industryPriceLine = (List<String>) request.getAttribute("industryPriceLine");
    List<String> comparePriceLine = (List<String>) request.getAttribute("comparePriceLine");
%>


<script type="text/javascript">
    var industryPriceData = [];
    <%
        for(String s:industryPriceLine){%>
    industryPriceData.push(<%=s%>);
    <%
        }
    %>

    var comparePriceData = [];
    <%
        for(String s:comparePriceLine){%>
    comparePriceData.push(<%=s%>);
    <%
        }
    %>
    var industryPriceLegend = [];
    industryPriceLegend.push('价格');
    window.onload = createLineChart('avgPrice_line', industryPriceData, '行业平均价格折线图', industryPriceLegend);
    var comparePriceLegend = [];
    comparePriceLegend.push('大盘价格');
    comparePriceLegend.push('行业价格');
    window.onload = createLineChart('vsMarket_line', comparePriceData, '与大盘价格对比折线图', comparePriceLegend);
    var industryVolumeLegend = [];
    industryVolumeLegend.push('成交量');
    window.onload = createBarChart('industryVolume', <%=industryVolume%>, '行业成交量柱状图', industryVolumeLegend);
</script>


<div>
    <h3>
        <a name="analysis">技术诊股</a>
    </h3>
    <hr width="80%">
    <div class="single_label">
        <div class="analysis_line">
            <div class="allTabs">
                <div class="tabs">
                    <div class="on" id="tab2_MACD">MACD</div>
                    <div class="tab" id="tab2_EMA">EMA</div>
                    <div class="tab" id="tab2_RSI">RSI</div>

                </div>
            </div>
            <div class="analysis_linechart" id="MER">MACD/EMA/RSI</div>

        </div>


        <div class="analysis_pie">
            <div class="allTabs">
                <div class="tabs" id='TabsContainer'>
                    <div class="on" id='tab3_volume'>交易量</div>
                    <div class="tab" id='tab3_price'>交易金额</div>
                </div>
            </div>
            <div class="analysis_piechart" id="pie">饼图</div>
        </div>

        <div class="blank"></div>

        <div class="analysis_bar">柱状图</div>


    </div>
    <div>一些基本信息</div>
</div>



<%
    List<String> MACDLine = (List<String>) request.getAttribute("macdLine");
    List<String> RSILine = (List<String>) request.getAttribute("rsiLine");
    List<String> EMALine = (List<String>) request.getAttribute("emaLine");

    List<String> amountList = (List<String>)request.getAttribute("amountPie");
    List<String> priceList = (List<String>)request.getAttribute("pricePie");
%>
<script type="text/javascript">
    <%--window.onload = createPieChart('pie','成交量饼图',<%=amountList.get(0)%>,<%=amountList.get(1)%>);--%>
</script>

<script type="text/javascript">
    var MACDData = [];
    <%
        for(String s:MACDLine){%>
    MACDData.push(<%=s%>);
    <%
        }
    %>

    var MACDLegend = [];
    MACDLegend.push('line1');
    MACDLegend.push('line2');

    window.onload = createLineChart('MER', MACDData, 'MACD', MACDLegend);

    $(document).ready(function () {

        $('.single_label .tabs .tab[id^="tab2"],.on[id^="tab2"]').on('click', function () {
            $('.single_label .tabs .on[id^="tab2"]').attr('class', 'tab');
            $(this).attr('class', 'on');

            id = $(this).attr('id');
            if (id == 'tab2_MACD') {
                var MACDData = [];
                <%
                    for(String s:MACDLine){%>
                MACDData.push(<%=s%>);
                <%
                    }
                %>

                var MACDLegend = [];
                MACDLegend.push('line1');
                MACDLegend.push('line2');
                createLineChart('MER', MACDData, 'MACD', MACDLegend);
            } else if (id == 'tab2_RSI') {
                var RSIData = [];
                <%
                    for(String s:RSILine){%>
                RSIData.push(<%=s%>);
                <%
                    }
                %>

                var RSILegend = [];
                RSILegend.push('line1');
                RSILegend.push('line2');
                RSILegend.push('line3');
                createLineChart('MER', RSIData, 'RSI', RSILegend);
            } else if (id == 'tab2_EMA') {
                var EMAData = [];
                <%
                    for(String s:EMALine){%>
                EMAData.push(<%=s%>);
                <%
                    }
                %>

                var EMALegend = [];
                EMALegend.push('line1');
                EMALegend.push('line2');
                EMALegend.push('line3');
                createLineChart('MER', EMAData, 'EMA', EMALegend);
            }

        });

        $('.single_label .tabs .tab[id^="tab3"],.on[id^="tab3"]').on('click', function () {
            $('.single_label .tabs .on[id^="tab3"]').attr('class', 'tab');
            $(this).attr('class', 'on');

        });

    });

</script>


<%--历史数据--%>
<div>
    <h3>
        <a name="history">历史数据</a>
    </h3>
    <hr width="80%">

    <div></div>

    <div class="single_label">
        <br>
        <form>
            开始日期<input type="text"/> 结束日期<input type="text"/> <input
                type="submit" value="搜索"/>
        </form>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td align="right"><span>筛选条件</span></td>
                <td align="center">
                    <form>
                        开盘价：<input type="text" style="width: 80px;"/>至<input
                            type="text" style="width: 80px;"/> 最高价：<input type="text"
                                                                          style="width: 80px;"/>至<input type="text"
                                                                                                        style="width: 80px;"/>
                        <input type="submit" value="重置"/><br> <br> 收盘价：<input
                            type="text" style="width: 80px;"/>至<input type="text"
                                                                      style="width: 80px;"/> 最低价：<input type="text"
                                                                                                        style="width: 80px;"/>至<input
                            type="text" style="width: 80px;"/>
                        <input type="submit" value="确认"/><br>
                    </form>
                </td>
            </tr>
        </table>

        <br>

        <table border="1" width=100% cellspacing="0" cellpadding="0">
            <tr>
                <td align="center">日期</td>
                <td align="center">开盘价</td>
                <td align="center">最高价</td>
                <td align="center">最低价</td>
                <td align="center">收盘价</td>
                <td align="center">后复权价</td>
                <td align="center">成交量</td>
                <td align="center">换手率</td>
                <td align="center">市盈率</td>
                <td align="center">市净率</td>

            </tr>
            <%
                StockVO vo = (StockVO) request.getAttribute("stockVO");
                List<StockAttribute> attributeList = vo.getAttributes();

                for (int i = 0; i < attributeList.size(); i++) {
                    StockAttribute stockAttribute = attributeList.get(i);
                    String date = stockAttribute.getDate();
            %>

            <tr>
                <td align="center"><%=date%>
                </td>
                <td align="center"><%=stockAttribute.getAttribute(AttributeEnum.getENbyCH("开盘价"))%>
                </td>
                <td align="center"><%=stockAttribute.getAttribute(AttributeEnum.getENbyCH("最高价"))%>
                </td>
                <td align="center"><%=stockAttribute.getAttribute(AttributeEnum.getENbyCH("最低价"))%>
                </td>
                <td align="center"><%=stockAttribute.getAttribute(AttributeEnum.getENbyCH("收盘价"))%>
                </td>
                <td align="center"><%=stockAttribute.getAttribute(AttributeEnum.getENbyCH("后复权价"))%>
                </td>
                <td align="center"><%=stockAttribute.getAttribute(AttributeEnum.getENbyCH("成交量"))%>
                </td>
                <td align="center"><%=stockAttribute.getAttribute(AttributeEnum.getENbyCH("换手率"))%>
                </td>
                <td align="center"><%=stockAttribute.getAttribute(AttributeEnum.getENbyCH("市盈率"))%>
                </td>
                <td align="center"><%=stockAttribute.getAttribute(AttributeEnum.getENbyCH("市净率"))%>
                </td>


            </tr>

            <%
                }
            %>


        </table>
    </div>

</div>

<div class="bookmark" id="z1">
    <a class="nav" href="#single">基<br>本<br>行<br>情<br></a>
</div>

<div class="bookmark" id="z2">
    <a class="nav" href="#industry">行<br>业<br>分<br>析<br></a>
</div>

<div class="bookmark" id="z3">
    <a class="nav" href="#analysis">技<br>术<br>诊<br>股<br></a>
</div>

<div class="bookmark" id="z4">
    <a class="nav" href="#history">历<br>史<br>数<br>据<br></a>
</div>

<div class="bookmark" id="z5">
    <a class="nav" href="#top">回<br>到<br>顶<br>部<br></a>
</div>

<!-- </center> -->

</body>


</html>