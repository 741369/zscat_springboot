package com.zscat.sys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.zscat.base.BaseEntity;

@Table(name = "sys_log")
public class SysLog extends BaseEntity{
    

    /**
     * 日志类型
     */
    private String type;

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
     * 操作IP地址
     */
    @Column(name = "remote_addr")
    private String remoteAddr;

    /**
     * 用户代理
     */
    @Column(name = "user_agent")
    private String userAgent;

    /**
     * 请求URI
     */
    @Column(name = "request_uri")
    private String requestUri;

    /**
     * 操作方式
     */
    private String method;

    /**
     * 操作提交的数据
     */
    private String params;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 描述
     */
    private String description;

    

    /**
     * 获取日志类型
     *
     * @return type - 日志类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置日志类型
     *
     * @param type 日志类型
     */
    public void setType(String type) {
        this.type = type;
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
     * 获取操作IP地址
     *
     * @return remote_addr - 操作IP地址
     */
    public String getRemoteAddr() {
        return remoteAddr;
    }

    /**
     * 设置操作IP地址
     *
     * @param remoteAddr 操作IP地址
     */
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    /**
     * 获取用户代理
     *
     * @return user_agent - 用户代理
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * 设置用户代理
     *
     * @param userAgent 用户代理
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 获取请求URI
     *
     * @return request_uri - 请求URI
     */
    public String getRequestUri() {
        return requestUri;
    }

    /**
     * 设置请求URI
     *
     * @param requestUri 请求URI
     */
    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    /**
     * 获取操作方式
     *
     * @return method - 操作方式
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置操作方式
     *
     * @param method 操作方式
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取操作提交的数据
     *
     * @return params - 操作提交的数据
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置操作提交的数据
     *
     * @param params 操作提交的数据
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 获取异常信息
     *
     * @return exception - 异常信息
     */
    public String getException() {
        return exception;
    }

    /**
     * 设置异常信息
     *
     * @param exception 异常信息
     */
    public void setException(String exception) {
        this.exception = exception;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}