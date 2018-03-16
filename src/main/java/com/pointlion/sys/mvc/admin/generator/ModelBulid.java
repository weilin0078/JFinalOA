package com.pointlion.sys.mvc.admin.generator;
 
import javax.sql.DataSource;
 
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
 
public class ModelBulid extends MetaBuilder{
	
	private String createTableName;
 
	public String getCreateTableName() {
		return createTableName;
	}
 
	public void setCreateTableName(String createTableName) {
		this.createTableName = createTableName;
	}
 
	public ModelBulid(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}
	
	public ModelBulid(DataSource dataSource,String createTableName,String dataType) {
		super(dataSource);
		if(dataType.equals("sqlserver")){
			setDialect(new SqlServerDialect());
		}else if(dataType.equals("mysql")){
			setDialect(new MysqlDialect());
		}else if(dataType.equals("oracle")){
			setDialect(new OracleDialect());
		}
		setCreateTableName(createTableName);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	protected boolean isSkipTable(String tableName) {
		if(!tableName.equals(createTableName)){
			return true;
		}
		return false;
	}
}