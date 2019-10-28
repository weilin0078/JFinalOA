package com.pointlion.mvc.admin.sys.dataauth;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pointlion.mvc.admin.sys.dataauth.bean.SysDataAuthRuleBean;
import com.pointlion.mvc.common.model.SysDataAuth;
import com.pointlion.mvc.common.model.SysRole;
import com.pointlion.mvc.common.utils.Constants;
import com.pointlion.mvc.common.utils.JSONUtil;
import com.pointlion.plugin.shiro.ShiroKit;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysDataAuthTranslator {
	/***
	 * 参考博客
	 * http://www.cnblogs.com/leoxie2011/archive/2012/03/20/2408542.html
	 */
	
	public static final SysDataAuthTranslator me = new SysDataAuthTranslator();
	private static final SysDataAuthVariable dataAuthVar = SysDataAuthVariable.me;

	/***
	 * 执行转译
	 * @param tableName
	 */
	public String translator(String tableName){
		String sql = tableName;
		List<SysRole> roleList = SysRole.dao.getAllRoleByUserid(ShiroKit.getUserId());//当前登录用户所有的角色
		List<SysDataAuth> dataAuthList = new ArrayList<SysDataAuth>();//角色对应所有的规则
		for(SysRole r:roleList){//所有角色
			List<SysDataAuth> list =SysDataAuth.dao.getAllRuleByTableAndRoleid(tableName,r.getId());
			dataAuthList.addAll(list);
		}
		if(dataAuthList.size()==0){//如果所有角色都没有合适的规则，查询没有角色时候的规则
			List<SysDataAuth> list =SysDataAuth.dao.getAllRuleByTableAndRoleid(tableName,Constants.SYS_DATA_AUTH_RULE_NOROLE);
			dataAuthList.addAll(list);
		}
		if(dataAuthList.size()>0){//解析所有的数据权限命令
			long count = -1;
			for(SysDataAuth auth:dataAuthList){
				String commandText = auth.getCommandText();
				String sqlWhere = getDataAuthSql(commandText);//解析所有的数据权限命令
				String nowsql = "(select * from "+ tableName + " where " + sqlWhere+")";
				Record r = Db.findFirst("select count(*) count from "+ tableName + " where " + sqlWhere);//查询该角色查出来的条数
				long nowCount = r.getLong("count");//用多的那个角色对应的规则
				if(nowCount>count){
					count = nowCount;
					sql = nowsql;
				}
			}
		}
		return sql;
	}
	/***
	 * 解析sql
	 * @param groupStr
	 * @return
	 */
	private String getDataAuthSql(String groupStr){
		String sql = translatorCommand(groupStr);//转译json命令
		return sql;
	}

	/***
	 * 给出json指令翻译成sql语句
	 * @param groupStr
	 * @return
	 */
	private String translatorCommand(String groupStr){
		String sql = "";
		List<String> sqlList = new ArrayList<String>(); //sql集合
		JSONObject jsonobj = JSONObject.fromObject(groupStr);
		String operation = " "+jsonobj.getString("operation")+" ";//操作
//		if(jsonobj.has("groups")){//解析所有的分组
//			JSONArray groups = jsonobj.getJSONArray("groups");
//			for(int i =0 ;i<groups.size();i++){
//				String groupSql = translatorCommand(groups.getJSONObject(i).toString());
//				sqlList.add("("+groupSql+")");
//			}
//		}
		
		if(jsonobj.has("rules")){//解析所有的规则
			JSONArray rules = jsonobj.getJSONArray("rules");
			for(int i =0 ;i<rules.size();i++){
				String ruleSql = translatorRule(rules.getJSONObject(i).toString()); 
				if(StrKit.notBlank(ruleSql)){
					sqlList.add(ruleSql);
				}
			}
		}
		sql = StringUtils.join(sqlList,operation);
		return sql;
	}
	
	/***
	 * 解析规则
	 * @param ruleStr
	 * @return
	 */
	private String translatorRule(String ruleStr){
		String sql = "";
		SysDataAuthRuleBean rule = JSONUtil.jsonToBean(ruleStr, SysDataAuthRuleBean.class);
		String operation = rule.getOperation();
		String value = dataAuthVar.transRuleVariable(rule);//转译变量
		if(StrKit.notBlank(value)){
			sql = rule.getField()+" "+operation+" "+value+" ";
		}
		return sql;
	}
	
	public static void main(String[] args) {
		SysDataAuthTranslator s = new SysDataAuthTranslator();
//		String s1 = "{    \"groups\": [        {            \"groups\": [                {                    \"operation\": \"or\",                    \"rules\": [                        {                            \"field\": \"CustomerID\",                            \"operation\": \"=\",                            \"value\": \"VINET\",                            \"type\":\"string\"                        },                        {                            \"field\": \"CustomerID\",                            \"operation\": \"=\",                            \"value\": \"TOMSP\",                            \"type\":\"string\"                        }                    ]                }            ],            \"operation\": \"and\",            \"rules\": [                {                    \"field\": \"OrderDate\",                    \"operation\": \"<\",                    \"value\": \"2012-01-01\",                    \"type\":\"string\"                }            ]        }    ],    \"operation\": \"and\",    \"rules\": [        {            \"field\": \"EmployeeID\",            \"operation\": \"=\",            \"value\": 5,            \"type\":\"string\"        }    ]}";
//		{    "operation": "or",    "rules": [        {            "field": "parent_child_company_id",            "operation": "=",            "value": "$CurrentUser_Org_ParentChildCompanyId$"        },        {            "field": "id",            "operation": "=",            "value": "$CurrentUser_Org_id$"        }    ]}
		String s2 = "{    \"operation\": \"or\",    \"rules\": [        {            \"field\": \"parent_child_company_id\",            \"operation\": \"=\",            \"value\": \"$CurrentUser_Org_ParentChildCompanyId$\",            \"type\":\"string\"        },        {            \"field\": \"id\",            \"operation\": \"=\",            \"value\": \"$CurrentUser_Org_id$\",            \"type\":\"string\"        }    ]}";
		String result = s.translatorCommand(s2);
		System.out.println(result);
	}
}
