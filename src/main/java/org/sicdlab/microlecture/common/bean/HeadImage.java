package org.sicdlab.microlecture.common.bean;

// Generated 2013-9-27 23:42:58 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * HeadImage generated by hbm2java
 */
@Entity
@Table(name = "head_image", catalog = "supper_microlecture")
public class HeadImage implements java.io.Serializable {

	private String imageId;
	private String imageLage;
	private String imageMid;
	private String imageSmall;
	private Set<Team> teams = new HashSet<Team>(0);
	private Set<User> users = new HashSet<User>(0);

	public HeadImage() {
	}

	public HeadImage(String imageId) {
		this.imageId = imageId;
	}

	public HeadImage(String imageId, String imageLage, String imageMid,
			String imageSmall, Set<Team> teams, Set<User> users) {
		this.imageId = imageId;
		this.imageLage = imageLage;
		this.imageMid = imageMid;
		this.imageSmall = imageSmall;
		this.teams = teams;
		this.users = users;
	}

	@Id
	@Column(name = "IMAGE_ID", unique = true, nullable = false, length = 32)
	public String getImageId() {
		return this.imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	@Column(name = "IMAGE_LAGE", length = 100)
	public String getImageLage() {
		return this.imageLage;
	}

	public void setImageLage(String imageLage) {
		this.imageLage = imageLage;
	}

	@Column(name = "IMAGE_MID", length = 100)
	public String getImageMid() {
		return this.imageMid;
	}

	public void setImageMid(String imageMid) {
		this.imageMid = imageMid;
	}

	@Column(name = "IMAGE_SMALL", length = 100)
	public String getImageSmall() {
		return this.imageSmall;
	}

	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "headImage")
	public Set<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "headImage")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
