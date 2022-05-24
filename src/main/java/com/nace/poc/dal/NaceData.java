package com.nace.poc.dal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "nacedata")
public class NaceData {
	
	private String orderId;

	private int level;

	private String code;

	private String parent;

	private String description;

	private String itemIncludes;

	private String alsoIncludes;

	private String rulings;

	private String excludes;

	private String refToISICRev4;
	
	@Id
	@Column(name = "orderid")
	public String getOrderId() {
		return orderId;
	}

	@Column(name = "level")
	public int getLevel() {
		return level;
	}

	@Column(name = "parent")
	public String getParent() {
		return parent;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	@Column(name = "itemincludes")
	public String getItemIncludes() {
		return itemIncludes;
	}

	@Column(name = "alsoincludes")
	public String getAlsoIncludes() {
		return alsoIncludes;
	}

	@Column(name = "rulings")
	public String getRulings() {
		return rulings;
	}

	@Column(name = "excludes")
	public String getExcludes() {
		return excludes;
	}

	@Column(name = "reftoisicrev4")
	public String getRefToISICRev4() {
		return refToISICRev4;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setItemIncludes(String itemIncludes) {
		this.itemIncludes = itemIncludes;
	}

	public void setAlsoIncludes(String alsoIncludes) {
		this.alsoIncludes = alsoIncludes;
	}

	public void setRulings(String rulings) {
		this.rulings = rulings;
	}

	public void setExcludes(String excludes) {
		this.excludes = excludes;
	}

	public void setRefToISICRev4(String refToISICRev4) {
		this.refToISICRev4 = refToISICRev4;
	}
}
