new Vue({
    el:"#app",
    data:{
        dialogVisible:false,
        Basic:[
            {
                name:'赵征',
                date:'2023',
                target:2000000,
                actual:930000,
                weeks: 1
            }
        ],
        salesas:{
            x:["3月1日","3月2日","3月3日","3月4日","3月5日","3月6日","3月7日","3月8日"
                ,"3月9日","3月10日"],
            y : [180,190,150,145,168,188,178,193,195,198]
        },
        pieChart:[],
        tableData:[],
        PurNum:[],
        ProductPie:[],
        CNYmap:[],
        agentList:[],
        visitList:[],
        ProductPiessList:[]
    },
    created(){

    },
    mounted(){
        this.sales(); //销售折线图
        this.Payment(); //回款饼状图
        this.Debt();// 客户未回款表
        this.Purchases();//客户购买情况表
        this.ProductSales();//产品总销售情况饼状图
        this.Chinamap();//销售地图
        this.AgentDis();//代理商销售统计和折扣建议
        this.VisitPrompt();
    },
    methods:{
        Generate(){
            html2canvas(document.body, {
                onrendered:function(canvas) {

                    var contentWidth = canvas.width;
                    var contentHeight = canvas.height;

                    //一页pdf显示html页面生成的canvas高度;
                    var pageHeight = contentWidth / 595.28 * 841.89;
                    //未生成pdf的html页面高度
                    var leftHeight = contentHeight;
                    //pdf页面偏移
                    var position = 50;
                    //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
                    var imgWidth = 555.28;
                    var imgHeight = 555.28 / contentWidth * contentHeight;

                    var pageData = canvas.toDataURL('image/jpeg', 1.0);

                    var pdf = new jsPDF('','pt','a4');
                    //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
                    //当内容未超过pdf一页显示的范围，无需分页
                    if (leftHeight < pageHeight) {
                        pdf.addImage(pageData, 'JPEG', 20, 0, imgWidth, imgHeight);
                    } else {
                        while (leftHeight > 0) {
                            pdf.addImage(pageData, 'JPEG', 20, position, imgWidth, imgHeight)
                            leftHeight -= pageHeight;
                            position -= 841.89;
                            //避免添加空白页
                            if (leftHeight > 0) {
                                pdf.addPage();
                            }
                        }
                    }

                    pdf.save('content.pdf');

                }
            })
        },
        Generates(){
            location.href="/Statement/outPDF1"



        },
        sales(){
            var myChart = echarts.init(document.getElementById("container"));
            var option = {
                title: {
                    text: '折线图',
                    subtext: '单位 万元',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: ["3月1日","3月2日","3月3日","3月4日","3月5日","3月6日","3月7日","3月8日"
                        ,"3月9日","3月10日"]
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        stack: 'Total',
                        type: 'line',
                        data: [180,190,150,145,168,188,178,193,195,198]
                    }
                ]
            };
            myChart.setOption(option);
            window.addEventListener('resize', myChart.resize);
        },
        Payment(){
            this.pieChart = [
                {value:630000,name:'已回款'},
                {value:222100,name:'未回款'}
            ]
            var myChart = echarts.init(document.getElementById("container1"));
            var option = {
                title: {
                    text: '饼状图',
                    subtext: '单位 元',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        name: '回款',
                        type: 'pie',
                        radius: '70%',
                        data: this.pieChart,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            myChart.setOption(option);
            window.addEventListener('resize', myChart.resize);

        },
        Debt(){
            this.tableData = [
                {name:"aa生物公司",arrears:18000,date:"2022-12-20"},
                {name:"bb生物公司",arrears:36000,date:"2022-12-20"},
                {name:"cc医院",arrears:6000,date:"2023-01-20"},
                {name:"dd生物公司",arrears:19000,date:"2022-12-20"},
                {name:"ee生物公司",arrears:9000,date:"2022-12-20"},
                {name:"ff医院",arrears:50000,date:"2023-04-20"},
                {name:"gg生物公司",arrears:18000,date:"2022-12-20"},
                {name:"hh医院",arrears:8000,date:"2022-12-20"},
                {name:"ii生物公司",arrears:18000,date:"2023-02-20"},
                {name:"jj医院",arrears:16000,date:"2022-12-20"},
            ]
        },
        Purchases(){
            this.PurNum = [
                {name:"aa生物公司",gene:12,antibody:1,virus:3,other:4},
                {name:"bb生物公司",gene:12,antibody:1,virus:3,other:4},
                {name:"cc医院",gene:12,antibody:1,virus:3,other:4},
                {name:"dd生物公司",gene:12,antibody:1,virus:3,other:4},
                {name:"ee生物公司",gene:12,antibody:1,virus:3,other:4},
                {name:"ff医院",gene:12,antibody:1,virus:3,other:4},
                {name:"gg生物公司",gene:12,antibody:1,virus:3,other:4},
                {name:"hh医院",gene:12,antibody:1,virus:3,other:4},
            ]
        },
        ProductSales(){

            this.ProductPie = [
                {value:630,name:'CND'},
                {value:100,name:'AAV'},
                {value:300,name:'shRNA'},
                {value:450,name:'IHC'},
                {value:800,name:'MOP'},
                {value:630,name:'PCT'},
                {value:960,name:'T3'},
                {value:70,name:'TNT'},
                {value:610,name:'PCNA'}
            ]

            var myChart = echarts.init(document.getElementById("container2"));
            var option = {
                title: {
                    text: '本周产品销售统计',
                    subtext: '单位(ml)',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        name: '回款',
                        type: 'pie',
                        radius: '70%',
                        data: this.ProductPie,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            myChart.setOption(option);
            window.addEventListener('resize', myChart.resize);

        },
        AgentDis(){
            this.agentList = [
                {name:"河北中心医院",volume:"100L",room:"10000万元",pdiscount:8,rdiscount:8.4},{name:"河南中心医院",volume:"75L",room:"7000万元",pdiscount:8,rdiscount:8.4},
                {name:"广东中心医院",volume:"94L",room:"11000万元",pdiscount:8,rdiscount:8.4},{name:"北京中心医院",volume:"46L",room:"5000万元",pdiscount:8,rdiscount:8.4},
                {name:"天津中心医院",volume:"60L",room:"7000万元",pdiscount:8,rdiscount:8.4},{name:"浙江中心医院",volume:"77L",room:"8000万元",pdiscount:8,rdiscount:8.4},
                {name:"台湾中心医院",volume:"73L",room:"7000万元",pdiscount:8,rdiscount:8.4},{name:"重庆中心医院",volume:"63L",room:"6000万元",pdiscount:8,rdiscount:8.4},
                {name:"贵州中心医院",volume:"82L",room:"8000万元",pdiscount:8,rdiscount:8.4},{name:"湖北中心医院",volume:"56L",room:"6000万元",pdiscount:8,rdiscount:8.4},
            ]

        },
        VisitPrompt(){
            this.visitList = [
                {name:"hh医院",address:"北京市通州区xx街",last_purchase_time:"2022-12-20",last_purchase_product:"CND,shRNA,TNT",mail:"zhaozheng@origene.cn"},
                {name:"jj医院",address:"北京市通州区xx街",last_purchase_time:"2022-12-20",last_purchase_product:"CND,shRNA,TNT",mail:"zhaozheng@origene.cn"},
                {name:"hh医院",address:"北京市通州区xx街",last_purchase_time:"2022-12-20",last_purchase_product:"CND,shRNA,TNT",mail:"zhaozheng@origene.cn"},
                {name:"hh医院",address:"北京市通州区xx街",last_purchase_time:"2022-12-20",last_purchase_product:"CND,shRNA,TNT",mail:"zhaozheng@origene.cn"},
                {name:"hh医院",address:"北京市通州区xx街",last_purchase_time:"2022-12-20",last_purchase_product:"CND,shRNA,TNT",mail:"zhaozheng@origene.cn"},
                {name:"hh医院",address:"北京市通州区xx街",last_purchase_time:"2022-12-20",last_purchase_product:"CND,shRNA,TNT",mail:"zhaozheng@origene.cn"},
                {name:"hh医院",address:"北京市通州区xx街",last_purchase_time:"2022-12-20",last_purchase_product:"CND,shRNA,TNT",mail:"zhaozheng@origene.cn"}
            ]
        },
        ProductPiess(row){


            console.log(row);
            this.dialogVisible = true;

            this.ProductPiessList = [
                {value:12,name:'gene'},
                {value:1,name:'antibody'},
                {value:3,name:'virus'},
                {value:4,name:'other'}
            ];

            this.ProductPiessone()
        },
        ProductPiessone(){
            console.log(document.getElementById("container3"))

            var myChart = echarts.init(document.getElementById("container3"));
            var option = {
                title: {
                    text: '饼状图',
                    subtext: '单位 ml',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        name: '回款',
                        type: 'pie',
                        radius: '70%',
                        data: this.ProductPiessList,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            myChart.setOption(option);
            window.addEventListener('resize', myChart.resize);
        }













        ,
        Chinamap(){

            var aaa = this.CNYmap = [{province:"北京",salevolume:1120,saleroom:21120},
                {province:"河北",salevolume:1000,saleroom:21000},{province:"河南",salevolume:1500,saleroom:21500},{province:"广东",salevolume:1825,saleroom:21825},
                {province:"湖南",salevolume:1250,saleroom:21250},{province:"山东",salevolume:700,saleroom:20700},{province:"广西",salevolume:600,saleroom:20600},
                {province:"浙江",salevolume:800,saleroom:20080},{province:"天津",salevolume:1330,saleroom:21330},
                {province:"甘肃",salevolume:1720,saleroom:21720},{province:"重庆",salevolume:1001,saleroom:21001},{province:"江西",salevolume:1050,saleroom:21050},
                {province:"台湾",salevolume:500,saleroom:20500},{province:"香港",salevolume:488,saleroom:20488},{province:"澳门",salevolume:980,saleroom:29800},
                {province:"福建",salevolume:798,saleroom:20798},{province:"上海",salevolume:860,saleroom:20860},{province:"湖北",salevolume:950,saleroom:20950},
                {province:"贵州",salevolume:888,saleroom:20888},{province:"云南",salevolume:555,saleroom:20555},{province:"四川",salevolume:360,saleroom:20360},
                {province:"陕西",salevolume:750,saleroom:20750},{province:"宁夏",salevolume:830,saleroom:20830},{province:"山西",salevolume:1580,saleroom:21580},
                {province:"安徽",salevolume:1420,saleroom:21420},{province:"江苏",salevolume:1555,saleroom:21555},{province:"西藏",salevolume:1970,saleroom:21970},
                {province:"新疆",salevolume:1111,saleroom:21111},{province:"内蒙古",salevolume:1560,saleroom:21560},{province:"黑龙江",salevolume:1888,saleroom:21888},
                {province:"吉林",salevolume:1921,saleroom:21921},{province:"辽宁",salevolume:1732,saleroom:21732},{province:"海南",salevolume:1401,saleroom:21401},
                {province:"青海",salevolume:1589,saleroom:21589}
            ]

            function CNYVolume(name) {
                for (let i = 0; i < aaa.length; i++) {
                    if (name== aaa[i].province){
                        return aaa[i].salevolume;
                    }
                }
            }
            function CNYRoom(name) {
                for (let i = 0; i < aaa.length; i++) {
                    if (name== aaa[i].province){
                        return aaa[i].saleroom;
                    }
                }
            }


            function randomml() {
                return Math.round(Math.random() * 2000);
            }
            function randomunit() {
                return Math.round(Math.random() * 20000);
            }
            option = {
                backgroundColor: '#80B3FF',
                tooltip: {
                    trigger: 'item',
                    formatter: function (params) {
                        //定义一个res变量来保存最终返回的字符结果,并且先把地区名称放到里面
                        var res = params.name + '<br />';
                        //定义一个变量来保存series数据系列
                        var myseries = option.series;
                        //循环遍历series数据系列
                        for (var i = 0; i < myseries.length; i++) {
                            //在内部继续循环series[i],从data中判断：当地区名称等于params.name的时候就将当前数据和名称添加到res中供显示
                            for (var k = 0; k < myseries[i].data.length; k++) {
                                //console.log(myseries[i].data[k].name);
                                //如果data数据中的name和地区名称一样
                                if (myseries[i].data[k].name == params.name) {
                                    //将series数据系列每一项中的name和数据系列中当前地区的数据添加到res中
                                    /*res += myseries[i].name + ':' + myseries[i].data[k]
                                        .value + '<br />';*/
                                    if (myseries[i].name=="销售量"){
                                        res += myseries[i].name + ':' + myseries[i].data[k]
                                            .value + 'L<br />';
                                    }
                                    if (myseries[i].name == "销售额") {
                                        res += myseries[i].name + ':' + myseries[i].data[k]
                                            .value + '万元<br />';
                                    }
                                }
                            }
                        }
                        return res;
                    }
                },
                visualMap: {
                    min: 0,
                    max: 2000,
                    text: ['High', 'Low'],
                    realtime: false,
                    calculable: true,
                    inRange: {
                        color: ['#e0ffff', 'red']
                    }
                },
                geo: {
                    map: 'china',
                    roam: true,
                    label: {
                        normal: {
                            show: true,
                            textStyle: {
                                color: 'rgba(0,0,0,0.4)'
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            borderColor: 'rgba(0, 0, 0, 0.2)'
                        },
                        emphasis: {
                            areaColor: null,
                            shadowOffsetX: 0,
                            shadowOffsetY: 0,
                            shadowBlur: 20,
                            borderWidth: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                },

                series: [{
                    name: '销售量',
                    type: 'map',
                    roam: false,
                    coordinateSystem: 'geo',
                    geoIndex: 0,
                    // tooltip: {show: false},
                    label: {
                        normal: {
                            formatter: '{b}',
                            position: 'right',
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#F06C00'
                        }
                    },
                    data: [{
                        name: '北京',
                        value: CNYVolume('北京')
                    },
                        {
                            name: '天津',
                            value: CNYVolume('天津')
                        },
                        {
                            name: '上海',
                            value: CNYVolume('上海')
                        },
                        {
                            name: '重庆',
                            value: CNYVolume('重庆')
                        },
                        {
                            name: '河北',
                            value: CNYVolume('河北')
                        },
                        {
                            name: '河南',
                            value: CNYVolume('河南')
                        },
                        {
                            name: '云南',
                            value: CNYVolume('云南')
                        },
                        {
                            name: '辽宁',
                            value: CNYVolume('辽宁')
                        },
                        {
                            name: '黑龙江',
                            value: CNYVolume('黑龙江')
                        },
                        {
                            name: '湖南',
                            value: CNYVolume('湖南')
                        },
                        {
                            name: '安徽',
                            value: CNYVolume('安徽')
                        },
                        {
                            name: '山东',
                            value: CNYVolume('山东')
                        },
                        {
                            name: '新疆',
                            value: CNYVolume('新疆')
                        },
                        {
                            name: '江苏',
                            value: CNYVolume('江苏')
                        },
                        {
                            name: '浙江',
                            value: CNYVolume('浙江')
                        },
                        {
                            name: '江西',
                            value: CNYVolume('江西')
                        },
                        {
                            name: '湖北',
                            value: CNYVolume('湖北')
                        },
                        {
                            name: '广西',
                            value: CNYVolume('广西')
                        },
                        {
                            name: '甘肃',
                            value: CNYVolume('甘肃')
                        },
                        {
                            name: '山西',
                            value: CNYVolume('山西')
                        },
                        {
                            name: '内蒙古',
                            value: CNYVolume('内蒙古')
                        },
                        {
                            name: '陕西',
                            value: CNYVolume('陕西')
                        },
                        {
                            name: '吉林',
                            value: CNYVolume('吉林')
                        },
                        {
                            name: '福建',
                            value: CNYVolume('福建')
                        },
                        {
                            name: '贵州',
                            value: CNYVolume('贵州')
                        },
                        {
                            name: '广东',
                            value: CNYVolume('广东')
                        },
                        {
                            name: '青海',
                            value: CNYVolume('青海')
                        },
                        {
                            name: '西藏',
                            value: CNYVolume('西藏')
                        },
                        {
                            name: '四川',
                            value: CNYVolume('四川')
                        },
                        {
                            name: '宁夏',
                            value: CNYVolume('宁夏')
                        },
                        {
                            name: '海南',
                            value: CNYVolume('海南')
                        },
                        {
                            name: '台湾',
                            value: CNYVolume('台湾')
                        },
                        {
                            name: '香港',
                            value: CNYVolume('香港')
                        },
                        {
                            name: '澳门',
                            value: CNYVolume('澳门')
                        }
                    ]
                },
                    {
                        name: '销售额',
                        type: 'map',
                        roam: false,
                        coordinateSystem: 'geo',
                        // geoIndex: 0,
                        // tooltip: {show: false},
                        label: {
                            normal: {
                                formatter: '{b}',
                                position: 'right',
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#F06C00'
                            }
                        },
                        data: [{
                            name: '北京',
                            value: CNYRoom('北京')
                        },
                            {
                                name: '天津',
                                value: CNYRoom('天津')
                            },
                            {
                                name: '上海',
                                value: CNYRoom('上海')
                            },
                            {
                                name: '重庆',
                                value: CNYRoom('重庆')
                            },
                            {
                                name: '河北',
                                value: CNYRoom('河北')
                            },
                            {
                                name: '河南',
                                value: CNYRoom('河南')
                            },
                            {
                                name: '云南',
                                value: CNYRoom('云南')
                            },
                            {
                                name: '辽宁',
                                value: CNYRoom('辽宁')
                            },
                            {
                                name: '黑龙江',
                                value: CNYRoom('黑龙江')
                            },
                            {
                                name: '湖南',
                                value: CNYRoom('湖南')
                            },
                            {
                                name: '安徽',
                                value: CNYRoom('安徽')
                            },
                            {
                                name: '山东',
                                value: CNYRoom('山东')
                            },
                            {
                                name: '新疆',
                                value: CNYRoom('新疆')
                            },
                            {
                                name: '江苏',
                                value: CNYRoom('江苏')
                            },
                            {
                                name: '浙江',
                                value: CNYRoom('浙江')
                            },
                            {
                                name: '江西',
                                value: CNYRoom('江西')
                            },
                            {
                                name: '湖北',
                                value: CNYRoom('湖北')
                            },
                            {
                                name: '广西',
                                value: CNYRoom('广西')
                            },
                            {
                                name: '甘肃',
                                value: CNYRoom('甘肃')
                            },
                            {
                                name: '山西',
                                value: CNYRoom('山西')
                            },
                            {
                                name: '内蒙古',
                                value: CNYRoom('内蒙古')
                            },
                            {
                                name: '陕西',
                                value: CNYRoom('陕西')
                            },
                            {
                                name: '吉林',
                                value: CNYRoom('吉林')
                            },
                            {
                                name: '福建',
                                value: CNYRoom('福建')
                            },
                            {
                                name: '贵州',
                                value: CNYRoom('贵州')
                            },
                            {
                                name: '广东',
                                value: CNYRoom('广东')
                            },
                            {
                                name: '青海',
                                value: CNYRoom('青海')
                            },
                            {
                                name: '西藏',
                                value: CNYRoom('西藏')
                            },
                            {
                                name: '四川',
                                value: CNYRoom('四川')
                            },
                            {
                                name: '宁夏',
                                value: CNYRoom('宁夏')
                            },
                            {
                                name: '海南',
                                value: CNYRoom('海南')
                            },
                            {
                                name: '台湾',
                                value: CNYRoom('台湾')
                            },
                            {
                                name: '香港',
                                value: CNYRoom('香港')
                            },
                            {
                                name: '澳门',
                                value: CNYRoom('澳门')
                            }
                        ]
                    }
                ]
            };

            var chart = echarts.init(document.getElementById("main"))
            chart.setOption(option);
        }

    }
})