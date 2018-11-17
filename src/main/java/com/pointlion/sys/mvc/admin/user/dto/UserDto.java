package com.pointlion.sys.mvc.admin.user.dto;


import com.pointlion.sys.mvc.common.utils.excel.annotation.ExcelField;

public class UserDto {

    @ExcelField(value = "登录名")
    private String username;

    @ExcelField(value = "密码")
    private String password;

    @ExcelField(value = "姓名")
    private String name;

    @ExcelField(value = "电话")
    private String mobile;

    @ExcelField(value = "部门")
    private String deptName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
