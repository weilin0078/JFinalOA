package com.lion.sys.plugin.activiti;

import org.activiti.engine.impl.cfg.IdGenerator;

import com.lion.sys.tool.UuidUtil;


public class UuidGenerator implements IdGenerator {

	@Override
	public String getNextId() {
		
		return UuidUtil.getUUID();
	}

}
