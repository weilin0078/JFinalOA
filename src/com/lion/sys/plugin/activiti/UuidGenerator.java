package com.lion.sys.plugin.activiti;

import org.activiti.engine.impl.cfg.IdGenerator;

import com.lion.sys.tool.UuidUtil;

/**
 * @author Lion
 * @date 2017年1月24日 下午12:02:35
 * @qq 439635374
 */
public class UuidGenerator implements IdGenerator {

	@Override
	public String getNextId() {
		
		return UuidUtil.getUUID();
	}

}
