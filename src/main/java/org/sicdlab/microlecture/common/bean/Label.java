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
 * Label generated by hbm2java
 */
@Entity
@Table(name = "label", catalog = "supper_microlecture")
public class Label implements java.io.Serializable {

	private String labelId;
	private String labelName;
	private Integer frequency;
	private Set<LabelObject> labelObjects = new HashSet<LabelObject>(0);

	public Label() {
	}

	public Label(String labelId) {
		this.labelId = labelId;
	}

	public Label(String labelId, String labelName, Integer frequency,
			Set<LabelObject> labelObjects) {
		this.labelId = labelId;
		this.labelName = labelName;
		this.frequency = frequency;
		this.labelObjects = labelObjects;
	}

	@Id
	@Column(name = "LABEL_ID", unique = true, nullable = false, length = 32)
	public String getLabelId() {
		return this.labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	@Column(name = "LABEL_NAME", length = 50)
	public String getLabelName() {
		return this.labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	@Column(name = "FREQUENCY")
	public Integer getFrequency() {
		return this.frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "label")
	public Set<LabelObject> getLabelObjects() {
		return this.labelObjects;
	}

	public void setLabelObjects(Set<LabelObject> labelObjects) {
		this.labelObjects = labelObjects;
	}

}
