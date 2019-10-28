package com.pointlion.enums;

/**
 * @Description: BusinessType
 * @Author: liyang
 * @Date: 2019/10/15 0015 16:00
 * @Version 1.0
 */
public enum BusinessType {

    /**
     * 其它
     */
    OTHER(0),

    /**
     * 查询
     */
    QUERY(1),

    /**
     * 新增
     */
    INSERT(2),

    /**
     * 修改
     */
    UPDATE(3),

    /**
     * 删除
     */
    DELETE(4),

    /**
     * 授权
     */
    GRANT(5),

    /**
     * 导出
     */
    EXPORT(6),

    /**
     * 导入
     */
    IMPORT(7),

    /**
     * 办理任务
     */
    FLOW_COMPLETE(8),
    /**
     * 撤回
     */
    FLOW_CALLBACK(9),
    /**
     * 驳回
     */
    FLOW_REJECT(10),
    /**
     * 开启
     */
    OPEN(11),
    /**
     * 登录
     */
    LOGIN(12)
    ;


    private int iNum = 0;

    /***
     * 构造器，必须是私有的~
     * @param iNum
     */
    private BusinessType(int iNum) {
        this.iNum = iNum;
    }

    public int toNumber() {
        return this.iNum;
    }
}
