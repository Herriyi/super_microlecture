package org.sicdlab.microlecture.common.bean;

// Generated 2013-9-27 23:42:58 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SensitiveWords generated by hbm2java
 */
@Entity
@Table(name = "sensitive_words", catalog = "supper_microlecture")
public class SensitiveWords implements java.io.Serializable {

	private String sensitiveWordsId;
	private String sensitiveWord;
	private String replaceWord;

	public SensitiveWords() {
	}

	public SensitiveWords(String sensitiveWordsId) {
		this.sensitiveWordsId = sensitiveWordsId;
	}

	public SensitiveWords(String sensitiveWordsId, String sensitiveWord,
			String replaceWord) {
		this.sensitiveWordsId = sensitiveWordsId;
		this.sensitiveWord = sensitiveWord;
		this.replaceWord = replaceWord;
	}

	@Id
	@Column(name = "SENSITIVE_WORDS_ID", unique = true, nullable = false, length = 32)
	public String getSensitiveWordsId() {
		return this.sensitiveWordsId;
	}

	public void setSensitiveWordsId(String sensitiveWordsId) {
		this.sensitiveWordsId = sensitiveWordsId;
	}

	@Column(name = "SENSITIVE_WORD", length = 50)
	public String getSensitiveWord() {
		return this.sensitiveWord;
	}

	public void setSensitiveWord(String sensitiveWord) {
		this.sensitiveWord = sensitiveWord;
	}

	@Column(name = "REPLACE_WORD", length = 50)
	public String getReplaceWord() {
		return this.replaceWord;
	}

	public void setReplaceWord(String replaceWord) {
		this.replaceWord = replaceWord;
	}

}