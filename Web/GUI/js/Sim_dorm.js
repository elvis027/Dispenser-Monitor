window.onload = function() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState==4 && this.status==200){
            document.getElementById("total").style.display = "block";
            document.getElementById("loading").style.display = "none";
            document.getElementById("chart1").style.display = "block";
            document.getElementById("chart2").style.display = "block";
            document.getElementById("title").innerHTML = "清大信齋";
            document.getElementById("remaining").innerHTML = "酒精估計剩餘量";
            var subtitle = document.createElement("span");
            subtitle.innerHTML = "乾洗手機";
            document.getElementById("title").appendChild(subtitle);
            var obj = JSON.parse(this.responseText);
            document.getElementById("times-total").innerHTML = "1/" + obj[0].created_at[8] + obj[0].created_at[9] + "~" + "1/14" + "使用總次數: "+ obj.length + "次";
            var left = Math.round((1 - (obj.length/2000)*5)*100);
            var progress_bar = document.createElement("div");                               //
            progress_bar.setAttribute("class","progress");                                  //
            var remain_bar = document.createElement("div");                                 //顯示剩餘量
            remain_bar.setAttribute("class","progress-bar progress-bar-striped active");    //
            remain_bar.setAttribute("role","progressbar");                                  //
            remain_bar.setAttribute("aria-valuemin",0);                                     //
            remain_bar.setAttribute("aria-valuemax",100);                                   //
            remain_bar.setAttribute("aria-valuenow",left);                                  //
            remain_bar.setAttribute("style","width:"+left+"%");                             //
            document.getElementById("prog").appendChild(progress_bar);                      //
            progress_bar.appendChild(remain_bar);                                           //
            remain_bar.innerHTML = left + "%";                                              //
            var ctx1 = document.getElementById("times-per-day");
            var ctx2 = document.getElementById("times-per-hr");
            var date = [];//日期
            var first_date = parseInt(obj[0].created_at.substr(8,2));
            var days = 14 - first_date + 1;
            for(let i=0;i<days;i++){
                var d = first_date++;
                date.push("1/" + d);
            }
            var times = []; //每天使用次數
            for(let i=0;i<date.length;i++){
                times.push(0);
            }
            var freq = []; //每小時使用次數
            for(let i=0;i<24;i++){
                freq.push(0);
            }
            for(let i=0;i<obj.length;i++){
                times[parseInt(obj[i].created_at.substr(8,2)-obj[0].created_at.substr(8,2))]++;
                freq[parseInt(obj[i].created_at.substr(11,2))]++;
            }
            new Chart(ctx1, {
                // 參數設定[註1]
                type: "line", // 圖表類型
                data: {
                    labels: date, // 標題
                    datasets: [{
                        label: "每天使用次數", // 標籤
                        data: times, // 資料
                        fill: false,
                        lineTension:0.1,
                        borderColor: 'rgba(110,25,250,1)',
                        borderWidth: 2 // 外框寬度
                    }]
                },
                options:{
                    maintainAspectRatio: false,
                    scales: {
                        yAxes: [{
                            display: true,
                            ticks:{
                                suggestedMin: 20,
                                suggestedMax: 50,
                            }
                        }]
                    }
                }
            });
            new Chart(ctx2, {
                // 參數設定[註1]
                type: "bar", // 圖表類型
                data: {
                    labels: ["00:00~00:59",
                     "01:00~01:59",
                     "02:00~02:59", 
                     "03:00~03:59", 
                     "04:00~04:59", 
                     "05:00~05:59", 
                     "06:00~06:59", 
                     "07:00~07:59", 
                     "08:00~08:59", 
                     "09:00~09:59", 
                     "10:00~10:59", 
                     "11:00~11:59", 
                     "12:00~12:59",
                     "13:00~13:59",
                     "14:00~14:59", 
                     "15:00~15:59", 
                     "16:00~16:59", 
                     "17:00~17:59", 
                     "18:00~18:59", 
                     "19:00~19:59", 
                     "20:00~20:59", 
                     "21:00~21:59", 
                     "22:00~22:59", 
                     "23:00~23:59"], // 標題
                    datasets: [{
                        label: "每小時使用次數", // 標籤
                        data: freq, // 資料
                        fill: false,
                        lineTension:0.1,
                        borderColor: 'rgba(110,25,250,1)',
                        borderWidth: 2 // 外框寬度
                    }]
                },
                options:{
                    maintainAspectRatio: false,
                    scales: {
                        yAxes: [{
                            display: true,
                            ticks:{
                                suggestedMin: 0,
                                suggestedMax: 30,
                            }
                        }]
                    }
                }
            });
            document.getElementById("total-per-day").innerHTML = "每天每時段分布";
            for(let i=0;i<date.length;i++){
                var btn = document.getElementById("accordion");
                var div1 = document.createElement("div");
                div1.setAttribute("class","panel panel-default");
                var div2 = document.createElement("div");
                div2.setAttribute("class","panel-heading");
                var head4 = document.createElement("div");
                head4.setAttribute("class","panel-title");
                var atag = document.createElement("a");
                atag.setAttribute("data-toggle","collapse");
                atag.setAttribute("data-parent","#accordion");
                atag.setAttribute("href","#collapse"+ (i+1).toString());
                var div3 = document.createElement("div");
                div3.setAttribute("id","collapse"+(i+1).toString());
                div3.setAttribute("class","panel-collapse collapse");
                var div4 = document.createElement("div");
                div4.setAttribute("class","panel-body");
                div4.setAttribute("id",i);
                var TextNode = document.createTextNode(date[i]);
                btn.appendChild(div1);
                div1.appendChild(div2);
                div2.appendChild(head4);
                head4.appendChild(atag);
                atag.appendChild(TextNode);
                div1.appendChild(div3);
                div3.appendChild(div4);
                MyFunction(date[i],obj,times,i);
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/final/GUI/php/Sim_dorm.php",true);
    xhttp.send(null);
}

function MyFunction(text,obj,times,num) {
    var content = document.getElementById(num);
    var head4 = document.createElement("h4");
    head4.innerHTML = text + " 每小時分布圖";
    var div1 = document.createElement("div");
    var chart = document.createElement("canvas");
    div1.setAttribute("class","chart-container");
    div1.style.position = "relative";
    div1.style.height = "60vh";
    div1.style.width = "90vh";
    div1.style.margin = "auto";
    chart.width = 400;
    chart.height = 200;
    chart.setAttribute("id","times-" + text);
    content.appendChild(head4);
    content.appendChild(div1);
    div1.appendChild(chart);
    var ctx = document.getElementById("times-" + text);
    var freq = []; //每小時使用次數
    for(let i=0;i<24;i++){
        freq.push(0);
    }
    var last_times = 0;
    var lastest = 0;
    for(let i=0;i<=num;i++){
        if(i==num){
            lastest += times[i];
        }
        else{
            lastest += times[i];
            last_times += times[i];
        }
    }
    for(let i=last_times;i<lastest;i++){
        freq[parseInt(obj[i].created_at.substr(11,2))]++;
    }
    new Chart(ctx, {
        // 參數設定[註1]
        type: "bar", // 圖表類型
        data: {
            labels: ["00:00~00:59",
             "01:00~01:59",
             "02:00~02:59", 
             "03:00~03:59", 
             "04:00~04:59", 
             "05:00~05:59", 
             "06:00~06:59", 
             "07:00~07:59", 
             "08:00~08:59", 
             "09:00~09:59", 
             "10:00~10:59", 
             "11:00~11:59", 
             "12:00~12:59",
             "13:00~13:59",
             "14:00~14:59", 
             "15:00~15:59", 
             "16:00~16:59", 
             "17:00~17:59", 
             "18:00~18:59", 
             "19:00~19:59", 
             "20:00~20:59", 
             "21:00~21:59", 
             "22:00~22:59", 
             "23:00~23:59"], // 標題
            datasets: [{
                label: "每小時使用次數", // 標籤
                data: freq, // 資料
                fill: false,
                lineTension:0.1,
                borderColor: 'rgba(110,25,250,1)',
                borderWidth: 2 // 外框寬度
            }]
        },
        options:{
            maintainAspectRatio: false,
            scales: {
                yAxes: [{
                    display: true,
                    ticks:{
                        suggestedMin: 0,
                        suggestedMax: 25,
                    }
                }]
            }
        }
    });
    var total = document.createElement("h4");
    total.innerHTML = "總使用次數:" + times[num];
    content.appendChild(total);
}