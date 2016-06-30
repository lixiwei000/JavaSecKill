// 存放主要的交互逻辑js代码
// javascript模块化
var seckill = {
    // 封装秒杀相关的URL
    URL:{
        now : function(){
            return "/seckill/time/now";
        },
        exposer : function(seckillId){
            return "/seckill/" + seckillId + "/exposer";
        },
        execution : function(seckillId,md5){
            return "/seckill/" + seckillId + "/" + md5 + "/execution";
        }
    },
    validatePhone : function(phone)
    {
        if (phone && phone.length == 11 && !isNaN(phone))
            return true;
        else
            return false;
    },
    countdown : function(seckillId,nowTime,startTime,endTime){
        var seckillBox = $("#seckill-box");
        // 判断时间
        if (nowTime > endTime){
            // 秒杀结束
            seckillBox.html("秒杀结束!");
        }
        else if (nowTime < startTime){
            // 秒杀未开始,绑定计时时间
            var killTime = new Date(startTime + 1000);
            // 调用countdown插件,使用回调函数显示秒杀倒计时时间
            seckillBox.countdown(killTime,function(event){
                // 时间格式
                var format = event.strftime("秒杀倒计时: %D天 %H时 %M分 %S秒");
                seckillBox.html(format);
                // 执行完成后回调时间
            }).on("finish.countdown",function(){
                // 获取秒杀地址,控制实现逻辑,执行秒杀
                seckill.handleSecKill(seckillId,seckillBox);
            });
        }
        else{
            seckill.handleSecKill(seckillId,seckillBox);
        }
    } ,
    handleSecKill : function(seckillId,node){
        // 获取秒杀地址,控制实现逻辑,执行秒杀
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId),{},function(result){
            console.log("exposerUrl:" + seckill.URL.exposer(seckillId));
            // 在回调函数中执行交互逻辑
            if (result && result['success']){
                var exposer = result['data'];
                // 服务端是否真的达到秒杀条件
                if (exposer['exposed']){
                    // 获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId,md5);
                    console.log(killUrl);
                    // 给秒杀按钮绑定事件
                    $("#killBtn").one('click',function(){
                        // 1.先禁用按钮
                       $(this).addClass('disabled');
                        // 2.执行秒杀请求
                        $.post(killUrl,{},function(result){
                            var killResult = result['data'];
                            var state = killResult['state'];
                            var stateInfo = killResult['stateInfo'];
                            if (result && result['success']) {
                                node.html('<span class="label label-success">'+ stateInfo + '</span>');
                            }
                            else{
                                node.html('<span class="label label-failed">'+ stateInfo + '</span>');
                            }
                        });
                    });
                    node.show();
                }
                else{
                    // 服务端没有达到秒杀时间,重新计算计时逻辑
                    var nowTime = exposer['now'];
                    var startTime = exposer['start'];
                    var endTime = exposer['end'];
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }
            }
            else{
                console.log("result:" + result);
            }
        });
    },
    // 详情页秒杀逻辑
    detail:{
        // 详情页初始化
        init : function(param){
            // 手机验证交互,计时交互
            // 规划我们的交互流程
            // 在Cookie中查找手机号
            var killPhone = $.cookie("killPhone");
            // 验证手机号
            if (!seckill.validatePhone(killPhone))
            {
                // 绑定Phone
                // 控制输出
                var killPhoneModal = $("#killPhoneModal");
                // 显示弹出层
                killPhoneModal.modal({
                    show : true, // 显示弹出层
                    backdrop : false, //禁止位置关闭
                    keyboard : false // 禁止键盘事件
                });
                $("#killPhoneBtn").click(function () {
                    var inputPhone = $("#killPhoneKey").val();
                    console.log("inputPhone=" + inputPhone);
                    if (seckill.validatePhone(inputPhone)){
                        // 电话写入Cookie
                        $.cookie("killPhone",inputPhone,{expires:7,path:"/seckill"})
                        // 刷新页面
                        window.location.reload();
                    }
                    else{
                        $("#killPhoneMessage").hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                    }

                });
            }
            var seckillId = param["seckillId"];
            var startTime = param["startTime"];
            var endTime = param["endTime"];
            // 已经登陆
            // 获取当前系统时间
            $.get(seckill.URL.now(),{}, function (result) {
                if (result && result['success']){
                    var nowTime = result['data'];
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }
                else{
                    console("result:" + result);
                }
            });
        }
    }
}