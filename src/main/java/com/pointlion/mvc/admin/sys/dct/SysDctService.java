package com.pointlion.mvc.admin.sys.dct;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.dto.ZtreeNode;
import com.pointlion.mvc.common.model.SysDct;
import com.pointlion.mvc.common.model.SysDctGroup;

public class SysDctService{
	public static final SysDctService me = new SysDctService();
	public static final String TABLE_NAME = SysDct.tableName;
	public static final String GROUP_TABLE_NAME = SysDctGroup.tableName;
	
	/***
	 * 根据主键查询
	 */
	public SysDct getById(String id){
		return SysDct.dao.findById(id);
	}
	
	/***
	 * 获取分页
	 */
	public Page<Record> getPage(int pnum,int psize,String groupId){
		String sql  = " from "+TABLE_NAME+" d where 1=1";
		if(StrKit.notBlank(groupId)){
			sql = sql + " and d.group_id='"+groupId+"'";
		}
		sql = sql + " order by d.key";
		return Db.paginate(pnum, psize, " select * ", sql);
	}
	
	/***
	 * 删除
	 * @param ids
	 */
	@Before(Tx.class)
	public void deleteByIds(String ids){
    	String idarr[] = ids.split(",");
    	for(String id : idarr){
    		SysDct o = me.getById(id);
    		o.delete();
    	}
	}
	
	/***
	 * 递归
	 * 查询孩子
	 * @param id
	 * @return
	 */
	public List<SysDctGroup> getChildrenAllTree(String id){
		List<SysDctGroup> list =  getChildrenByPid(id);//根据id查询孩子
		for(SysDctGroup o : list){
			System.out.println(o.getName());
			o.setChildren(getChildrenAllTree(o.getId()));
		}
		return list;
	}
	
	/***
	 * 根据id 查询孩子
	 * @param id
	 * @return
	 */
	public List<SysDctGroup> getChildrenByPid(String id){
		return SysDctGroup.dao.find("select * from "+GROUP_TABLE_NAME+" m where m.parent_id='"+id+"' order by m.sort");
	}
	
	/***
	 * 转成ZTreeNode
	 * @param 
	 * olist 数据
	 * open  是否展开所有
	 * ifOnlyLeaf 是否只选叶子
	 * @return
	 */
	public List<ZtreeNode> toZTreeNode(List<SysDctGroup> olist,Boolean open,Boolean ifOnlyLeaf){
		List<ZtreeNode> list = new ArrayList<ZtreeNode>();
		for(SysDctGroup o : olist){
			ZtreeNode node = toZTreeNode(o);
			if(o.getChildren()!=null&&o.getChildren().size()>0){//如果有孩子
				node.setChildren(toZTreeNode(o.getChildren(),open,ifOnlyLeaf));
				if(ifOnlyLeaf){//如果只选叶子
					node.setNocheck(true);
				}
			}
			if("1".equals(node.getType())){
				node.setOpen(true);
			}else{
				node.setOpen(open);
			}
			list.add(node);
		}
		return list;
	}
	/***
	 * 菜单转成ZtreeNode
	 * @param 
	 * @return
	 */
	public ZtreeNode toZTreeNode(SysDctGroup group){
		ZtreeNode node = new ZtreeNode();
		node.setId(group.getId());
		node.setName(group.getName());
		return node;
	}
}