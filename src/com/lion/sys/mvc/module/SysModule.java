package com.lion.sys.mvc.module;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.lion.sys.dto.LayTreeNode;
import com.lion.sys.mvc.base.model.BaseSysModule;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class SysModule extends BaseSysModule<SysModule> {
	public static final SysModule dao = new SysModule();
	
	/***
	 * 根据主键获取对象
	 * @param id
	 * @return
	 */
	public SysModule getById(String id){
		return SysModule.dao.findById(id);
	}
	/***
	 * 根据id 查询孩子
	 * @param id
	 * @return
	 */
	public List<SysModule> getChildrenByPid(String id){
		return SysModule.dao.find("select * from sys_module m where m.parent_id='"+id+"' order by sort");
	}
	/***
	 * 根据id 查询孩子分页
	 * @param id
	 * @return
	 */
	public Page<SysModule> getChildrenPageByPid(int pnum,int psize, String pid){
		return SysModule.dao.paginate(pnum, psize, "select * ", " from sys_module m where m.parent_id='"+pid+"' order by sort");
	}
	/***
	 * 菜单转成LayTreeNode
	 * @param menu
	 * @return
	 */
	public LayTreeNode toLayTreeNode(SysModule menu){
		LayTreeNode node = new LayTreeNode();
		node.setId(menu.getId());
		node.setName(menu.getName());
//		node.setChildren(children);
		return node;
	}
	/***
	 * 菜单转成LayTreeNode
	 * @param menu
	 * @return
	 */
	public List<LayTreeNode> toLayTreeNode(List<SysModule> menuList,Boolean spread){
		List<LayTreeNode> list = new ArrayList<LayTreeNode>();
		for(SysModule menu : menuList){
			LayTreeNode node = toLayTreeNode(menu);
			node.setChildren(toLayTreeNode(menu.getChildren(),spread));
			node.setSpread(spread);
			list.add(node);
		}
		return list;
	}
	/***
	 * 获取所有菜单
	 * @return
	 */
	public List<SysModule> getAllMenu(){
		List<SysModule> list =  getChildrenAll("#root");
		return list;
	}
	/***
	 * 递归
	 * 查询孩子
	 * @param id
	 * @return
	 */
	public List<SysModule> getChildrenAll(String id){
		List<SysModule> menuList =  getChildrenByPid(id);//根据id查询孩子
		for(SysModule m : menuList){
			System.out.println(m.getName());
			m.setChildren(getChildrenAll(m.getId()));
		}
		return menuList;
	}
}