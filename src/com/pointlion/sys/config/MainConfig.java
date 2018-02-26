/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.pointlion.sys.handler.GlobalHandler;
import com.pointlion.sys.interceptor.IfLoginInterceptor;
import com.pointlion.sys.mvc.admin.bumph.BumphController;
import com.pointlion.sys.mvc.admin.chat.ChatController;
import com.pointlion.sys.mvc.admin.cstmsetting.CustomSettingController;
import com.pointlion.sys.mvc.admin.generator.GeneratorController;
import com.pointlion.sys.mvc.admin.home.HomeController;
import com.pointlion.sys.mvc.admin.login.LoginController;
import com.pointlion.sys.mvc.admin.menu.MenuController;
import com.pointlion.sys.mvc.admin.notice.NoticeController;
import com.pointlion.sys.mvc.admin.org.OrgController;
import com.pointlion.sys.mvc.admin.resget.OaResGetController;
import com.pointlion.sys.mvc.admin.role.RoleController;
import com.pointlion.sys.mvc.admin.user.UserController;
import com.pointlion.sys.mvc.admin.workflow.WorkFlowController;
import com.pointlion.sys.mvc.admin.workflow.main.StencilsetRestResource;
import com.pointlion.sys.mvc.admin.workflow.model.ModelController;
import com.pointlion.sys.mvc.admin.workflow.model.ModelEditorJsonRestResource;
import com.pointlion.sys.mvc.admin.workflow.model.ModelSaveRestResource;
import com.pointlion.sys.mvc.admin.workflow.rest.ProcessDefinitionDiagramLayoutResource;
import com.pointlion.sys.mvc.admin.workflow.rest.ProcessInstanceDiagramLayoutResource;
import com.pointlion.sys.mvc.admin.workflow.rest.ProcessInstanceHighlightsResource;
import com.pointlion.sys.mvc.common.model._MappingKit;
import com.pointlion.sys.mvc.mobile.bumph.MobileBumphController;
import com.pointlion.sys.mvc.mobile.login.MobileLoginController;
import com.pointlion.sys.mvc.mobile.notice.MobileNoticeController;
import com.pointlion.sys.mvc.mobile.user.MobileUserController;
import com.pointlion.sys.plugin.activiti.ActivitiPlugin;
import com.pointlion.sys.plugin.shiro.ShiroInterceptor;
import com.pointlion.sys.plugin.shiro.ShiroKit;
import com.pointlion.sys.plugin.shiro.ShiroPlugin;

public class MainConfig extends JFinalConfig {
    Routes routes;
    
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
	/**
	 * 配置JFinal常量
	 */
	@Override
	public void configConstant(Constants me) {
		//读取数据库配置文件
		PropKit.use("config.properties");
		//设置当前是否为开发模式
		me.setDevMode(PropKit.getBoolean("devMode"));
		//设置默认上传文件保存路径 getFile等使用
		me.setBaseUploadPath("upload/");
		//设置上传最大限制尺寸
		me.setMaxPostSize(1024*1024*10);
		//获取beetl模版引擎
//		me.setRenderFactory(new BeetlRenderFactory());
		me.setError404View("/error/404.html");
        // 获取GroupTemplate ,可以设置共享变量等操作
//        @SuppressWarnings("unused")
//		GroupTemplate groupTemplate = BeetlRenderFactory.groupTemplate ;
	}

	/**
	 * 配置JFinal插件
	 * 数据库连接池
	 * ORM
	 * 缓存等插件
	 * 自定义插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		//配置数据库连接池插件
		DruidPlugin druidPlugin =new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"));
		//orm映射 配置ActiveRecord插件
		ActiveRecordPlugin arp=new ActiveRecordPlugin(druidPlugin);
		arp.setShowSql(PropKit.getBoolean("devMode"));
		arp.setDialect(new MysqlDialect());
		_MappingKit.mapping(arp);
		ActivitiPlugin acitivitiPlugin = new ActivitiPlugin();
		ShiroPlugin shiroPlugin = new ShiroPlugin(this.routes);
	    shiroPlugin.setLoginUrl("/admin/login");//登陆url：未验证成功跳转
	    shiroPlugin.setSuccessUrl("/admin/index");//登陆成功url：验证成功自动跳转
	    shiroPlugin.setUnauthorizedUrl("/admin/login/needPermission");//授权url：未授权成功自动跳转
	    //添加到插件列表中
	    me.add(druidPlugin);
	    me.add(arp);
	    me.add(acitivitiPlugin);
	    me.add(shiroPlugin);
	}
	
	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		 me.add(new ShiroInterceptor());//shiro拦截器
		 me.add(new IfLoginInterceptor());//判断是否登录拦截器
	}
	
	/**
	 * 配置全局处理器
	 */
	@Override
	public void configHandler(Handlers handler) {
		//log.info("configHandler 全局配置处理器，设置跳过哪些URL不处理");
		handler.add(new UrlSkipHandler("/|/admin/friendchat/(.*?)$|/ca/.*|/se/.*|.*.htm|.*.html|.*.js|.*.css|.*.json|.*.png|.*.gif|.*.jpg|.*.jpeg|.*.bmp|.*.ico|.*.exe|.*.txt|.*.zip|.*.rar|.*.7z|.*.tff|.*.woff", false));
		handler.add(new GlobalHandler());
	}
	

	
	/**
	 * 配置JFinal路由映射
	 */
	@Override
	public void configRoute(Routes me) {
		this.routes = me;//shiro使用
		//系统管理
		me.add("/admin/login", LoginController.class);//登陆
		me.add("/admin/home", HomeController.class);//首页
		me.add("/admin/org", OrgController.class);//用户
		me.add("/admin/chat",ChatController.class);//即时聊天
		me.add("/admin/user", UserController.class);//用户
		me.add("/admin/menu", MenuController.class);//菜单
		me.add("/admin/role",RoleController.class);//执行对象-功能
		me.add("/admin/customsetting",CustomSettingController.class);//执行对象-功能
		me.add("/admin/model",ModelController.class);//工作流-模型
		me.add("/admin/workflow",WorkFlowController.class);//工作流
		me.add("/admin/resget",OaResGetController.class);//物品领用
		//代码生成器
		me.add("/admin/generator",GeneratorController.class);//工作流
		//在线办公
		me.add("/admin/bumph",BumphController.class);//公文管理---内部发文，收文转发
		me.add("/admin/notice",NoticeController.class);//通知公告
		//流程在线编辑器和流程跟踪所用路由
		me.add("/admin/process-instance/highlights",ProcessInstanceHighlightsResource.class);//modeler
		me.add("/admin/process-instance/diagram-layout",ProcessInstanceDiagramLayoutResource.class);//modeler
		me.add("/admin/process-definition/diagram-layout",ProcessDefinitionDiagramLayoutResource.class);//modeler
		me.add("/admin/modelEditor/save",ModelSaveRestResource.class);
		me.add("/admin/editor/stencilset",StencilsetRestResource.class);
		me.add("/admin/modelEditor/json",ModelEditorJsonRestResource.class);
		//手机端接口
		me.add("/mobile/notice",MobileNoticeController.class);
		me.add("/mobile/user",MobileUserController.class);
		me.add("/mobile/bumph",MobileBumphController.class);
		me.add("/mobile/login",MobileLoginController.class);
	}
	
	@Override
	public void configEngine(Engine me) {
		me.addSharedObject("shiro",new ShiroKit());
	}
}
