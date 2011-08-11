/**
 * jQuery wBox plugin
 * wBox是一个轻量级的弹出窗口jQuery插件，基于jQuery1.4.2开发，
 * 主要实现弹出框的效果，并且加入了很多有趣的功能，比如可img灯箱效果，callback函数，显示隐藏层，Ajax页面，iframe嵌入页面等功能
 * @name wBox
 * @author WangYongqing - http://www.js8.in（王永清 http://www.js8.in）
 * @version 0.1
 * @copyright (c) 2010 WangYongqing (js8.in)
 * @example Visit http://www.js8.in/mywork/wBox/ for more informations about this jQuery plugin
 */
(function($){
    //class为.wBoxClose为关闭
    $.fn.wBox = function(options){
        var defaults = {
            opacity: 0.5,//背景透明度
            drag: false,//是否可拖拽
            title: 'wBox弹出框',//wBox窗口名字
            noTitle: false,//是否不显示wBox标题边框
            html: '',//wBox内容
            callBack: null,//wBox返回函数
            isImage: false,// image灯箱
            images: [],//image灯箱图片地址数组
            left: 300,//wBox位置，当setPos为true时有用
            top: 400,
            iframeWH: {//跨域的iframe 设置高宽
                width: 400,
                height: 300
            },
            setPos: false,//是否自定义wBox位置
            overlay: true,//是否显示wBox背景
            isFrame: false,//是否为 iframe，为跨域可以自适应窗口大小            
            imageTypes: ['png', 'jpg', 'jpeg', 'gif']//支持image查看的图片类型，用于正则
        };
        var YQ = $.extend(defaults, options);
        init();
        var wBoxHtml = '\
				    <div id="wBox" style="display:none;"> \
				      <div class="popup"> \
				        <table> \
				          <tbody> \
				            <tr class="imgRemove"> \
				              <td class="tl"/><td class="b"/><td class="tr"/> \
				            </tr> \
				            <tr> \
				              <td class="b imgRemove"/> \
				              <td class="body">' + (YQ.noTitle ? '' : '<table class="title imgRemove"><tr><td class="dragTitle"><div class="itemTitle">' + YQ.title + '</div></td>\
                                  <td width="20px" title="关闭"><div class="close"></div></td></tr></table> ') +
        '</div> \
				                <div class="content"> \
				                </div> \
				              </td> \
				              <td class="b imgRemove"/> \
				            </tr> \
				            <tr class="imgRemove"> \
				              <td class="bl"/><td class="b"/><td class="br"/> \
				            </tr> \
				          </tbody> \
				        </table> \
				      </div> \
				    </div>';
        
        var Loading = $("<div class='wBox_loading'><img src='../images/loading.gif'/></div>");
        var imgObj = [];
        var imgNum = 0;
        var B = null;//背景jquery div
        var C = null;//内容jquery div             
        var E = null;//正则匹配img
        /*
         * 触发click事件
         */
        $(this).click(function(){
            B ? B.remove() : null;
            C ? C.remove() : null;
            
            YQ.overlay ? B = $("<div id='wBox_overlay' class='wBox_hide'></div>").hide().addClass('wBox_overlayBG').css('opacity', YQ.isImage ? 0.8 : YQ.opacity).dblclick(function(){
                close();
            }).appendTo('body').fadeIn(300) : null;
            C = $(wBoxHtml).appendTo('body');
            handleClick(this.href);
            return false;
        });
        /*
         * 处理点击
         * @param {string} what
         */
        function handleClick(what){
            var con = C.find(".content");
            if (YQ.isImage) { //img灯箱
                C.find('.imgRemove').remove();
                C.find('.content').css({
                    'padding': '10px',
                    'background-color': '#FFFFFF'
                });
                Loading.appendTo(con);
                var lt = calPosition(370);
                C.css({
                    left: lt[0],
                    top: lt[1]
                });
                imgHandle()
            }
            else 
                if (YQ.isFrame) {//iframe
                    ifr = $("<iframe name='wBoxIframe' style='width:" + YQ.iframeWH.width + "px;height:" + YQ.iframeWH.height + "px;' scrolling='auto' frameborder='0' src='" + what + "'></iframe>");
                    ifr.appendTo(con);
                    ifr.load(function(){
                        try {
                            $t = $(this).contents();
                            $t.find('.wBoxClose').click(close);
                            fH = $t.height();//iframe height
                            fW = $t.width();
                            w = $(window);
                            newW = Math.min(w.width() - 40, fW);
                            newH = w.height() - 25 - (YQ.noTitle ? 0 : 30);
                            newH = Math.min(newH, fH);
                            if (!newH) 
                                return;
                            var lt = calPosition(newW);
                            C.animate({
                                left: lt[0],
                                top: lt[1]
                            }, 1000);
                            $(this).animate({
                                height: newH,
                                width: newW
                            }, 1000);
                        //                          setPosition();
                        } 
                        catch (e) {
                        }
                    });
                }
                else 
                    if (what !== window.location.href && what) {
                        if (what.match(/#/)) {//href=#info
                            var url = window.location.href.split('#')[0]
                            var target = what.replace(url, '');
                            con.append($(target).clone(true).show());
                        }
                        else 
                            if (what.match(Exp)) {//href=*.jpg gif png
                                var image = new Image();
                                Loading.appendTo(con);
                                image.onload = function(){
                                    w = image.width < 100 ? 100 : image.width;
                                    h = image.height < 100 ? 100 : image.height + 24;
                                    var lt = calPosition(w);
                                    imgC = $('<div class="image"><img src="' + image.src + '" /></div>');
                                    C.css({
                                        left: lt[0],
                                        top: lt[1]
                                    })
                                    Loading.remove();
                                    con.html(imgC.hide().fadeIn());
                                    
                                }
                                image.src = what;
                            }
                            else {//href=1.html
                                Loading.appendTo(con);
                                con.load(what, function(){
                                    Loading.remove();
                                    $(this).find('.wBoxClose').click(close);
                                });
                            }
                    }
                    else {//使用YQ.html
                        con.html(YQ.html);
                    }
            afterHandleClick();
        }
        /*
         * img 处理函数
         * 用于图片预加载后运行处理
         * 为防止IE出现错误，使用onload在src之前
         */
        function imgHandle(){
            for (var i = YQ.images.length; i--;) {
                imgObj[i] = new Image();
                i ? null : imgObj[0].onload = function(){
                    Loading.fadeOut(300, imgAjax);
                    
                }
                imgObj[i].src = YQ.images[i];
            }
            
        }
        /*
         * img load handle fn
         */
        function imgAjax(){
            var tImg = imgObj[imgNum], con = C.find(".content").html(Loading.show());
            w = tImg.width < 100 ? 100 : tImg.width;
            h = tImg.height < 100 ? 100 : tImg.height + 24;
            var lt = calPosition(w);
            var speed = w * 2 < 900 ? 900 : w * 2;
            var imgC = $("<div id='IMG'><img src='" + tImg.src + "' id='imgURL'/><div class='imgBTN'><a id='imgNext' href='' title='左键点击或按ESC返回'></a></div></div>");
            imgC.click(function(){
            	close();
                return false;
            });
            //					C.css({left:lt[0],top:lt[1]});con.html(imgC.hide().fadeIn());Loading.stop().hide();
            C.animate({
                left: lt[0],
                top: lt[1]
            }, speed).find(".body").animate({
                height: h,
                width: w
            }, speed, function(){
                con.html(imgC.hide().fadeIn());
                Loading.stop().hide();
            })
        }
        /*
         * first kiss
         */
        function init(){
            var imageTypes = YQ.imageTypes.join('|')
            Exp = new RegExp('\.' + imageTypes + '$', 'i');
            var iA = ['../images/titleBG.png', '../images/wbox.png', '../images/close.png', '../images/loading.gif'];
            var v = [];
            for (var i = iA.length; i--;) {
                v[i] = new Image();
                v[i].src = iA[i];
            }
        }
        /*
         * 处理点击之后的处理
         */
        function afterHandleClick(){
            setPosition();
            C.find('.close').click(function(){
                close()
            }).hover(function(){
                $(this).addClass("on");
            }, function(){
                $(this).removeClass("on");
            });
            $(document).unbind('keydown.wBox').bind('keydown.wBox', function(e){
                if (e.keyCode === 27) 
                    close();
                return true
            });
            YQ.drag ? drag() : null;
            typeof YQ.callBack === 'function' ? YQ.callBack() : null;
        }
        /*
         * 设置wBox的位置
         */
        function setPosition(){
            var lt = calPosition($("#wBox").width());
            $("#wBox").css({
                left: lt[0],
                top: lt[1]
            }).show();
            
            var $w = ___getPageSize();   			      
            B?B.height($w[1]).width($w[0]):null;            
        }
        function ___getPageSize(){
            var xScroll, yScroll;
            if (window.innerHeight && window.scrollMaxY) {
                xScroll = window.innerWidth + window.scrollMaxX;
                yScroll = window.innerHeight + window.scrollMaxY;
            }
            else 
                if (document.body.scrollHeight > document.body.offsetHeight) { // all but Explorer Mac
                    xScroll = document.body.scrollWidth;
                    yScroll = document.body.scrollHeight;
                }
                else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
                    xScroll = document.body.offsetWidth;
                    yScroll = document.body.offsetHeight;
                }
            var windowWidth, windowHeight;
            if (self.innerHeight) { // all except Explorer
                if (document.documentElement.clientWidth) {
                    windowWidth = document.documentElement.clientWidth;
                }
                else {
                    windowWidth = self.innerWidth;
                }
                windowHeight = self.innerHeight;
            }
            else 
                if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
                    windowWidth = document.documentElement.clientWidth;
                    windowHeight = document.documentElement.clientHeight;
                }
                else 
                    if (document.body) { // other Explorers
                        windowWidth = document.body.clientWidth;
                        windowHeight = document.body.clientHeight;
                    }
            // for small pages with total height less then height of the viewport
            if (yScroll < windowHeight) {
                pageHeight = windowHeight;
            }
            else {
                pageHeight = yScroll;
            }
            // for small pages with total width less then width of the viewport
            if (xScroll < windowWidth) {
                pageWidth = xScroll;
            }
            else {
                pageWidth = windowWidth;
            }
			pageWidth=Math.max(pageWidth,windowWidth);
			pageHeight=Math.max(pageHeight,windowHeight);
            arrayPageSize = new Array(pageWidth, pageHeight);
            return arrayPageSize;
        };
        /*
         * 计算wBox的位置
         * @param {number} w 宽度
         */
        function calPosition(w){
            var $win = $(window);
            if (YQ.setPos) {
                l = YQ.left;
                t = YQ.top;
            }
            else {
                l = ($win.width() - w) / 2;
                t = $win.scrollTop() + $win.height() / 10;
            }
            return [l, t];
        }
        /*
         * 拖拽函数drag
         */
        function drag(){
            var dx, dy, moveout;
            var T = C.find('.dragTitle').css('cursor', 'move');
            T.bind("selectstart", function(){
                return false;
            });
            
            T.mousedown(function(e){
                dx = e.clientX - parseInt(C.css("left"));
                dy = e.clientY - parseInt(C.css("top"));
                C.mousemove(move).mouseout(out).css('opacity', 0.8);
                T.mouseup(up);
            });
            /*
             * 移动改变生活
             * @param {Object} e 事件
             */
            function move(e){
                moveout = false;
                win = $(window);
                if (e.clientX - dx < 0) {
                    l = 0;
                }
                else 
                    if (e.clientX - dx > win.width() - C.width()) {
                        l = win.width() - C.width();
                    }
                    else {
                        l = e.clientX - dx
                    }
                C.css({
                    left: l,
                    top: e.clientY - dy
                });
                
            }
            /*
             * 你已经out啦！
             * @param {Object} e 事件
             */
            function out(e){
                moveout = true;
                setTimeout(function(){
                    moveout && up(e);
                }, 10);
            }
            /*
             * 放弃
             * @param {Object} e事件
             */
            function up(e){
                C.unbind("mousemove", move).unbind("mouseout", out).css('opacity', 1);
                T.unbind("mouseup", up);
            }
        }
        /*
         * 关闭弹出框就是移除还原
         */
        function close(){
            if (C) {
                B ? B.remove() : null;
                C.find('.body').stop();
                C.stop().fadeOut(300, function(){
                    C.remove();
                })
            }
        }
        $('.wBoxClose').click(close);
        $(window).resize(function(){
            setPosition();
        });
    };
})(jQuery);
