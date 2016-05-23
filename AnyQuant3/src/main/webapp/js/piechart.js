/**
 * Created by Seven on 16/5/14.
 */
var piechart = echarts.init(document.getElementById('piechart'));
//rawdata的格式为arraylist 包含属性为value和name的对象
var rawData="<%=rawData%>";
var data0=rawData.sort(function (a, b) { return a.value - b.value});
var option = {
    backgroundColor: '#2c343c',

    title: {
        text: '买入卖出量对比饼图',
        left: 'center',
        top: 20,
        textStyle: {
            color: '#ccc'
        }
    },

    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },

    visualMap: {
        show: false,
        min: 80,
        max: 600,
        inRange: {
            colorLightness: [0, 1]
        }
    },
    series : [
        {
            name:'交易方式',
            type:'pie',
            radius : '55%',
            center: ['50%', '50%'],
            data:data0,
            roseType: 'angle',
            label: {
                normal: {
                    textStyle: {
                        color: 'rgba(255, 255, 255, 0.3)'
                    }
                }
            },
            labelLine: {
                normal: {
                    lineStyle: {
                        color: 'rgba(255, 255, 255, 0.3)'
                    },
                    smooth: 0.2,
                    length: 10,
                    length2: 20
                }
            },
            itemStyle: {
                normal: {
                    color: '#c23531',
                    shadowBlur: 200,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};

piechart.setOption(option);