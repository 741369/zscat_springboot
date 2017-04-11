package com.zscat.sys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.zscat.base.BaseEntity;

@Table(name = "sys_user")
public class SysUser extends BaseEntity{
  

    /**
     * 归属公司
     */
    @Column(name = "company_id")
    private Long companyId;

    /**
     * 归属部门
     */
    @Column(name = "office_id")
    private Long officeId;

    /**
     * 登录名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 工号
     */
    private String no;

    /**
     * 姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 用户类型(0.普通 1.系统超级管理员)
     */
    @Column(name = "user_type")
    private String userType;

    /**
     * 最后登陆IP
     */
    @Column(name = "login_ip")
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @Column(name = "login_date")
    private Date loginDate;

    /**
     * 创建者
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 更新者
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 删除标记
     */
    @Column(name = "del_flag")
    private String delFlag;

    private String status;

   

    /**
     * 获取归属公司
     *
     * @return company_id - 归属公司
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * 设置归属公司
     *
     * @param companyId 归属公司
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取归属部门
     *
     * @return office_id - 归属部门
     */
    public Long getOfficeId() {
        return officeId;
    }

    /**
     * 设置归属部门
     *
     * @param officeId 归属部门
     */
    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    /**
     * 获取登录名
     *
     * @return username - 登录名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置登录名
     *
     * @param username 登录名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取工号
     *
     * @return no - 工号
     */
    public String getNo() {
        return no;
    }

    /**
     * 设置工号
     *
     * @param no 工号
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取手机
     *
     * @return mobile - 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机
     *
     * @param mobile 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取用户类型(0.普通 1.系统超级管理员)
     *
     * @return user_type - 用户类型(0.普通 1.系统超级管理员)
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 设置用户类型(0.普通 1.系统超级管理员)
     *
     * @param userType 用户类型(0.普通 1.系统超级管理员)
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 获取最后登陆IP
     *
     * @return login_ip - 最后登陆IP
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置最后登陆IP
     *
     * @param loginIp 最后登陆IP
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * 获取最后登陆时间
     *
     * @return login_date - 最后登陆时间
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * 设置最后登陆时间
     *
     * @param loginDate 最后登陆时间
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * 获取创建者
     *
     * @return create_by - 创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建者
     *
     * @param createBy 创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取更新者
     *
     * @return update_by - 更新者
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新者
     *
     * @param updateBy 更新者
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return update_date - 更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置更新时间
     *
     * @param updateDate 更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取备注信息
     *
     * @return remarks - 备注信息
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注信息
     *
     * @param remarks 备注信息
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取删除标记
     *
     * @return del_flag - 删除标记
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除标记
     *
     * @param delFlag 删除标记
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}