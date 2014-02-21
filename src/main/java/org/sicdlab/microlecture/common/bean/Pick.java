package org.sicdlab.microlecture.common.bean;

// Generated 2013-9-27 23:42:58 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Pick generated by hbm2java
 */
@Entity
@Table(name = "pick", catalog = "supper_microlecture")
public class Pick implements java.io.Serializable {

	private String pickId;
	private String content;
	private String author;

	public Pick() {
	}

	public Pick(String pickId) {
		this.pickId = pickId;
	}

	public Pick(String pickId, String content, String author) {
		this.pickId = pickId;
		this.content = content;
		this.author = author;
	}

	@Id
	@Column(name = "PICK_ID", unique = true, nullable = false, length = 32)
	public String getPickId() {
		return this.pickId;
	}

	public void setPickId(String pickId) {
		this.pickId = pickId;
	}

	@Column(name = "CONTENT", length = 200)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "AUTHOR", length = 50)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
