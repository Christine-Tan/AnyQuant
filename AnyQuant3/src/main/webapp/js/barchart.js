/**
 * Created by Seven on 16/5/13.
 */
// 基于准备好的dom，初始化echarts实例
var volumeBarChart = echarts.init(document.getElementById('volume_barchart'));
var rawData="<%=rawData%>"
var data0=splitData(rawData
    // [
    // ['2013/5/24', 2292.59],
    // ['2013/5/27', 2301.7],
    // ['2013/5/28', 2322.1],
    // ['2013/5/29', 2334.33],
    // ['2013/5/30', 2325.72],
    // ['2013/5/31', 2325.53],
    // ['2013/6/3',2313.43],
    // ['2013/6/4', 2297.1],
    // ['2013/6/5', 2276.86],
    // ['2013/6/6', 2266.69],
    // ['2013/6/7', 2250.63]
// ]
);

function splitData(rawData) {
    var categoryData = [];
    var values = [];
    for (var i = 0; i < rawData.length; i++) {
        categoryData.push(rawData[i].splice(0, 1)[0]);
        values.push(rawData[i].splice(0,1)[0])
    }
    return {
        categoryData: categoryData,
        values: values
    };
}

// 显示标题,图例和空的坐标轴
var option = {
    title: {
        text: '成交量柱状图'
    },
    tooltip: {},
    legend: {
        data:['成交量(万手)']
    },
    xAxis: {
        data: data0.categoryData
    },
    yAxis: {
        scale:true
    },
    series: [{
        name: '成交量(万手)',
        type: 'bar',
        data: data0.values
    }]
};
volumeBarChart.setOption(option);

       // //异步加载数据
       // $.get('util.json').done(function (impl) {
       //     //填入数据
       //      volumeBarChart.setOption({
       //          xAxis:{
       //              impl:impl.categories
       //          },
       //          series:[{
       //              //根据名字对应到相应的列
       //              name:'成交量(万手)',
       //              impl:impl.impl
       //          }]
       //      });
       // });