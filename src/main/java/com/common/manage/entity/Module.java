package com.common.manage.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * @classDescription:模块对象
 * @author:xiayingjie
 * @createTime:2010-7-5
 */
@Entity
@Table(name = "module")
public class Module  extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 2465389918795349942L;
	
	@Column(name="moduleName",nullable=false)
	private String moduleName;//模块名称
	@Column(name="moduleInfo")
	private String moduleInfo;//模块介绍
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime",nullable=false)
	private Date createTime;//注册时间
	
	@OneToMany(
			   cascade= CascadeType.ALL,
			   mappedBy="module",
               fetch = FetchType.LAZY

	)
	@OrderBy("orders")
	private Set<Action> actions = new HashSet<Action>(0);

	public Module(){
		createTime=new Date();
	}
	
	/**
	 * @return the moduleInfo
	 */
	public String getModuleInfo() {
		return moduleInfo;
	}
	/**
	 * @param moduleInfo the moduleInfo to set
	 */
	public void setModuleInfo(String moduleInfo) {
		this.moduleInfo = moduleInfo;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}
	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	/**
	 * @return the actions
	 */
	public Set<Action> getActions() {
		return actions;
	}
	/**
	 * @param actions the actions to set
	 */
	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}

	


}