/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.config;

import com.jfinal.config.*;
import com.jfinal.core.Const;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.pointlion.config.routes.*;
import com.pointlion.handler.GlobalHandler;
import com.pointlion.interceptor.ExceptionInterceptor;
import com.pointlion.interceptor.IfLoginInterceptor;
import com.pointlion.interceptor.LogInterceptor;
import com.pointlion.interceptor.WorkFlowHisInterceptor;
import com.pointlion.mvc.admin.oa.common.OAConstants;
import com.pointlion.mvc.admin.oa.workflow.WorkFlowUtil;
import com.pointlion.mvc.common.model._MappingKit;
import com.pointlion.mvc.common.utils.UuidUtil;
import com.pointlion.plugin.flowable.FlowablePlugin;
import com.pointlion.plugin.mail.MailPlugin;
import com.pointlion.plugin.quartz.QuartzPlugin;
import com.pointlion.plugin.shiro.ShiroInterceptor;
import com.pointlion.plugin.shiro.ShiroKit;
import com.pointlion.plugin.shiro.ShiroPlugin;

public class MainConfig extends JFinalConfig {
	protected static final Log LOG = Log.getLog(MainConfig.class);
	public static Routes routes;
	public static Constants constants;
	/**
	 * 配置JFinal常量
	 * 加载顺序！！！！！！！！！  第一
	 */
	@Override
	public void configConstant(Constants me) {
		MainConfig.constants = me;
		//读取数据库配置文件
		PropKit.use("config.properties");
		//设置当前是否为开发模式
		me.setDevMode(PropKit.getBoolean("devMode"));
		//设置默认上传文件保存路径 getFile等使用
		me.setBaseUploadPath("upload/");
		//设置上传最大限制尺寸
		me.setMaxPostSize(100*Const.DEFAULT_MAX_POST_SIZE);
		//获取beetl模版引擎
//		me.setRenderFactory(new BeetlRenderFactory());
//		me.setError404View("/error/404.html");
		// 获取GroupTemplate ,可以设置共享变量等操作
//        @SuppressWarnings("unused")
//		GroupTemplate groupTemplate = BeetlRenderFactory.groupTemplate ;
	}
	
	/**
	 * 配置JFinal插件
	 * 数据库连接池
	 * ORM
	 * 缓存等插件
	 *
	 *
	 * 自定义插件
	 *
	 * 加载顺序！！！！    第三
	 */
	@Override
	public void configPlugin(Plugins me) {
		//配置数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"));
		//orm映射 配置ActiveRecord插件
		ActiveRecordPlugin arp=new ActiveRecordPlugin(druidPlugin);
		arp.setShowSql(PropKit.getBoolean("devMode"));
//		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));//jfinal将sql全部转为小写配置。activiti相关的表字段为大写。如要开启该配置，需要修改，模型管理，流程管理list等页面的列表信息。
		arp.setDialect(new MysqlDialect());
		_MappingKit.mapping(arp);
		FlowablePlugin acitivitiPlugin = new FlowablePlugin();
		ShiroPlugin shiroPlugin = new ShiroPlugin(MainConfig.routes);
		shiroPlugin.setLoginUrl("/admin/login");//登录url：未验证成功跳转
		shiroPlugin.setSuccessUrl("/admin/index");//登录成功url：验证成功自动跳转
		shiroPlugin.setUnauthorizedUrl("/admin/login/needPermission");//授权url：未授权成功自动跳转
		QuartzPlugin quatrZPlugin = new QuartzPlugin();
		MailPlugin mailPlugin = new MailPlugin(PropKit.use("mail.properties").getProperties());
		//添加到插件列表中
		me.add(druidPlugin);//数据库连接池插件
		me.add(arp);//ARP插件
		me.add(acitivitiPlugin);//流程插件
		me.add(shiroPlugin);//权限插件
		me.add(mailPlugin);//邮件发送插件
		me.add(quatrZPlugin);//定时任务插件
		LOG.info("插件启动成功");
	}
	
	/**
	 * 配置全局拦截器
	 *
	 * 加载顺序！！！！    第五
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new ShiroInterceptor());//shiro拦截器
		me.add(new IfLoginInterceptor());//判断是否登录拦截器
		me.add(new WorkFlowHisInterceptor());//流程历史拦截器
		me.add(new LogInterceptor());//通用日志处理拦截器
		me.add(new ExceptionInterceptor());//通用异常处理拦截器
	}
	
	/**
	 * 配置全局处理器
	 *
	 * 加载顺序！！！！    第六
	 */
	@Override
	public void configHandler(Handlers handler) {
		//log.info("configHandler 全局配置处理器，设置跳过哪些URL不处理");
		handler.add(new UrlSkipHandler("/|/admin/friendchat/.*|/ca/.*|/se/.*|.*.htm|.*.html|.*.js|.*.css|.*.json|.*.png|.*.gif|.*.jpg|.*.jpeg|.*.bmp|.*.ico|.*.exe|.*.txt|.*.zip|.*.rar|.*.7z|.*.tff|.*.woff|.*.ttf|.*.map|.*.xml|.*.woff2|.*.pdf", false));
		handler.add(new GlobalHandler());
	}
	
	
	
	/**
	 * 配置JFinal路由映射
	 * 加载顺序！！！！！！！！     第二
	 */
	@Override
	public void configRoute(Routes me) {
		MainConfig.routes = me;//shiro使用
		me.add(new OARoutes());//办公路由
		me.add(new AMSRoutes());//资产路由
		me.add(new SysRoutes());//系统管理路由
		me.add(new GrapRoutes());//爬取网站数据路由
		me.add(new MobileRoutes());//手机端管理路由
	}
	
	/****
	 * 加载顺序！！！！    第四
	 */
	@Override
	public void configEngine(Engine me) {
		me.addSharedObject("shiro",new ShiroKit());//提供给模板能使用Shiro权限校验
		me.addSharedObject("OAConstants", new OAConstants());//提供给模板能引用后台常量类
		me.addSharedObject("WorkFlowUtil", new WorkFlowUtil());//提供给模板使用能流程工具类
		me.addSharedObject("UuidUtil", new UuidUtil());//提供给模板能生成UUID
		//业务通用的工具函数
		me.addSharedFunction("/WEB-INF/admin/sys/template/commonTemplate.html");
		//业务通用的工具函数
		me.addSharedFunction("/WEB-INF/admin/sys/attachment/businessIncludeBtn.html");
		//通用流程相关函数
		me.addSharedFunction("/WEB-INF/admin/oa/workflow/commonFlowModule.html");
	}
	
	/***
	 * 项目启动之后执行
	 */
	@Override
	public void onStart(){
//		GrapDbSourceService service = GrapDbSourceService.me;
//		List<GrapDbSource> allDbsource = GrapDbSource.dao.getAllDbsource();
//		for(GrapDbSource dbs:allDbsource){
//			try{
//				if(service.testConnect(dbs)){
//					System.out.println("*********数据源："+dbs.getName()+"连接成功。！");
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
	}
}
