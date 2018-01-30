package com.pointlion.sys.mvc.admin.generator;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
 
import com.jfinal.plugin.activerecord.generator.MappingKitGenerator;
import com.jfinal.plugin.activerecord.generator.TableMeta;
 
public class MappingKitBulid extends MappingKitGenerator{
 
	public MappingKitBulid(String mappingKitPackageName,
			String mappingKitOutputDir) {
		super(mappingKitPackageName, mappingKitOutputDir);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected void genMappingMethod(List<TableMeta> tableMetas, StringBuilder ret) {
		ret.append(String.format(mappingMethodDefineTemplate));
		String add="";
		String addTableName="";
		for (TableMeta tableMeta : tableMetas) {
			boolean isCompositPrimaryKey = tableMeta.primaryKey.contains(",");
			if (isCompositPrimaryKey)
				ret.append(String.format(compositeKeyTemplate, tableMeta.primaryKey));
			add = String.format(mappingMethodContentTemplate, tableMeta.name, tableMeta.primaryKey, tableMeta.modelName);
			addTableName=tableMeta.name;
		}
		readOldMapping(ret,add,addTableName);
		ret.append(String.format("\t}%n"));
	}
	
	//读取之前mapping生成的数据，然后进行追加
	public void readOldMapping(StringBuilder ret,String add,String addTableName){
		String path=mappingKitOutputDir + File.separator + mappingKitClassName + ".java";
		File file=new File(path);
		BufferedReader reader = null; 
		if(file.exists()){
			try {  
	            reader = new BufferedReader(new FileReader(file));  
	            String tempString = null;  
	            boolean start=false;
	            boolean exist=false;
	            // 一次读入一行，直到读入null为文件结束  
	            while ((tempString = reader.readLine()) != null) {  
	                // 显示行号  
	                //System.out.println("line " + line + ": " + tempString);
	            	
	            	if(start && tempString.equals("	}")){
	            		start=false;
	            	}
	            	
	            	if(start && tempString.indexOf("\t\tarp.addMapping")==0 && !tempString.trim().equals("")){
	            		int tempi=tempString.indexOf("\"");
	            		String tableName=tempString.substring(tempi+1,tempString.indexOf("\"",tempi+1));
	            		if(!tableName.equals("") && tableName.equals(addTableName)){
	            			if(!exist){
	            				ret.append(add);
	            			}
	            			exist=true;
	            		}else{
	            			ret.append(tempString);
	            			ret.append("\r\n");
	            		}
	            	}
	            	
	            	if(tempString.equals("	public static void mapping(ActiveRecordPlugin arp) {")){
	            		start=true;
	            	}
	            	
	            }
	            
	            if(!exist){
	            	ret.append(add);
	            }
	            
	            reader.close();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            if (reader != null) {  
	                try {  
	                    reader.close();  
	                } catch (IOException e1) {  
	                }  
	            }  
	        }  
		}
	}
	
 
}