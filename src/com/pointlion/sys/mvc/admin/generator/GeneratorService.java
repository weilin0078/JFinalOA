package com.pointlion.sys.mvc.admin.generator;

import java.util.List;

import javax.sql.DataSource;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.DataDictionaryGenerator;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;

public class GeneratorService {

    
    public static final GeneratorService me  = new GeneratorService();
    protected final Enjoy jfEngine    = new Enjoy();
    
    protected Kv tablemetaMap       = null;
    protected String packageBase    = "com.momathink";
    protected String srcFolder      = "src";
    protected String viewFolder     = "_view";
    protected String basePath       = "";
    
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
    
    /**
     * 生成手脚架代码
     */
    public void allRender(String className, String tableName) {
        me.javaRender(className, tableName);
        me.htmlRender(className, tableName);
    }
    
    /**
     * java 代码生成
     * */
    public void javaRender(String className, String tableName) {
        //刷新 映射对象
//        _JFinalDemoGenerator.main(null);
        controller(className);
        validator(className);
        service(className , tableName);
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
    
    private String toPackages(String className) {
        return new StringBuffer(packageBase).append(".").append(basePath)
                .append(".").append(className.toLowerCase()).toString();
    }
    
    /**
     * 生成Controller
     * @param className         类名称
     */
    public void controller(String className){
        String packages = toPackages(className);
        String classNameSmall = toClassNameCamel(className);
        jfEngine.render("/java/controller.html", 
                Kv.by("package", packages)
                .set("className", className)
                .set("classNameSmall", classNameSmall)
                .set("basePath", basePath )
                , 
                new StringBuilder()
                .append(System.getProperty("user.dir"))
                .append("/")
                .append(srcFolder)
                .append("/")
                .append(packages.replace(".", "/"))
                .append("/")
                .append(className)
                .append("Controller.java")
                );
    }
    
    /**
     * 生成validator
     * @param className         类名称
     */
    public void validator(String className){
        String packages = toPackages(className);
        String classNameSmall = toClassNameCamel(className);
        jfEngine.render("/java/validator.html", 
                Kv.by("package", packages)
                .set("className", className)
                .set("classNameSmall", classNameSmall)
                , 
                new StringBuilder()
                .append(System.getProperty("user.dir"))
                .append("/")
                .append(srcFolder)
                .append("/")
                .append(packages.replace(".", "/"))
                .append("/")
                .append(className)
                .append("Validator.java")
                );
    }
    
    /**
     * 生成Service
     * @param className         类名称
     * @param tableName         表名
     */
    public void service(String className, String tableName){
        String packages = toPackages(className);
        String classNameSmall = toClassNameCamel(className);
        jfEngine.render("/java/service.html", 
                Kv.by("package", packages)
                .set("className", className)
                .set("classNameSmall", classNameSmall)
                .set("tableName", tableName)
                , 
                new StringBuilder()
                .append(System.getProperty("user.dir"))
                .append("/")
                .append(srcFolder)
                .append("/")
                .append(packages.replace(".", "/"))
                .append("/")
                .append(className)
                .append("Service.java")
                );
    }
    
    /**
     * @param className 
     * @param tableName 
     * */
    public void htmlRender(String className, String tableName){
        TableMeta tablemeta = getTableMeta(tableName);
        htmlList(className, tablemeta);
    }
    
    //页面的生成一般定制比较多..就来个简单的吧
    public void htmlList(String className, TableMeta tablemeta){
        String packages = toPackages(className);
        String classNameSmall = toClassNameCamel(className);
        String basePathUrl = basePath.replace('.', '/');
        jfEngine.render("/html/list.html", 
                Kv.by("tablemeta", tablemeta)
                .set("package", packages)
                .set("className", className)
                .set("classNameSmall", classNameSmall)
                .set("basePath", basePathUrl )
                , 
                new StringBuilder()
                .append(PathKit.getWebRootPath())
                .append("/")
                .append(viewFolder)
                .append("/")
                .append(basePathUrl)
                .append("/")
                .append(classNameSmall)
                .append("/")
                .append(classNameSmall)
                .append("List.html")
                );
    }
    
    
    

}
