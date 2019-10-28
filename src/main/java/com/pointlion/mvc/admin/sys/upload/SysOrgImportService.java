package com.pointlion.mvc.admin.sys.upload;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.pointlion.mvc.common.model.SysOrg;
import com.pointlion.mvc.common.utils.UuidUtil;

public class SysOrgImportService {
	public static final SysOrgImportService me = new SysOrgImportService();
	public static void main(String[] args) throws FileNotFoundException, IOException {
	}
	
	public void importOrg(List<List<String>> list){
		long firstSort = 1;
		long secondSort = 1;
		long thirdSort = 1;
		long fourSort = 1;
		for(List<String> rows:list){
				String first = rows.get(0).trim();//1级机构
				String second = rows.get(1).trim();//2级机构
				String third = rows.get(2).trim();//3级机构
				String four = rows.get(3).trim();//4级机构
				SysOrg firstOrg = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+first+"'");//一级肯定不会有重复的
				if(firstOrg==null&&StrKit.notBlank(first)){//1级没有，录入1级录入1级
					firstOrg = new SysOrg();
					firstOrg.setId(UuidUtil.getUUID());
					firstOrg.setName(first);
					firstOrg.setType("1");//子公司
					firstOrg.setDescription(first);
					firstOrg.setParentId("#root");//父级
					firstOrg.setSort(firstSort);;
					firstOrg.save();
					firstSort = firstSort + 1;
				}
				SysOrg secondOrg = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+second+"' and o.parent_id='"+firstOrg.getId()+"' ");//一级肯定不会有重复的
				if(secondOrg==null&&StrKit.notBlank(first)&&StrKit.notBlank(second)){//2级没有录入2级
					secondOrg = new SysOrg();
					secondOrg.setId(UuidUtil.getUUID());
					secondOrg.setName(second);
					secondOrg.setType("0");//子公司
					secondOrg.setDescription(second);
					secondOrg.setParentId(firstOrg.getId());//父级
					secondOrg.setParentChildCompanyId(firstOrg.getId());//所属子公司id
					secondOrg.setSort(secondSort);
					secondOrg.save();
					secondSort = secondSort + 1;
				}
				SysOrg thirdOrg = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+third+"' and o.parent_id='"+secondOrg.getId()+"' ");//一级肯定不会有重复的
				if(thirdOrg==null&&StrKit.notBlank(second)&&StrKit.notBlank(third)){//录入3级
					thirdOrg = new SysOrg();
					thirdOrg.setId(UuidUtil.getUUID());
					thirdOrg.setName(third);
					thirdOrg.setType("0");//子公司
					thirdOrg.setDescription(third);
					thirdOrg.setParentId(secondOrg.getId());//父级
					thirdOrg.setParentChildCompanyId(firstOrg.getId());//所属子公司id
					thirdOrg.setSort(thirdSort);
					thirdOrg.save();
					thirdSort = thirdSort + 1;
				}
				if(StrKit.notBlank(third)&&StrKit.notBlank(four)){
					SysOrg fourOrg = SysOrg.dao.findFirst("select * from sys_org o where o.name='"+four+"' and o.parent_id='"+thirdOrg.getId()+"' ");//一级肯定不会有重复的
					if(fourOrg==null){//录入4级
						fourOrg = new SysOrg();
						fourOrg.setId(UuidUtil.getUUID());
						fourOrg.setName(four);
						fourOrg.setType("0");//子公司
						fourOrg.setDescription(four);
						fourOrg.setParentId(thirdOrg.getId());//父级
						fourOrg.setParentChildCompanyId(firstOrg.getId());//所属子公司id
						fourOrg.setSort(fourSort);
						fourOrg.save();
						fourSort = fourSort + 1;
					}
				}
				
		}
	}
}
