<%@ page import="model.stock.StockVO" %>
<%@ page import="model.stock.StockAttribute" %>
<%@ page import="java.util.List" %>
<%@ page import="util.enums.AttributeEnum" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>stock</title>


    <link rel="stylesheet" type="text/css" href="/css/stock.css"/>
    <script src="/js/echarts.min.js"></script>
    <script src="/js/jquery-2.2.3.min.js"></script>
    <script src="/js/stock.js"></script>
</head>


<body>
<!-- <center> -->
<h1 name="top">单股界面</h1>
<hr>
<div>

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

            var top;
            var down;

            $(document).ready(function () {

                $('.single_label .tabs .tab,.on').css('cursor', 'pointer');

                $('.single_label .tabs .tab[id^="tab1"],.on[id^="tab1"]').on('click', function () {
                    $('.single_label .tabs .on[id^="tab1"]').attr('class', 'tab');
                    $(this).attr('class', 'on');


                    var type = $(this).attr('id');
                    if(type=='tab1_kline'){
                        top=createKLine("single_k_line",<%=request.getAttribute("kLine")%>);
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
            var top;
            var down;
            top=createKLine("single_k_line",<%=request.getAttribute("kLine")%>);
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