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

import org.codehaus.jackson.map.annotate.JsonView;

import com.soni.messenger.json.views.Views;

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
	@JsonView(Views.Public.class)
	private Integer commentId;
	
	@Column(name="CONTENT")
	@JsonView(Views.Public.class)
	private String content;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MESSAGE_ID")
	@JsonView(Views.Internal.class)
	private Message message;
}
