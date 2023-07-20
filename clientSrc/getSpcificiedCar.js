function navigateTopage0() {
    window.location.href = "A-headpage.html";
}
function setMark() {
    let panelMark = document.querySelector(".panel-mark");
    let panelItem = "";
    for (let i = 0; i < 60; i++) {
        panelItem += `<li class="panel-item" style="transform: rotate(${i * 6}deg)"><span class="mark-num"></span></li>`;
        panelMark.innerHTML = panelItem;
    }
    let markNum = document.querySelectorAll(".mark-num");
    for (let i = 0; i < markNum.length; i++) {
        if (i * 4 < markNum.length) {
            markNum[i * 4].innerHTML = 20 * i;
        }
    }
}
setMark();
let panelHand = document.querySelector(".panel-hand");
let panelSpeed = document.querySelector(".panel-speed");
let panelSpeed2 = document.querySelector(".panel-speed2");
function speedup(deg) {

    panelHand.style = `transform: rotate(${deg}deg)`;
    panelHand.style.transition = 'transform 0.5s ease-in-out';
}
// 获取车门元素
const ltop = document.getElementById("ltop");
const rtop = document.getElementById("rtop");
const lbot = document.getElementById("lbot");
const rbot = document.getElementById("rbot");
let doorState1 = 0; // 初始状态为关门（0度）
let doorState2 = 0; // 初始状态为关门（0度）
let doorState3 = 0; // 初始状态为关门（0度）
let doorState4 = 0; // 初始状态为关门（0度）

// 监听表单输入状态变化
const radioButtons1 = document.querySelectorAll('input[name="doorState1"]');
const radioButtons2 = document.querySelectorAll('input[name="doorState2"]');
const radioButtons3 = document.querySelectorAll('input[name="doorState3"]');
const radioButtons4 = document.querySelectorAll('input[name="doorState4"]');
//左上门

let speedDeg = 0,
    flagDown = true,
    timerDown,
    timerUp,
    kmh = 0;
var requestData = {};
var requestDatabrake = {};
var requestDataaccelerate = {};
var requestDoor = {};
let doordate = 0;//当且仅当为0的时候没有车门打开
let brakedate = 0;//当且仅当为0的时候打开了手刹 可以加速

// const serAdd = "http://www.016imcn.love:4567"
// const serAdd = "http://172.18.3.218:4567"
const serAdd = "http://127.0.0.1:4567"


$(document).ready(function () {
    // $('#myForm').submit(function (event) {
    // event.preventDefault(); // 阻止表单默认提交行为

    // var userInput = $('#caridInput').val(); // 获取输入字段的值
    // requestData.id = userInput;
    // requestDatabrake.id = userInput;
    // requestDataaccelerate.id = userInput;


    //==================================
    //获取信息
    //=================================


    $.ajax({
        type: "get",
        url: serAdd + "/getCarInfo",
        dataType: "json",
        data: requestData,
        success: function (response) {
            console.log(response)
            $.ajax({
                type: "get",
                url: serAdd + "/v_setLight?value=" + response.data.light,
                success: function (response) {

                }

            })

            $.ajax({
                type: "get",
                url: serAdd + "/v_setSpeed?value=" + Math.round(response.data.speed),
                success: function (response) {

                }

            })
            $.ajax({
                type: "get",
                url: serAdd + "/v_setPower?value=" + Math.round(response.data.power),
                success: function (response) {

                }

            })
            $.ajax({
                type: "get",
                url: serAdd + "/v_setGear?value=" + response.data.gear,
                success: function (response) {

                }

            })

            $.ajax({
                type: "get",
                url: serAdd + "/v_setMileage?value=" + 82455,
                success: function (response) {

                }

            })
            console.log(response);
            //=========================================light
            $(".lightSwitch").removeClass("active3");

            if (response.data.light === 1) {
                // $(".lightSwitch").removeClass("active3");
                $("#fog").addClass("active3");
            }
            else if (response.data.light === 2) {
                $("#high").addClass("active3");
            }
            else if (response.data.light === 4) {
                $("#dipped").addClass("active3");
            }
            else if (response.data.light === 8) {
                $("#clearance").addClass("active3");
            }
            else if (response.data.light === 0) {
                $(".lightSwich").removeClass("active3")
            }

            //===================================================

            //==========================================brake
            if (response.data.braking === 1) {
                brakedate = 1;
                $(".brake").removeClass(".brake");
                $(".brake").addClass("active2");
            }
            else if (response.data.braking === 0) {
                $(".brake").removeClass("active2");
                brakedate = 0;
            }
            //====================================================

            //==========================================battery
            $('.capacity').css({
                width: `${response.data.power * 35 * 0.01}` + 'px'
            })
            var power = Math.round(response.data.power)
            $('.batterylevel').text(`${power}` + '%')
            //====================================================

            //==========================================gear
            $(".gears div").removeClass("active");
            if (response.data.gear === 0) {
                $("#p").addClass("active");
            }
            else if (response.data.gear === 1) {
                $("#R").addClass("active");
            }
            else if (response.data.gear === 2) {
                $("#N").addClass("active");
            }
            else if (response.data.gear === 3) {
                $("#D").addClass("active");
            }
            else if (response.data.gear === 4) {
                $("#S").addClass("active");
            }
            else if (response.data.gear === 5) {
                $("#L").addClass("active");
            }
            //=====================================================

            //==========================================dashboard

            //===================================================
            var showspeed = Math.round(response.data.speed)
            panelSpeed.innerHTML = showspeed + 'km/h';
            // panelSpeed2.innerHTML = e.data.speed + 'km/h';
            speedDeg = response.data.speed / 0.82 - 145;
            speedup(speedDeg);
            if (response.data.turnLight === 0) {
                $("#leftlight, #rightlight").css("visibility", "hidden");
            } else if (response.data.turnLight === 3) {
                $("#leftlight, #rightlight")
                    .css("visibility", "visible")
                    .addClass("blink-lights");
            } else if (response.data.turnLight === 1) {
                $("#rightlight").css("visibility", "hidden");
                $("#leftlight")
                    .css("visibility", "visible")
                    .addClass("blink-lights");
            } else if (response.data.turnLight === 2) {
                $("#leftlight").css("visibility", "hidden");
                $("#rightlight")
                    .css("visibility", "visible")
                    .addClass("blink-lights");
            }

            //=============================================================
            $("#ltOpen").change(function () {
                $('input[name="doorState1"]').val(1);
                doorState1 = 1;
                openDoor1();
                var a = $('input[name="doorState1"]').val();
                var b = $('input[name="doorState3"]').val();
                var c = $('input[name="doorState2"]').val();
                var d = $('input[name="doorState4"]').val();
                requestDoor.door = a * 8 + b * 4 + c * 2 + d * 1;
                doordate = requestDoor.door;
                this.blur();
                $.ajax({
                    type: "get",
                    url: serAdd + "/shiftD",
                    dataType: "json",
                    data: requestDoor,
                    success: function (response) {

                    }
                })
            });
            $("#lbOpen").change(function () {
                $('input[name="doorState2"]').val(1);
                // console.log($('input[name="doorState2"]').val()) 
                doorState2 = 1;
                openDoor2();
                var a = $('input[name="doorState1"]').val();
                var b = $('input[name="doorState3"]').val();
                var c = $('input[name="doorState2"]').val();
                var d = $('input[name="doorState4"]').val();
                requestDoor.door = a * 8 + b * 4 + c * 2 + d * 1;
                doordate = requestDoor.door;
                this.blur();
                $.ajax({
                    type: "get",
                    url: serAdd + "/shiftD",
                    dataType: "json",
                    data: requestDoor,
                    success: function (response) {

                    }
                })
            });
            $("#rtOpen").change(function () {
                $('input[name="doorState3"]').val(1);
                // console.log($('input[name="doorState3"]').val()) 
                doorState3 = 1;
                openDoor3();
                var a = $('input[name="doorState1"]').val();
                var b = $('input[name="doorState3"]').val();
                var c = $('input[name="doorState2"]').val();
                var d = $('input[name="doorState4"]').val();
                requestDoor.door = a * 8 + b * 4 + c * 2 + d * 1;
                doordate = requestDoor.door;
                this.blur();
                $.ajax({
                    type: "get",
                    url: serAdd + "/shiftD",
                    dataType: "json",
                    data: requestDoor,
                    success: function (response) {

                    }
                })
            });
            $("#rbOpen").change(function () {
                $('input[name="doorState4"]').val(1);
                // console.log($('input[name="doorState4"]').val()) 
                doorState4 = 1;
                openDoor4();
                var a = $('input[name="doorState1"]').val();
                var b = $('input[name="doorState3"]').val();
                var c = $('input[name="doorState2"]').val();
                var d = $('input[name="doorState4"]').val();
                requestDoor.door = a * 8 + b * 4 + c * 2 + d * 1;
                doordate = requestDoor.door;
                this.blur();
                $.ajax({
                    type: "get",
                    url: serAdd + "/shiftD",
                    dataType: "json",
                    data: requestDoor,
                    success: function (response) {

                    }
                })
            });
            $("#ltClose").change(function () {
                $('input[name="doorState1"]').val(0);
                // console.log($('input[name="doorState1"]').val()) 
                doorState1 = 0;
                closeDoor1();
                var a = $('input[name="doorState1"]').val();
                var b = $('input[name="doorState3"]').val();
                var c = $('input[name="doorState2"]').val();
                var d = $('input[name="doorState4"]').val();
                requestDoor.door = a * 8 + b * 4 + c * 2 + d * 1;
                doordate = requestDoor.door;
                this.blur();
                $.ajax({
                    type: "get",
                    url: serAdd + "/shiftD",
                    dataType: "json",
                    data: requestDoor,
                    success: function (response) {

                    }
                })
            });
            $("#lbClose").change(function () {
                $('input[name="doorState2"]').val(0);
                // console.log($('input[name="doorState2"]').val()) 
                doorState2 = 0;
                closeDoor2();
                var a = $('input[name="doorState1"]').val();
                var b = $('input[name="doorState3"]').val();
                var c = $('input[name="doorState2"]').val();
                var d = $('input[name="doorState4"]').val();
                requestDoor.door = a * 8 + b * 4 + c * 2 + d * 1;
                doordate = requestDoor.door;
                this.blur();
                $.ajax({
                    type: "get",
                    url: serAdd + "/shiftD",
                    dataType: "json",
                    data: requestDoor,
                    success: function (response) {

                    }
                })
            });
            $("#rtClose").change(function () {
                $('input[name="doorState3"]').val(0);
                // console.log($('input[name="doorState3"]').val()) 
                doorState3 = 0;
                closeDoor3();
                var a = $('input[name="doorState1"]').val();
                var b = $('input[name="doorState3"]').val();
                var c = $('input[name="doorState2"]').val();
                var d = $('input[name="doorState4"]').val();
                requestDoor.door = a * 8 + b * 4 + c * 2 + d * 1;
                doordate = requestDoor.door;
                this.blur();
                $.ajax({
                    type: "get",
                    url: serAdd + "/shiftD",
                    dataType: "json",
                    data: requestDoor,
                    success: function (response) {

                    }
                })
            });
            $("#rbClose").change(function () {
                $('input[name="doorState4"]').val(0);
                // console.log($('input[name="doorState4"]').val()) 
                doorState4 = 0;
                closeDoor4();
                var a = $('input[name="doorState1"]').val();
                var b = $('input[name="doorState3"]').val();
                var c = $('input[name="doorState2"]').val();
                var d = $('input[name="doorState4"]').val();
                requestDoor.door = a * 8 + b * 4 + c * 2 + d * 1;
                doordate = requestDoor.door;
                this.blur();
                $.ajax({
                    type: "get",
                    url: serAdd + "/shiftD",
                    dataType: "json",
                    data: requestDoor,
                    success: function (response) {

                    }
                })
            });
            //车门开闭的16种情况
            if (response.data.door === 0) {
                $("#ltOpen").prop("checked", false);
                $("#ltClose").prop("checked", true).change();
                $("#rtOpen").prop("checked", false);
                $("#rtClose").prop("checked", true).change();
                $("#lbOpen").prop("checked", false);
                $("#lbClose").prop("checked", true).change();
                $("#rbOpen").prop("checked", false);
                $("#rbClose").prop("checked", true).change();
                //==================
                $('input[name="doorState1"]').val(0);
                $('input[name="doorState3"]').val(0);
                $('input[name="doorState2"]').val(0);
                $('input[name="doorState4"]').val(0);
            }
            if (response.data.door === 1) {
                $("#ltOpen").prop("checked", false);
                $("#ltClose").prop("checked", true).change();
                $("#rtOpen").prop("checked", false);
                $("#rtClose").prop("checked", true).change();
                $("#lbOpen").prop("checked", false);
                $("#lbClose").prop("checked", true).change();
                $("#rbOpen").prop("checked", true).change();
                $("#rbClose").prop("checked", false);
                //==================
                $('input[name="doorState1"]').val(0);
                $('input[name="doorState3"]').val(0);
                $('input[name="doorState2"]').val(0);
                $('input[name="doorState4"]').val(1);
            }
            if (response.data.door === 2) {
                $("#ltOpen").prop("checked", false);
                $("#ltClose").prop("checked", true).change();
                $("#rtOpen").prop("checked", false);
                $("#rtClose").prop("checked", true).change();
                $("#lbOpen").prop("checked", true).change();
                $("#lbClose").prop("checked", false);
                $("#rbOpen").prop("checked", false);
                $("#rbClose").prop("checked", true).change();
                //==================
                $('input[name="doorState1"]').val(0);
                $('input[name="doorState3"]').val(0);
                $('input[name="doorState2"]').val(1);
                $('input[name="doorState4"]').val(0);
            }
            if (response.data.door === 3) {
                $("#ltOpen").prop("checked", false);
                $("#ltClose").prop("checked", true).change();
                $("#rtOpen").prop("checked", false);
                $("#rtClose").prop("checked", true).change();
                $("#lbOpen").prop("checked", true).change();
                $("#lbClose").prop("checked", false);
                $("#rbOpen").prop("checked", true).change();
                $("#rbClose").prop("checked", false);
                //==================
                $('input[name="doorState1"]').val(0);
                $('input[name="doorState3"]').val(0);
                $('input[name="doorState2"]').val(1);
                $('input[name="doorState4"]').val(1);
            }
            if (response.data.door === 4) {
                $("#ltOpen").prop("checked", false);
                $("#ltClose").prop("checked", true).change();
                $("#rtOpen").prop("checked", true).change();
                $("#rtClose").prop("checked", false);
                $("#lbOpen").prop("checked", false);
                $("#lbClose").prop("checked", true).change();
                $("#rbOpen").prop("checked", false);
                $("#rbClose").prop("checked", true).change();
                //==================
                $('input[name="doorState1"]').val(0);
                $('input[name="doorState3"]').val(1);
                $('input[name="doorState2"]').val(0);
                $('input[name="doorState4"]').val(0);
            }
            if (response.data.door === 5) {
                $("#ltOpen").prop("checked", false);
                $("#ltClose").prop("checked", true).change();
                $("#rtOpen").prop("checked", true).change();
                $("#rtClose").prop("checked", false);
                $("#lbOpen").prop("checked", false);
                $("#lbClose").prop("checked", true).change();
                $("#rbOpen").prop("checked", true).change();
                $("#rbClose").prop("checked", false);
                //==================
                $('input[name="doorState1"]').val(0);
                $('input[name="doorState3"]').val(1);
                $('input[name="doorState2"]').val(0);
                $('input[name="doorState4"]').val(1);
            }
            if (response.data.door === 6) {
                $("#ltOpen").prop("checked", false);
                $("#ltClose").prop("checked", true).change();
                $("#rtOpen").prop("checked", true).change();
                $("#rtClose").prop("checked", false);
                $("#lbOpen").prop("checked", true).change();
                $("#lbClose").prop("checked", false);
                $("#rbOpen").prop("checked", false);
                $("#rbClose").prop("checked", true).change();
                //==================
                $('input[name="doorState1"]').val(0);
                $('input[name="doorState3"]').val(1);
                $('input[name="doorState2"]').val(1);
                $('input[name="doorState4"]').val(0);
            }
            if (response.data.door === 7) {
                $("#ltOpen").prop("checked", false);
                $("#ltClose").prop("checked", true).change();
                $("#rtOpen").prop("checked", true).change();
                $("#rtClose").prop("checked", false);
                $("#lbOpen").prop("checked", true).change();
                $("#lbClose").prop("checked", false);
                $("#rbOpen").prop("checked", true).change();
                $("#rbClose").prop("checked", false);
                //==================
                $('input[name="doorState1"]').val(0);
                $('input[name="doorState3"]').val(1);
                $('input[name="doorState2"]').val(1);
                $('input[name="doorState4"]').val(1);
            }
            if (response.data.door === 8) {
                $("#ltOpen").prop("checked", true).change();
                $("#ltClose").prop("checked", false);
                $("#rtOpen").prop("checked", false);
                $("#rtClose").prop("checked", true).change();
                $("#lbOpen").prop("checked", false);
                $("#lbClose").prop("checked", true).change();
                $("#rbOpen").prop("checked", false);
                $("#rbClose").prop("checked", true).change();
                //==================
                $('input[name="doorState1"]').val(1);
                $('input[name="doorState3"]').val(0);
                $('input[name="doorState2"]').val(0);
                $('input[name="doorState4"]').val(0);
                var x = document.getElementById('prompts');
                x.classList.add('show'); /* Update this line */
                x.innerHTML = '左侧车门me';
                //var overlay = document.getElementById("overlay");
                //overlay.classList.add("show");
                setTimeout(function () {
                    //overlay.classList.remove("show");
                    x.classList.remove('show'); /* Update this line */
                }, 3000); // 5秒后隐藏
                var utterance = new SpeechSynthesisUtterance(x.innerHTML);
                speechSynthesis.speak(utterance);
            }
            if (response.data.door === 9) {
                $("#ltOpen").prop("checked", true).change();
                $("#ltClose").prop("checked", false);
                $("#rtOpen").prop("checked", false);
                $("#rtClose").prop("checked", true).change();
                $("#lbOpen").prop("checked", false);
                $("#lbClose").prop("checked", true).change();
                $("#rbOpen").prop("checked", true).change();
                $("#rbClose").prop("checked", false);
                //==================
                $('input[name="doorState1"]').val(1);
                $('input[name="doorState3"]').val(0);
                $('input[name="doorState2"]').val(0);
                $('input[name="doorState4"]').val(1);
            }
            if (response.data.door === 10) {
                $("#ltOpen").prop("checked", true).change();
                $("#ltClose").prop("checked", false);
                $("#rtOpen").prop("checked", false);
                $("#rtClose").prop("checked", true).change();
                $("#lbOpen").prop("checked", true).change();
                $("#lbClose").prop("checked", false);
                $("#rbOpen").prop("checked", false);
                $("#rbClose").prop("checked", true).change();
                //==================
                $('input[name="doorState1"]').val(1);
                $('input[name="doorState3"]').val(0);
                $('input[name="doorState2"]').val(1);
                $('input[name="doorState4"]').val(0);
            }
            if (response.data.door === 11) {
                $("#ltOpen").prop("checked", true).change();
                $("#ltClose").prop("checked", false);
                $("#rtOpen").prop("checked", false);
                $("#rtClose").prop("checked", true).change();
                $("#lbOpen").prop("checked", true).change();
                $("#lbClose").prop("checked", false);
                $("#rbOpen").prop("checked", true).change();
                $("#rbClose").prop("checked", false);
                //==================
                $('input[name="doorState1"]').val(1);
                $('input[name="doorState3"]').val(0);
                $('input[name="doorState2"]').val(1);
                $('input[name="doorState4"]').val(1);
            }
            if (response.data.door === 12) {
                $("#ltOpen").prop("checked", true).change();
                $("#ltClose").prop("checked", false);
                $("#rtOpen").prop("checked", true).change();
                $("#rtClose").prop("checked", false);
                $("#lbOpen").prop("checked", false);
                $("#lbClose").prop("checked", true).change();
                $("#rbOpen").prop("checked", false);
                $("#rbClose").prop("checked", true).change();
                //==================
                $('input[name="doorState1"]').val(1);
                $('input[name="doorState3"]').val(1);
                $('input[name="doorState2"]').val(0);
                $('input[name="doorState4"]').val(0);
            }
            if (response.data.door === 13) {
                $("#ltOpen").prop("checked", true).change();
                $("#ltClose").prop("checked", false);
                $("#rtOpen").prop("checked", true).change();
                $("#rtClose").prop("checked", false);
                $("#lbOpen").prop("checked", false);
                $("#lbClose").prop("checked", true).change();
                $("#rbOpen").prop("checked", true).change();
                $("#rbClose").prop("checked", false);
                //==================
                $('input[name="doorState1"]').val(1);
                $('input[name="doorState3"]').val(1);
                $('input[name="doorState2"]').val(0);
                $('input[name="doorState4"]').val(1);
            }
            if (response.data.door === 14) {
                $("#ltOpen").prop("checked", true).change();
                $("#ltClose").prop("checked", false);
                $("#rtOpen").prop("checked", true).change();
                $("#rtClose").prop("checked", false);
                $("#lbOpen").prop("checked", true).change();
                $("#lbClose").prop("checked", false);
                $("#rbOpen").prop("checked", false);
                $("#rbClose").prop("checked", true).change();
                //==================
                $('input[name="doorState1"]').val(1);
                $('input[name="doorState3"]').val(1);
                $('input[name="doorState2"]').val(1);
                $('input[name="doorState4"]').val(0);
            }
            if (response.data.door === 15) {
                $("#ltOpen").prop("checked", true).change();
                $("#ltClose").prop("checked", false);
                $("#rtOpen").prop("checked", true).change();
                $("#rtClose").prop("checked", false);
                $("#lbOpen").prop("checked", true).change();
                $("#lbClose").prop("checked", false);
                $("#rbOpen").prop("checked", true).change();
                $("#rbClose").prop("checked", false);
                //==================
                $('input[name="doorState1"]').val(1);
                $('input[name="doorState3"]').val(1);
                $('input[name="doorState2"]').val(1);
                $('input[name="doorState4"]').val(1);
            }

            //======================================================
        }
    });
    // });
    //=======================减速
    var startTime;
    var isTiming = false;
    var animationFrameId = null;
    // 在按键按下时执行的代码
    document.addEventListener("keydown", function (event) {
        if (!isTiming && event.key === "ArrowDown") {
            // 获取按键开始时间
            startTime = performance.now();
            isTiming = true;
            if (!animationFrameId) {
                animationFrameId = requestAnimationFrame(updateTime);
            }
        }
    });

    // 在按键释放时执行的代码
    document.addEventListener("keyup", function (event) {
        if (isTiming && event.key === "ArrowDown") {
            // 获取按键结束时间
            var endTime = performance.now();

            // 计算按键持续时间（毫秒）
            var duration = endTime - startTime;

            //取整
            duration = Math.round(duration);
            // 将时间参数添加到requestData对象中
            requestDatabrake.time = duration;

            isTiming = false;
            // 取消动画帧请求
            cancelAnimationFrame(animationFrameId);
            animationFrameId = null;


            console.log(requestDatabrake);
            // 在控制台中打印requestData对象，以便查看结果

            $.ajax({
                type: "get",
                url: serAdd + "/brake",
                dataType: "json",
                data: requestDatabrake,
                success: function (response) {
                    var showspeed = Math.round(response.data.speed);
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setSpeed?value=" + showspeed,
                        success: function (response) {

                        }

                    })
                    panelSpeed.innerHTML = showspeed + 'km/h';
                    // panelSpeed2.innerHTML = e.data.speed + 'km/h';
                    speedDeg = response.data.speed / 0.82 - 145;
                    speedup(speedDeg);
                }
            })




        }
    });
    function updateTime(timestamp) {
        if (isTiming) {
            requestAnimationFrame(updateTime);
        }
    }
    $("#myForm2").submit(function (event) {
        event.preventDefault(); // 阻止表单默认提交行为
        requestDatabrake.strength = $('#carbrakeInput').val();
        console.log(requestDatabrake);
    })
    //====================================



    //====================================加速
    var startTimeup;
    var isTimingup = false;
    var animationFrameId2 = null;
    document.addEventListener("keydown", function (event) {
        if (brakedate == 0 && doordate == 0 && !isTimingup && event.key === "ArrowUp") {
            // 获取按键开始时间
            startTimeup = performance.now();
            isTimingup = true;
            if (!animationFrameId2) {
                animationFrameId2 = requestAnimationFrame(updateTime);
            }
        }
        if (brakedate == 1 && doordate == 0 && event.key === "ArrowUp") {
            var x = document.getElementById('prompts');
            x.classList.add('show'); /* Update this line */
            x.innerHTML = '请关闭刹车再加速';
            var utterance = new SpeechSynthesisUtterance(x.innerHTML);
            speechSynthesis.speak(utterance);
            setTimeout(function () {
                x.classList.remove('show'); /* Update this line */
            }, 3000);
        }
        if (brakedate == 0 && doordate != 0 && event.key === "ArrowUp") {
            var x = document.getElementById('prompts');
            x.classList.add('show'); /* Update this line */
            x.innerHTML = '您的车门没关紧';
            var utterance = new SpeechSynthesisUtterance(x.innerHTML);
            speechSynthesis.speak(utterance);
            setTimeout(function () {
                x.classList.remove('show'); /* Update this line */
            }, 3000);
        }
        if (brakedate != 0 && doordate != 0 && event.key === "ArrowUp") {
            var x = document.getElementById('prompts');
            x.classList.add('show'); /* Update this line */
            x.innerHTML = '请打好刹车关好门再加速';
            var utterance = new SpeechSynthesisUtterance(x.innerHTML);
            speechSynthesis.speak(utterance);
            setTimeout(function () {
                x.classList.remove('show'); /* Update this line */
            }, 3000);
        }
    });

    // 在按键释放时执行的代码
    document.addEventListener("keyup", function (event) {
        if (brakedate == 0 && doordate == 0 && isTimingup && event.key === "ArrowUp") {
            // 获取按键结束时间
            var endTime = performance.now();

            // 计算按键持续时间（毫秒）
            var duration = endTime - startTimeup;

            //取整
            duration = Math.round(duration);
            // 将时间参数添加到requestData对象中
            requestDataaccelerate.time = duration;

            isTimingup = false;
            // 取消动画帧请求
            cancelAnimationFrame(animationFrameId2);
            animationFrameId = null;


            console.log(requestDataaccelerate);
            // 在控制台中打印requestData对象，以便查看结果

            $.ajax({
                type: "get",
                url: serAdd + "/accelerate",
                dataType: "json",
                data: requestDataaccelerate,
                success: function (response) {
                    var showspeed = Math.round(response.data.speed);
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setSpeed?value=" + showspeed,
                        success: function (response) {

                        }

                    })
                    panelSpeed.innerHTML = showspeed + 'km/h';
                    // panelSpeed2.innerHTML = e.data.speed + 'km/h';
                    speedDeg = response.data.speed / 0.82 - 145;
                    speedup(speedDeg);
                }
            })


        }
    });
    function updateTime(timestamp) {
        if (isTimingup) {
            requestAnimationFrame(updateTime);
        }
    }
    $("#myForm3").submit(function (event) {
        event.preventDefault(); // 阻止表单默认提交行为
        requestDataaccelerate.strength = $('#caraccelerateInput').val();
        console.log(requestDataaccelerate);
    })

    //====================================刹车状态
    document.addEventListener("keyup", function (event) {
        if (event.key === "q") {
            $.ajax({
                type: "get",
                url: serAdd + "/shiftB",
                dataType: "json",
                // data: requestDatabrake,
                success: function (response) {
                    console.log(response);
                    if (response.data.braking === 1) {
                        brakedate = 1;
                        $(".brake").removeClass(".brake");
                        $(".brake").addClass("active2");
                    }
                    else if (response.data.braking === 0) {
                        brakedate = 0;
                        $(".brake").removeClass("active2");
                    }
                }
            })
        }
    })
    $(".brake").click(function () {
        $.ajax({
            type: "get",
            url: serAdd + "/shiftB",
            dataType: "json",
            // data: requestDatabrake,
            success: function (response) {
                console.log(response);
                if (response.data.braking === 1) {
                    brakedate = 1;
                    $(".brake").removeClass(".brake");
                    $(".brake").addClass("active2");
                }
                else if (response.data.braking === 0) {
                    brakedate = 0;
                    $(".brake").removeClass("active2");
                }
            }
        })
    })
    //============================================


    //============================================挡位信息
    document.addEventListener("keyup", function (event) {
        if (event.key === "p") {
            $.ajax({
                type: "get",
                url: serAdd + "/shiftG",
                dataType: "json",
                data: { gear: 0 },
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setGear?value=" + 0,
                        success: function (response) {

                        }

                    })
                    console.log(response);
                    $(".gears div").removeClass("active");
                    if (response.data.gear === 0) {
                        $("#p").addClass("active");
                    }
                    else if (response.data.gear === 1) {
                        $("#R").addClass("active");
                    }
                    else if (response.data.gear === 2) {
                        $("#N").addClass("active");
                    }
                    else if (response.data.gear === 3) {
                        $("#D").addClass("active");
                    }
                    else if (response.data.gear === 4) {
                        $("#S").addClass("active");
                    }
                    else if (response.data.gear === 5) {
                        $("#L").addClass("active");
                    }
                }
            })
        }
    })
    document.addEventListener("keyup", function (event) {

        if (event.key === "r") {
            $.ajax({
                type: "get",
                url: serAdd + "/shiftG",
                dataType: "json",
                data: { gear: 1 },
                success: function (response) {
                    // console.log(response);
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setGear?value=" + 1,
                        success: function (response) {

                        }

                    })
                    $(".gears div").removeClass("active");
                    if (response.data.gear === 0) {
                        $("#p").addClass("active");
                    }
                    else if (response.data.gear === 1) {
                        $("#R").addClass("active");
                    }
                    else if (response.data.gear === 2) {
                        $("#N").addClass("active");
                    }
                    else if (response.data.gear === 3) {
                        $("#D").addClass("active");
                    }
                    else if (response.data.gear === 4) {
                        $("#S").addClass("active");
                    }
                    else if (response.data.gear === 5) {
                        $("#L").addClass("active");
                    }
                }
            })
        }
    })
    document.addEventListener("keyup", function (event) {
        if (event.key === "n") {
            $.ajax({
                type: "get",
                url: serAdd + "/shiftG",
                dataType: "json",
                data: { gear: 2 },
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setGear?value=" + 2,
                        success: function (response) {

                        }

                    })
                    $(".gears div").removeClass("active");
                    if (response.data.gear === 0) {
                        $("#p").addClass("active");
                    }
                    else if (response.data.gear === 1) {
                        $("#R").addClass("active");
                    }
                    else if (response.data.gear === 2) {
                        $("#N").addClass("active");
                    }
                    else if (response.data.gear === 3) {
                        $("#D").addClass("active");
                    }
                    else if (response.data.gear === 4) {
                        $("#S").addClass("active");
                    }
                    else if (response.data.gear === 5) {
                        $("#L").addClass("active");
                    }
                }
            })
        }
    })
    document.addEventListener("keyup", function (event) {
        if (event.key === "d") {
            $.ajax({
                type: "get",
                url: serAdd + "/shiftG",
                dataType: "json",
                data: { gear: 3 },
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setGear?value=" + 3,
                        success: function (response) {

                        }

                    })
                    $(".gears div").removeClass("active");
                    if (response.data.gear === 0) {
                        $("#p").addClass("active");
                    }
                    else if (response.data.gear === 1) {
                        $("#R").addClass("active");
                    }
                    else if (response.data.gear === 2) {
                        $("#N").addClass("active");
                    }
                    else if (response.data.gear === 3) {
                        $("#D").addClass("active");
                    }
                    else if (response.data.gear === 4) {
                        $("#S").addClass("active");
                    }
                    else if (response.data.gear === 5) {
                        $("#L").addClass("active");
                    }
                }
            })
        }
    })
    document.addEventListener("keyup", function (event) {
        if (event.key === "s") {
            $.ajax({
                type: "get",
                url: serAdd + "/shiftG",
                dataType: "json",
                data: { gear: 4 },
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setGear?value=" + 4,
                        success: function (response) {

                        }

                    })
                    $(".gears div").removeClass("active");
                    if (response.data.gear === 0) {
                        $("#p").addClass("active");
                    }
                    else if (response.data.gear === 1) {
                        $("#R").addClass("active");
                    }
                    else if (response.data.gear === 2) {
                        $("#N").addClass("active");
                    }
                    else if (response.data.gear === 3) {
                        $("#D").addClass("active");
                    }
                    else if (response.data.gear === 4) {
                        $("#S").addClass("active");
                    }
                    else if (response.data.gear === 5) {
                        $("#L").addClass("active");
                    }
                }
            })
        }
    })
    document.addEventListener("keyup", function (event) {
        if (event.key === "l") {
            $.ajax({
                type: "get",
                url: serAdd + "/shiftG",
                dataType: "json",
                data: { gear: 5 },
                success: function (response) {
                    // console.log(response);
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setGear?value=" + 5,
                        success: function (response) {

                        }

                    })
                    $(".gears div").removeClass("active");
                    if (response.data.gear === 0) {
                        $("#p").addClass("active");
                    }
                    else if (response.data.gear === 1) {
                        $("#R").addClass("active");
                    }
                    else if (response.data.gear === 2) {
                        $("#N").addClass("active");
                    }
                    else if (response.data.gear === 3) {
                        $("#D").addClass("active");
                    }
                    else if (response.data.gear === 4) {
                        $("#S").addClass("active");
                    }
                    else if (response.data.gear === 5) {
                        $("#L").addClass("active");
                    }
                }
            })
        }
    })
    //鼠标点击
    $("#p").click(function () {
        $.ajax({
            type: "get",
            url: serAdd + "/shiftG",
            dataType: "json",
            data: { gear: 0 },
            success: function (response) {
                // console.log(response);
                $.ajax({
                    type: "get",
                    url: serAdd + "/v_setGear?value=" + 0,
                    success: function (response) {

                    }

                })
                $(".gears div").removeClass("active");
                if (response.data.gear === 0) {
                    $("#p").addClass("active");
                }
                else if (response.data.gear === 1) {
                    $("#R").addClass("active");
                }
                else if (response.data.gear === 2) {
                    $("#N").addClass("active");
                }
                else if (response.data.gear === 3) {
                    $("#D").addClass("active");
                }
                else if (response.data.gear === 4) {
                    $("#S").addClass("active");
                }
                else if (response.data.gear === 5) {
                    $("#L").addClass("active");
                }
            }
        })
    })
    $("#R").click(function () {
        $.ajax({
            type: "get",
            url: serAdd + "/shiftG",
            dataType: "json",
            data: { gear: 1 },
            success: function (response) {
                // console.log(response);
                $.ajax({
                    type: "get",
                    url: serAdd + "/v_setGear?value=" + 1,
                    success: function (response) {

                    }

                })
                $(".gears div").removeClass("active");
                if (response.data.gear === 0) {
                    $("#p").addClass("active");
                }
                else if (response.data.gear === 1) {
                    $("#R").addClass("active");
                }
                else if (response.data.gear === 2) {
                    $("#N").addClass("active");
                }
                else if (response.data.gear === 3) {
                    $("#D").addClass("active");
                }
                else if (response.data.gear === 4) {
                    $("#S").addClass("active");
                }
                else if (response.data.gear === 5) {
                    $("#L").addClass("active");
                }
            }
        })
    })
    $("#N").click(function () {
        $.ajax({
            type: "get",
            url: serAdd + "/shiftG",
            dataType: "json",
            data: { gear: 2 },
            success: function (response) {
                // console.log(response);
                $.ajax({
                    type: "get",
                    url: serAdd + "/v_setGear?value=" + 2,
                    success: function (response) {

                    }

                })
                $(".gears div").removeClass("active");
                if (response.data.gear === 0) {
                    $("#p").addClass("active");
                }
                else if (response.data.gear === 1) {
                    $("#R").addClass("active");
                }
                else if (response.data.gear === 2) {
                    $("#N").addClass("active");
                }
                else if (response.data.gear === 3) {
                    $("#D").addClass("active");
                }
                else if (response.data.gear === 4) {
                    $("#S").addClass("active");
                }
                else if (response.data.gear === 5) {
                    $("#L").addClass("active");
                }
            }
        })
    })
    $("#D").click(function () {
        $.ajax({
            type: "get",
            url: serAdd + "/shiftG",
            dataType: "json",
            data: { gear: 3 },
            success: function (response) {
                // console.log(response);
                $.ajax({
                    type: "get",
                    url: serAdd + "/v_setGear?value=" + 3,
                    success: function (response) {

                    }

                })
                $(".gears div").removeClass("active");
                if (response.data.gear === 0) {
                    $("#p").addClass("active");
                }
                else if (response.data.gear === 1) {
                    $("#R").addClass("active");
                }
                else if (response.data.gear === 2) {
                    $("#N").addClass("active");
                }
                else if (response.data.gear === 3) {
                    $("#D").addClass("active");
                }
                else if (response.data.gear === 4) {
                    $("#S").addClass("active");
                }
                else if (response.data.gear === 5) {
                    $("#L").addClass("active");
                }
            }
        })
    })
    $("#S").click(function () {
        $.ajax({
            type: "get",
            url: serAdd + "/shiftG",
            dataType: "json",
            data: { gear: 4 },
            success: function (response) {
                // console.log(response);
                $.ajax({
                    type: "get",
                    url: serAdd + "/v_setGear?value=" + 4,
                    success: function (response) {

                    }

                })
                $(".gears div").removeClass("active");
                if (response.data.gear === 0) {
                    $("#p").addClass("active");
                }
                else if (response.data.gear === 1) {
                    $("#R").addClass("active");
                }
                else if (response.data.gear === 2) {
                    $("#N").addClass("active");
                }
                else if (response.data.gear === 3) {
                    $("#D").addClass("active");
                }
                else if (response.data.gear === 4) {
                    $("#S").addClass("active");
                }
                else if (response.data.gear === 5) {
                    $("#L").addClass("active");
                }
            }
        })
    })
    $("#L").click(function () {
        $.ajax({
            type: "get",
            url: serAdd + "/shiftG",
            dataType: "json",
            data: { gear: 5 },
            success: function (response) {
                // console.log(response);
                $.ajax({
                    type: "get",
                    url: serAdd + "/v_setGear?value=" + 5,
                    success: function (response) {

                    }

                })
                $(".gears div").removeClass("active");
                if (response.data.gear === 0) {
                    $("#p").addClass("active");
                }
                else if (response.data.gear === 1) {
                    $("#R").addClass("active");
                }
                else if (response.data.gear === 2) {
                    $("#N").addClass("active");
                }
                else if (response.data.gear === 3) {
                    $("#D").addClass("active");
                }
                else if (response.data.gear === 4) {
                    $("#S").addClass("active");
                }
                else if (response.data.gear === 5) {
                    $("#L").addClass("active");
                }
            }
        })
    })
    //======================================================

    //======================================================车灯变换
    document.addEventListener("keyup", function (event) {
        if (event.key === "z") {
            $.ajax({
                type: "get",
                url: serAdd + "/shiftL",
                dataType: "json",
                data: { light: 1 },
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setLight?value=" + 1,
                        success: function (response) {

                        }

                    })
                    $(".lightSwitch").removeClass("active3");
                    if (response.data.light === 1) {
                        // $(".lightSwitch").removeClass("active3");
                        $("#fog").addClass("active3");
                    }
                    else if (response.data.light === 2) {
                        $("#high").addClass("active3");
                    }
                    else if (response.data.light === 4) {
                        $("#dipped").addClass("active3");
                    }
                    else if (response.data.light === 8) {
                        $("#clearance").addClass("active3");
                    }
                    else if (response.data.light === 0) {
                        $(".lightSwitch").removeClass("active3");
                    }
                }
            })
        }
    })
    document.addEventListener("keyup", function (event) {
        if (event.key === "x") {
            $.ajax({
                type: "get",
                url: serAdd + "/shiftL",
                dataType: "json",
                data: { light: 2 },
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setLight?value=" + 2,
                        success: function (response) {

                        }

                    })
                    $(".lightSwitch").removeClass("active3");
                    if (response.data.light === 1) {
                        // $(".lightSwitch").removeClass("active3");
                        $("#fog").addClass("active3");
                    }
                    else if (response.data.light === 2) {
                        $("#high").addClass("active3");
                    }
                    else if (response.data.light === 4) {
                        $("#dipped").addClass("active3");
                    }
                    else if (response.data.light === 8) {
                        $("#clearance").addClass("active3");
                    }
                    else if (response.data.light === 0) {
                        $(".lightSwitch").removeClass("active3");
                    }
                }
            })
        }
    })
    document.addEventListener("keyup", function (event) {
        if (event.key === "c") {
            $.ajax({
                type: "get",
                url: serAdd + "/shiftL",
                dataType: "json",
                data: { light: 4 },
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setLight?value=" + 4,
                        success: function (response) {

                        }

                    })
                    $(".lightSwitch").removeClass("active3");
                    if (response.data.light === 1) {
                        // $(".lightSwitch").removeClass("active3");
                        $("#fog").addClass("active3");
                    }
                    else if (response.data.light === 2) {
                        $("#high").addClass("active3");
                    }
                    else if (response.data.light === 4) {
                        $("#dipped").addClass("active3");
                    }
                    else if (response.data.light === 8) {
                        $("#clearance").addClass("active3");
                    }
                    else if (response.data.light === 0) {
                        $(".lightSwitch").removeClass("active3");
                    }
                }
            })
        }
    })
    document.addEventListener("keyup", function (event) {
        if (event.key === "v") {
            $.ajax({
                type: "get",
                url: serAdd + "/shiftL",
                dataType: "json",
                data: { light: 8 },
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setLight?value=" + 8,
                        success: function (response) {

                        }

                    })
                    $(".lightSwitch").removeClass("active3");
                    if (response.data.light === 1) {
                        // $(".lightSwitch").removeClass("active3");
                        $("#fog").addClass("active3");
                    }
                    else if (response.data.light === 2) {
                        $("#high").addClass("active3");
                    }
                    else if (response.data.light === 4) {
                        $("#dipped").addClass("active3");
                    }
                    else if (response.data.light === 8) {
                        $("#clearance").addClass("active3");
                    }
                    else if (response.data.light === 0) {
                        $(".lightSwitch").removeClass("active3");
                    }
                }
            })
        }
    })
    document.addEventListener("keyup", function (event) {
        if (event.key === "b") {
            $.ajax({
                type: "get",
                url: serAdd + "/shiftL",
                dataType: "json",
                data: { light: 0 },
                success: function (response) {
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setLight?value=" + 0,
                        success: function (response) {

                        }

                    })
                    $(".lightSwitch").removeClass("active3");
                    if (response.data.light === 1) {
                        // $(".lightSwitch").removeClass("active3");
                        $("#fog").addClass("active3");
                    }
                    else if (response.data.light === 2) {
                        $("#high").addClass("active3");
                    }
                    else if (response.data.light === 4) {
                        $("#dipped").addClass("active3");
                    }
                    else if (response.data.light === 8) {
                        $("#clearance").addClass("active3");
                    }
                    else if (response.data.light === 0) {
                        $(".lightSwitch").removeClass("active3");
                    }
                }
            })
        }
    })
    $("#fog").click(function () {
        $.ajax({
            type: "get",
            url: serAdd + "/shiftL",
            dataType: "json",
            data: { light: 1 },
            success: function (response) {
                $.ajax({
                    type: "get",
                    url: serAdd + "/v_setLight?value=" + 1,
                    success: function (response) {

                    }

                })
                $(".lightSwitch").removeClass("active3");
                if (response.data.light === 1) {
                    // $(".lightSwitch").removeClass("active3");
                    $("#fog").addClass("active3");
                }
                else if (response.data.light === 2) {
                    $("#high").addClass("active3");
                }
                else if (response.data.light === 4) {
                    $("#dipped").addClass("active3");
                }
                else if (response.data.light === 8) {
                    $("#clearance").addClass("active3");
                }
                else if (response.data.light === 0) {
                    $(".lightSwitch").removeClass("active3");
                }
            }
        })
    })
    $("#high").click(function () {
        $.ajax({
            type: "get",
            url: serAdd + "/shiftL",
            dataType: "json",
            data: { light: 2 },
            success: function (response) {
                $.ajax({
                    type: "get",
                    url: serAdd + "/v_setLight?value=" + 2,
                    success: function (response) {

                    }

                })
                $(".lightSwitch").removeClass("active3");
                if (response.data.light === 1) {
                    // $(".lightSwitch").removeClass("active3");
                    $("#fog").addClass("active3");
                }
                else if (response.data.light === 2) {
                    $("#high").addClass("active3");
                }
                else if (response.data.light === 4) {
                    $("#dipped").addClass("active3");
                }
                else if (response.data.light === 8) {
                    $("#clearance").addClass("active3");
                }
                else if (response.data.light === 0) {
                    $(".lightSwitch").removeClass("active3");
                }
            }
        })
    })
    $("#dipped").click(function () {
        $.ajax({
            type: "get",
            url: serAdd + "/shiftL",
            dataType: "json",
            data: { light: 4 },
            success: function (response) {
                $.ajax({
                    type: "get",
                    url: serAdd + "/v_setLight?value=" + 4,
                    success: function (response) {

                    }

                })
                $(".lightSwitch").removeClass("active3");
                if (response.data.light === 1) {
                    // $(".lightSwitch").removeClass("active3");
                    $("#fog").addClass("active3");
                }
                else if (response.data.light === 2) {
                    $("#high").addClass("active3");
                }
                else if (response.data.light === 4) {
                    $("#dipped").addClass("active3");
                }
                else if (response.data.light === 8) {
                    $("#clearance").addClass("active3");
                }
                else if (response.data.light === 0) {
                    $(".lightSwitch").removeClass("active3");
                }
            }
        })
    })
    $("#clearance").click(function () {
        $.ajax({
            type: "get",
            url: serAdd + "/shiftL",
            dataType: "json",
            data: { light: 8 },
            success: function (response) {
                $.ajax({
                    type: "get",
                    url: serAdd + "/v_setLight?value=" + 8,
                    success: function (response) {

                    }

                })
                $(".lightSwitch").removeClass("active3");
                if (response.data.light === 1) {
                    // $(".lightSwitch").removeClass("active3");
                    $("#fog").addClass("active3");
                }
                else if (response.data.light === 2) {
                    $("#high").addClass("active3");
                }
                else if (response.data.light === 4) {
                    $("#dipped").addClass("active3");
                }
                else if (response.data.light === 8) {
                    $("#clearance").addClass("active3");
                }
                else if (response.data.light === 0) {
                    $(".lightSwitch").removeClass("active3");
                }
            }
        })
    })

    //======================================================

    //======================================================转向灯
    // 定义一个对象来存储按键状态
    var keys = {
        ArrowLeft: false,
        ArrowRight: false
    };

    //====双闪
    document.addEventListener("keydown", function (event) {
        // 按键被按下时将其状态设置为true
        if (event.code === "ArrowLeft") {
            keys.ArrowLeft = true;
        } else if (event.code === "ArrowRight") {
            keys.ArrowRight = true;
        }

        // 如果同时按下了ArrowLeft和ArrowRight，则发送turnLight:2的请求
        if (keys.ArrowLeft && keys.ArrowRight) {
            // $("#leftlight, #rightlight").css("animation", "none");
            $.ajax({
                type: "GET",
                url: serAdd + "/shiftT",
                dataType: "json",
                data: { turnLight: 3 },
                success: function (response) {
                    console.log(response);
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setTurnlight?value=" + 3,
                        success: function () {

                        }
                    })
                    if (response.data.turnLight === 0) {
                        $("#leftlight, #rightlight").css("visibility", "hidden");
                    } else if (response.data.turnLight === 3) {
                        $("#leftlight, #rightlight").css("visibility", "hidden");
                        $("#leftlight, #rightlight")
                            .css("visibility", "visible")
                            .addClass("blink-lights");
                    } else if (response.data.turnLight === 1) {
                        $("#rightlight").css("visibility", "hidden");
                        $("#leftlight")
                            .css("visibility", "visible")
                            .addClass("blink-lights");
                    } else if (response.data.turnLight === 2) {
                        $("#leftlight").css("visibility", "hidden");
                        $("#rightlight")
                            .css("visibility", "visible")
                            .addClass("blink-lights");
                    }
                    // if (turnLight === 1) {
                    //     $("#leftlight, #rightlight").css("visibility", "hidden");
                    // } else if (turnLight === 2) {
                    //     $("#leftlight, #rightlight").css("visibility", "visible").addClass("blink-lights");
                    // } else if (turnLight === 4) {
                    //     $("#leftlight, #rightlight").css("visibility", "visible").removeClass("blink-lights");
                    //     $("#leftlight").css("animation-direction", "normal");
                    //     $("#rightlight").css("animation-direction", "normal");
                    // } else if (turnLight === 8) {
                    //     $("#leftlight, #rightlight").css("visibility", "visible").removeClass("blink-lights");
                    //     $("#leftlight").css("animation-direction", "reverse");
                    //     $("#rightlight").css("animation-direction", "reverse");
                    // }






                }
            });
        }
        if (keys.ArrowLeft && !keys.ArrowRight) {

            $.ajax({
                type: "GET",
                url: serAdd + "/shiftT",
                dataType: "json",
                data: { turnLight: 1 },
                success: function (response) {

                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setTurnlight?value=" + 1,
                        success: function () {

                        }
                    })
                    console.log(response);

                    if (response.data.turnLight === 0) {
                        $("#leftlight, #rightlight").css("visibility", "hidden");
                    } else if (response.data.turnLight === 3) {
                        $("#leftlight, #rightlight")
                            .css("visibility", "visible")
                            .addClass("blink-lights");
                    } else if (response.data.turnLight === 1) {
                        $("#rightlight").css("visibility", "hidden");
                        $("#leftlight")
                            .css("visibility", "visible")
                            .addClass("blink-lights");
                    } else if (response.data.turnLight === 2) {
                        $("#leftlight").css("visibility", "hidden");
                        $("#rightlight")
                            .css("visibility", "visible")
                            .addClass("blink-lights");
                    }

                }
            });
        }
        if (!keys.ArrowLeft && keys.ArrowRight) {
            $.ajax({
                type: "GET",
                url: serAdd + "/shiftT",
                dataType: "json",
                data: { turnLight: 2 },
                success: function (response) {

                    console.log(response);
                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setTurnlight?value=" + 2,
                        success: function () {

                        }
                    })
                    if (response.data.turnLight === 0) {
                        $("#leftlight, #rightlight").css("visibility", "hidden");
                    } else if (response.data.turnLight === 3) {
                        $("#leftlight, #rightlight")
                            .css("visibility", "visible")
                            .addClass("blink-lights");
                    } else if (response.data.turnLight === 1) {
                        $("#rightlight").css("visibility", "hidden");
                        $("#leftlight")
                            .css("visibility", "visible")
                            .addClass("blink-lights");
                    } else if (response.data.turnLight === 2) {
                        $("#leftlight").css("visibility", "hidden");
                        $("#rightlight")
                            .css("visibility", "visible")
                            .addClass("blink-lights");
                    }

                }
            });
        }
    });

    document.addEventListener("keyup", function (event) {
        // 按键被松开时将其状态设置为false
        if (event.code === "ArrowLeft") {
            keys.ArrowLeft = false;
        } else if (event.code === "ArrowRight") {
            keys.ArrowRight = false;
        }
    });
    //直行灯
    document.addEventListener("keyup", function (event) {
        if (event.key === "t") {


            $.ajax({
                type: "get",
                url: serAdd + "/shiftT",
                dataType: "json",
                data: { turnLight: 0 },
                success: function (response) {


                    $.ajax({
                        type: "get",
                        url: serAdd + "/v_setTurnlight?value=" + 0,
                        success: function () {

                        }
                    })


                    console.log(response);
                    if (response.data.turnLight === 0) {
                        $("#leftlight, #rightlight").css("visibility", "hidden");
                    } else if (response.data.turnLight === 3) {
                        $("#leftlight, #rightlight")
                            .css("visibility", "visible")
                            .addClass("blink-lights");
                    } else if (response.data.turnLight === 1) {
                        $("#rightlight").css("visibility", "hidden");
                        $("#leftlight")
                            .css("visibility", "visible")
                            .addClass("blink-lights");
                    } else if (response.data.turnLight === 2) {
                        $("#leftlight").css("visibility", "hidden");
                        $("#rightlight")
                            .css("visibility", "visible")
                            .addClass("blink-lights");
                    }

                }
            })


        }
    })

    //======================================================


});
radioButtons1.forEach((radioButton) => {
    radioButton.addEventListener("change", function () {
        if (this.value === "1" && doorState1 === 0) {
            doorState1 = 1; // 更新状态为开门（30度）
            openDoor1();
        } else if (this.value === "0" && doorState1 === 1) {
            doorState1 = 0; // 更新状态为关门（0度）
            closeDoor1();
        }
    });
});
//左下门
radioButtons2.forEach((radioButton) => {
    radioButton.addEventListener("change", function () {
        if (this.value === "1" && doorState2 === 0) {
            doorState2 = 1; // 更新状态为开门（30度）
            openDoor2();
        } else if (this.value === "0" && doorState2 === 1) {
            doorState2 = 0; // 更新状态为关门（0度）
            closeDoor2();
        }
    });
});
//右上门
radioButtons3.forEach((radioButton) => {
    radioButton.addEventListener("change", function () {
        if (this.value === "1" && doorState3 === 0) {
            doorState3 = 1; // 更新状态为开门（30度）
            openDoor3();
        } else if (this.value === "0" && doorState3 === 1) {
            doorState3 = 0; // 更新状态为关门（0度）
            closeDoor3();
        }
    });
});
//右下门
radioButtons4.forEach((radioButton) => {
    radioButton.addEventListener("change", function () {
        if (this.value === "1" && doorState4 === 0) {
            doorState4 = 1; // 更新状态为开门（30度）
            openDoor4();
        } else if (this.value === "0" && doorState4 === 1) {
            doorState4 = 0; // 更新状态为关门（0度）
            closeDoor4();
        }
    });
});
// 开门动画
function openDoor1() {
    let currentAngle = 0; // 当前角度
    const targetAngle = 30; // 目标角度
    $('#ltop').css("background-color", "red");
    const animation = setInterval(() => {
        if (currentAngle < targetAngle) {
            currentAngle++;
            ltop.style.transform = `rotate(${currentAngle}deg)`;
        } else {
            clearInterval(animation);
        }
    }, 10);
}

// 关门动画
function closeDoor1() {
    let currentAngle = 30; // 当前角度
    const targetAngle = 0; // 目标角度
    $('#ltop').css("background-color", "lightblue");
    const animation = setInterval(() => {
        if (currentAngle > targetAngle) {
            currentAngle--;
            ltop.style.transform = `rotate(${currentAngle}deg)`;
        } else {
            clearInterval(animation);
        }
    }, 10);
}
// 开门动画
function openDoor2() {
    let currentAngle = 0; // 当前角度
    const targetAngle = 30; // 目标角度
    $('#lbot').css("background-color", "red");
    const animation = setInterval(() => {
        if (currentAngle < targetAngle) {
            currentAngle++;
            lbot.style.transform = `rotate(${currentAngle}deg)`;
        } else {
            clearInterval(animation);
        }
    }, 10);
}

// 关门动画
function closeDoor2() {
    let currentAngle = 30; // 当前角度
    const targetAngle = 0; // 目标角度
    $('#lbot').css("background-color", "lightblue");
    const animation = setInterval(() => {
        if (currentAngle > targetAngle) {
            currentAngle--;
            lbot.style.transform = `rotate(${currentAngle}deg)`;
        } else {
            clearInterval(animation);
        }
    }, 10);
}
// 右开门动画
function openDoor3() {
    let currentAngle = 0; // 当前角度
    const targetAngle = -30; // 目标角度
    $('#rtop').css("background-color", "red");
    const animation = setInterval(() => {
        if (currentAngle > targetAngle) {
            currentAngle--;
            rtop.style.transform = `rotate(${currentAngle}deg)`;
        } else {
            clearInterval(animation);
        }
    }, 10);
}

// 右关门动画
function closeDoor3() {
    let currentAngle = -30; // 当前角度
    const targetAngle = 0; // 目标角度
    $('#rtop').css("background-color", "lightblue");
    const animation = setInterval(() => {
        if (currentAngle < targetAngle) {
            currentAngle++;
            rtop.style.transform = `rotate(${currentAngle}deg)`;
        } else {
            clearInterval(animation);
        }
    }, 10);
}
// 右开门动画
function openDoor4() {
    let currentAngle = 0; // 当前角度
    const targetAngle = -30; // 目标角度
    $('#rbot').css("background-color", "red");
    const animation = setInterval(() => {
        if (currentAngle > targetAngle) {
            currentAngle--;
            rbot.style.transform = `rotate(${currentAngle}deg)`;
        } else {
            clearInterval(animation);
        }
    }, 10);
}

// 右关门动画
function closeDoor4() {
    let currentAngle = -30; // 当前角度
    const targetAngle = 0; // 目标角度
    $('#rbot').css("background-color", "lightblue");
    const animation = setInterval(() => {
        if (currentAngle < targetAngle) {
            currentAngle++;
            rbot.style.transform = `rotate(${currentAngle}deg)`;
        } else {
            clearInterval(animation);
        }
    }, 10);
}

//
//==============行人警告
document.addEventListener("keydown", function (event) {
    if (event.key === 'a') {
        var x = document.getElementById('prompts');
        x.classList.add('show'); /* Update this line */
        x.innerHTML = '附近有行人，请小心驾驶';
        var overlay = document.getElementById("overlay");
        overlay.classList.add("show");
        setTimeout(function () {
            overlay.classList.remove("show");
            x.classList.remove('show'); /* Update this line */
        }, 3000); // 5秒后隐藏
        var utterance = new SpeechSynthesisUtterance(x.innerHTML);
        speechSynthesis.speak(utterance);
    }
})
//===================================压线检测
document.addEventListener("keydown", function (event) {
    if (event.key === 'k') {
        var x = document.getElementById('prompts');
        x.classList.add('show'); /* Update this line */
        x.innerHTML = '车辆压线，请注意！';
        //var overlay = document.getElementById("overlay");
        //overlay.classList.add("show");
        setTimeout(function () {
            //overlay.classList.remove("show");
            x.classList.remove('show'); /* Update this line */
        }, 3000); // 5秒后隐藏
        var utterance = new SpeechSynthesisUtterance(x.innerHTML);
        speechSynthesis.speak(utterance);
    }
})
//====================================红绿灯
var redLight = document.getElementById("redLight");
var greenLight = document.getElementById("greenLight");



let lightstate;
let humenstate;
function trafficlight() {
    $.ajax({
        type: "get",
        url: "http://127.0.0.1:4567/newImg",
        success: function (response) {

            console.log(response)

            let str = response
            let s = str.split('#')

            lightstate = s[3]
            humenstate = s[4]

            if (lightstate === 'True') {
                console.log(s[3])
                var text = '当前是红灯';
                var utterance = new SpeechSynthesisUtterance(text);
                speechSynthesis.speak(utterance);
                redLight.style.visibility = "visible";
                // greenLight.style.visibility = "hidden";
                redLight.style.display = "block";
                greenLight.style.display = "none";
            } else if (lightstate === 'False') {
                var text = '当前是绿灯';
                var utterance = new SpeechSynthesisUtterance(text);
                speechSynthesis.speak(utterance);
                // redLight.style.visibility = "hidden";
                greenLight.style.visibility = "visible";
                redLight.style.display = "none";
                greenLight.style.display = "block";
            }

            if (humenstate === 'Ture') {
                var x = document.getElementById('prompts');
                x.classList.add('show'); /* Update this line */
                x.innerHTML = '附近有行人，请小心驾驶';
                var overlay = document.getElementById("overlay");
                overlay.classList.add("show");
                setTimeout(function () {
                    overlay.classList.remove("show");
                    x.classList.remove('show'); /* Update this line */
                }, 3000); // 5秒后隐藏
                var utterance = new SpeechSynthesisUtterance(x.innerHTML);
                speechSynthesis.speak(utterance);
            }

        },
        error: function (xhr, status, error) {
            console.error(error);
        }

    })
}
setInterval(trafficlight, 100)
