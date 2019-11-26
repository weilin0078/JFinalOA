package com.pointlion.mvc.admin.sys.generator;

import java.io.File;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.generator.Generator;

public class DoGenerator {

    
    public static final DoGenerator me  = new DoGenerator();
    public static final GeneratorService service = GeneratorService.me;
    protected final Enjoy enjoy    = new Enjoy();
    
    protected Kv tablemetaMap        = null;
    protected String packageBase     = "com.pointlion.mvc.admin";
//    protected String srcFolder       = "/src";
//    protected String sourceFolder    = "/WebRoot";
    protected String srcFolder       = "/src/main/java/";
    protected String sourceFolder    = "/src/main/webapp";
    protected String pageFolder      = "/WEB-INF/admin/";
    protected String modelPrefixes   = "";//生成model的时候去掉的表名前缀
    protected String packagePrefixes = "Oa";//生成model的时候去掉的ClassName前缀
    protected final static String workSpacePath = PropKit.get("workSpacePath");//工作空间路径
    
    /************固有属性START******************/
    public void setPackageBase(String packageBase){
        this.packageBase = packageBase;
    }
    public void setSrcFolder(String srcFolder){
        this.srcFolder = srcFolder;
    }
	public void setModelPrefixes(String modelPrefixes) {
		this.modelPrefixes = modelPrefixes;
	}
	public void setPageFolder(String pageFolder) {
		this.pageFolder = pageFolder;
	}
	public void setPackagePrefixes(String packagePrefixes) {
		this.packagePrefixes = packagePrefixes;
	}
    /************固有属性END******************/

	/*************脚手架START******************/
    /**
     * 生成手脚架代码
     */
    public String allRender(String tableName,HtmlGenerateBean b) {
        String result = me.javaRender(tableName,b);
        result = result + me.htmlRender(tableName,b);
        return result;
    }
    
    /**
     * java 代码生成
     * service,controller不覆盖
     * */
    public String javaRender(String tableName,HtmlGenerateBean b) {
        String result = "";
        Integer ret = controller(tableName,b);
        if(ret.equals(Enjoy.EXIST)){//已存在
        	result = result + "controller已存在<br/>";
        }else if(ret.equals(Enjoy.SUCCESS)){//成功！
        	result = result + "controller生成成功<br/>";
        }else{//-1:失败
        	result = result + "controller生成失败<br/>";
        }
        ret = service(tableName,b);
        if(ret.equals(Enjoy.EXIST)){//已存在
        	result = result + "service已存在<br/>";
        }else if(ret.equals(Enjoy.SUCCESS)){//成功！
        	result = result + "service生成成功<br/>";
        }else{//-1:失败
        	result = result + "service生成失败<br/>";
        }
        model(tableName);
        result = result + "model生成成功<br/>";
        return result;
    }
    /***
     * html 代码生成
     * @param tableName
     */
    public String htmlRender(String tableName,HtmlGenerateBean b){
    	String result = "";
    	//生成list.html
    	Integer ret = htmlList(tableName,b);
        if(ret.equals(Enjoy.EXIST)){//已存在
        	result = result + "html已存在<br/>";
        }else if(ret.equals(Enjoy.SUCCESS)){//成功！
        	result = result + "html生成成功<br/>";
        }else{//-1:失败
        	result = result + "html生成失败<br/>";
        }
        //生成edit.html
        ret = htmlEdit(tableName,b);
        if(ret.equals(Enjoy.EXIST)){//已存在
        	result = result + "edit已存在<br/>";
        }else if(ret.equals(Enjoy.SUCCESS)){//成功！
        	result = result + "edit生成成功<br/>";
        }else{//-1:失败
        	result = result + "edit生成失败<br/>";
        }
        //生成editForm.html
        ret = htmlEditForm(tableName,b);
        if(ret.equals(Enjoy.EXIST)){//已存在
            result = result + "edit已存在<br/>";
        }else if(ret.equals(Enjoy.SUCCESS)){//成功！
            result = result + "edit生成成功<br/>";
        }else{//-1:失败
            result = result + "edit生成失败<br/>";
        }
        return result;
    }
    /*************脚手架END******************/
   
    
    /**
     * 生成Controller
     * @param tableName         表名称
     */
    public Integer controller(String tableName,HtmlGenerateBean b){
    	String className = tableNameToClassName(tableName);
        String packages = toPackages(className);
        Kv kv = getKv(tableName,b);
        String filePath = workSpacePath+srcFolder+packages.replace(".", File.separator)+File.separator+className+"Controller.java";
        return enjoy.render("/java/controller.html", kv, filePath);
    }
    /**
     * 生成Model
     * @param tableName         表名称
     */
    public void model(String tableName){
    	generatorModel(tableName);
    }
    
    /**
     * 生成Service
     * @param tableName         表名
     * @param b         类名称
     */
    public Integer service(String tableName,HtmlGenerateBean b){
    	String className = tableNameToClassName(tableName);
    	String packages = toPackages(className);
    	Kv kv = getKv(tableName,b);
        String filePath = workSpacePath+srcFolder+"/"+packages.replace(".", "/")+"/"+className+"Service.java";
        return enjoy.render("/java/service.html", kv, filePath);
    }
    
    public Integer htmlList(String tableName,HtmlGenerateBean b){
    	String className = tableNameToClassName(tableName);
        Kv kv = getKv(tableName,b);
        String filePath = workSpacePath+sourceFolder+getPageFilePath(className)+"/list.html";
        return enjoy.render("/html/list.html", kv, filePath);
    }
    
    public Integer htmlEdit(String tableName,HtmlGenerateBean b){
    	String className = tableNameToClassName(tableName);
        Kv kv = getKv(tableName,b);
        String filePath = workSpacePath+sourceFolder+getPageFilePath(className)+"/edit.html";
        return enjoy.render("/html/edit.html", kv, filePath);
    }
    public Integer htmlEditForm(String tableName,HtmlGenerateBean b){
        String className = tableNameToClassName(tableName);
        Kv kv = getKv(tableName,b);
        String filePath = workSpacePath+sourceFolder+getPageFilePath(className)+"/editForm.html";
        return enjoy.render("/html/editForm.html", kv, filePath);
    }

    /****************工具类START*************************/
    private Kv getKv(String tableName,HtmlGenerateBean b){
    	String className = tableNameToClassName(tableName);
    	String packages = toPackages(className);
    	Kv kv = new Kv();
        kv.set("package", packages);
        kv.set("tableName",tableName);
        kv.set("className", className);
        kv.set("classNameSmall", toNameSmall(className));
        kv.set("classNameCamel", toNameCamel(className));
        kv.set("parentPackageName", getParentPackageName(className));
        kv.set("ifShowOnColA",b.getIfShowOnColA().split(","));
        kv.set("ifShowOnColN",b.getIfShowOnColN().split(","));
        kv.set("ifUseForQueryA",b.getIfUserForQueryA().split(","));
        kv.set("ifUseForQueryN",b.getIfUserForQueryN().split(","));
        kv.set("pageFilePath",getPageFilePath(className));
        return kv;
    }
    /***
     * 表名转类名
     * @param tableName
     * @return
     */
    private String tableNameToClassName(String tableName){
    	String arr[] = tableName.split("_");
    	String result = "";
    	for(String s:arr){
    		result = result+s.substring(0, 1).toUpperCase()+s.substring(1).toLowerCase();
    	}
    	return result;
    }
    
    /***
     * 类名转首字母小写
     * @param className
     * @return
     */
    private String toNameCamel(String className) {
        return new StringBuffer(className.substring(0, 1).toLowerCase()).append(className.substring(1)).toString();
    }
    /***
     * 类名小写
     * @param className
     * @return
     */
    private String toNameSmall(String className) {
        return new StringBuffer(className.substring(0, 1).toLowerCase()).append(className.substring(1)).toString();
    }
    /***
     * 转包名
     * @param className
     * @return
     */
    private String toPackages(String className) {
    	return packageBase+"."+getParentPackageName(className);
    }
    /***
     * 获取类名父级包名
     * @param className
     * @return
     */
    private String getParentPackageName(String className){
    	if(className.indexOf(packagePrefixes)==0){
    		return className.replace(packagePrefixes, "").toLowerCase();
    	}else{
    		return className.toLowerCase();
    	}
    }
    
    /***
     * 获取前台文件统一路径
     * @param className
     * @return
     */
    private String getPageFilePath(String className){
    	return pageFolder+getParentPackageName(className);
    }
    /****************工具类END*************************/

    public void generatorModel(String tableName){
    	String pkgBase = "com.pointlion.mvc.common";
    	String modelPackageName = pkgBase+".model";
    	// base model 所使用的包名
		String baseModelPackageName = modelPackageName+".base";
		// base model 文件保存路径
		String baseModelOutputDir = DoGenerator.workSpacePath + srcFolder+"/"+baseModelPackageName.replace(".", "/");
		
		// model 所使用的包名 (MappingKit 默认使用的包名)
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = baseModelOutputDir + "/..";
		
		// 创建生成器
		Generator generator = new Generator(DbKit.getConfig().getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		// 设置是否生成链式 setter 方法
		generator.setGenerateChainSetter(false);
		// 添加不需要生成的表名
		generator.addExcludedTable("sys_user");//用户
		generator.addExcludedTable("sys_role_user");//用户角色
		generator.addExcludedTable("sys_menu");//菜单
		generator.addExcludedTable("sys_role");//角色
		generator.addExcludedTable("sys_role_menu");//角色对应功能权限
		generator.addExcludedTable("sys_org");//组织结构
		generator.addExcludedTable("sys_friend");//用户好友
		generator.addExcludedTable("oa_notice");
		generator.addExcludedTable("oa_notice_user");
		generator.addExcludedTable("oa_bumph");
		generator.addExcludedTable("oa_bumph_org");
		generator.addExcludedTable("oa_bumph_org_user");
		generator.addExcludedTable("act_re_model");//流程模型
		generator.addExcludedTable("act_re_procdef");
		generator.addExcludedTable("sys_custom_setting");//自定义设置
		generator.addExcludedTable("v_tasklist");
		generator.addExcludedTable("v_tasklist_candidate");
		generator.addExcludedTable("v_tasklist_candidate_d");
		// 设置是否在 Model 中生成 dao 对象
		generator.setGenerateDaoInModel(true);
		// 设置是否生成字典文件
		generator.setGenerateDataDictionary(false);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
		generator.setRemovedTableNamePrefixes(modelPrefixes);
		ModelBulid modelBulid=new ModelBulid(DbKit.getConfig().getDataSource(), tableName,PropKit.get("dbType"));
		generator.setMetaBuilder(modelBulid);
		MappingKitBulid mappingKitBulid=new MappingKitBulid(modelPackageName, modelOutputDir);
		mappingKitBulid.setMappingKitClassName("_newMapping_Kit");
		generator.setMappingKitGenerator(mappingKitBulid);
		//加载自定义model模板，模板中的标签不是自己kv里面的属性，参照底层的TableMeta类和model_template.jf(jar包中自带模板)
		generator.setModelTemplate("/template/java/model.html");
		// 生成
		generator.generate();
    }
    

}
