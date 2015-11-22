package com.soni.messenger.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@Getter @Setter @NoArgsConstructor
@Entity 
@Table(name="COMMENTS_T")
public class Comment extends AuditableEntity {

	private static final long serialVersionUID = 8349512201165665470L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COMMENT_ID")
	private Integer commentId;
	
	@Column(name="CONTENT")
	private String content;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MESSAGE_ID")
	private Message message;
}
