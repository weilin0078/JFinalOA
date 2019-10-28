(function() {
    var scrollSetp = 500,
    operationWidth = 90,
    leftOperationWidth = 30,
    animatSpeed = 150,
    linkframe = function(url, value) {//链接iframe---控制显示隐藏
        $("#menu-list a.active").removeClass("active");
        $("#menu-list a[data-url='" + url + "'][data-value='" + value + "']").addClass("active");
        $("#jfinaloa-tab-page-content iframe.active").removeClass("active");
        $("#jfinaloa-tab-page-content .iframe-content[data-url='" + url + "'][data-value='" + value + "']").addClass("active");
        $("#jfinaloa-menu-all-ul li.active").removeClass("active");
        $("#jfinaloa-menu-all-ul li[data-url='" + url + "'][data-value='" + value + "']").addClass("active")
    },
    move = function(selDom) {//移动
    	var marginLeft = 0;
    	if($("#container").hasClass("mainnav-lg")){
    		marginLeft = 220;
    	}else if($("#container").hasClass("mainnav-sm")){
    		marginLeft = 20;
    	}
        var nav = $("#menu-list");
        var releft = selDom.offset().left;
        var wwidth = parseInt($("#page-tab").width());
        var left = parseInt(nav.css("margin-left"));
        if (releft < 0 && releft <= wwidth) {
            nav.animate({
                "margin-left": (left - releft + leftOperationWidth + marginLeft) + "px"
            },
            animatSpeed)
        } else {
            if (releft + selDom.width() > wwidth - operationWidth) {
                nav.animate({
                    "margin-left": (left - releft + wwidth - selDom.width() - operationWidth - marginLeft) + "px"
                },
                animatSpeed)
            }
        }
    },
    createmove = function() {//创建新菜单的时候移动移动
        var nav = $("#menu-list");
        var wwidth = parseInt($("#page-tab").width());
        var navwidth = parseInt(nav.width());
        if (wwidth - operationWidth < navwidth) {
            nav.animate({
                "margin-left": "-" + (navwidth - wwidth + operationWidth) + "px"
            },
            animatSpeed)
        }
    },
    closemenu = function(event) {//关闭
        $(this.parentElement).animate({
            "width": "0",
            "padding": "0"
        },
        0,
        function() {
            var jthis = $(this);
            if (jthis.hasClass("active")) {
                var linext = jthis.next();
                if (linext.length > 0) {
                    linext.click();
                    move(linext)
                } else {
                    var liprev = jthis.prev();
                    if (liprev.length > 0) {
                        liprev.click();
                        move(liprev)
                    }
                }
            }
            $(this).remove();
            $("#jfinaloa-menu-all #jfinaloa-menu-all-ul li[data-url='" + jthis.data("url") + "'][data-value='" + jthis.data("value") + "']").remove();
            $("#jfinaloa-tab-page-content .iframe-content[data-url='" + jthis.data("url") + "'][data-value='" + jthis.data("value") + "']").remove();
        });
        event.stopPropagation()
    },
    bindMouseRightClickMenu = function(e,o){//绑定右键菜单
    	e.preventDefault();//取消默认的浏览器自带右键 很重要！！
		var menu=document.querySelector("#mouseRightClickMenu");//获取我们自定义的右键菜单
		//根据事件对象中鼠标点击的位置，进行定位
		menu.style.left=e.clientX+'px';
		menu.style.top=e.clientY+'px';
		$(".tabMenuCloseAll").show();//关闭自己，关闭所有
		$(".tabMenuCloseThis").show();//关闭自己，关闭所有
		$('#mouseRightClickMenu').show();//右键菜单
		var linkUrl = $(o).data("url");
		var linkHtml = $(o).data("value").trim();
		$('#mouseRightClickMenu a').data("url",linkUrl);
		$('#mouseRightClickMenu a').data("value",linkHtml);
    },
    init = function() {//初始化
        $("#page-prev").bind("click",function() {//绑定向前
            var nav = $("#menu-list");
            var left = parseInt(nav.css("margin-left"));
            if (left !== 0) {
                nav.animate({
                    "margin-left": (left + scrollSetp > 0 ? 0 : (left + scrollSetp)) + "px"
                },
                animatSpeed)
            }
        });
        $("#page-next").bind("click",function() {//绑定向后
            var nav = $("#menu-list");
            var left = parseInt(nav.css("margin-left"));
            var wwidth = parseInt($("#page-tab").width());
            var navwidth = parseInt(nav.width());
            var allshowleft = -(navwidth - wwidth + operationWidth);
            if (allshowleft !== left && navwidth > wwidth - operationWidth) {
                var temp = (left - scrollSetp);
                nav.animate({
                    "margin-left": (temp < allshowleft ? allshowleft: temp) + "px"
                },
                animatSpeed)
            }
        });
        $("#page-operation").bind("click",function(){//下拉菜单点击事件
            var menuall = $("#jfinaloa-menu-all");
            if (menuall.is(":visible")) {
                menuall.hide()
            } else {
                menuall.show()
            }
        });
        $("body").bind("mousedown",function(event) {
            if (! (event.target.id === "jfinaloa-menu-all" || event.target.id === "jfinaloa-menu-all-ul" || event.target.id === "page-operation" || event.target.id === "page-operation" || event.target.parentElement.id === "jfinaloa-menu-all-ul")) {
                $("#jfinaloa-menu-all").hide()
            }
        })
    };
    $.fn.tab = function() {
        init();
        this.bind("click",
        function() {
            var linkUrl = this.href;
        	if(linkUrl.indexOf("?")>0){//绑定打开新页面的。使用iframe打开
        		linkUrl = linkUrl + "&action=openPage";
        	}else{
        		linkUrl = linkUrl + "?action=openPage";
        	}
            var linkHtml = this.text.trim();
            var selDom = $("#menu-list a[data-url='" + linkUrl + "'][data-value='" + linkHtml + "']");
            if (selDom.length === 0) {
            	//创建关闭按钮以及事件
                var iel = $("<i>", {
                    "class": "menu-close ion-close"
                }).bind("click", closemenu);
                //创建tab页签以及事件
                $("<a>", {
                    "html": linkHtml,
                    "href": "javascript:void(0);",
                    "data-url": linkUrl,
                    "data-value": linkHtml
                }).bind("click",function() {//点击事件
                    var jthis = $(this);
                    linkframe(jthis.data("url"), jthis.data("value"))
                }).on("contextmenu",function(e){//右键下拉事件
                	bindMouseRightClickMenu(e,this);
                }).append(iel).appendTo("#menu-list");
                //创建iframe以及事件
                $("<iframe>", {
                    "class": "iframe-content",
                    "data-url": linkUrl,
                    "data-value": linkHtml,
                    src: linkUrl
                }).appendTo("#jfinaloa-tab-page-content");
                //创建下拉菜单以及事件
                $("<li>", {
                    "html": linkHtml,
                    "data-url": linkUrl,
                    "data-value": linkHtml
                }).bind("click",function(event) {
                    var jthis = $(this);
                    linkframe(jthis.data("url"), jthis.data("value"));
                    move($("#menu-list a[data-url='" + linkUrl + "'][data-value='" + linkHtml + "']"));
                    $("#jfinaloa-menu-all").hide();
                    event.stopPropagation()
                }).appendTo("#jfinaloa-menu-all-ul");
                //创建移动
                createmove();
            } else {
                move(selDom)
            }
            linkframe(linkUrl, linkHtml);
            return false
        });
        return this
    };
    /***
     * 移动到首页 tab
     */
     $.fn.moveToHome = function(o){
	      var linkUrl = $(o).data("url");
	      var linkHtml = $(o).data("value").trim();
	      linkframe(linkUrl, linkHtml);
	      move(o);
    }
})();
//移动到首页
function moveToHomeTab(){
	$("#jfinaloa-home-tab").moveToHome($("#jfinaloa-home-tab"));
}

