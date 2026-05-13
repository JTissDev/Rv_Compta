package com.jtissdev_API.features.core.dto;

import com.jtissdev_API.features.PCG.dto.AccountingType;
import com.jtissdev_API.features.PCG.dto.AccountingTypeDetails;
import com.jtissdev_API.features.PCG.dto.SubAccountingType;

/**
 * Represents a SelectionContext DTO.
 *
 * @author jtiss
 * @version 1.0.0
 * @since 0.6
 */
public class SelectionContext {
	private AccountingType type;
	private SubAccountingType subType;
	private AccountingTypeDetails detail;

	public SelectionContext() {
	}
	public SelectionContext(AccountingType type) {
		this.setType(type);
	}

	public SelectionContext(AccountingType type, SubAccountingType subType) {
		this(type);
		this.setSubType(subType);
	}

	public SelectionContext(AccountingType type, SubAccountingType subType, AccountingTypeDetails detail) {
		this(type, subType);
		this.setDetail(detail);
	}

	public AccountingType getType() {
		return this.type;
	}
	public SelectionContext setType(AccountingType type) {
		this.type = type;
		return this;
	}
	public SubAccountingType getSubType() {
		return this.subType;
	}
	public SelectionContext setSubType(SubAccountingType subType) {
		this.subType = subType;
		return this;
	}
	public AccountingTypeDetails getDetail() {
		return this.detail;
	}
	public SelectionContext setDetail(AccountingTypeDetails detail) {
		this.detail = detail;
		return this;
	}
}
