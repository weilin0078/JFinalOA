package com.pointlion.mvc.admin.sys.role;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pointlion.mvc.common.model.SysRole;
import com.pointlion.mvc.common.model.SysRoleOrg;
import com.pointlion.mvc.common.utils.UuidUtil;

public class RoleService {
    public static final RoleService me = new RoleService();

    /***
     * 保存数据权限
     */
    @Before(Tx.class)
    public void saveDataScope(String roleId,String scope,String orgListStr){
        SysRole role = SysRole.dao.getById(roleId);
        role.setDataScope(scope);//数据权限类型
        if("4".equals(scope)){//自定义类型保存
            SysRoleOrg.dao.deleteByRoleId(roleId);//删除所有自定义数据权限
            String orgList[] = orgListStr.split(",");
            for(String org :orgList){
                SysRoleOrg roleOrg = new SysRoleOrg();
                roleOrg.setId(UuidUtil.getUUID());
                roleOrg.setOrgId(org);
                roleOrg.setRoleId(roleId);
                roleOrg.save();
            }
        }
        role.update();
    }
}
