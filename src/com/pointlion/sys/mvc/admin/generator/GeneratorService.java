package com.pointlion.sys.mvc.admin.generator;

import java.util.List;

import javax.sql.DataSource;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.DataDictionaryGenerator;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;

public class GeneratorService {

    
    public static final GeneratorService me  = new GeneratorService();
    protected final Enjoy enjoy    = new Enjoy();
    
    protected Kv tablemetaMap       = null;
    protected String packageBase    = "com.momathink";
    protected String srcFolder      = "src";
    protected String viewFolder     = "_view";
    protected String basePath       = "";
    
    /************固有属性START******************/
    public void setPackageBase(String packageBase){
        this.packageBase = packageBase;
    }
    
    public void setBasePath(String basePath){
        this.basePath = basePath;
    }
    
    public void setSrcFolder(String srcFolder){
        this.srcFolder = srcFolder;
    }
    
    public void setViewFolder(String viewFolder){
        this.viewFolder = viewFolder;
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
        validator(tableName);
        service(tableName);
    }
    /*************脚手架END******************/
   
    
    /**
     * 生成Controller
     * @param className         类名称
     */
    public void controller(String className){
        String packages = toPackages(className);
        String classNameSmall = toClassNameCamel(className);
        Kv kv = new Kv();
        kv.set("package", packages);
        kv.set("className", className);
        kv.set("classNameSmall", classNameSmall);
        kv.set("basePath", basePath );
        String filePath = System.getProperty("user.dir")+"/"+srcFolder+"/"+packages.replace(".", "/")+className+"Controller.java";
        enjoy.render("/java/controller.html", kv, filePath);
    }
    
    /**
     * 生成validator
     * @param className         类名称
     */
    public void validator(String className){
        String packages = toPackages(className);
        String classNameSmall = toClassNameCamel(className);
        Kv kv = new Kv();
        kv.set("package", packages);
        kv.set("className", className);
        kv.set("classNameSmall", classNameSmall);
        String filePath = System.getProperty("user.dir")+"/"+srcFolder+"/"+packages.replace(".", "/")+className+"Validator.java";
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
        String classNameSmall = toClassNameCamel(className);
        Kv kv = new Kv();
        kv.set("package", packages);
        kv.set("className", className);
        kv.set("classNameSmall", classNameSmall);
        kv.set("tableName", tableName);
        String filePath = System.getProperty("user.dir")+"/"+srcFolder+"/"+packages.replace(".", "/")+className+"Service.java";
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
        String classNameSmall = toClassNameCamel(className);
        String basePathUrl = basePath.replace('.', '/');
        Kv kv = new Kv();
        kv.set("package", packages);
        kv.set("className", className);
        kv.set("classNameSmall", classNameSmall);
        kv.set("basePath", basePathUrl );
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
    private String toClassNameCamel(String className) {
        return new StringBuffer(className.substring(0, 1).toLowerCase()).append(className.substring(1)).toString();
    }
    /***
     * 类名小写
     * @param className
     * @return
     */
    private String toClassNameSmall(String className) {
        return new StringBuffer(className.substring(0, 1).toLowerCase()).append(className.substring(1)).toString();
    }
    /***
     * 转包名
     * @param className
     * @return
     */
    private String toPackages(String className) {
        return new StringBuffer(packageBase).append(".").append(basePath)
                .append(".").append(className.toLowerCase()).toString();
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
    
    
    public static void main(String[] args) {
		System.out.println(me.tableNameToClassName("sys_user"));
	}
}
