package com.zscat.sys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.zscat.base.BaseEntity;

@Table(name = "sys_resource")
public class SysResource extends BaseEntity{
   

    /**
     * 资源名称
     */
    private String name;

    /**
     * 是否是公共资源(0.不是 1.是)
     */
    private String common;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 父级id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 类型(0.菜单 1.按钮)
     */
    private String type;

    /**
     * 链接
     */
    private String url;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态(0.正常 1.禁用)
     */
    private String status;

    /**
     * 父级集合
     */
    @Column(name = "parent_ids")
    private String parentIds;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "del_flag")
    private String delFlag;

    @Column(name = "permission_str")
    private String permissionStr;

    

    /**
     * 获取资源名称
     *
     * @return name - 资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     *
     * @param name 资源名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取是否是公共资源(0.不是 1.是)
     *
     * @return common - 是否是公共资源(0.不是 1.是)
     */
    public String getCommon() {
        return common;
    }

    /**
     * 设置是否是公共资源(0.不是 1.是)
     *
     * @param common 是否是公共资源(0.不是 1.是)
     */
    public void setCommon(String common) {
        this.common = common;
    }

    /**
     * 获取图标
     *
     * @return icon - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取排序号
     *
     * @return sort - 排序号
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序号
     *
     * @param sort 排序号
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取父级id
     *
     * @return parent_id - 父级id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父级id
     *
     * @param parentId 父级id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取类型(0.菜单 1.按钮)
     *
     * @return type - 类型(0.菜单 1.按钮)
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型(0.菜单 1.按钮)
     *
     * @param type 类型(0.菜单 1.按钮)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取链接
     *
     * @return url - 链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置链接
     *
     * @param url 链接
     */
    public void setUrl(String url) {
        this.url = url;
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

    /**
     * 获取状态(0.正常 1.禁用)
     *
     * @return status - 状态(0.正常 1.禁用)
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态(0.正常 1.禁用)
     *
     * @param status 状态(0.正常 1.禁用)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取父级集合
     *
     * @return parent_ids - 父级集合
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 设置父级集合
     *
     * @param parentIds 父级集合
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return update_by
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * @return del_flag
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * @return permission_str
     */
    public String getPermissionStr() {
        return permissionStr;
    }

    /**
     * @param permissionStr
     */
    public void setPermissionStr(String permissionStr) {
        this.permissionStr = permissionStr;
    }
}