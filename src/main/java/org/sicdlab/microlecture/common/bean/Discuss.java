package org.sicdlab.microlecture.common.bean;

// Generated 2013-9-27 23:42:58 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Discuss generated by hbm2java
 */
@Entity
@Table(name = "discuss", catalog = "supper_microlecture")
public class Discuss implements java.io.Serializable {

	private String discussId;
	private User user;
	private Team team;
	private Date publishDate;
	private Integer scanNum;
	private Integer top;
	private String essence;
	private String discussIntor;
	private String topic;

	public Discuss() {
	}

	public Discuss(String discussId) {
		this.discussId = discussId;
	}

	public Discuss(String discussId, User user, Team team, Date publishDate,
			Integer scanNum, Integer top, String essence, String discussIntor,
			String topic) {
		this.discussId = discussId;
		this.user = user;
		this.team = team;
		this.publishDate = publishDate;
		this.scanNum = scanNum;
		this.top = top;
		this.essence = essence;
		this.discussIntor = discussIntor;
		this.topic = topic;
	}

	@Id
	@Column(name = "DISCUSS_ID", unique = true, nullable = false, length = 32)
	public String getDiscussId() {
		return this.discussId;
	}

	public void setDiscussId(String discussId) {
		this.discussId = discussId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_ID")
	public Team getTeam() {
		return this.team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PUBLISH_DATE", length = 19)
	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	@Column(name = "SCAN_NUM")
	public Integer getScanNum() {
		return this.scanNum;
	}

	public void setScanNum(Integer scanNum) {
		this.scanNum = scanNum;
	}

	@Column(name = "TOP")
	public Integer getTop() {
		return this.top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	@Column(name = "ESSENCE", length = 50)
	public String getEssence() {
		return this.essence;
	}

	public void setEssence(String essence) {
		this.essence = essence;
	}

	@Column(name = "DISCUSS_INTOR", length = 200)
	public String getDiscussIntor() {
		return this.discussIntor;
	}

	public void setDiscussIntor(String discussIntor) {
		this.discussIntor = discussIntor;
	}

	@Column(name = "TOPIC", length = 50)
	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
