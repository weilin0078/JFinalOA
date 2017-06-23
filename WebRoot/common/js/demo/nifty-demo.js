// Nifty-demo.js
// ====================================================================
// Set user options for current page.
// This file is only used for demonstration purposes.
// ====================================================================
// - ThemeOn.net -


$(document).ready(function () {

    // SETTINGS WINDOW
    // =================================================================

    var demoSetBody         = $('#demo-set'),
        demoSetIcon         = $('#demo-set-icon'),
        demoSetBtnGo        = $('#demo-set-btngo'),
        niftyContainer      = $('#container'),
        niftyMainNav        = $('#mainnav-container'),
        niftyAside          = $('#aside-container');

    if (demoSetBody.length) {
        function InitializeSettingWindow(){
            // BOXED LAYOUT
            // =================================================================
            var boxedLayoutCheckbox     = document.getElementById('demo-box-lay'),
                boxedLayoutImgBtn       = document.getElementById('demo-box-img'),
                boxedLayoutImgBox       = $('#demo-bg-boxed'),
                boxedLayoutBtnClose     = document.getElementById('demo-close-boxed-img'),
                blurredBgList           = $('#demo-blurred-bg'),
                polygonBgList           = $('#demo-polygon-bg'),
                abstractBgList          = $('#demo-abstract-bg');


            // Initialize
            // ================================================================= 如果有boxed的样式      初始化功能按钮状态
            if(niftyContainer.hasClass('boxed-layout')){
                boxedLayoutCheckbox.checked = true;
                boxedLayoutImgBtn.disabled = false;
            }else{
                boxedLayoutCheckbox.checked = false;
                boxedLayoutImgBtn.disabled = true;
            }
            //添加背景图
            function bg_thumb_template(cat){
                var list = '';
                for (var i = 1; i < 17; i++) {
                    list += '<a href="#" class="thumbnail box-inline"><img class="img-responsive" src="'+ctx+'/common/premium/boxed-bg/'+cat+'/thumbs/'+i+'.jpg" alt="Background Image"></a>';
                }
                return list;
            }
            function add_bg_thumbs(){
                blurredBgList.append(bg_thumb_template('blurred'));
                polygonBgList.append(bg_thumb_template('polygon'));
                abstractBgList.append(bg_thumb_template('abstract'));

                var boxedBgthumb = boxedLayoutImgBox.find('.thumbnail');
                boxedBgthumb.on('click', function(){//选中一个背景
                    boxedBgthumb.removeClass('selected');
                    var url = $(this).children('img').prop('src').replace('thumbs','bg');
                    $(this).addClass('selected');
                    niftyContainer.css({
                        'background-image': 'url('+url+')',
                        'background-repeat': 'no-repeat',
                        'background-size': 'cover'
                    })
                    setCustomStatus({"sysCustomSetting.box_back_img":url});
                });
            }



            // Boxed Layout Checkbox    选择box按钮
            // =================================================================
            boxedLayoutCheckbox.onchange = function(){
                if (boxedLayoutCheckbox.checked) {//盒子模式
                    niftyContainer.addClass('boxed-layout');
                    boxedLayoutImgBtn.disabled = false;//开启盒子背景图
                    setCustomStatus({"sysCustomSetting.box_lay":1});
                } else {//全屏模式
//                    niftyContainer.removeClass('boxed-layout').removeAttr( 'style' );
                	niftyContainer.removeClass('boxed-layout');
                    boxedLayoutImgBtn.disabled = true;
                    boxedLayoutImgBox.removeClass('open').find('.thumbnail').removeClass('selected');
                    setCustomStatus({"sysCustomSetting.box_lay":0});
                }
                $(window).trigger('resize');
            };

            // Boxed Image Buttons
            // =================================================================
            boxedLayoutImgBtn.onclick = function(){
                if (!boxedLayoutImgBox.hasClass('open')) {
                    boxedLayoutImgBox.addClass('open');
                    if(!demoSetBody.hasClass('hasbgthumbs')){
                        add_bg_thumbs();
                        demoSetBody.addClass('hasbgthumbs')
                    }
                } else {
                    boxedLayoutImgBox.removeClass('open');
                }
            };

            // Close Button
            // =================================================================
            boxedLayoutBtnClose.onclick = function(){
                boxedLayoutImgBox.removeClass('open');
            }






            // TRANSITION EFFECTS   动画
            // =================================================================
            // =================================================================
            var effectList = 'easeInQuart easeOutQuart easeInBack easeOutBack easeInOutBack steps jumping rubber',
                animCheckbox = document.getElementById('demo-anim'),
                transitionVal = document.getElementById('demo-ease');//设置里下拉菜单


            // Initialize
            // =================================================================
            if(niftyContainer.hasClass('effect')){//如果开启了动画
                animCheckbox.checked = true;
                transitionVal.disabled = false;
            }else{
                animCheckbox.checked = false;
                transitionVal.disabled = true;
            }

            // Animations checkbox
            animCheckbox.onchange = function(){//切换开启关闭动画效果
                if (animCheckbox.checked) {
                    niftyContainer.addClass('effect');//添加动画样式
                    transitionVal.disabled = false;//下拉菜单不可用
                    transitionVal.value = 'effect';//绑定
                    setCustomStatus({"sysCustomSetting.animate":"1"});//存库-开启动画
                } else {
                    niftyContainer.removeClass('effect ' + effectList);//移除所有动画
                    transitionVal.disabled = true;//下拉不可用
                    setCustomStatus({"sysCustomSetting.animate":"0"});//存库-关闭动画
                }
            };


            // Transition selectbox
            var effectArray = effectList.split(" ");
            for (i = 0; i < effectArray.length; i++) {//绑定动画下拉菜单值
                if (niftyContainer.hasClass(effectArray[i])){
                    transitionVal.value = effectArray[i];
                    break;
                }
            }
            transitionVal.onchange = function(){//下拉菜单值改变
                var optionSelected = $("option:selected", this);
                var valueSelected = this.options[this.selectedIndex].value;
                if (valueSelected) {
                    niftyContainer.removeClass(effectList).addClass(valueSelected);//移除所有动画 , 添加所选动画
                    setCustomStatus({"sysCustomSetting.animate_type":valueSelected});//存库-关闭动画
                }
            };





            // NAVBAR  固定标头
            // =================================================================
            // =================================================================
            var navbarFixedCheckbox = document.getElementById('demo-navbar-fixed');

            // Initialize
            // =================================================================
            if(niftyContainer.hasClass('navbar-fixed')){//绑定 
                navbarFixedCheckbox.checked = true;
            }else{
                navbarFixedCheckbox.checked = false;
            }

            // Fixed Position
            // =================================================================
            navbarFixedCheckbox.onchange = function(){//固定标头  按钮改变
                if (navbarFixedCheckbox.checked) {
                    niftyContainer.addClass('navbar-fixed');
                    setCustomStatus({"sysCustomSetting.fixed_navbar":"1"});//固定标头
                } else {
                    niftyContainer.removeClass('navbar-fixed');
                    setCustomStatus({"sysCustomSetting.fixed_navbar":"0"});//不固定标头
                }

                // Refresh the aside, to enable or disable the "Bootstrap Affix" when the navbar in a "static position".
                niftyMainNav.niftyAffix('update');
                niftyAside.niftyAffix('update');
            };



            // FOOTER  固定底部
            // =================================================================
            // =================================================================
            var footerFixedCheckbox = document.getElementById('demo-footer-fixed');


            // Initialize
            // =================================================================
            if(niftyContainer.hasClass('footer-fixed')){//绑定
                footerFixedCheckbox.checked = true;
            }else{
                footerFixedCheckbox.checked = false;
            }

            // Fixed Position
            // =================================================================
            footerFixedCheckbox.onchange = function(){
                if (footerFixedCheckbox.checked) {
                    niftyContainer.addClass('footer-fixed');
                    setCustomStatus({"sysCustomSetting.fixed_footer":"1"});//固定底部
                } else {
                    niftyContainer.removeClass('footer-fixed');
                    setCustomStatus({"sysCustomSetting.fixed_footer":"0"});//不固定底部
                }
            };




            // NAVIGATION  固定导航
            // =================================================================
            // =================================================================
            var collapsedCheckbox   = document.getElementById('demo-nav-coll'),
                navFixedCheckbox    = document.getElementById('demo-nav-fixed'),
                navProfileCheckbox  = document.getElementById('demo-nav-profile'),
                navShortcutCheckbox = document.getElementById('demo-nav-shortcut'),
                navOffcanvasSB      = document.getElementById('demo-nav-offcanvas'),
                navProfile          = $('#mainnav-profile'),
                navShortcut         = $('#mainnav-shortcut');



            // Initialize
            // =================================================================
            if(niftyContainer.hasClass('mainnav-fixed')){//绑定固定导航
                navFixedCheckbox.checked = true;
            }else{
                navFixedCheckbox.checked = false;
            }


//            // Fixed Position
//            // =================================================================
//            if(niftyContainer.hasClass('mainnav-fixed')){//
//                navFixedCheckbox.checked = true;
//            } else{
//                navFixedCheckbox.checked = false;
//            }
            navFixedCheckbox.onchange = function(){//固定导航 - 改变
                if (navFixedCheckbox.checked) {
                	$.niftyNav('fixedPosition');
                    setCustomStatus({"sysCustomSetting.fixed_nav":"1"});//固定导航
                } else {
                    $.niftyNav('staticPosition');
                    setCustomStatus({"sysCustomSetting.fixed_nav":"0"});//不固定导航
                }
            };
            
            
    		
            // Profile  显示头像
            // =================================================================
            if(navProfile.hasClass('hidden')){
                navProfileCheckbox.checked = false;
            } else{
                navProfileCheckbox.checked = true;
            }
            navProfileCheckbox.onchange = function(){
                if (navProfileCheckbox.checked) {
                	navProfile.removeClass("hidden");
                    setCustomStatus({"sysCustomSetting.show_user_img":"1"});//显示头像
                } else {
                	navProfile.addClass("hidden");
                    setCustomStatus({"sysCustomSetting.show_user_img":"0"});//不显示头像
                }
            };



            // Shortcut Buttons  快捷按钮
            // =================================================================
            if(navShortcut.hasClass('hidden')){
                navShortcutCheckbox.checked = false;
            } else{
                navShortcutCheckbox.checked = true;
            }
            navShortcutCheckbox.onchange = function(){
                if (navShortcutCheckbox.checked) {
                	navShortcut.removeClass("hidden");
                    setCustomStatus({"sysCustomSetting.show_short_cut":"1"});//显示快捷按钮
                } else {
                	navShortcut.addClass("hidden");
                    setCustomStatus({"sysCustomSetting.show_short_cut":"0"});//不显示快捷按钮
                }
            };



            // Collapsing/Expanding Navigation   -- 折叠模式
            // =================================================================
            if(niftyContainer.hasClass('mainnav-sm')){
                collapsedCheckbox.checked = true;
            }else{
                collapsedCheckbox.checked = false;
            }
            collapsedCheckbox.onchange = function(){
                if (collapsedCheckbox.checked) {
                    if(navOffcanvasSB.value != 'none'){
                        navOffcanvasSB.value = 'none';
                        location.href = location.protocol + '//' + location.host + location.pathname;//重置回没有特效的状态
                    }
                    $.niftyNav('collapse');
                    setCustomStatus({"sysCustomSetting.nav_coll":"1","sysCustomSetting.off_type":"none"});//开启折叠模式,去除打开方式
                } else {
                    $.niftyNav('expand');
                    setCustomStatus({"sysCustomSetting.nav_coll":"0"});//关闭折叠模式
                }
            };



            // Offcanvas Navigation   --导航打开模式
            // =================================================================
            navOffcanvasSB.onchange = function(){
                if (collapsedCheckbox.checked) { //选中折叠模式  ,  打开方式不可用
                    collapsedCheckbox.checked = false;
                }
                setCustomStatus({"sysCustomSetting.nav_coll":"0","sysCustomSetting.off_type":this.options[this.selectedIndex].value});//设置打开方式 , 关闭折叠模式
                demoSetBody.removeClass('open');
                location.href = location.protocol + '//' + location.host + location.pathname + '?&offcanvas=' + this.options[this.selectedIndex].value;
            };

            var nav_mode = function(){
                var query = window.location.search.substring(1);
                var vars = query.split("&");
                for (var i=0;i<vars.length;i++) {
                       var pair = vars[i].split("=");
                       if(pair[0] == "offcanvas"){return pair[1];}
                }
                return(false);
            }();
            if(nav_mode == "push" || nav_mode == "slide" ||nav_mode == "reveal"){
                $('.mainnav-toggle').removeClass('push slide reveal').addClass(nav_mode);

                navOffcanvasSB.value = nav_mode;
            }else{
                if(niftyContainer.hasClass('mainnav-sm')){
                    collapsedCheckbox.checked = true;
                }else{
                    collapsedCheckbox.checked = false;
                }
            }







            // ASIDE  -- 侧边栏
            // =================================================================
            // =================================================================
            var asdVisCheckbox      = document.getElementById('demo-asd-vis'),
                asdFixedCheckbox    = document.getElementById('demo-asd-fixed'),
                asdFloatCheckbox    = document.getElementById('demo-asd-float'),
                asdPosCheckbox      = document.getElementById('demo-asd-align'),
                asdThemeCheckbox    = document.getElementById('demo-asd-themes');





            // Visible
            // =================================================================
            if(niftyContainer.hasClass('aside-in')){//绑定
                asdVisCheckbox.checked = true;
            } else{
                asdVisCheckbox.checked = false;
            }
            asdVisCheckbox.onchange = function() {
                if (asdVisCheckbox.checked) {
                    $.niftyAside('show');
                    setCustomStatus({"sysCustomSetting.show_asd":"1"});//显示侧边栏
                } else {
                    $.niftyAside('hide');
                    setCustomStatus({"sysCustomSetting.show_asd":"0"});//不显示侧边栏
                }
            };



            // Fixed Position  --固定侧边栏
            // =================================================================
            if(niftyContainer.hasClass('aside-fixed')){//绑定
                asdFixedCheckbox.checked = true;
            } else{
                asdFixedCheckbox.checked = false;
            }
            asdFixedCheckbox.onchange = function() {
                if (asdFixedCheckbox.checked) {
                    $.niftyAside('fixedPosition');
                    setCustomStatus({"sysCustomSetting.fixed_asd":"1"});//固定侧边栏
                } else {
                    $.niftyAside('staticPosition');
                    setCustomStatus({"sysCustomSetting.fixed_asd":"0"});//不固定侧边栏
                };
            };


            // Floating Aside -- 侧边栏浮动
            // =================================================================
            if(niftyContainer.hasClass('aside-float')){//绑定
                asdFloatCheckbox.checked = true;
            } else{
                asdFloatCheckbox.checked = false;
            }
            asdFloatCheckbox.onchange = function() {
                if (asdFloatCheckbox.checked) {
                    niftyContainer.addClass('aside-float');
                    setCustomStatus({"sysCustomSetting.float_asd":"1"});//侧边栏浮动
                } else {
                    niftyContainer.removeClass('aside-float');
                    setCustomStatus({"sysCustomSetting.float_asd":"0"});//侧边栏不浮动
                };
                $(window).trigger('resize');
            };


            // Align --左侧显示侧边栏
            // =================================================================
            if(niftyContainer.hasClass('aside-left')){//绑定
                asdPosCheckbox.checked = true;
            } else{
                asdPosCheckbox.checked = false;
            }
            asdPosCheckbox.onchange = function() {
                if (asdPosCheckbox.checked) {
                    $.niftyAside('alignLeft');
                    setCustomStatus({"sysCustomSetting.left_asd":"1"});//左侧显示侧边栏
                } else {
                    $.niftyAside('alignRight');
                    setCustomStatus({"sysCustomSetting.left_asd":"0"});//右侧显示侧边栏
                };
            };


            // Themes --黑色侧边栏
            // =================================================================
            if(niftyContainer.hasClass('aside-bright')){//绑定
                asdThemeCheckbox.checked = false;
            } else{
                asdThemeCheckbox.checked = true;
            }
            asdThemeCheckbox.onchange = function() {
                if (asdThemeCheckbox.checked) {
                    $.niftyAside('darkTheme');
                    setCustomStatus({"sysCustomSetting.dark_asd":"1"});//黑色侧边栏
                } else {
                    $.niftyAside('brightTheme');
                    setCustomStatus({"sysCustomSetting.dark_asd":"0"});//白色侧边栏
                };
            };



            // COLOR SCHEMES  --颜色主题
            // =================================================================
            var themeBtn = $('.demo-theme'),
            changeTheme = function (themeName, type) {
                var themeCSS = $('#theme'),
                    fileext = '.min.css',
                    filename = ctx+'/common/css/themes/type-' + type + '/' + themeName + fileext;
                	savepath = '/common/css/themes/type-' + type + '/' + themeName + fileext;
                	setCustomStatus({"sysCustomSetting.color_theme":savepath});//白色侧边栏
                if (themeCSS.length) {
                    themeCSS.prop('href', filename);
                } else {
                    themeCSS = '<link id="theme" href="' + filename + '" rel="stylesheet">';
                    $('head').append(themeCSS);
                }
            };

            $('#demo-theme').on('click', '.demo-theme', function (e) {
                e.preventDefault();
                var el = $(this);
                if (el.hasClass('disabled')) {
                    return false;
                }
                changeTheme(el.attr('data-theme'), el.attr('data-type'));
                themeBtn.removeClass('disabled');
                el.addClass('disabled');
                return false;
            });
        }
        var nav_mode = function(){
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i=0;i<vars.length;i++) {
                   var pair = vars[i].split("=");
                   if(pair[0] == "offcanvas"){return pair[1];}
            }
            return(false);
        }();
        if(nav_mode == "push" || nav_mode == "slide" ||nav_mode == "reveal"){
            $('.mainnav-toggle').removeClass('push slide reveal').addClass(nav_mode);
            niftyContainer.removeClass('mainnav-lg mainnav-sm').addClass('mainnav-out '+nav_mode);
        }
        var demoSetBody = $('#demo-set-body'), demoSetBtn = $('#demo-set-btn');
        $('html').on('click', function (e) {
            if (demoSetBody.hasClass('in')) {
                if (!$(e.target).closest('#demo-set').length) {
                    demoSetBtn.trigger('click')
                }
            }
        });

        demoSetBtn.one('click', InitializeSettingWindow);
        $('#demo-btn-close-settings').on('click', function () {
            demoSetBtn.trigger('click')
        });
    };
});


//保存参数
function setCustomStatus(data){
	common_ajax.ajaxFunc("/admin/customsetting/saveCustomSetting",data,"json",null);
}