package com.pointlion.mvc.admin.oa.workflow.listener;

import com.pointlion.mvc.admin.oa.common.OAConstants;
import com.pointlion.mvc.common.model.SysUser;
import com.pointlion.mvc.common.utils.Constants;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

import java.util.ArrayList;
import java.util.List;

/***
 * 设置办理人
 * 申请人所在部门的，OrgLeader角色的用户办理
 */
public class ApplyerOrgLeaderTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask task) {
        String applyer = String.valueOf(task.getVariable(OAConstants.WORKFLOW_VAR_APPLY_USERNAME));
        //获取所在部门下，拥有OrgLeader角色的用户
        List<SysUser> list = SysUser.dao.getUserListByMyRoleAndMyOrg(Constants.SYS_ROLE_ORGLEADER,applyer);
        List<String> candidate = new ArrayList<String>();
        for(SysUser user:list){
            candidate.add(user.getUsername());
        }
        task.addCandidateUsers(candidate);
    }
}
