package org.sicdlab.microlecture.common.bean;

// Generated 2013-9-27 23:42:58 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * NoticeTemplate generated by hbm2java
 */
@Entity
@Table(name = "notice_template", catalog = "supper_microlecture")
public class NoticeTemplate implements java.io.Serializable {

	private String templateId;
	private String templateType;
	private String templateContent;
	private String templateTitle;

	public NoticeTemplate() {
	}

	public NoticeTemplate(String templateId) {
		this.templateId = templateId;
	}

	public NoticeTemplate(String templateId, String templateType,
			String templateContent, String templateTitle) {
		this.templateId = templateId;
		this.templateType = templateType;
		this.templateContent = templateContent;
		this.templateTitle = templateTitle;
	}

	@Id
	@Column(name = "TEMPLATE_ID", unique = true, nullable = false, length = 32)
	public String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Column(name = "TEMPLATE_TYPE", length = 50)
	public String getTemplateType() {
		return this.templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	@Column(name = "TEMPLATE_CONTENT", length = 65535)
	public String getTemplateContent() {
		return this.templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	@Column(name = "TEMPLATE_TITLE", length = 50)
	public String getTemplateTitle() {
		return this.templateTitle;
	}

	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

}
