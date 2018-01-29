package com.pointlion.sys.mvc.admin.generator;

import java.util.List;

import javax.sql.DataSource;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.DataDictionaryGenerator;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;

public class GeneratorC {

    
    public static final GeneratorC me  = new GeneratorC();
    public static final GeneratorService service = GeneratorService.me;
    protected final Enjoy enjoy    = new Enjoy();
    
    protected Kv tablemetaMap       = null;
    protected String packageBase    = "com.pointlion.sys.mvc.admin.generator.generated";
    protected String srcFolder      = "src";
    protected final static String workSpacePath = PropKit.get("workSpacepath");//工作空间路径
    
    /************固有属性START******************/
    public void setPackageBase(String packageBase){
        this.packageBase = packageBase;
    }
    
    public void setSrcFolder(String srcFolder){
        this.srcFolder = srcFolder;
    }
    
    /************固有属性END******************/
    
    
    
    /*************脚手架START******************/
    /**
     * 生成手脚架代码
     */
    public void allRender(String className, String tableName) {
        me.javaRender(tableName);
        me.htmlRender(className, tableName);
    }
    
    /**
     * java 代码生成
     * */
    public void javaRender(String tableName) {
        //刷新 映射对象
//        _JFinalDemoGenerator.main(null);
        controller(tableName);
        model(tableName);
    	generatorAllModel();
//        validator(tableName);
//        service(tableName);
    }
    /*************脚手架END******************/
   
    
    /**
     * 生成Controller
     * @param tableName         表名称
     */
    public void controller(String tableName){
    	String className = tableNameToClassName(tableName);
        String packages = toPackages(className);
        Kv kv = new Kv();
        kv.set("package", packages);
        kv.set("className", className);
        kv.set("classNameSmall", toNameSmall(className));
        kv.set("classNameCamel", toNameCamel(className));
        String filePath = workSpacePath+"/"+srcFolder+"/"+packages.replace(".", "/")+"/"+className+"Controller.java";
        enjoy.render("/java/controller.html", kv, filePath);
    }
    /**
     * 生成Model
     * @param tableName         表名称
     */
    public void model(String tableName){
    	String className = tableNameToClassName(tableName);
        String packages = toPackages(className);
        Kv kv = new Kv();
        kv.set("package", packages);
        kv.set("className", className);
        kv.set("classNameSmall", toNameSmall(className));
        kv.set("classNameCamel", toNameCamel(className));
        kv.set("tableName", tableName);
        String filePath = workSpacePath+"/"+srcFolder+"/"+packages.replace(".", "/")+"/"+className+".java";
        enjoy.render("/java/model.html", kv, filePath);
    }
    
    /**
     * 生成validator
     * @param tableName         表名称
     */
    public void validator(String tableName){
    	String className = tableNameToClassName(tableName);
        String packages = toPackages(className);
        Kv kv = new Kv();
        kv.set("package", packages);
        kv.set("className", className);
        kv.set("classNameSmall", toNameSmall(className));
        kv.set("classNameCamel", toNameCamel(className));
        String filePath = workSpacePath+"/"+srcFolder+"/"+packages.replace(".", "/")+"/"+className+"Validator.java";
        enjoy.render("/java/validator.html", kv,filePath);
    }
    
    /**
     * 生成Service
     * @param className         类名称
     * @param tableName         表名
     */
    public void service(String tableName){
    	String className = tableNameToClassName(tableName);
        String packages = toPackages(className);
        Kv kv = new Kv();
        kv.set("package", packages);
        kv.set("className", className);
        kv.set("classNameSmall", toNameSmall(className));
        kv.set("classNameCamel", toNameCamel(className));
        kv.set("tableName", tableName);
        String filePath = workSpacePath+"/"+srcFolder+"/"+packages.replace(".", "/")+"/"+className+"Service.java";
        enjoy.render("/java/service.html", kv, filePath);
    }
    
    /**
     * @param className 
     * @param tableName 
     * */
    public void htmlRender(String className, String tableName){
        TableMeta tablemeta = getTableMeta(tableName);
        htmlList(className, tablemeta);
    }
    public void htmlList(String className, TableMeta tablemeta){
        String packages = toPackages(className);
        String classNameSmall = toNameCamel(className);
        Kv kv = new Kv();
        kv.set("package", packages);
        kv.set("className", className);
        kv.set("classNameSmall", classNameSmall);
        String filePath = System.getProperty("user.dir")+"/"+srcFolder+"/"+packages.replace(".", "/")+className+"Service.java";
        enjoy.render("/html/list.html", kv, filePath);
    }
    
    

    /****************工具类START*************************/
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
    	return packageBase+"."+className.toLowerCase();
    }
    
    
    protected class DataGenerator extends DataDictionaryGenerator {
        public DataGenerator(DataSource dataSource, String dataDictionaryOutputDir) {
            super(dataSource, dataDictionaryOutputDir);
        }
        public void rebuildColumnMetas(List<TableMeta> tableMetas) {
            super.rebuildColumnMetas(tableMetas);
        }
    };    
    public TableMeta getTableMeta(String tableName){
        if( tablemetaMap == null ){
            DataSource dataSource = DbKit.getConfig().getDataSource();
            MetaBuilder metaBuilder = new MetaBuilder(dataSource);
            metaBuilder.setDialect(new MysqlDialect());
            //metaBuilder.addExcludedTable(_JFinalDemoGenerator.excludedTable);
            List<TableMeta> tableMetas = metaBuilder.build();
            new DataGenerator(dataSource, null).rebuildColumnMetas(tableMetas);
            if (tableMetas.size() == 0) {
                System.out.println("TableMeta 数量为 0，不生成任何文件");
                return null;
            }
            Kv kv = Kv.create();
            for (TableMeta tableMeta : tableMetas) {
                kv.set(tableMeta.name, tableMeta);
            }
            tablemetaMap = kv;
        }
        return (TableMeta) tablemetaMap.get(tableName);
    }
    /****************工具类END*************************/

    public void generatorAllModel(){
    	String modelPackageName = packageBase+".model";
    	// base model 所使用的包名
		String baseModelPackageName = modelPackageName+".base";
		// base model 文件保存路径
		String baseModelOutputDir = GeneratorC.workSpacePath + "/src/"+baseModelPackageName.replace(".", "/");
		
		// model 所使用的包名 (MappingKit 默认使用的包名)
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = baseModelOutputDir + "/..";
		
		// 创建生成器
		Generator generator = new Generator(DbKit.getConfig().getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		// 设置是否生成链式 setter 方法
		generator.setGenerateChainSetter(false);
		// 添加不需要生成的表名
		generator.addExcludedTable("v_tasklist");
		generator.addExcludedTable("v_tasklist_candidate");
		generator.addExcludedTable("v_tasklist_candidate_d");
		// 设置是否在 Model 中生成 dao 对象
		generator.setGenerateDaoInModel(true);
		// 设置是否生成链式 setter 方法
		generator.setGenerateChainSetter(false);
		// 设置是否生成字典文件
		generator.setGenerateDataDictionary(false);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
		generator.setRemovedTableNamePrefixes("t_");
		// 生成
		generator.generate();
    }
    
    public static void main(String[] args) {
		System.out.println(me.tableNameToClassName("sys_user"));
	}
}
