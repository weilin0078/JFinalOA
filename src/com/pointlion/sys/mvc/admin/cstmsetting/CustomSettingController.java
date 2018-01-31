/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
package com.pointlion.sys.mvc.admin.cstmsetting;

import com.pointlion.sys.mvc.common.base.BaseController;
import com.pointlion.sys.mvc.common.model.SysCustomSetting;
import com.pointlion.sys.mvc.common.utils.UuidUtil;
import com.pointlion.sys.plugin.shiro.ShiroKit;

/***
 * 首页控制器
 */
public class CustomSettingController extends BaseController {
	
	public void saveCustomSetting(){
		String username = ShiroKit.getUsername();
		SysCustomSetting newSet = getModel(SysCustomSetting.class);//传递过来的对象
		SysCustomSetting userSet = SysCustomSetting.dao.getCstmSettingByUsername(username);
		if(userSet==null){
			newSet.setId(UuidUtil.getUUID());
			newSet.setUserId(username);//字段叫user_id，存的是username
			newSet.save();
		}else{
			newSet.setId(userSet.getId());
			newSet.update();
		}
		renderSuccess();
	}
}
